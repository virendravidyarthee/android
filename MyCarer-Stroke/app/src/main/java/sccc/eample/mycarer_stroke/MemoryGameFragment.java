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
public class MemoryGameFragment extends Fragment {

    View view;
    FragmentTransaction fragmentTransaction;
    Button bt_4x4, bt_3x4;
    public MemoryGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_memory_game, container, false);

        bt_4x4 = (Button) view.findViewById(R.id.button_4x4);
        bt_4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new MemoryGameRandom());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        bt_3x4 = (Button) view.findViewById(R.id.button_3x4);
        bt_3x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new MemoryGame3x4());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Memory Game");

        return view;
    }


}
