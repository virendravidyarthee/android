package com.virendra.mpd.meshcom.Adapters;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.virendra.mpd.meshcom.R;

import java.util.List;

/**
 * Created by Virendra on 23-03-2017.
 */

public class NearbyAdapter extends ArrayAdapter<WifiP2pDevice> {
    private List<WifiP2pDevice> myDeviceList;
    private LayoutInflater myInflater;

    public NearbyAdapter(Context context, List<WifiP2pDevice> list) {
        super(context, 0);
        this.myInflater = LayoutInflater.from(context);
        this.myDeviceList = list;
    }

    public void refreshList(List<WifiP2pDevice> list)
    {
        this.myDeviceList.clear();
        this.myDeviceList.addAll(list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = myInflater.inflate(R.layout.row_nearby, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.username);
        TextView emails = (TextView)convertView.findViewById(R.id.email);

        name.setText(String.valueOf(myDeviceList.get(position).deviceName));
        emails.setText(String.valueOf(myDeviceList.get(position).deviceAddress));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getPosition(@Nullable WifiP2pDevice item) {
        return super.getPosition(item);
    }

    @Override
    public int getCount() {
        return myDeviceList.size();
    }
}
