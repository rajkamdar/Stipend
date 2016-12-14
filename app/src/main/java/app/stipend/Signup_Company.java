package app.stipend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Signup_Company extends AppCompatActivity {

    TextView cdetails;
    EditText cname,location,aboutcompany,mno,mno1,website,cemail,cpwd;
    Button register;
    ProgressDialog mProgressDialog;
    int cnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__company);

        cdetails= (TextView) findViewById(R.id.signup_cdetails);
        cname= (EditText) findViewById(R.id.signup_cname);
        location= (EditText) findViewById(R.id.signup_location);
        aboutcompany= (EditText) findViewById(R.id.signup_aboutcompany);
        mno= (EditText) findViewById(R.id.signup_cmno);
        mno1= (EditText) findViewById(R.id.signup_mno1);
        website= (EditText) findViewById(R.id.signup_website);
        register= (Button) findViewById(R.id.cregister);
        cemail= (EditText) findViewById(R.id.signup_cemail);
        cpwd= (EditText) findViewById(R.id.signup_cpwd);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00868B")));


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JSONObject studentJsonObject=new JSONObject();
                StringEntity studentEntity=null;
                for(int i=0;i<cemail.getText().length();i++)
                {
                    if(cemail.getText().charAt(i)=='@')
                    {
                        cnt++;
                    }
                }
                if(cname.getText().length()==0)
                {
                    Snackbar.make(findViewById(R.id.signupcompanies), "Please Insert Name", Snackbar.LENGTH_LONG).show();
                }
                else if(cpwd.getText().length()>10 || cpwd.getText().length()==0)
                {
                    Snackbar.make(findViewById(R.id.signupcompanies), "Enter a valid password between 1 to 10 characters", Snackbar.LENGTH_LONG).show();
                }
                else if(cnt!=1)
                {
                    Snackbar.make(findViewById(R.id.signupcompanies), "Please enter valid email address", Snackbar.LENGTH_LONG).show();

                }
                else if(mno.getText().length()!=10)
                {
                    Snackbar.make(findViewById(R.id.signupcompanies), "Please enter a valid mobile number", Snackbar.LENGTH_LONG).show();
                }
                else {
                    try {
                        studentJsonObject.put("name", cname.getText() + "");
                        studentJsonObject.put("email", cemail.getText() + "");
                        studentJsonObject.put("pwd", cpwd.getText() + "");
                        studentJsonObject.put("mno", mno.getText());
                        studentJsonObject.put("mno2", mno1.getText());
                        studentJsonObject.put("website", website.getText() + "");
                        studentJsonObject.put("desc", aboutcompany.getText() + "");
                        studentJsonObject.put("add", location.getText() + "");

                        studentEntity = new StringEntity(studentJsonObject.toString());
                        AsyncHttpClient studentClient = new AsyncHttpClient();
                        studentClient.addHeader("Accept", "application/json");
                        studentClient.post(Signup_Company.this, "http://192.168.23.1:12345/webservice.asmx/signupcompany", studentEntity, "application/json", new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);

                                try {
                                    int b = response.getInt("d");

                                    if (b == 1) {
                                        Intent studentIntent = new Intent(Signup_Company.this, Login.class);
                                        startActivity(studentIntent);
                                    } else {
                                        Snackbar.make(findViewById(R.id.signupcompanies), "Fill Details", Snackbar.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                Snackbar.make(findViewById(R.id.signupcompanies), "Please fill all details", Snackbar.LENGTH_LONG).show();
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                mProgressDialog = ProgressDialog.show(Signup_Company.this, "Loading", "Please Wait");
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                if (mProgressDialog.isShowing()) {
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

            }
        });
    }
}
