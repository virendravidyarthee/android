package sccc.eample.mycarer_stroke;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryGame3x4 extends Fragment implements View.OnClickListener {

    View view;
    private int numberOfElements;

    private MemoryButton[] buttons;
    private int[] buttonGraphicLocation;
    private int[] buttonGraphic;

    private  MemoryButton selectedButton1;
    private  MemoryButton selectedButton2;

    public int counter, score;
    TextView tv_score;

    SQLiteHelper sqLiteHelper;

    FragmentTransaction fragmentTransaction;

    GridLayout gridLayout;

    private boolean isBusy = false;

    public MemoryGame3x4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_memory_game3x4, container, false);

        sqLiteHelper = new SQLiteHelper(getActivity(), "DATABASE.sqlite", null, 1);

        counter = 0;
        score = 0;

        tv_score = (TextView) view.findViewById(R.id.tv_score);


        gridLayout = (GridLayout) view.findViewById(R.id.grid3x4);

        int numberRows = gridLayout.getRowCount();
        int numberColumn = gridLayout.getColumnCount();

        numberOfElements = numberColumn * numberRows;

        buttons = new MemoryButton[numberOfElements];

        buttonGraphic = new int[numberOfElements/2];

        buttonGraphic[0] = R.drawable.u1;
        buttonGraphic[1] = R.drawable.u2;
        buttonGraphic[2] = R.drawable.u3;
        buttonGraphic[3] = R.drawable.u4;
        buttonGraphic[4] = R.drawable.u5;
        buttonGraphic[5] = R.drawable.u6;

        buttonGraphicLocation = new int[numberOfElements];

        shuffleButtonGraphics();

        for(int r = 0; r < numberRows; r++)
        {
            for (int c = 0; c < numberColumn; c++)
            {
                MemoryButton tempButton = new MemoryButton(getContext(), r, c, buttonGraphic[buttonGraphicLocation[r* numberColumn + c]]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttons[r * numberColumn + c] = tempButton;
                gridLayout.addView(tempButton);
            }
        }

        if(counter==1){
            Toast.makeText(getContext(), "Game Over", Toast.LENGTH_LONG);
        }

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Memory Game - 3X4");
        return view;
    }

    protected void shuffleButtonGraphics(){
        Random rand = new Random();

        for(int i=0; i < numberOfElements; i++)
        {
            buttonGraphicLocation[i] = i % (numberOfElements/2);
        }

        for (int i=0; i < numberOfElements; i++)
        {
            int temp = buttonGraphicLocation[i];
            int swapIndex = rand.nextInt(12);
            buttonGraphicLocation[i] = buttonGraphicLocation[swapIndex];
            buttonGraphicLocation[swapIndex] = temp;
        }
    }

    @Override
    public void onClick(View view) {
        if (isBusy)
            return;

        MemoryButton button = (MemoryButton) view;
        if(button.isMatched)
            return;

        if(selectedButton1 == null)
        {
            selectedButton1 = button;
            selectedButton1.flip();
            return;
        }

        if (selectedButton1.getId() == button.getId())
        {
            return;
        }

        if(selectedButton1.getFrontId() == button.getFrontId())
        {
            button.flip();

            button.setMatched(true);
            counter++;
            score++;
            tv_score.setText("Matches Found: "+Integer.toString(counter)+"/6                                     Score:" +Integer.toString(score));

            if(counter==6){
                showalert();
            }

            selectedButton1.setMatched(true);

            selectedButton1.setEnabled(false);
            button.setEnabled(false);

            selectedButton1 = null;

            return;
        }

        else
        {
            selectedButton2 = button;
            selectedButton2.flip();
            score++;
            tv_score.setText("Matches Found: "+Integer.toString(counter)+"/6                                     Score:" +Integer.toString(score));
            isBusy = true;

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2.flip();
                    selectedButton1.flip();
                    selectedButton2 = null;
                    selectedButton1 = null;
                    isBusy = false;
                }
            }, 500);
        }
    }

    public void showalert(){

        String formattedDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        String scores = "Score = " + Integer.toString(score);
        sqLiteHelper.insertDataScore3x4(scores, formattedDate);
        new AlertDialog.Builder(getContext())
                .setTitle("Game Over")
                .setMessage("Would you like to restart? \n Your Score - " + Integer.toString(score) )
                .setNegativeButton("Return to Home", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new HomeFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);

                    }
                })
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new MemoryGame3x4());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*fragmentTransaction.addToBackStack(null);*/
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }
}
