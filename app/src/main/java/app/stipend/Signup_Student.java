package app.stipend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Signup_Student extends AppCompatActivity {
    TextView pdetails,edetails,tv1,tv2;
    EditText fname,lname,emailid,pwd,mno,ten,twelve,clgmarks,desc;
    Button register;
    Spinner field1,year1,spinner;
    String ans,ans2;
    int ans1,cnt=0;
    ProgressDialog studentProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__student);

        pdetails= (TextView) findViewById(R.id.signup_pdetails);
        edetails= (TextView) findViewById(R.id.signup_edetails);
        tv1= (TextView) findViewById(R.id.field);
        tv2= (TextView) findViewById(R.id.year);

        fname= (EditText) findViewById(R.id.signup_fname);
        lname= (EditText) findViewById(R.id.signup_lname);
        emailid= (EditText) findViewById(R.id.signup_id);
        pwd= (EditText) findViewById(R.id.signup_pwd);
        mno= (EditText) findViewById(R.id.signup_mno);
        field1= (Spinner) findViewById(R.id.signup_field);
        year1= (Spinner) findViewById(R.id.signup_year);
        ten= (EditText) findViewById(R.id.signup_ten);
        twelve= (EditText) findViewById(R.id.signup_twelve);
        clgmarks= (EditText) findViewById(R.id.signup_clgmarks);
        desc= (EditText) findViewById(R.id.signup_studentdescription);
        register= (Button) findViewById(R.id.register);
        spinner= (Spinner) findViewById(R.id.spinner2);



        final String field[] = new String[10];
        field[0] = "Information Technology";
        field[1] = "Management";
        field[2] = "Science";
        field[3] = "Accounting/Commerce";
        field[4] = "Arts and Literature";
        field[5]="Medical";
        field[6]="Pharmacy";
        field[7]="Liberal Studies";
        field[8]="Mass Media/Film Production";
        field[9]="Other";

        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(Signup_Student.this, R.layout.support_simple_spinner_dropdown_item, field);
        Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        field1.setAdapter(Adapter);

        field1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ans = "Information Technology";
                        break;
                    case 1:
                        ans = "Management";
                        break;
                    case 2:
                        ans = "Science";
                        break;
                    case 3:
                        ans = "Commerce/Accounting";
                        break;
                    case 4:
                        ans = "Arts and Literature";
                        break;
                    case 5:
                        ans = "Medical";
                        break;
                    case 6:
                        ans = "Pharmacy";
                        break;
                    case 7:
                        ans = "Liberal Studies";
                        break;
                    case 8:
                        ans = "Mass Media";
                        break;
                    case 9:
                        ans = "Other";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final String year[] = new String[7];
        year[0] = "1(Bachelors)";
        year[1] = "2(Bachelors)";
        year[2] = "3(Bachelors)";
        year[3] = "4(Bachelors)";
        year[4] = "1(Masters)";
        year[5] = "2(Masters)";
        year[6] = "3(Masters)";

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(Signup_Student.this, R.layout.support_simple_spinner_dropdown_item, year);
        Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        year1.setAdapter(yearAdapter);

        year1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ans1 = 1;
                        break;
                    case 1:
                        ans1 = 2;
                        break;
                    case 2:
                        ans1 = 3;
                        break;
                    case 3:
                        ans1= 4;
                        break;
                    case 4:
                        ans1 = 5;
                        break;
                    case 5:
                        ans1 = 6;
                        break;
                    case 6:
                        ans1 = 7;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        final String marks[] = new String[4];
        marks[0] = "CGPA";
        marks[1] = "GPA";
        marks[2] = "SPI/CPI";
        marks[3] = "Percentage";


        ArrayAdapter<String> marksAdapter = new ArrayAdapter<String>(Signup_Student.this, R.layout.support_simple_spinner_dropdown_item, marks);
        Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(marksAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ans2 = " CGPA";
                        break;
                    case 1:
                        ans2 = " GPA";
                        break;
                    case 2:
                        ans2 = " SPI/CPI";
                        break;
                    case 3:
                        ans2 = " Percentage";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    for(int i=0;i<emailid.getText().length();i++)
    {
        if(emailid.getText().charAt(i)=='@')
        {
            cnt++;
        }
    }
            if(fname.getText().length()==0)
            {
                Snackbar.make(findViewById(R.id.signupstudents), "Please Insert Name", Snackbar.LENGTH_LONG).show();
            }
                else if(pwd.getText().length()>10 || pwd.getText().length()==0)
            {
                Snackbar.make(findViewById(R.id.signupstudents), "Enter a valid password between 1 to 10 characters", Snackbar.LENGTH_LONG).show();
            }
            else if(cnt!=1)
            {
                Snackbar.make(findViewById(R.id.signupstudents), "Please enter valid email address", Snackbar.LENGTH_LONG).show();

            }
                else if(mno.getText().length()!=10)
            {
                Snackbar.make(findViewById(R.id.signupstudents), "Please enter a valid mobile number", Snackbar.LENGTH_LONG).show();
            }
                else {
                JSONObject studentJsonObject = new JSONObject();
                StringEntity studentEntity = null;

                try {
                    studentJsonObject.put("name", fname.getText() + " " + lname.getText());
                    studentJsonObject.put("email", emailid.getText() + "");
                    studentJsonObject.put("pwd", pwd.getText() + "");
                    studentJsonObject.put("mno", mno.getText() + "");
                    studentJsonObject.put("field", ans + "");
                    studentJsonObject.put("ssc", ten.getText() + " %");
                    studentJsonObject.put("hsc", twelve.getText() + " %");
                    studentJsonObject.put("year", ans1);
                    studentJsonObject.put("clg", clgmarks.getText() + " " + ans2);
                    studentJsonObject.put("desc", desc.getText() + "");

                    studentEntity = new StringEntity(studentJsonObject.toString());
                    AsyncHttpClient studentClient = new AsyncHttpClient();
                    studentClient.addHeader("Accept", "application/json");
                    studentClient.post(Signup_Student.this, "http://192.168.23.1:12345/WebService.asmx/signupstudent", studentEntity, "application/json", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                int b = response.getInt("d");

                                if (b == 1) {
                                    Intent studentIntent = new Intent(Signup_Student.this, Login.class);
                                    startActivity(studentIntent);
                                } else {
                                    Snackbar.make(findViewById(R.id.signupstudents), "Please Fill Details", Snackbar.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Snackbar.make(findViewById(R.id.signupstudents), "No Internet", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            studentProgressDialog = ProgressDialog.show(Signup_Student.this, "Registering", "Please Wait");
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            if (studentProgressDialog.isShowing()) {
                                studentProgressDialog.dismiss();
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

