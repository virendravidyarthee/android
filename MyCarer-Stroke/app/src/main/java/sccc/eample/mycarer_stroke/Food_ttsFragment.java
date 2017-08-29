package sccc.eample.mycarer_stroke;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Food_ttsFragment extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener {

    View view;
    TextView tv_want, tv_notgood, tv_spicy;
    TextToSpeech tts;

    public Food_ttsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_tts, container, false);

        tts = new TextToSpeech(getContext(),this);

        tv_want = (TextView) view.findViewById(R.id.tv_want);
        tv_want.setOnClickListener(this);

        tv_notgood = (TextView) view.findViewById(R.id.tv_notgood);
        tv_notgood.setOnClickListener(this);

        tv_spicy = (TextView) view.findViewById(R.id.tv_spicy);
        tv_spicy.setOnClickListener(this);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Text-To-Speech - Food");

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_want:
                speak(tv_want.getText().toString());
                break;
            case R.id.tv_notgood:
                speak(tv_notgood.getText().toString());
                break;
            case R.id.tv_spicy:
                speak(tv_spicy.getText().toString());
                break;

        }

    }

    private void speak(String text)
    {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
        Toast.makeText(getContext(), text + " spoken.", Toast.LENGTH_SHORT).show();
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
