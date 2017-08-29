package sccc.eample.mycarer_stroke;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.Button;
import android.widget.GridLayout;

public class MemoryButton extends Button {

    protected int row;
    protected int column;
    protected int frontId;

    protected boolean isFipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;

    public MemoryButton(Context context, int r, int c, int frontImageId)
    {
        super(context);

        row = r;
        column = c;
        frontId = frontImageId;

        front = AppCompatDrawableManager.get().getDrawable(context, frontImageId);

        back = AppCompatDrawableManager.get().getDrawable(context, R.drawable.unknown);
        setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));

        tempParams.width = (int) getResources().getDisplayMetrics().density*140;
        tempParams.height = (int) getResources().getDisplayMetrics().density*200;

        setLayoutParams(tempParams);
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getFrontId() {
        return frontId;
    }

    public void flip() {
        if(isMatched)
            return;
        if(isFipped){
            setBackground(back);
            isFipped = false;
        }
        else
        {
            setBackground(front);
            isFipped = true;
        }
    }

}
