package com.a.acs2;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.offline.DownloadIndex;
import androidx.media3.exoplayer.offline.DownloadManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.offline.Download;
import androidx.media3.exoplayer.offline.DownloadCursor;
import androidx.media3.exoplayer.offline.DownloadIndex;
import androidx.media3.exoplayer.upstream.BandwidthMeter;
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter;

import androidx.media3.exoplayer.offline.DownloadManager;
import androidx.media3.exoplayer.offline.DownloadManager.Listener;
import androidx.media3.exoplayer.offline.DownloadRequest;
import androidx.media3.exoplayer.offline.DownloadService;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.media3.exoplayer.offline.Download;
import androidx.media3.exoplayer.offline.DownloadManager;
import androidx.media3.exoplayer.offline.DownloadRequest;
import androidx.media3.exoplayer.offline.DownloadService;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dmanager#newInstance} factory method to
 * create an instance of this fragment.
 */
@UnstableApi
public class Dmanager extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DownloadManager downloadManager;
    Context context = getContext();


    private Handler handler;
    private Runnable updateRunnable;
    RecyclerView recyclerView;
    List<ItemDmanager> items;
    DownloadIndex downloadIndex;
    String jsonString;

    // TODO: Rename and change types
    private String mParam1;
    private String mParam2;

    public Dmanager() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dmanager.
     */
    // TODO: Rename and change types and number of parameters
    public static Dmanager newInstance(String param1, String param2) {
        Dmanager fragment = new Dmanager();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dmanager, container, false);

        jsonString = "{\"m1\":{\"z\":{\"m2\":{\"src\":\"http://192.168.0.101:3004/m2.mp4\",\"title\":\"lec 3  bro sorol\"},\"Lec1\":{\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2FThis.mp4?alt=media&token=5baedafe-5a03-4fd5-a40b-282be1ae12b5\",\"title\":\"lec 1 of sorol!!\"},\"Lec2\":{\"src\":\"https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Fvideoplayback%20(1).mp4?alt=media&token=75bf7507-3575-406d-8f40-702cc974c4be\",\"title\":\"lec 2  bro sorol\"},\"Lec3\":{\"src\":\"https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Fvideoplayback.mp4?alt=media&token=db7395ee-3e7f-4709-9ba9-305743b5db91\",\"title\":\"lec 3  bro sorol\"}},\"01\":{\"সরলরেখা\":{\"1\":{\"id\":\"m101l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_m1.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc\",\"title\":\"Lec 1\"},\"1.2\":{\"id\":\"m101l1.2\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_m2.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc\",\"title\":\"Lec 2\"}}},\"bri\":{\"Lec1\":\"lec 0.1 of bri\",\"Lec2\":\"haya bri 2 lec\"}},\"m2\":{\"9\":{\"gotibidda\":{\"Lec1\":{\"id\":\"m29l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_main.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 1\"},\"Lec2\":{\"id\":\"m29l1.2\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_w.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 2\"},\"Lec3\":{\"id\":\"m29l3\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_j.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 3\"},\"Lec4\":{\"id\":\"m29l2\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_s.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 4\"}}},\"06\":{\"কনিক\":{\"1\":{\"id\":\"m206l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_main.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 1\"}}}},\"p1\":{\"08\":{\"পর্যায়বৃত্ত গতি\":{\"1\":{\"id\":\"p108l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_p.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc\",\"title\":\"Lec 1\"}}}}}";

        recyclerView = view.findViewById(R.id.recyclerview);

   Log.d("gggg","first");
        downloadManager = DemoUtil.getDownloadManager(getContext());

        DownloadIndex downloadIndex = downloadManager.getDownloadIndex();
        items = new ArrayList<ItemDmanager>();

        DownloadCursor downloadsCursor = null;
        try {
            downloadsCursor = downloadIndex.getDownloads();
            if (downloadsCursor != null) {
                downloadIndex = downloadManager.getDownloadIndex();

                List<String> playlist = new ArrayList<>();
                Map<String, List<String>> groupedNames = new HashMap<>();
                for (int i = downloadsCursor.getCount() - 1; i >= 0; i--) {
                    downloadsCursor.moveToPosition(i);
                    Download download = downloadsCursor.getDownload();


                    if (download != null) {
                        String lecNum = String.valueOf(download.request.id).substring(5);
                        String first = String.valueOf(download.request.id).substring(0, 5);
                        Log.d("ffffffkff", String.valueOf(download.request.id));
                        String sub6 = "";
                        int pic = R.drawable.math;

                        if (first.contains("m101")) {
                            pic = R.drawable.sorollec;
                            sub6 = "সরলরেখা";
                        } else if (first.contains("m206")) {
                            pic = R.drawable.konik;
                            sub6 = "কনিক";
                        } else if (first.contains("p108")) {
                            pic = R.drawable.pmm;
                            sub6 = "পর্যায়বৃত্ত গতি";
                        } else {

                        }

                        if (download.getPercentDownloaded() > 0) {


                            if (download.state == 2) {
                                items.add(new ItemDmanager(sub6, String.valueOf((int) download.getPercentDownloaded() + "%"), download.getPercentDownloaded(), pic, R.drawable.play, R.drawable.delete, false, download.request.id, "", "Lec " + lecNum));

                            } else if (download.state == 1) {
                                items.add(new ItemDmanager(sub6, String.valueOf((int) download.getPercentDownloaded() + "%"), download.getPercentDownloaded(), pic, R.drawable.pause, R.drawable.delete, false, download.request.id, "", "Lec " + lecNum));

                            } else {
                                items.add(new ItemDmanager(sub6, String.valueOf((int) download.getPercentDownloaded() + "%"), download.getPercentDownloaded(), pic, R.drawable.pause, R.drawable.delete, false, download.request.id, "", "Lec " + lecNum));

                            }
                        }
                    }


//                    String prefix = download.request.id.substring(0, 3);
//
//
//                    if (!groupedNames.containsKey(prefix)) {
//                        groupedNames.put(prefix, new ArrayList<>());
//                    }
//                    groupedNames.get(prefix).add(download.request.id);
//                    playlist.add(prefix);

                }
//
//
//                for (Map.Entry<String, List<String>> entry : groupedNames.entrySet()) {
//                    String groupName = entry.getKey();  // Access group name directly
//                    List<String> namesList = entry.getValue();  // Access list of names directly
//
//                    // Loop through the list of names
//                    for (int i = 0; i < namesList.size(); i++) {
//                        String name = namesList.get(i);
//
//                    }
//
//                    if (namesList.size() > 1) {
//                        items.add(new Item(groupName, String.join(", ", namesList), 404, R.drawable.math, R.drawable.play, R.drawable.delete, true, String.valueOf(namesList.size())));
//
//
//                    } else {
//
//                        try {
//                            Download download = downloadIndex.getDownload(namesList.get(0));
//                            if (download != null) {
//
//                                if (download.state == 2) {
//                                    items.add(new Item(String.valueOf(download.request.id), String.valueOf((int) download.getPercentDownloaded() + "%"), download.getPercentDownloaded(), R.drawable.math, R.drawable.play, R.drawable.delete, false, download.request.id));
//
//                                } else if (download.state == 1) {
//                                    items.add(new Item(String.valueOf(download.request.id), String.valueOf((int) download.getPercentDownloaded() + "%"), download.getPercentDownloaded(), R.drawable.math, R.drawable.pause, R.drawable.delete, false, download.request.id));
//
//                                } else {
//                                    items.add(new Item(String.valueOf(download.request.id), String.valueOf((int) download.getPercentDownloaded() + "%"), download.getPercentDownloaded(), R.drawable.math, R.drawable.pause, R.drawable.delete, false, download.request.id));
//
//                                }
//                            }
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }


//                    }
//                }


//                for (int i = downloadsCursor.getCount() - 1; i >= 0; i--) {
//                    downloadsCursor.moveToPosition(i);
//                    Download download = downloadsCursor.getDownload();
//
//                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                dmanager_adapter adapter = new dmanager_adapter(getContext(), items, new SelectListenerdownload() {

                    @Override
                    @UnstableApi
                    public void onIteamClicked(ItemDmanager item, int position) {


                        // Log.d(TAG, "tttttttttttttt-l---------------- " + String.valueOf(position));
                        //  Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();

                        //      ((dmanager_adapter) recyclerView.getAdapter()).done(0); // Assuming item.getPercentDownloaded() returns an int
                        try {
                            // Parse JSON string into JSONObject
                            JSONObject jsonObject = new JSONObject(jsonString);
                            JSONObject m1Object = jsonObject.getJSONObject(item.getimgnum().substring(0, 2));
                            String id2ndpart = item.getimgnum().substring(2, 4);
                            String id3ndpart = item.getimgnum().substring(5);


                            Iterator<String> keys = m1Object.keys();
                            Log.d(TAG, "keys-l---------------- " + String.valueOf(keys));

                            while (keys.hasNext()) {
                                String keyy = keys.next();
                                Log.d(TAG, "lllll-l---------------- " + keyy + " - " + id2ndpart);
                                if (keyy.matches(id2ndpart)) {
                                    JSONObject sub00 = m1Object.getJSONObject(id2ndpart);

                                    Iterator<String> keys6 = sub00.keys();
                                    String key98 = keys6.next();


                                    JSONObject lecs78 = sub00.getJSONObject(key98);


                                    Iterator<String> keys77 = lecs78.keys();

                                    while (keys77.hasNext()) {
                                        String keyy8 = keys77.next();
                                        Log.d(TAG, "keyy8-l---------------- " + keyy8 + " - ");
                                        if (keyy8.matches(id3ndpart)) {
                                            JSONObject sub005 = lecs78.getJSONObject(keyy8);
                                            String src08 = sub005.getString("src");
                                            String title4 = sub005.getString("title");
                                            Log.d(TAG, "lecs78-l---------------- " + sub005 + " - ");

                                            //
                                            String id = item.getimgnum();

                                            Log.d(TAG, "ttttttttttttttt-l---------------- " + String.valueOf(keyy8));

                                            Intent intent = new Intent(getContext(), Offline_vid2.class);
                                            intent.putExtra("playlist", "yes");
                                            intent.putExtra("ids", keyy8);
                                            intent.putExtra("src", src08);
                                            intent.putExtra("title", title4);
                                            intent.putExtra("preTitle", key98);
                                            intent.putExtra("lecs", String.valueOf(lecs78));
                                            startActivity(intent);
                                        }
                                    }
//
//                                    String id = item.getimgnum();
//
//                                    Intent intent = new Intent(getContext(), Offline_vid2.class);
//                                    intent.putExtra("playlist", "yes");
//                                    intent.putExtra("ids", id);
//                                    intent.putExtra("lecs", String.valueOf(lecs78));
//                                    startActivity(intent);
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ///////////////////
                    }

                    @Override
                    @UnstableApi
                    public void onDltClicked(ItemDmanager item, int position) {
                        //  Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();

                        //  Log.d(TAG, "dlt "  +item.getName());
                        if (item.getPercent() > 200) {

                            List<String> stringList = new ArrayList<>(List.of(item.getEmail().split(", ")));

                            for (int i = 0; i < stringList.size(); i++) {
                                DownloadService.sendRemoveDownload(
                                        getContext(), OfflineVideoDownloadService.class, stringList.get(i), /* foreground= */ true);

                            }
                          //  Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();


                            ((dmanager_adapter) recyclerView.getAdapter()).removeItem(position); // Assuming item.getPercentDownloaded() returns an int

                        } else {

                            DownloadService.sendRemoveDownload(
                                    getContext(), OfflineVideoDownloadService.class, item.getimgnum(), /* foreground= */ true);


                            ((dmanager_adapter) recyclerView.getAdapter()).removeItem(position); // Assuming item.getPercentDownloaded() returns an int

                        }


                    }

                    @Override
                    @UnstableApi
                    public void onPauseClicked(ItemDmanager item, int position) {
                        // Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();
                        // String contentId="ttz";
                        // Initialize the download manager and download tracker
                        //  downloadManager = DemoUtil.getDownloadManager(this);
                        if (isNetworkAvailable()) {
                            
                       


                        try {

                            DownloadIndex downloadIndex0 = downloadManager.getDownloadIndex();
                            Download download = downloadIndex0.getDownload(item.getimgnum());

                            int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(item.getimgnum());
                            Log.d("cccccccccccccc", String.valueOf(positionm));


                            if (download != null) {


                                if (download.state == 1) {
                                    if (positionm != -1) {
                                        // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                                        ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.play); // Assuming item.getPercentDownloaded() returns an int

                                    }
                                    DownloadService.sendSetStopReason(
                                            getContext(), OfflineVideoDownloadService.class, String.valueOf(item.getimgnum()), 0, /* foreground= */ true);

                                } else if (download.state == 2) {
                                    if (positionm != -1) {
                                        // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                                        ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.pause); // Assuming item.getPercentDownloaded() returns an int





                                    }
                                    DownloadService.sendSetStopReason(
                                            getContext(), OfflineVideoDownloadService.class, item.getimgnum(), 1, /* foreground= */ true);


                                } else {
                                    ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.play); // Assuming item.getPercentDownloaded() returns an int


                                    DownloadService.sendSetStopReason(
                                            getContext(), OfflineVideoDownloadService.class, item.getimgnum(), 0, /* foreground= */ true);

                                }
                            }


                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }else {
                            Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                        }

                    }


                });

                recyclerView.setAdapter(adapter);
                //recyclerView.setAdapter(new dmanager_adapter(context(),items,this));
                downloadsCursor.close(); // Don't forget to close the cursor

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //------------


        handler = new Handler();
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateIndex();
                 handler.postDelayed(this, 100); // Update every second
            }
        };
        handler.post(updateRunnable);


        return view;
    }


    @UnstableApi
    private void updateIndex() {
        List<String> newList = new ArrayList<>();

        for (ItemDmanager item : items) {
            if (item.getPercent() < 100) {
                newList.add(item.getimgnum());

            }
        }
        //Log.d(TAG, "500---------------------main---------- "  );
//

        ArrayList<String> idlist = new ArrayList<>();

        List<Download> downloads = downloadManager.getCurrentDownloads();
        for (Download item : downloads) {
            idlist.add(item.request.id);
        }
//        System.out.println("276...---"+newList);


        for (Download item : downloads) {


            if (newList.contains(item.request.id)) {
                //  System.out.println("updated " +item.getPercentDownloaded());
//                Item itemm = items.get(0);
//                itemm.setEmail (String.valueOf(item.getPercentDownloaded())); // Update the percentage
//                recyclerView.getAdapter().notifyItemChanged(0);
//                System.out.println("275...---"+item.getPercentDownloaded());
//                System.out.println("276...---"+(int)item.getPercentDownloaded());
//                System.out.println("276...---"+(int)item.getPercentDownloaded());


//                for (Item item0 : items) {
//                    if (item0.getName().equals(item.request.id)) {
//                        if ((int)item.getPercentDownloaded() != (int)item0.getPercent()) {
//
//                        }
//
//                    }
//                }


                for (int i = 0; i < items.size(); i++) {

//                    System.out.println("276...---"+items.get(i));


                    if (items.get(i).getimgnum().equals(item.request.id)) {
                        if ((int) item.getPercentDownloaded() != (int) items.get(i).getPercent()) {


                            ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(i, "Downloading... " + String.valueOf((int) item.getPercentDownloaded()) + "%", item.getPercentDownloaded()); // Assuming item.getPercentDownloaded() returns an int



                        } else {

                        }


                    }
//                    User user = items.get(i);
//                    // Access both user and index i
                }


//                int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(item.request.id);
//                if (positionm != -1) {
//                   // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
//                    ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(positionm,"Downloading... "+ String.valueOf((int) item.getPercentDownloaded())+"%",  item.getPercentDownloaded()); // Assuming item.getPercentDownloaded() returns an int
//
//                }
//                recyclerView.getAdapter().
                // recyclerView.getAdapter().updateDownloadPercentage(0, String.valueOf(item.getPercentDownloaded())); // Update the download percentage for that item
                //  ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(0, String.valueOf(item.getPercentDownloaded())); // Assuming item.getPercentDownloaded() returns an int

            }
            // System.out.println(item.getPercentDownloaded());
        }

        for (String fruit : newList) {
            if (!idlist.contains(fruit)) {
                Log.d(TAG, "stuck at 99% fixing it now ");
                downloadIndex = downloadManager.getDownloadIndex();


                try {
                    Download download = downloadIndex.getDownload(fruit);
                    if (download != null) {
                        if (download.state == 3) {


                            int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(download.request.id);
                            if (positionm != -1) {
                                // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                                ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(positionm, "Downloading... 100%", 100); // Assuming item.getPercentDownloaded() returns an int

                            }

                            items.get(positionm).setHidden(true);

                            //  ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(0, String.valueOf(100)); // Assuming item.getPercentDownloaded() returns an int


                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }


        downloadIndex = downloadManager.getDownloadIndex();
        for (ItemDmanager itemm : items) {
            try {
            Download download = downloadIndex.getDownload(itemm.getimgnum());

                int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(itemm.getimgnum());

                if(download.state==2){
            ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.play); // Assuming item.getPercentDownloaded() returns an int

        }else if(download.state==1){
            ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.pause); // Assuming item.getPercentDownloaded() returns an int

        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        }



//---------------------end
//
//        for (Item item : items) {
//            System.out.println(item.getName()+item.getEmail());
//        }
//        Log.d(TAG, "recy items : "  +items);
//
//
//
//        List<Download> downloads = downloadManager.getCurrentDownloads();
//        Log.d(TAG, "getCurrentDownloads : "  +downloads);
//        for (Download item : downloads) {
//            System.out.println(item.getPercentDownloaded());
//        }


        //-------------------------
//        downloadManager = DemoUtil.getDownloadManager(this);
//
//        DownloadIndex downloadIndex = downloadManager.getDownloadIndex();
//        List<Item> items = new ArrayList<Item>();
////        items.add(new Item("Download","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",R.drawable.math));
////        items.add(new Item("dgdfdfffffdgfffg","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",R.drawable.math));
//
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new dmanager_adapter(getApplicationContext(),items,this));
//
//
//        DownloadCursor downloadsCursor = null;
//        try {
//            downloadsCursor =   downloadIndex.getDownloads();
//            if (downloadsCursor != null) {
//                while (downloadsCursor.moveToNext()) {
//
//                    Download download = downloadsCursor.getDownload(  );
//
//
//                  //  Log.d(TAG, "index : state "+download.state+" id "+download.request.id );
//
//                  //  TextView  textView = findViewById(R.id.textView2);
//                  //  textView.setText("index : state "+String.valueOf(download.state) +" id "+String.valueOf(download.request.id) );
//                    Toast.makeText(getApplicationContext(),String.valueOf(download.getPercentDownloaded()), Toast.LENGTH_SHORT).show();
//                  // items.add(new Item(download.,"https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",R.drawable.math));
//
//
//                }
//                downloadsCursor.close(); // Don't forget to close the cursor
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//-----end-update-Index

//        if(item.state==2){
//            ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(i, R.drawable.play); // Assuming item.getPercentDownloaded() returns an int
//
//        }else if(item.state==1){
//            ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(i, R.drawable.play); // Assuming item.getPercentDownloaded() returns an int
//
//        }

    }
    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){


        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        };
        return false;
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                try {
                    InetAddress ipAddr = InetAddress.getByName("google.com");
                    return !ipAddr.equals("");
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false; // No active network or no internet
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable); // Stop updates when the activity is destroyed
    }
}