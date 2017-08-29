package sccc.eample.mycarer_stroke;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    FragmentTransaction fragmentTransaction;
    Button profile, tts, relatives, sos, strokeinfo, memorygame, physiotherapy, dwd, help1;
    View view;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        profile = (Button) view.findViewById(R.id.profile_button);
        profile.setOnClickListener(this);

        tts = (Button) view.findViewById(R.id.texttospeech_button);
        tts.setOnClickListener(this);

        relatives = (Button) view.findViewById(R.id.relatives_button);
        relatives.setOnClickListener(this);

        sos = (Button) view.findViewById(R.id.sos_button);
        sos.setOnClickListener(this);

        strokeinfo = (Button) view.findViewById(R.id.strokeinfo_button);
        strokeinfo .setOnClickListener(this);

        memorygame = (Button) view.findViewById(R.id.game_button);
        memorygame.setOnClickListener(this);

        physiotherapy = (Button) view.findViewById(R.id.physiotherapy_button);
        physiotherapy.setOnClickListener(this);

        help1 = (Button) view.findViewById(R.id.help_button);
        help1.setOnClickListener(this);

        dwd = (Button) view.findViewById(R.id.dwd_button);
        dwd.setOnClickListener(this);



        /*boolean setVisibleToUser = true;
        setUserVisibleHint(setVisibleToUser);*/
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Home");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.profile_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new ProfileFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                /*((HomeActivity)getActivity()).getSupportActionBar().setTitle("Profile");*/
                break;

            case R.id.texttospeech_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new TextToSpeechFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                /*((HomeActivity)getActivity()).getSupportActionBar().setTitle(R.string.tts_title);*/
                break;

            case R.id.relatives_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new RelationListFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                /*((HomeActivity)getActivity()).getSupportActionBar().setTitle("Relatives");*/
                break;

            case R.id.sos_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new SOSFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                /*((HomeActivity)getActivity()).getSupportActionBar().setTitle(R.string.sos_title);*/
                break;

            case R.id.strokeinfo_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new StrokeInfoFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case R.id.game_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new MemoryGameFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case R.id.physiotherapy_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new PhysiotherapyFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case R.id.dwd_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new DepressionFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case R.id.help_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new HelpFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;


        }
    }

   /* public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title
            ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Home");
        }
    }*/

}
