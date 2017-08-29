package com.virendra.mpd.meshcom.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.virendra.mpd.meshcom.BusApplication;
import com.virendra.mpd.meshcom.Adapters.ChatMessagesAdapter;
import com.virendra.mpd.meshcom.Interfaces.MessageListenerInterface;
import com.virendra.mpd.meshcom.UIFragments.NearbyFragment;
import com.virendra.mpd.meshcom.R;
import com.virendra.mpd.meshcom.Database.User;
import com.virendra.mpd.meshcom.BackgroudTasks.Utility;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ChatActivity extends AppCompatActivity implements MessageListenerInterface
{

    RecyclerView chatRecycler;
    EditText textMessage;
    ImageButton sendButton;
    RecyclerView.LayoutManager layoutManager;
    ChatMessagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatRecycler = (RecyclerView) findViewById(R.id.chatRecycler);
        textMessage = (EditText) findViewById(R.id.textmessage);
        sendButton = (ImageButton) findViewById(R.id.sendButton);

        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        BusApplication.getBus().register(this);

        layoutManager = new LinearLayoutManager(this);
        chatRecycler.setLayoutManager(layoutManager);

        adapter = new ChatMessagesAdapter(this);
        chatRecycler.setAdapter(adapter);

        if (HomeActivity.myService != null) {
            HomeActivity.myService.addListener(this);
            //HomeActivity.myService.startMessageReceiver();
        }


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendEmail();
            }
        }, 0, 2000, TimeUnit.MILLISECONDS);


    }

    public void sendEmail() {
        String message = User.emails;
        String encodedMessage = Utility.createMessage(User.name, message, Utility.MESSAGE, message.length(), "email");
        HomeActivity.myService.sendMessage(encodedMessage);
    }

    public void sendMessage() {
        String message = textMessage.getText().toString();

        textMessage.setText("");
        adapter.addMessage(message, true);
        String encodedMessage = Utility.createMessage(User.name, message, Utility.MESSAGE, message.length(), "text");
        HomeActivity.myService.sendMessage(encodedMessage);
    }

    @Override
    public void onMessageReceived(final String response) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] decodedMessage = Utility.parseMessage(response);
                if (decodedMessage[2].equals("email")) {
                    getSupportActionBar().setSubtitle(decodedMessage[3]);
                    return;
                } else {
                    String receivedMessage = decodedMessage[0] + ": " + decodedMessage[3];
                    adapter.addMessage(receivedMessage, false);
                    NearbyFragment.chatDatabase.addToMessageHistory(decodedMessage[0], decodedMessage[3]);
                }
            }
        });
    }

    @Override
    public void onConnected(boolean isConnected) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }
}
