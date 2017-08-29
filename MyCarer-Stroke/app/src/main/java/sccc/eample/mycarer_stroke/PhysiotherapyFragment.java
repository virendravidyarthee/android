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
public class PhysiotherapyFragment extends Fragment implements View.OnClickListener {

    View view;
    FragmentTransaction fragmentTransaction;
    Button bt_elbow, bt_upperLimb, bt_lowerLimb;
    public PhysiotherapyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_physiotherapy, container, false);

        bt_elbow = (Button) view.findViewById(R.id.bt_elbow);
        bt_elbow.setOnClickListener(this);

        bt_upperLimb = (Button) view.findViewById(R.id.bt_upperLimb);
        bt_upperLimb.setOnClickListener(this);

        bt_lowerLimb = (Button) view.findViewById(R.id.bt_lowerLimb);
        bt_lowerLimb.setOnClickListener(this);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Physiotherapy");

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_elbow:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new PhysiotherapyElbow());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;
            case R.id.bt_upperLimb:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new PhysiotherapyUpperLimb());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;
            case R.id.bt_lowerLimb:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new PhysiotherapyLowerLimb());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;
        }

    }
}
