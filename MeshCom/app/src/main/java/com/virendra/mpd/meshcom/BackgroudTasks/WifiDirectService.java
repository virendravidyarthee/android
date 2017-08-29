package com.virendra.mpd.meshcom.BackgroudTasks;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import com.virendra.mpd.meshcom.BusApplication;
import com.virendra.mpd.meshcom.Constants;
import com.virendra.mpd.meshcom.Events;
import com.virendra.mpd.meshcom.Interfaces.MessageListenerInterface;
import com.virendra.mpd.meshcom.Notification;
import com.virendra.mpd.meshcom.Interfaces.SocketListenerInterface;
import com.virendra.mpd.meshcom.Interfaces.TransferInterface;
import com.virendra.mpd.meshcom.Database.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class WifiDirectService extends Service implements WifiP2pManager.ActionListener, WifiP2pManager.ChannelListener,
        SocketListenerInterface, WifiP2pManager.PeerListListener
        , WifiP2pManager.ConnectionInfoListener {

    private static final String TAG = "WifiService";
    private static final int PORT = 8888;
    private IntentFilter intentFilter;
    private IBinder myBinder = new ServiceBind();


    public WifiP2pManager myManager;
    public WifiP2pManager.Channel myChannel;
    private MessageListenerInterface myListener;
    private WifiBroadcastReceiver myReciver;
    private TransferInterface myTransferInterface;
    public List<WifiP2pDevice> myDevicesList;


    @Override
    public void onCreate() {
        super.onCreate();

        intentFilter = new IntentFilter();
        initIntentFilter();

        myManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

        myChannel = myManager.initialize(getApplicationContext(), Looper.getMainLooper(), null);

        myReciver = new WifiBroadcastReceiver(myManager, myChannel, this, this);

        myTransferInterface = new TransferSocket(getApplicationContext());

        myTransferInterface.addListener(this);
        myTransferInterface.addListener(this);


        myDevicesList = new ArrayList<>();
        try {
            Method m = myManager.getClass().getMethod("setDeviceName", new Class[]{myChannel.getClass(), String.class,
                    WifiP2pManager.ActionListener.class});
            m.invoke(myManager, myChannel, User.name, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    //Toast.makeText(WifiDirectService.this, "Rename successful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int reason) {
                    Toast.makeText(WifiDirectService.this, "Rename failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(myReciver, intentFilter);
        if (myManager != null) {
            myManager.discoverPeers(myChannel, this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void refreshList() {
        myManager.discoverPeers(myChannel, this);
    }

    private void initIntentFilter() {
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
    }


    public class ServiceBind extends Binder {
        public WifiDirectService getService() {
            return WifiDirectService.this;
        }
    }


    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        Log.e(TAG, "Connection Info Available");

        if (info.groupFormed && info.isGroupOwner) {
            Log.e(TAG, "GroupFormed, isGroupOwner");

            myTransferInterface.setPort(PORT).startServer();

            sendBroadcastToFragment(Constants.BROADCAST_ACTION_INFO_GROUP_FORMED_OWNER
                    , null);

        } else if (info.groupFormed) {

            Log.e(TAG, "GroupFormed");

            myTransferInterface.setPort(PORT).setAddress(info.groupOwnerAddress.getHostAddress())
                    .startClient();

        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    @Override
    public void onSuccess() {
        Log.e(TAG, "Success");
    }


    @Override
    public void onFailure(int reason) {
        Log.e(TAG, "Failure" + String.valueOf(reason));
    }

    @Override
    public void onChannelDisconnected() {
        Log.e(TAG, "Channel Disconnected");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void connectToDevice(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        config.groupOwnerIntent = 0;

        myManager.connect(myChannel, config, this);
        ;
    }


    @Override
    public void onReceiver(String s) {
        Log.e(TAG, s);
        try {
            if (myListener != null) {
                myListener.onMessageReceived(s);
                if (!Utility.parseMessage(s)[2].equals("email")) {
                    Notification.makeNotification(getApplicationContext(), Utility.parseMessage(s)[3]);
                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    public void stopMessageReceiver() {
        myTransferInterface.stopMessageReceiver();
    }

    public void startFileReceiver() {
        myTransferInterface.startFileReceiver();
    }

    public void stopFileReceiver() {
        myTransferInterface.stopFileReceiver();
    }

    public void addListener(MessageListenerInterface listener) {
        myListener = listener;
    }


    public void startMessageReceiver() {
        myTransferInterface.startMessageReceiver();
    }


    public void sendMessage(String message) {
        myTransferInterface.sendMessage(message);
    }


    public void sendFile(String path) {
        myTransferInterface.sendFile(path);
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        if (!myDevicesList.isEmpty()) {
            myDevicesList.clear();
        }
        myDevicesList.addAll(peers.getDeviceList());
    }


    public void removeConnection() {
        myManager.removeGroup(myChannel, this);
        myTransferInterface.endConnection();
    }


    private void sendBroadcastToFragment(final String state, String value) {
        Events.WifiState wifiState = new Events.WifiState();
        wifiState.state = state;
        wifiState.value = value;
        BusApplication.getBus().post(wifiState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReciver);
    }


    private class WifiBroadcastReceiver extends BroadcastReceiver {
        private WifiP2pManager manager;
        private WifiP2pManager.ConnectionInfoListener connectionInfoListener;
        private WifiP2pManager.Channel channel;
        private WifiP2pManager.PeerListListener peerListListener;


        public WifiBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                     WifiP2pManager.ConnectionInfoListener connectionInfoListener,
                                     WifiP2pManager.PeerListListener peerListListener) {
            this.manager = manager;
            this.channel = channel;
            this.peerListListener = peerListListener;
            this.connectionInfoListener = connectionInfoListener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                Log.e(TAG, "P2P STATE CHANGED");
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    sendBroadcastToFragment(Constants.BROADCAST_ACTION_WIFI_ENABLE
                            , null);
                } else {
                    sendBroadcastToFragment(Constants.BROADCAST_ACTION_WIFI_DISABLE
                            , null);
                }

            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

                Log.e(TAG, "P2P CONNECTION CHANGED");
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(
                        WifiP2pManager.EXTRA_NETWORK_INFO);

                if (networkInfo.isConnected()) {
                    sendBroadcastToFragment(Constants.BROADCAST_ACTION_IS_CONNECTED
                            , networkInfo.getExtraInfo());
                    manager.requestConnectionInfo(channel, connectionInfoListener);
                }


            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
                Log.e(TAG, "P2P PEERS CHANGED");
                sendBroadcastToFragment(Constants.BROADCAST_ACTION_PEERS_LIST, null);
                if (manager != null) {
                    manager.requestPeers(channel, peerListListener);
                }


            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {


            } else if (WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)) {

                int state = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE, -1);

                if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED) {
                    sendBroadcastToFragment(Constants.BROADCAST_ACTION_DISCOVERY_STARTED
                            , null);
                } else {
                    sendBroadcastToFragment(Constants.BROADCAST_ACTION_DISCOVERY_STOPPED
                            , null);
                }

            }

        }
    }


}
