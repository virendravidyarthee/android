package sccc.eample.mycarer_stroke;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SOSFragment extends Fragment implements View.OnClickListener {

    Button sos;
    View view;
    FragmentTransaction fragmentTransaction;
    SQLiteHelper sqLiteHelper;
    String ct1number, ct2number;
    Button button;
    TextView textView;

    public SOSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_so, container, false);
       /* ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},1);*/
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
        } else {
        }

        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);

        Cursor getPhone = sqLiteHelper.getRowFromIDPatient();
        /*Cursor userData = sqLiteHelper.getRowFromID(2);*/
        if (getPhone.moveToNext()) {
            ct1number = getPhone.getString(6);
            ct2number = getPhone.getString(8);
        }

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("SOS");

        sos = (Button) view.findViewById(R.id.sos_button);
        sos.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.sos_button:
                sendSMS(ct1number, "I AM IN NEED OF HELP!!");
                String ct2 = ct2number.toString();
                if(ct2.matches(""))
                {
                }
                else {
                    sendSMS(ct2number, "I AM IN NEED OF HELP!!");
                }

                showalert();
        }

    }

    public void showalert(){
        new AlertDialog.Builder(getContext())
                .setTitle("Sent")
                .setMessage("Your message has been sent")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

    public void sendSMS(String number, String body)
    {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, body, null, null);
        }catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
