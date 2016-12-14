package app.stipend;

/**
 * Created by admin on 1/24/2016.
 */
public class CompanyHomeClass {

    String pk_internship_id,post,pk_student_email,student_name,student_mobile,field,ssc_marks,hsc_marks,year,college_marks,student_report,student_description;


    public CompanyHomeClass(String post, String student_name) {
        this.post = post;
        this.student_name = student_name;
    }

    public CompanyHomeClass(String pk_internship_id,String post, String pk_student_email, String student_name, String student_mobile, String field, String ssc_marks, String hsc_marks, String year, String college_marks, String student_report, String student_description) {
        this.pk_internship_id = pk_internship_id;
        this.post = post;
        this.pk_student_email = pk_student_email;
        this.student_name = student_name;
        this.student_mobile = student_mobile;
        this.field = field;
        this.ssc_marks = ssc_marks;
        this.hsc_marks = hsc_marks;
        this.year = year;
        this.college_marks = college_marks;
        this.student_report = student_report;
        this.student_description = student_description;
    }

    public String getPk_internship_id() {
        return pk_internship_id;
    }

    public void setPk_internship_id(String pk_internship_id) {
        this.pk_internship_id = pk_internship_id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPk_student_email() {
        return pk_student_email;
    }

    public void setPk_student_email(String pk_student_email) {
        this.pk_student_email = pk_student_email;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_mobile() {
        return student_mobile;
    }

    public void setStudent_mobile(String student_mobile) {
        this.student_mobile = student_mobile;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSsc_marks() {
        return ssc_marks;
    }

    public void setSsc_marks(String ssc_marks) {
        this.ssc_marks = ssc_marks;
    }

    public String getHsc_marks() {
        return hsc_marks;
    }

    public void setHsc_marks(String hsc_marks) {
        this.hsc_marks = hsc_marks;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCollege_marks() {
        return college_marks;
    }

    public void setCollege_marks(String college_marks) {
        this.college_marks = college_marks;
    }

    public String getStudent_report() {
        return student_report;
    }

    public void setStudent_report(String student_report) {
        this.student_report = student_report;
    }

    public String getStudent_description() {
        return student_description;
    }

    public void setStudent_description(String student_description) {
        this.student_description = student_description;
    }
}


