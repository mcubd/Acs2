package com.a.acs2;


import android.content.Context;

import androidx.annotation.Nullable;

import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.database.DatabaseProvider;
import androidx.media3.database.StandaloneDatabaseProvider;
import androidx.media3.datasource.AesCipherDataSource;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.cache.Cache;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.datasource.cache.NoOpCacheEvictor;
import androidx.media3.datasource.cache.SimpleCache;
import androidx.media3.datasource.cronet.CronetDataSource;
import androidx.media3.datasource.cronet.CronetUtil;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.RenderersFactory;
import androidx.media3.exoplayer.offline.DownloadManager;
import androidx.media3.exoplayer.offline.DownloadNotificationHelper;
import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import org.chromium.net.CronetEngine;

/** Utility methods for the demo app. */
@UnstableApi
public final class DemoUtil {

    public static final String DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_channel";

    /**
     * Whether the demo application uses Cronet for networking. Note that Cronet does not provide
     * automatic support for cookies (https://github.com/google/ExoPlayer/issues/5975).
     *
     * <p>If set to false, the platform's default network stack is used with a {@link CookieManager}
     * configured in {@link #getHttpDataSourceFactory}.
     */
    private static final boolean USE_CRONET_FOR_NETWORKING = true;

    private static final String TAG = "DemoUtil";
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";

    private static DataSource.Factory dataSourceFactory;

    private static DataSource.Factory cacheAesDataSourceFactory;

    private static DataSource.Factory cacheAndHttpAesDataSourceFactory;
    private static DataSource.Factory httpDataSourceFactory;
    private static DatabaseProvider databaseProvider;
    private static  File downloadDirectory;
    private static Cache downloadCache;
    private static DownloadManager downloadManager;

    private static DownloadNotificationHelper downloadNotificationHelper;

    private static MediaItem lastDownloadedMediaItem; // Add this variable

    /** XOR encryption key */
    private static final byte[] XOR_ENCRYPTION_KEY = "fires".getBytes();


    static String aesKeyString = "1234567890abcdef";
   static   byte[] aesKey = aesKeyString.getBytes(StandardCharsets.UTF_8);



    /** Returns whether extension renderers should be used. */

    public static RenderersFactory buildRenderersFactory(Context context) {
        return new DefaultRenderersFactory(context.getApplicationContext())
                .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF);
    }

    public static synchronized DataSource.Factory getHttpDataSourceFactory(Context context) {
        if (httpDataSourceFactory == null) {
            if (USE_CRONET_FOR_NETWORKING) {
                context = context.getApplicationContext();
                @Nullable CronetEngine cronetEngine = CronetUtil.buildCronetEngine(context);
                if (cronetEngine != null) {
                    httpDataSourceFactory =
                            new CronetDataSource.Factory(cronetEngine, Executors.newSingleThreadExecutor());
                }
            }
            if (httpDataSourceFactory == null) {
                // We don't want to use Cronet, or we failed to instantiate a CronetEngine.
                CookieManager cookieManager = new CookieManager();
                cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
                CookieHandler.setDefault(cookieManager);
                httpDataSourceFactory = new DefaultHttpDataSource.Factory();
            }
        }
        return httpDataSourceFactory;
    }

    /** Returns a {@link DataSource.Factory}. */
    public static synchronized DataSource.Factory getDataSourceFactory(Context context) {
        if (dataSourceFactory == null) {
            context = context.getApplicationContext();
            DefaultDataSource.Factory upstreamFactory =
                    new DefaultDataSource.Factory(context, getHttpDataSourceFactory(context));
            dataSourceFactory = buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache(context));
        }
        return dataSourceFactory;
    }





    public static synchronized DataSource.Factory getCacheNHttpDataSourceFactory(Context context) {
        if (dataSourceFactory == null) {
            context = context.getApplicationContext();
            DefaultDataSource.Factory upstreamFactory =
                    new DefaultDataSource.Factory(context, getHttpDataSourceFactory(context));
            dataSourceFactory = buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache(context));
        }
        return dataSourceFactory;
    }


//    public static synchronized DataSource.Factory getCacheNHttpAesDataSourceFactory(Context context) {
//        if (dataSourceFactory == null) {
//            context = context.getApplicationContext();
//
//            DefaultDataSource.Factory upstreamFactory =
//                    new DefaultDataSource.Factory(context);
//            String aesKeyString = "1234567890abcdef";
//            byte[] aesKey = aesKeyString.getBytes(StandardCharsets.UTF_8);
//            DataSource.Factory encryptedUpstreamFactory = () -> new AesCipherDataSource(aesKey, upstreamFactory.createDataSource());
//
//
//
//            dataSourceFactory = buildReadOnlyAesCacheDataSource(encryptedUpstreamFactory, getDownloadCache(context));
//        }
//        return dataSourceFactory;
//    }









    public static synchronized DataSource.Factory getDataSourceFactoryy(Context context) {
        if (dataSourceFactory == null) {
            context = context.getApplicationContext();
            DefaultDataSource.Factory upstreamFactory =
                    new DefaultDataSource.Factory(context, getHttpDataSourceFactory(context));
            dataSourceFactory = buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache(context));
        }
        return dataSourceFactory;
    }

    public static synchronized DownloadNotificationHelper getDownloadNotificationHelper(
            Context context) {
        if (downloadNotificationHelper == null) {
            downloadNotificationHelper =
                    new DownloadNotificationHelper(context, DOWNLOAD_NOTIFICATION_CHANNEL_ID);
        }
        return downloadNotificationHelper;
    }

    public static synchronized DownloadManager getDownloadManager(Context context) {
        ensureDownloadManagerInitialized(context);
        return downloadManager;
    }



    private static synchronized Cache getDownloadCache(Context context) {
        if (downloadCache == null) {
            File downloadContentDirectory =
                    new File(getDownloadDirectory(context), DOWNLOAD_CONTENT_DIRECTORY);
            downloadCache =
                    new SimpleCache(
                            downloadContentDirectory, new NoOpCacheEvictor(), getDatabaseProvider(context));
        }
        return downloadCache;
    }

    private static synchronized void ensureDownloadManagerInitialized(Context context) {
        if (downloadManager == null) {
            downloadManager =
                    new DownloadManager(
                            context,
                            getDatabaseProvider(context),
                            getDownloadCache(context),
                            getHttpDataSourceFactory(context),
                            Executors.newFixedThreadPool(/* nThreads= */ 6));



        }
    }

    private static synchronized DatabaseProvider getDatabaseProvider(Context context) {
        if (databaseProvider == null) {
            databaseProvider = new StandaloneDatabaseProvider(context);
        }
        return databaseProvider;
    }

    private static synchronized File getDownloadDirectory(Context context) {
        if (downloadDirectory == null) {
            downloadDirectory = context.getExternalFilesDir(/* type= */ null);
            if (downloadDirectory == null) {
                downloadDirectory = context.getFilesDir();
            }
        }
        return downloadDirectory;
    }



    private static CacheDataSource.Factory buildReadOnlyCacheDataSource(
            DataSource.Factory upstreamFactory, Cache cache) {
        return new CacheDataSource.Factory()
                .setCache(cache)
                .setUpstreamDataSourceFactory(upstreamFactory)
                .setCacheWriteDataSinkFactory(null)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
    }





    public static synchronized DataSource.Factory getCacheNHttpAesDataSourceFactory(Context context, byte[] aesKey) {
        if (cacheAndHttpAesDataSourceFactory == null) {
            context = context.getApplicationContext();
            Cache downloadCache = getDownloadCache(context.getApplicationContext());
            DataSource.Factory cacheDataSourceFactory = new CacheDataSource.Factory()
                    .setCache(downloadCache)
                    .setUpstreamDataSourceFactory(getHttpDataSourceFactory(context))
                .setCacheWriteDataSinkFactory(null)
                    .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
            cacheAndHttpAesDataSourceFactory = () -> new AesCipherDataSource(aesKey, cacheDataSourceFactory.createDataSource());
        }
        return cacheAndHttpAesDataSourceFactory;
    }




    public static synchronized DataSource.Factory getCacheAesDataSourceFactory(Context context, byte[] aesKey) {
        if (cacheAesDataSourceFactory == null) {
            context = context.getApplicationContext();
            Cache downloadCache = getDownloadCache(context.getApplicationContext());
            DataSource.Factory cacheDataSourceFactory = new CacheDataSource.Factory()
                    .setCache(downloadCache)
                    .setUpstreamDataSourceFactory(null)
                    .setCacheWriteDataSinkFactory(null)
                    .setFlags(CacheDataSource.FLAG_BLOCK_ON_CACHE);
            cacheAesDataSourceFactory = () -> new AesCipherDataSource(aesKey, cacheDataSourceFactory.createDataSource());
        }
        return cacheAesDataSourceFactory;
    }








    public static void setLastDownloadedMediaItem(MediaItem mediaItem) {
        lastDownloadedMediaItem = mediaItem;
    }

    public static MediaItem getLastDownloadedMediaItem() {
        return lastDownloadedMediaItem;
    }

    private DemoUtil() {}


}