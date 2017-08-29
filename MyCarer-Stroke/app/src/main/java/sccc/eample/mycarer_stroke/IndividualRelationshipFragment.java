package sccc.eample.mycarer_stroke;


import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class IndividualRelationshipFragment extends Fragment implements View.OnClickListener {

    View view;
    FragmentTransaction fragmentTransaction;
    String id;
    Button bt_delete, bt_update;
    SQLiteHelper sqLiteHelper;
    public IndividualRelationshipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_individual_relationship, container, false);

        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);

        bt_delete = (Button) view.findViewById(R.id.deleteRelativeButton);
        bt_delete.setOnClickListener(this);

        bt_update = (Button) view.findViewById(R.id.updateRelativeButton);
        bt_update.setOnClickListener(this);

        Bundle bundle = getArguments();
        id = bundle.getString("ID");



        /*String id = getActivity().getIntent().getStringExtra("ID");*/



        //MainActivity.sqLiteHelper.getAllData();
        Cursor relativeData = sqLiteHelper.getRowFromID(Integer.parseInt(id));
        if (relativeData.moveToNext()) {
            String name = relativeData.getString(1);
            String relationship = relativeData.getString(2);
            byte[] imageRelative = relativeData.getBlob(3);
            String description = relativeData.getString(4);
            String dobrelative = relativeData.getString(5);

            TextView relation = (TextView) view.findViewById(R.id.relation);
            relation.setText("Relationship: " + relationship);

            TextView relativeInfo = (TextView) view.findViewById(R.id.relativeInfo);
            relativeInfo.setText("Notes: " + description);

            TextView dob = (TextView) view.findViewById(R.id.relativedob);
            dob.setText("DOB: " + dobrelative);

            ImageView imageView = (ImageView) view.findViewById(R.id.relativePicture);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageRelative, 0, imageRelative.length);
            imageView.setImageBitmap(bitmap);

            ((HomeActivity)getActivity()).getSupportActionBar().setTitle(name);
        }



        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.deleteRelativeButton:
                showalert();
                break;
            case R.id.updateRelativeButton:
                final Bundle bundle1 = new Bundle();
                bundle1.putString("IDD", id);
                fragmentTransaction = getFragmentManager().beginTransaction();
                UpdateRelativeFragment updateRelativeFragment = new UpdateRelativeFragment();
                updateRelativeFragment.setArguments(bundle1);
                fragmentTransaction.replace(R.id.home_container, updateRelativeFragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
        }
    }

    public void showalert(){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Relative")
                .setMessage("Are you sure you want to delete relative?")
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sqLiteHelper.deleteRelative(Integer.parseInt(id));
                        RelationListFragment.adaptor.notifyDataSetChanged();
                        fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new RelationListFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

}
