package com.virendra.mpd.meshcom.UIFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.virendra.mpd.meshcom.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {



    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats, container, false);


        return v;
    }

}
