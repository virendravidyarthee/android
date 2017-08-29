package sccc.eample.mycarer_stroke;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateRelativeFragment extends Fragment{

    View view;
    FragmentTransaction fragmentTransaction;
    SQLiteHelper sqLiteHelper;
    EditText et_name, et_relation, et_notes, et_dob;
    Button btnChoose, btnUpdate;
    ImageView imageView;
    String id;
    final int REQUEST_CODE_GALLERY = 999;


    public UpdateRelativeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_relative, container, false);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Update Relative");
        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);

        Bundle bundle1 = getArguments();
        id = bundle1.getString("IDD");

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_relation = (EditText) view.findViewById(R.id.et_relation);
        et_notes = (EditText) view.findViewById(R.id.et_notes);
        et_dob = (EditText) view.findViewById(R.id.et_dob);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        Cursor userData = sqLiteHelper.getRowFromID(Integer.parseInt(id));
        /*Cursor userData = sqLiteHelper.getRowFromID(2);*/
        if (userData.moveToNext()) {
            String name = userData.getString(1);
            String relationship = userData.getString(2);
            byte[] imageRelative = userData.getBlob(3);
            String description = userData.getString(4);
            String dobrelative = userData.getString(5);

            Bitmap bitmap = BitmapFactory.decodeByteArray(imageRelative, 0, imageRelative.length);
            imageView.setImageBitmap(bitmap);

            et_name.setText(name);

            et_dob.setText(dobrelative);

            et_relation.setText(relationship);

            et_notes.setText(description);
        }

            btnChoose = (Button) view.findViewById(R.id.bt_choose);
            btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_GALLERY);
                }
            });


            btnUpdate = (Button) view.findViewById(R.id.bt_updateR);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = et_name.getText().toString();
                    String dob = et_dob.getText().toString();
                    String notes = et_notes.getText().toString();

                    if (name.matches("") || dob.matches("") || notes.matches("")) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Warning")
                                .setMessage("You left a required field empty")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setCancelable(false)
                                .show();
                    } else {
                        try {
                            sqLiteHelper.updateDataRelative(
                                    et_name.getText().toString().trim(),
                                    et_relation.getText().toString().trim(),
                                    imageViewToByte(imageView),
                                    et_notes.getText().toString().trim(),
                                    et_dob.getText().toString().trim(),
                                    Integer.parseInt(id)
                            );
                    /*Toast.makeText(getContext(),"Added Successfully!", Toast.LENGTH_SHORT).show();*/

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Not Successful", Toast.LENGTH_SHORT).show();
                        }
                        showalert();
                    }
                }
            });


        return view;
        }

    public void showalert(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Successful")
                .setMessage("Relative's profile has successful been Updated!")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
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

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getContext(), "You Don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputstream = getActivity().getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}
