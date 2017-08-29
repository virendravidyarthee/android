package sccc.eample.mycarer_stroke;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Intro_ttsFragment extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener{


    View view;
    TextView tv_hello, tv_name, tv_fine;
    TextToSpeech tts;

    public Intro_ttsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_intro_tts, container, false);

        tts = new TextToSpeech(getContext(),this);

        tv_hello = (TextView) view.findViewById(R.id.tv_hello);
        tv_hello.setOnClickListener(this);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setOnClickListener(this);
        tv_fine = (TextView) view.findViewById(R.id.tv_fine);
        tv_fine.setOnClickListener(this);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Text-To-Speech - Intro");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_hello:
                speak(tv_hello.getText().toString());
                break;
            case R.id.tv_name:
                speak(tv_name.getText().toString());
                break;
            case R.id.tv_fine:
                speak(tv_fine.getText().toString());
                break;

        }
    }

    private void speak(String text)
    {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
        /*Toast.makeText(getContext(), text + " spoken.", Toast.LENGTH_SHORT).show();*/
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS)
        {
            Locale lang = tts.getLanguage();
            int result = tts.setLanguage(lang);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS","This language is not supported");
            }
            else{

            }
        }
        else
        {
            Log.e("TTS", "Initialisation failed.");
        }
    }
}
