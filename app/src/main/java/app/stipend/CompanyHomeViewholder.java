package app.stipend;

import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

/**
 * Created by admin on 1/24/2016.
 */
public class CompanyHomeViewholder {

    TextView name,position,position1,icn;
MaterialLetterIcon icon;

    public TextView getIcn() {
        return icn;
    }

    public void setIcn(TextView icn) {
        this.icn = icn;
    }
/*  public MaterialLetterIcon getIcon() {
        return icon;
    }

    public void setIcon(MaterialLetterIcon icon) {
        this.icon = icon;
    }*/

    public CompanyHomeViewholder(TextView name, TextView position, TextView position1, MaterialLetterIcon icon) {
        this.name = name;
        this.position = position;
        this.position1 = position1;
        this.icon = icon;
    }

    public CompanyHomeViewholder(TextView name, TextView position,TextView position1) {
        this.name = name;
        this.position = position;
        this.position1=position1;
    }

    public CompanyHomeViewholder(TextView name, TextView position, TextView position1, TextView icn) {
        this.name = name;
        this.position = position;
        this.position1 = position1;
        this.icn = icn;
    }

    public TextView getName() {
        return name;
    }


    public TextView getPosition() {
        return position;
    }

    public TextView getPosition1() {
        return position1;
    }
}
