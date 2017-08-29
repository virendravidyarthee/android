package sccc.eample.mycarer_stroke;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
public class RelativesAddFragment extends Fragment implements View.OnClickListener{


    View view;
    FragmentTransaction fragmentTransaction;
    EditText et_name, et_relation, et_notes, et_dob;
    Button btnChoose, btnAdd, btnView;
    ImageView imageView;
    final int REQUEST_CODE_GALLERY = 999;

    public static SQLiteHelper sqLiteHelper;

    public RelativesAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_relatives_add, container, false);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Add Relatives");

        /*if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            button.setEnabled(false);
        } else {
        }*/

        init();

        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);

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
        btnAdd = (Button) view.findViewById(R.id.bt_add);
        btnAdd.setOnClickListener(this);
        btnView = (Button) view.findViewById(R.id.bt_view);
        btnView.setOnClickListener(this);


        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Add Relatives");
        return view;
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

    private void init(){
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_relation = (EditText) view.findViewById(R.id.et_relation);
        et_notes = (EditText) view.findViewById(R.id.et_notes);
        et_dob = (EditText) view.findViewById(R.id.et_dob);
        btnAdd = (Button) view.findViewById(R.id.bt_add);
        btnChoose = (Button) view.findViewById(R.id.bt_choose);
        imageView = (ImageView) view.findViewById(R.id.imageView);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_add:
                String name = et_name.getText().toString();
                String dob = et_dob.getText().toString();
                String relation = et_relation.getText().toString();
                if (name.matches("") || dob.matches("") || relation.matches("")) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Warning")
                            .setMessage("You left a required field empty")
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .show();
                }
                else
                {
                    try{
                        sqLiteHelper.insertData(
                                et_name.getText().toString().trim(),
                                et_relation.getText().toString().trim(),
                                imageViewToByte(imageView),
                                et_notes.getText().toString().trim(),
                                et_dob.getText().toString().trim()
                        );
                    /*Toast.makeText(getContext(),"Added Successfully!", Toast.LENGTH_SHORT).show();*/
                        et_name.setText("");
                        et_dob.setText("");
                        et_relation.setText("");
                        imageView.setImageResource(R.drawable.ic_face_black_48dp);
                        et_notes.setText("");
                        et_dob.setText("");
                        showalert();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    /*Toast.makeText(getContext(),"Not Successful", Toast.LENGTH_SHORT).show();*/
                    }
                }

                break;
            case R.id.bt_view:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_container, new RelationListFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("Home");*/
                break;
        }

    }
    public void showalert(){
        new AlertDialog.Builder(getContext())
                .setTitle("User Added")
                .setMessage("Do you want to add another relative?")
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setPositiveButton("no", new DialogInterface.OnClickListener() {
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
}
