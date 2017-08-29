package sccc.eample.mycarer_stroke;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RelationshipListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<RelationObject> relationshipslist;


    public RelationshipListAdapter(Context context, int layout, ArrayList<RelationObject> relationshipslist){
        this.context = context;
        this.layout = layout;
        this.relationshipslist = relationshipslist;
    }
    @Override
    public int getCount() {
        return relationshipslist.size();
    }

    @Override
    public Object getItem(int position) {
        return relationshipslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;

    }


    private class ViewHolder{
        ImageView imageView;
        TextView txtname, txtrelationship;//txtnotes;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if ((row == null)){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtname = (TextView) row.findViewById(R.id.txtname);
            holder.imageView = (ImageView) row.findViewById(R.id.relationshippic);
            // holder.txtnotes = (TextView) row.findViewById(R.id.txtnotes);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }


        RelationObject relationship = relationshipslist.get(position);

        holder.txtname.setText(relationship.getName());
        //holder.txtnotes.setText(relationship.getNotes());
        byte[] relationshippic = relationship.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(relationshippic, 0, relationshippic.length);
        holder.imageView.setImageBitmap(bitmap);


        return row;
    }
}
