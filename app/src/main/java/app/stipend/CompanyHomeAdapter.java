package app.stipend;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;

/**
 * Created by admin on 1/24/2016.
 */
public class CompanyHomeAdapter extends BaseAdapter {

    Context companyContext;
    LayoutInflater companyInflater;
    ArrayList<CompanyHomeClass> companyHomeClassArrayList;


    public CompanyHomeAdapter(Context companyContext, ArrayList<CompanyHomeClass> companyHomeClassArrayList) {
        this.companyContext = companyContext;
        this.companyHomeClassArrayList = companyHomeClassArrayList;
        companyInflater= (LayoutInflater) companyContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return companyHomeClassArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return companyHomeClassArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CompanyHomeViewholder mViewholder;
        if(convertView==null)
        {
            convertView=companyInflater.inflate(R.layout.custom_companyhome_layout,null);
            mViewholder=new CompanyHomeViewholder((TextView)convertView.findViewById(R.id.homestudentname),(TextView)convertView.findViewById(R.id.homeposition),(TextView)convertView.findViewById(R.id.position),(TextView)convertView.findViewById(R.id.view));
            convertView.setTag(mViewholder);
        }
        else
        {
        mViewholder= (CompanyHomeViewholder) convertView.getTag();
        }

        mViewholder.getName().setText(companyHomeClassArrayList.get(position).student_name);
        mViewholder.getPosition().setText(companyHomeClassArrayList.get(position).post);
       // mViewholder.icon= new MaterialLetterIcon(companyContext);

      //  mViewholder.icon.setLetter(companyHomeClassArrayList.get(position).getStudent_name());
        String str=companyHomeClassArrayList.get(position).getStudent_name();

        mViewholder.getIcn().setText( " "+companyHomeClassArrayList.get(position).student_name.charAt(0));
       // mViewholder.icon.setLetter("P");
       // mViewholder.icon.setInitials(true);
      //  mViewholder.icon.setInitialsNumber(1);
    //    mViewholder.getIcon().
    //    mViewholder.getIcon().setShapeColor(Color.parseColor("#4167d1"));
        return convertView;
    }
}
