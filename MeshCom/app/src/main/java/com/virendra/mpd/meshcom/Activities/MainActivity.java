package com.virendra.mpd.meshcom.Activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.transition.TransitionManager;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.virendra.mpd.meshcom.R;
import com.virendra.mpd.meshcom.Database.User;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView textView, textView2, tv_welcome;
    EditText username;
    ImageView logo;
    RelativeLayout splashLayout;
    Button register;
    User mainUser;
    String emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        emails = "";

        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccounts();
        for(Account account : accounts)
        {
            if(emailPattern.matcher(account.name).matches() && account.type.equals("com.google"))
            {
                if(emails != "")
                {
                    emails+=", ";
                }
                emails += account.name;

            }
        }
        User.emails = emails;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveLogo();
            }
        }, 3000);
    }

    private void moveLogo() {
        splashLayout.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        TransitionManager.beginDelayedTransition(splashLayout);
        RelativeLayout.LayoutParams positionRules = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        positionRules.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
        positionRules.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        logo.setLayoutParams(positionRules);


        ViewGroup.LayoutParams sizeRules = logo.getLayoutParams();
        sizeRules.width = 300;
        sizeRules.height = 300;
        logo.setLayoutParams(sizeRules);

        if(mainUser.getUserExists())
        {
            //Start home activity
            tv_welcome.setVisibility(View.VISIBLE);
            startHomeActivity();
        }
        else
        {
            Toast.makeText(this, "Welcome to MeshCom!", Toast.LENGTH_SHORT).show();
            username.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
        }
        splashLayout.setLayerType(View.LAYER_TYPE_NONE, null);
    }

    private void startHomeActivity()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);

    }
    private void init()
    {
        mainUser = new User(this);
        register = (Button)findViewById(R.id.register);
        splashLayout = (RelativeLayout)findViewById(R.id.splashLayout);
        username = (EditText)findViewById(R.id.username);
        username.setVisibility(View.GONE);
        logo = (ImageView) findViewById(R.id.logo);
        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        tv_welcome = (TextView)findViewById(R.id.tv_welcome);
        tv_welcome.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        register.setVisibility(View.GONE);

        username.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if(source.equals(""))
                        {
                            return source;
                        }
                        if(source.toString().matches("[a-zA-Z]+"))
                        {
                            return source;
                        }
                        username.setError("Alphabetic characters only");
                        return "";
                    }
                }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "You did not enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mainUser.getUserExists())
                {
                    Toast.makeText(MainActivity.this, "User already exits", Toast.LENGTH_SHORT).show();
                    startHomeActivity();
                    return;
                }
                else {
                    mainUser.setupUser(username.getText().toString().trim(), emails);
                    Toast.makeText(MainActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    startHomeActivity();
                }
            }
        });
    }
}
