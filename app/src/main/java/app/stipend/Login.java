package app.stipend;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Login extends AppCompatActivity {
EditText login_email,login_password;
    Button login_btn,login_btn_signup,login_btn_forgotpasword;
    ProgressDialog mProgressDialog;
    final String DONE="DONE";

    String student_email,student_password,student_name,student_mobile,field,ssc_marks,hsc_marks,year,college_marks,student_report,student_description;
    String company_email,company_name,company_password,company_number,company_number2,description,company_report,address,website;


    /*
     public Int64 company_number { get; set; }
    public string company_password { get; set; }
    public string website { get; set; }
    public long company_number2 { get; set; }
    public string description { get; set; }
    public int company_report { get; set; }
    public string address { get; set; }
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
         login_email= (EditText) findViewById(R.id.login_email);
         login_password= (EditText) findViewById(R.id.login_password);
         login_btn= (Button) findViewById(R.id.login_btn);
        login_btn_signup= (Button) findViewById(R.id.login_btn_signup);
         login_btn_forgotpasword= (Button) findViewById(R.id.login_btn_forgotpassword);

        login_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog signupDialog = new Dialog(Login.this);
                signupDialog.setContentView(R.layout.custom_signup);
                signupDialog.setTitle("Signup");
                signupDialog.show();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#101257")));
                Button popup_student = (Button) signupDialog.findViewById(R.id.popup_student);
                Button popup_institute = (Button) signupDialog.findViewById(R.id.popup_institute);
                popup_student.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent signupsIntent = new Intent(Login.this, Signup_Student.class);
                        startActivity(signupsIntent);
                    }
                });

                popup_institute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent signupclgIntent = new Intent(Login.this, Signup_Company.class);
                        startActivity(signupclgIntent);
                    }
                });
            }

        });



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(login_email.getText().equals("") || login_password.getText().equals(""))
{
    Snackbar.make(findViewById(R.id.lgin), "Please Enter All Details", Snackbar.LENGTH_LONG).show();
}



                final JSONObject LoginJsonObject=new JSONObject();
                StringEntity entity=null;
                try {
                    LoginJsonObject.put("id",login_email.getText()+"");
                    LoginJsonObject.put("pwd",login_password.getText()+"");
                    entity=new StringEntity(LoginJsonObject.toString());
                    AsyncHttpClient mClient=new AsyncHttpClient();
                    mClient.addHeader("Accept","application/json");
                    mClient.post(Login.this,"http://192.168.23.1:12345/webservice.asmx/login",entity,"application/json",new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try{
                                JSONArray mJsonArray=response.getJSONArray("d");
                                JSONObject nJsonObject=null;
                                //String a=nJsonObject.getString("fk_clg_id");



                                if(mJsonArray.length()==1)
                                {


                                    nJsonObject=mJsonArray.getJSONObject(0);
                                    student_email=nJsonObject.get("pk_student_email").toString();
                                    student_password=nJsonObject.get("student_password").toString();
                                    student_name=nJsonObject.get("student_name").toString();
                                    student_mobile=nJsonObject.get("student_mobile").toString();
                                    field=nJsonObject.get("field").toString();
                                    ssc_marks=nJsonObject.get("ssc_marks").toString();
                                    hsc_marks=nJsonObject.get("hsc_marks").toString();
                                    year=nJsonObject.get("year").toString();
                                    college_marks=nJsonObject.get("college_marks").toString();
                                    student_report=nJsonObject.get("student_report").toString();
                                    student_description=nJsonObject.get("student_description").toString();

                                    company_email=nJsonObject.get("pk_company_email").toString();
                                    company_password=nJsonObject.get("company_password").toString();
                                    company_name=nJsonObject.get("company_name").toString();
                                    company_number=nJsonObject.get("company_number").toString();
                                    company_number2=nJsonObject.get("company_number2").toString();
                                    description=nJsonObject.get("description").toString();
                                    address=nJsonObject.get("address").toString();
                                    company_report=nJsonObject.get("company_report").toString();
                                    website=nJsonObject.get("website").toString();

                                    /*
                                        String student_email,student_password,student_name,student_mobile,field,ssc_marks,hsc_marks,year,college_marks,student_report,student_description;
                                        String company_email,company_name,company_password,company_number,company_number2,description,company_report,address;
                                     */

                                    SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=mSharedPreferences.edit();
                                    editor.putString("login_status",DONE);
if(student_email.equals("null")) {
    editor.putString("company_email", company_email);
    editor.putString("company_password", company_password);
    editor.putString("company_name", company_name);
    editor.putString("company_number", company_number);
    editor.putString("company_number2", company_number2);
    editor.putString("description", description);
    editor.putString("company_report", company_report);
    editor.putString("address", address);
    editor.putString("website", website);
}
                                    else {
    editor.putString("student_email", student_email);
    editor.putString("student_password", student_password);
    editor.putString("student_name", student_name);
    editor.putString("student_mobile", student_mobile);
    editor.putString("field", field);
    editor.putString("ssc_marks", ssc_marks);
    editor.putString("hsc_marks", hsc_marks);
    editor.putString("year", year);
    editor.putString("college_marks", college_marks);
    editor.putString("student_report", student_report);
    editor.putString("student_description", student_description);
}

                                    editor.commit();

                                    if(company_email.equals("null")) {
                                        Intent studentIntent = new Intent(Login.this,StudentHome.class);
                                        startActivity(studentIntent);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent companyIntent = new Intent(Login.this,CompanyHome2.class);
                                        startActivity(companyIntent);
                                        finish();
                                    }

                                }

                                else
                                {
                                   // Toast.makeText(Login.this, "Invalid Id/password", Toast.LENGTH_LONG).show();

                                     Snackbar.make(findViewById(R.id.lgin), "Invalid Id/Password", Snackbar.LENGTH_LONG).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                             Snackbar.make(findViewById(R.id.lgin), "Check Your Internet Connection", Snackbar.LENGTH_LONG).show();

                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            mProgressDialog= ProgressDialog.show(Login.this, "Verifying", "Please Wait");
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            if(mProgressDialog.isShowing())
                            {
                                mProgressDialog.dismiss();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }





            }
        });

        login_btn_forgotpasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginForgot=new Intent(Login.this,ForgotPassword.class);
                startActivity(LoginForgot);
            }
        });
    }
}
