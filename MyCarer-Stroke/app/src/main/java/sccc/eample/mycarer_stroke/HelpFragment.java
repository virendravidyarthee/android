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
public class HelpFragment extends Fragment implements View.OnClickListener {

    View view;
    FragmentTransaction fragmentTransaction;
    Button adding;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);

        adding = (Button) view.findViewById(R.id.helpAdding);
        adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new HelpAddingFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });


        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Help");
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
