package sccc.eample.mycarer_stroke;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static sccc.eample.mycarer_stroke.R.id.et_notes;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button update, scores;
    View view;
    FragmentTransaction fragmentTransaction;
    SQLiteHelper sqLiteHelper;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);

        Cursor userData = sqLiteHelper.getRowFromIDPatient();
        /*Cursor userData = sqLiteHelper.getRowFromID(2);*/
        if (userData.moveToNext()) {
            String name = userData.getString(1);
            String dob = userData.getString(2);
            String notes = userData.getString(3);
            byte[] imageUser = userData.getBlob(4);
            String ct1name = userData.getString(5);
            String ct1number = userData.getString(6);
            String ct2name = userData.getString(7);
            String ct2number = userData.getString(8);

            ImageView imageView = (ImageView) view.findViewById(R.id.userImage);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageUser, 0, imageUser.length);
            imageView.setImageBitmap(bitmap);

            TextView tv_dob = (TextView) view.findViewById(R.id.et_dob);
            tv_dob.setText("DOB: " + dob);

            TextView tv_notes = (TextView) view.findViewById(et_notes);
            tv_notes.setText("Notes: " + notes);

            TextView tv_ct1name = (TextView) view.findViewById(R.id.et_caretaker1name);
            tv_ct1name.setText("CareTaker(1) Name: " + ct1name);

            TextView tv_ct1number = (TextView) view.findViewById(R.id.et_caretaker1number);
            tv_ct1number.setText("CareTaker(1) Phone Number: " + ct1number);

            TextView tv_ct2name = (TextView) view.findViewById(R.id.et_caretaker2name);
            tv_ct2name.setText("CareTaker(2) Name: " + ct2name);

            TextView tv_ct2number = (TextView) view.findViewById(R.id.et_caretaker2number);
            tv_ct2number.setText("CareTaker(2) Phone Number: " + ct2number);

            scores = (Button) view.findViewById(R.id.bt_gotoscores);
            scores.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.home_container, new ScoresFragment());
                    fragmentTransaction.commit();
                    fragmentTransaction.addToBackStack(null);
                }
            });

            update = (Button) view.findViewById(R.id.bt_gotoupdate);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
                    startActivity(intent);
                }
            });

            ((HomeActivity)getActivity()).getSupportActionBar().setTitle(name);
        }

        /*((HomeActivity)getActivity()).getSupportActionBar().setTitle("Profile");*/
        /*boolean setVisibleToUser = true;
        setUserVisibleHint(setVisibleToUser);*/

        return view;
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId())
        {

        }*/
    }


    /*public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title
            ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Home");
        }
    }*/
}
