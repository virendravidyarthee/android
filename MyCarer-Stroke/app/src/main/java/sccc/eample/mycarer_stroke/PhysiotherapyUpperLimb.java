package sccc.eample.mycarer_stroke;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhysiotherapyUpperLimb extends Fragment {


    public PhysiotherapyUpperLimb() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Physiotherapy - Upper Limb");
        return inflater.inflate(R.layout.fragment_physiotherapy_upper_limb, container, false);
    }

}
