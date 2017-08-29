package com.virendra.mpd.meshcom.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.virendra.mpd.meshcom.R;

import java.util.List;

/**
 * Created by Virendra on 30-03-2017.
 */

public class chatHistoryAdapter extends ArrayAdapter<String> {

    List<String> chatHistory;
    LayoutInflater inflater;

    public chatHistoryAdapter(Context context, List<String> list) {
        super(context, 0);
        this.chatHistory = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.row_history, parent, false);
        }

        TextView username = (TextView)convertView.findViewById(R.id.historyUsername);
        username.setText(chatHistory.get(0));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getPosition(@Nullable String item) {
        return super.getPosition(item);
    }

    @Override
    public int getCount() {
        return chatHistory.size();
    }
}
