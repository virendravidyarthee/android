package sccc.eample.mycarer_stroke;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepressionFragment extends Fragment {


    View view;
    TextView tv_quotes;
    public DepressionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_depression, container, false);

        tv_quotes = (TextView) view.findViewById(R.id.tv_quotes);
        tv_quotes.setText("“It’s Not Whether You Get Knocked Down, It’s Whether You Get Up.”- Vince Lombardi \n\n" +
                "“The Way Get Started Is To Quit Talking And Begin Doing.” -Walt Disney \n \n" +
                "“The Pessimist Sees Difficulty In Every Opportunity. The Optimist Sees The Opportunity In Every Difficulty.” -Winston Churchill\n\n" +
                "“Don’t Let Yesterday Take Up Too Much Of Today.” -Will Rogers\n\n" +
                "\"Live long and prosper\" - Spock\n\n" +
                "“If You Are Working On Something That You Really Care About, You Don’t Have To Be Pushed. The Vision Pulls You.”- Steve Jobs \n\n"
        );

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Dealing With Depression");

        return view;
    }

}
