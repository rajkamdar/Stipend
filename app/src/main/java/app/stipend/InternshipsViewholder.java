package app.stipend;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Raj Kamdar on 11-03-2016.
 */
public class InternshipsViewholder {

    ImageView iv;
    TextView tvname,tvpost,tvduration,tvcity;

    public InternshipsViewholder(ImageView iv, TextView tvname, TextView tvpost, TextView tvduration, TextView tvcity) {
        this.iv = iv;
        this.tvname = tvname;
        this.tvpost = tvpost;
        this.tvduration = tvduration;
        this.tvcity = tvcity;
    }

    public ImageView getIv() {
        return iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }

    public TextView getTvname() {
        return tvname;
    }

    public void setTvname(TextView tvname) {
        this.tvname = tvname;
    }

    public TextView getTvpost() {
        return tvpost;
    }

    public void setTvpost(TextView tvpost) {
        this.tvpost = tvpost;
    }

    public TextView getTvduration() {
        return tvduration;
    }

    public void setTvduration(TextView tvduration) {
        this.tvduration = tvduration;
    }

    public TextView getTvcity() {
        return tvcity;
    }

    public void setTvcity(TextView tvcity) {
        this.tvcity = tvcity;
    }
}
