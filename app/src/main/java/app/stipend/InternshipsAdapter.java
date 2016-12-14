package app.stipend;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Raj Kamdar on 11-03-2016.
 */
public class InternshipsAdapter extends BaseAdapter {

    Context iContext;
    LayoutInflater iLayoutInflater;
    ArrayList<InternshipsClass> mArrayList;

    public InternshipsAdapter(Context iContext, ArrayList<InternshipsClass> mArrayList) {
        this.iContext = iContext;
        this.mArrayList = mArrayList;
        iLayoutInflater= (LayoutInflater) iContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InternshipsViewholder iv;
        if(convertView==null)
        {
            convertView=iLayoutInflater.inflate(R.layout.custom_internships,null);
            iv=new InternshipsViewholder((ImageView)convertView.findViewById(R.id.ci_iv),(TextView)convertView.findViewById(R.id.ci_cname),(TextView)convertView.findViewById(R.id.ci_post),(TextView)convertView.findViewById(R.id.ci_duration),(TextView)convertView.findViewById(R.id.ci_city));
            convertView.setTag(iv);
        }
        else
        {
            iv= (InternshipsViewholder) convertView.getTag();
        }

        iv.getTvname().setText(mArrayList.get(position).getCompany_name());
        iv.getTvcity().setText(mArrayList.get(position).getCity());
        iv.getTvpost().setText(mArrayList.get(position).getPost());
        iv.getTvduration().setText(mArrayList.get(position).getDuration()+" Month/s");
if(mArrayList.get(position).getInternship_field().equals("Information Technology")) {
    iv.getIv().setImageResource(R.drawable.ia);

}
        else if(mArrayList.get(position).getInternship_field().equals("Management")) {
            iv.getIv().setImageResource(R.drawable.mgt);
        }
else if(mArrayList.get(position).getInternship_field().equals("Science")) {
    iv.getIv().setImageResource(R.drawable.event);
}
else if(mArrayList.get(position).getInternship_field().equals("Accounting/Commerce")) {
    iv.getIv().setImageResource(R.drawable.acc);
}
else if(mArrayList.get(position).getInternship_field().equals("Arts and Literature")) {
    iv.getIv().setImageResource(R.drawable.arts);
}
else if(mArrayList.get(position).getInternship_field().equals("Medical")) {
    iv.getIv().setImageResource(R.drawable.medical);
}
else if(mArrayList.get(position).getInternship_field().equals("Pharmacy")) {
    iv.getIv().setImageResource(R.drawable.pharmacy);
}
else if(mArrayList.get(position).getInternship_field().equals("Liberal Studies")) {
    iv.getIv().setImageResource(R.drawable.liberal);
}
else if(mArrayList.get(position).getInternship_field().equals("Mass")) {
    iv.getIv().setImageResource(R.drawable.mass);
}
else  {
    iv.getIv().setImageResource(R.drawable.gen);
}

        return convertView;
    }

}
