package com.virendra.mpd.meshcom.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.virendra.mpd.meshcom.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Virendra on 23-03-2017.
 */

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder>{
    private List<String> myMessages;
    private Context myContext;
    private boolean isMyMessage;
    private SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat("ccc 'at' H:mm a");
    private Calendar myCalendar = Calendar.getInstance();

    public ChatMessagesAdapter(Context context) {
        this.myMessages = new ArrayList<>();
        this.myContext = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView message;
        public TextView time;
        public RelativeLayout messageBody;
        public ViewHolder(View v) {
            super(v);
            message = (TextView)v.findViewById(R.id.tv_message);
            messageBody = (RelativeLayout)v.findViewById(R.id.relativemessagebody);
            time = (TextView)v.findViewById(R.id.tv_time);
        }
    }


    public void addMessage(String message, boolean status){
        myMessages.add(message);
        this.isMyMessage = status;
        notifyItemChanged(myMessages.size());
        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create view
        View view = LayoutInflater.from(myContext).inflate(R.layout.row_message, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.message.setText(myMessages.get(position));
        holder.time.setText(String.valueOf(mySimpleDateFormat.format(myCalendar.getTime())));

        if(isMyMessage) {
            holder.messageBody.setHorizontalGravity(Gravity.END);
            holder.messageBody.setGravity(Gravity.END);
        } else {
            holder.messageBody.setHorizontalGravity(Gravity.START);
            holder.messageBody.setGravity(Gravity.START);
        }

    }

    @Override
    public int getItemCount() {
        return myMessages.size();
    }

}
