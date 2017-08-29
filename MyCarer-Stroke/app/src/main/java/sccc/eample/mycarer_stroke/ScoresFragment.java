package sccc.eample.mycarer_stroke;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoresFragment extends Fragment implements View.OnClickListener {

    View view;
    Button bt_3x4, bt_4x4;
    SQLiteHelper sqLiteHelper;
    ListView listView;

    public ScoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scores, container, false);

        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);

        bt_3x4 = (Button) view.findViewById(R.id.bt_3x4);
        bt_3x4.setOnClickListener(this);

        bt_4x4 = (Button) view.findViewById(R.id.bt_4x4);
        bt_4x4.setOnClickListener(this);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Scores");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_3x4:
                populateListViewXXX();
                return;
            case R.id.bt_4x4:
                populateListViewXXXX();
                return;
        }

    }

    private void populateListViewXXX(){
        Cursor cursor = sqLiteHelper.getAllScoresXXX();
        String[] fromScores = new String[]{"SCORE", "DATE"};
        int[] tv_id = new int[] {R.id.tv_scoreHolder, R.id.tv_dateHolder};
        SimpleCursorAdapter simpleCursorAdapter;
        simpleCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.manage_scores, cursor, fromScores, tv_id, 0);
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(simpleCursorAdapter);
    }

    private void populateListViewXXXX(){
        Cursor cursor = sqLiteHelper.getAllScoresXXXX();
        String[] fromScores = new String[]{"SCORE", "DATE"};
        int[] tv_id = new int[] {R.id.tv_scoreHolder, R.id.tv_dateHolder};
        SimpleCursorAdapter simpleCursorAdapter;
        simpleCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.manage_scores, cursor, fromScores, tv_id, 0);
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(simpleCursorAdapter);
    }
}
