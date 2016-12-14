package app.stipend;

/**
 * Created by Raj Kamdar on 11-03-2016.
 */
public class InternshipsClass {

    String pk_internship_id,post,details,stipend,duration,fk_company_email,status,city,internship_field;
    String company_name,description,website,address;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InternshipsClass(String pk_internship_id, String post, String details, String stipend, String duration, String fk_company_email, String status, String city, String internship_field, String company_name, String description, String website, String address) {
        this.pk_internship_id = pk_internship_id;
        this.post = post;
        this.details = details;
        this.stipend = stipend;
        this.duration = duration;
        this.fk_company_email = fk_company_email;
        this.status = status;
        this.city = city;
        this.internship_field = internship_field;
        this.company_name = company_name;
        this.description = description;
        this.website = website;
        this.address = address;
    }

    public InternshipsClass(String pk_internship_id, String post, String details, String stipend, String duration, String fk_company_email, String status, String city, String internship_field) {
        this.pk_internship_id = pk_internship_id;
        this.post = post;
        this.details = details;
        this.stipend = stipend;
        this.duration = duration;
        this.fk_company_email = fk_company_email;
        this.status = status;
        this.city = city;
        this.internship_field = internship_field;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStipend() {
        return stipend;
    }

    public void setStipend(String stipend) {
        this.stipend = stipend;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFk_company_email() {
        return fk_company_email;
    }

    public void setFk_company_email(String fk_company_email) {
        this.fk_company_email = fk_company_email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInternship_field() {
        return internship_field;
    }

    public void setInternship_field(String internship_field) {
        this.internship_field = internship_field;
    }

    /*
       public int pk_internship_id { get; set; }
    public string post { get; set; }
    public string details { get; set; }
    public long stipend { get; set; }
    public int duration { get; set; }
    public string fk_company_email { get; set; }
    public string status { get; set; }
    public string city { get; set; }
    public string internship_field { get; set; }

     */
}
