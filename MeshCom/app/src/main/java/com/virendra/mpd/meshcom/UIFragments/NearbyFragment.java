package com.virendra.mpd.meshcom.UIFragments;


import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.squareup.otto.Subscribe;
import com.virendra.mpd.meshcom.Activities.ChatActivity;
import com.virendra.mpd.meshcom.Activities.HomeActivity;
import com.virendra.mpd.meshcom.Adapters.NearbyAdapter;
import com.virendra.mpd.meshcom.BusApplication;
import com.virendra.mpd.meshcom.Constants;
import com.virendra.mpd.meshcom.Database.SqliteHelper;
import com.virendra.mpd.meshcom.Events;
import com.virendra.mpd.meshcom.Interfaces.MessageListenerInterface;
import com.virendra.mpd.meshcom.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements MessageListenerInterface
{


    List<WifiP2pDevice> deviceList;
    NearbyAdapter adapter;
    ListView myListView;
    public static SqliteHelper chatDatabase;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        BusApplication.getBus().register(this);
        deviceList = new ArrayList<>();

        chatDatabase = new SqliteHelper(getContext(), "CHATS.db", null, 1);

        myListView = (ListView) view.findViewById(R.id.listview);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                HomeActivity.myService.connectToDevice(deviceList.get(position));
                Toast.makeText(getContext(), "Connecting. Please wait...", Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (deviceList.get(position).status == WifiP2pDevice.CONNECTED) {
                            HomeActivity.myService.startMessageReceiver();
                            Toast.makeText(getContext(), "Connected.", Toast.LENGTH_LONG).show();
                            chatDatabase.addToChatHistory(deviceList.get(position).deviceName);
                            //User.sqlDatabase.query("CREATE TABLE IF NOT EXISTS CHATHISTORY (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR");

                            Intent i = new Intent(getActivity(), ChatActivity.class);
                            startActivity(i);
                        }
                        else
                            Toast.makeText(getContext(), "Cannot connect to device. Check Wi-Fi and range", Toast.LENGTH_SHORT).show();

                    }
                }, 5000);
            }
        });
        adapter = new NearbyAdapter(getActivity(), deviceList);
        myListView.setAdapter(adapter);


        if (HomeActivity.myService != null) {
            HomeActivity.myService.addListener(this);
        }

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.myService.refreshList();
            }
        }, 1500);*/


        return view;
    }

    @Subscribe
    public void receiveBroadcasts(Events.WifiState event) {
        if (HomeActivity.myService == null)
            return;

        switch (event.state) {
            case Constants.BROADCAST_ACTION_PEERS_LIST:

                //Toast.makeText(getContext(), "Getting new device list", Toast.LENGTH_SHORT).show();

                if (HomeActivity.myService.myDevicesList.size() > 0) {
                    deviceList.clear();
                }
                    deviceList.addAll(HomeActivity.myService.myDevicesList);


                    //adapter refresh
                    adapter.clear();
                    adapter.addAll(deviceList);
                    adapter.notifyDataSetChanged();


                break;
            case Constants.BROADCAST_ACTION_DISCOVERY_STARTED:
                Toast.makeText(getContext(), "Discovery initiated. Please wait...", Toast.LENGTH_SHORT).show();
                break;

            case Constants.BROADCAST_ACTION_DISCOVERY_STOPPED:
                Toast.makeText(getContext(), "Discovery Stopped", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onMessageReceived(String response) {

    }

    @Override
    public void onConnected(boolean isConnected) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BusApplication.getBus().unregister(this);
    }

}
