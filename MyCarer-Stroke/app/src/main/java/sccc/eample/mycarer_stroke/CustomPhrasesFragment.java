package sccc.eample.mycarer_stroke;


import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomPhrasesFragment extends Fragment implements TextToSpeech.OnInitListener{

    View view;
    SQLiteHelper sqLiteHelper;
    ListView listView1;
    TextToSpeech tts;

    public CustomPhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_custom_phrases, container, false);

        tts = new TextToSpeech(getContext(),this);

        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);
        populateListViewPhrases();
        listView1 = (ListView) view.findViewById(R.id.listview1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.tv_prhaseHolder);
                String text = ((TextView) view.findViewById(R.id.tv_prhaseHolder)).getText().toString();
                speak(text);
            }
        });


        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Custom Phrases");
        return view;
    }

    public void populateListViewPhrases(){
        Cursor cursor = sqLiteHelper.getAllPhrases();
        String[] fromPhrases = new String[]{"PHRASES"};
        int[] tv_id = new int[] {R.id.tv_prhaseHolder};
        SimpleCursorAdapter simpleCursorAdapter;
        simpleCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.manage_phrases, cursor, fromPhrases, tv_id, 0);
        listView1 = (ListView) view.findViewById(R.id.listview1);
        listView1.setAdapter(simpleCursorAdapter);
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
