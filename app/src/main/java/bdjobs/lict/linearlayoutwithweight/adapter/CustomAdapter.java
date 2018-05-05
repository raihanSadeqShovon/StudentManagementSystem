package bdjobs.lict.linearlayoutwithweight.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bdjobs.lict.linearlayoutwithweight.R;
import bdjobs.lict.linearlayoutwithweight.model.Student;



public class CustomAdapter extends BaseAdapter {
    private static final String TAG = CustomAdapter.class.getSimpleName();

    public Context context;
    ArrayList<Student> arrayList;

    public CustomAdapter(Context c, ArrayList<Student> arrList){
        this.context= c;
        this.arrayList= arrList;
        Log.d(TAG, String.valueOf(arrList.size()));
    }

    @Override
    public int getCount() {

        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View custom_layout= inflater.inflate(R.layout.custom_list_item, null);

        TextView tVUserName= custom_layout.findViewById(R.id.textViewName);
        TextView tVCgpa= custom_layout.findViewById(R.id.textViewCGPA);
        ImageView callIcon = custom_layout.findViewById(R.id.imageViewCall);

        tVUserName.setText("User Name= " + arrayList.get(position).getUserName());
        tVCgpa.setText("Cgpa= " + arrayList.get(position).getCGpa());

        callIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String uri = arrayList.get(position).getPhoneNo();
                intent.setData(Uri.parse("tel:"+ uri));
                context.startActivity(intent);
            }
        });

        return custom_layout;
    }
}
