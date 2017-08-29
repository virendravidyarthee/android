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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    EditText et_name, et_dob, et_notes, et_caretaker1name, et_caretaker1number, et_caretaker2name, et_caretaker2number;
    ImageView imageView;
    Button bt_choose, bt_register;
    final int REQUEST_CODE_GALLERY = 999;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteHelper = new SQLiteHelper(this, "DATABASE.sqlite", null, 1);

        Cursor checkRegister = sqLiteHelper.getRowFromIDPatient();
        if(checkRegister.getCount() != 0)
        {
            Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_register);



        et_name = (EditText) findViewById(R.id.et_name);
        et_dob = (EditText) findViewById(R.id.et_dob);
        et_notes = (EditText) findViewById(R.id.et_notes);
        et_caretaker1name = (EditText) findViewById(R.id.et_caretaker1name);
        et_caretaker1number = (EditText) findViewById(R.id.caretaker1number);
        et_caretaker2name = (EditText) findViewById(R.id.et_caretaker2name);
        et_caretaker2number = (EditText) findViewById(R.id.caretaker2number);
        bt_choose = (Button) findViewById(R.id.bt_choose);
        bt_register = (Button) findViewById(R.id.bt_register);
        imageView = (ImageView) findViewById(R.id.imageView);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String dob = et_dob.getText().toString();
                String caretakername = et_caretaker1name.getText().toString();
                String caretakernumber = et_caretaker1number.getText().toString();

                if (name.matches("") || dob.matches("") || caretakername.matches("") || caretakernumber.matches("")) {
                    new AlertDialog.Builder(RegisterActivity.this)
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
                else {
                    try {
                        sqLiteHelper.insertDataPatient(
                                et_name.getText().toString().trim(),
                                et_dob.getText().toString().trim(),
                                et_notes.getText().toString().trim(),
                                imageViewToByte(imageView),
                                et_caretaker1name.getText().toString().trim(),
                                et_caretaker1number.getText().toString().trim(),
                                et_caretaker2name.getText().toString().trim(),
                                et_caretaker2number.getText().toString().trim()



                        );
                    /*Toast.makeText(getContext(),"Added Successfully!", Toast.LENGTH_SHORT).show();*/

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Not Successful", Toast.LENGTH_SHORT).show();
                    }
                    showalert();
                }
            }
        });

        bt_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_GALLERY);
            }
        });


    }

    public void showalert(){
        new AlertDialog.Builder(this)
                .setTitle("Registered")
                .setMessage("You have successful been registered!")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
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
                Toast.makeText(getApplicationContext(), "You Don't have permission to access file location!", Toast.LENGTH_SHORT).show();
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
                InputStream inputstream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}

