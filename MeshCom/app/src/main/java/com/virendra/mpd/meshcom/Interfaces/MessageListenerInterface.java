package com.virendra.mpd.meshcom.Interfaces;

public interface MessageListenerInterface {
    void onMessageReceived(String response);
    void onConnected(boolean isConnected);
}
