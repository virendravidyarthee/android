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


public class CategoriesFragment extends Fragment implements View.OnClickListener {

    FragmentTransaction fragmentTransaction;
    Button intro, food, custom;
    View view;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories, container, false);

        intro = (Button) view.findViewById(R.id.intro_button);
        intro.setOnClickListener(this);

        food = (Button) view.findViewById(R.id.food_button);
        food.setOnClickListener(this);

        custom = (Button) view.findViewById(R.id.custom_button);
        custom.setOnClickListener(this);


        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Text-To-Speech - Categories");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.intro_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new Intro_ttsFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;
            case R.id.food_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new Food_ttsFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;
            case R.id.custom_button:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new CustomPhrasesFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;
        }

    }
}
