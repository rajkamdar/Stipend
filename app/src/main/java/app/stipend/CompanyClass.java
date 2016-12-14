package app.stipend;

/**
 * Created by Raj Kamdar on 11-03-2016.
 */
public class CompanyClass {
    String pk_company_email,company_name,company_number,company_password,website,company_number2,description,company_report,address;

    public CompanyClass(String pk_company_email, String company_name, String company_number, String company_password, String website, String company_number2, String description, String company_report, String address) {
        this.pk_company_email = pk_company_email;
        this.company_name = company_name;
        this.company_number = company_number;
        this.company_password = company_password;
        this.website = website;
        this.company_number2 = company_number2;
        this.description = description;
        this.company_report = company_report;
        this.address = address;
    }

    public String getPk_company_email() {
        return pk_company_email;
    }

    public void setPk_company_email(String pk_company_email) {
        this.pk_company_email = pk_company_email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_number() {
        return company_number;
    }

    public void setCompany_number(String company_number) {
        this.company_number = company_number;
    }

    public String getCompany_password() {
        return company_password;
    }

    public void setCompany_password(String company_password) {
        this.company_password = company_password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompany_number2() {
        return company_number2;
    }

    public void setCompany_number2(String company_number2) {
        this.company_number2 = company_number2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany_report() {
        return company_report;
    }

    public void setCompany_report(String company_report) {
        this.company_report = company_report;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*
     public string pk_company_email { get; set; }
    public string company_name { get; set; }
    public Int64 company_number { get; set; }
    public string company_password { get; set; }
    public string website { get; set; }
    public long company_number2 { get; set; }
    public string description { get; set; }
    public int company_report { get; set; }
    public string address { get; set; }

     */
}
