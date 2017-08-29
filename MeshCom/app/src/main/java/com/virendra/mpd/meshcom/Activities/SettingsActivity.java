package com.virendra.mpd.meshcom.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.virendra.mpd.meshcom.R;
import com.virendra.mpd.meshcom.Database.User;

public class SettingsActivity extends AppCompatActivity {

    EditText et_username;
    Button okayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        et_username = (EditText)findViewById(R.id.et_username);
        okayButton = (Button)findViewById(R.id.okayButton);
        et_username.setHint(User.name);
        et_username.setFilters(new InputFilter[]{
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
                        et_username.setError("Alphabetic characters only");
                        return "";
                    }
                }
        });

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_username.getText().toString().trim().equals(User.name))
                {
                    Toast.makeText(SettingsActivity.this, "No change in username detected", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                else if(et_username.getText().toString().equals(""))
                {
                    finish();
                    return;
                }
                else
                {
                    HomeActivity.mainUser.editUser(et_username.getText().toString().trim());
                    Intent i = getBaseContext().getPackageManager().
                            getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
