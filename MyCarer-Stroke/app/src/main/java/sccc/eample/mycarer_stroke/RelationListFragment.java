package sccc.eample.mycarer_stroke;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RelationListFragment extends Fragment implements View.OnClickListener {

    GridView gridView;
    ArrayList<RelationObject> list;
    public static RelationshipListAdapter adaptor = null;
    FragmentTransaction fragmentTransaction;
    SQLiteHelper sqLiteHelper;
    View view;

    public RelationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_relation_list, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fbt_add);
        floatingActionButton.setOnClickListener(this);

        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);
        gridView = (GridView) view.findViewById(R.id.gridView);
        list = new ArrayList<>();
        adaptor = new RelationshipListAdapter(getContext(), R.layout.activity_relationship_list_adapter, list);
        gridView.setAdapter(adaptor);


        // get all data from Relatives table
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM RELATIVES");
        list.clear();;
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(3);
            //String notes = cursor.getString(4);

            list.add(new RelationObject(name, image, /*notes,*/id));
        }

        /*gridView.setOnClickListener(new  AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(RelationshipList.this, singleRelationship.class);
                intent.putExtra("relationship", gridView.getItemIdAtPosition(i).toString());
                startActivity(intent);

            }
        });*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Toast.makeText(getActivity(), "Blah", Toast.LENGTH_SHORT).show();*/
                RelationObject relationObject = (RelationObject)gridView.getItemAtPosition(i);
                int temp = relationObject.getId();

                final Bundle bundle = new Bundle();
                bundle.putString("ID", Integer.toString(temp));

                /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();*/
                fragmentTransaction = getFragmentManager().beginTransaction();
                IndividualRelationshipFragment individualRelationshipFragment = new IndividualRelationshipFragment();
                individualRelationshipFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.home_container, individualRelationshipFragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });



        adaptor.notifyDataSetChanged();


        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Relatives");
        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fbt_add:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new RelativesAddFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                break;
        }

    }
}
