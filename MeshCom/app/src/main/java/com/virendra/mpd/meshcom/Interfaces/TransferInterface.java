package com.virendra.mpd.meshcom.Interfaces;

import java.net.InetAddress;


public interface TransferInterface {

    TransferInterface setInetAddress(InetAddress address);
    void sendFile(String path);
    TransferInterface setPort(int port);
    void startFileReceiver();
    void addListener(SocketListenerInterface listener);
    TransferInterface setAddress(String address);
    void startServer();
    void stopMessageReceiver();
    void startClient();
    void endConnection();
    void startMessageReceiver();
    void stopFileReceiver();
    void sendMessage(String message);



}
