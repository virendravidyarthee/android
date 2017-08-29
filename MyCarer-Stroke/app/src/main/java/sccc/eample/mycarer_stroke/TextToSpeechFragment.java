package sccc.eample.mycarer_stroke;


import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextToSpeechFragment extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener {

    View view;
    FragmentTransaction fragmentTransaction;
    private EditText inputText;
    private Button clearButton, speakbutton, phrasesbutton, categoriesbutton;
    TextToSpeech tts;
    SQLiteHelper sqLiteHelper;
    String phrase;

    public TextToSpeechFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_text_to_speech, container, false);

        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);

        tts = new TextToSpeech(getContext(),this);

        inputText = (EditText) view.findViewById(R.id.et_inputText);

        clearButton = (Button) view.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(this);

        speakbutton = (Button) view.findViewById(R.id.speak_button);
        speakbutton.setOnClickListener(this);

        phrasesbutton = (Button) view.findViewById(R.id.addPhrases_button);
        phrasesbutton.setOnClickListener(this);

        categoriesbutton = (Button) view.findViewById(R.id.categories_button);
        categoriesbutton.setOnClickListener(this);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Text-To-Speech");


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear_button:
                inputText.setText("");
                break;

            case R.id.speak_button:
                String temp = inputText.getText().toString();
                speak(temp);
                break;

            case R.id.addPhrases_button:
                String text = inputText.getText().toString();
                if (text.matches("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Warning")
                            .setMessage("You left a required field empty")
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .show();
                }
                else {
                    addPhrase();
                }
                break;

            case R.id.categories_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new CategoriesFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;
        }

    }

    private void speak(String text)
    {
        if (text.isEmpty())
        {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Warning")
                    .setMessage("You left a required field empty")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();
        }
        else
        {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    public void addPhrase(){
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm")
                .setMessage("Are you sure you want to add the following phrases to your custom Category: \n" + inputText.getText().toString())
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        phrase = inputText.getText().toString();
                        sqLiteHelper.insertDataPhrases(phrase);
                        /*fragmentTransaction.addToBackStack(null);*/
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }


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
