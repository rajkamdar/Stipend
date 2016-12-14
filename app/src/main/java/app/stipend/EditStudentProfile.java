package app.stipend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class EditStudentProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView pdetails,edetails,tv1,tv2;
    EditText fname,lname,pwd,mno,ten,twelve,clgmarks,desc;
    TextView emailid;
    Button register;
    Spinner field1,year1,spinner;
    String ans,ans2;
    int ans1;
    ProgressDialog studentProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pdetails= (TextView) findViewById(R.id.edit_pdetails);
        edetails= (TextView) findViewById(R.id.edit_edetails);
        tv1= (TextView) findViewById(R.id.field);
        tv2= (TextView) findViewById(R.id.year);

        fname= (EditText) findViewById(R.id.edit_fname);

        emailid= (TextView) findViewById(R.id.edit_id);
        pwd= (EditText) findViewById(R.id.edit_pwd);
        mno= (EditText) findViewById(R.id.edit_mno);
        field1= (Spinner) findViewById(R.id.edit_field);
        year1= (Spinner) findViewById(R.id.edit_year);
        ten= (EditText) findViewById(R.id.edit_ten);
        twelve= (EditText) findViewById(R.id.edit_twelve);
        clgmarks= (EditText) findViewById(R.id.edit_clgmarks);
        desc= (EditText) findViewById(R.id.edit_studentdescription);
        register= (Button) findViewById(R.id.upd);
        spinner= (Spinner) findViewById(R.id.spinner_edit);

        SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
        emailid.setText(mSharedPreferences.getString("student_email",null));
       // emailid.setText("raj_kamdar@gmail.com");
        pwd.setText(mSharedPreferences.getString("student_password",null));
        fname.setText(mSharedPreferences.getString("student_name",null));
        mno.setText(mSharedPreferences.getString("student_mobile",null));
        ans=mSharedPreferences.getString("field",null);
       ten.setText(mSharedPreferences.getString("ssc_marks", null).trim());
       twelve.setText(mSharedPreferences.getString("hsc_marks", null).trim());
        mSharedPreferences.getString("year", null);
        String[] cmarks=mSharedPreferences.getString("college_marks",null).split(" ");
       clgmarks.setText(cmarks[0]+"");
        mSharedPreferences.getString("student_report", null);
        desc.setText(mSharedPreferences.getString("student_description", null));



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

        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(EditStudentProfile.this, R.layout.support_simple_spinner_dropdown_item, field);
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

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(EditStudentProfile.this, R.layout.support_simple_spinner_dropdown_item, year);
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


        ArrayAdapter<String> marksAdapter = new ArrayAdapter<String>(EditStudentProfile.this, R.layout.support_simple_spinner_dropdown_item, marks);
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


                JSONObject studentJsonObject = new JSONObject();
                StringEntity studentEntity = null;

                try {
                    studentJsonObject.put("name", fname.getText());
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
                    studentClient.post(EditStudentProfile.this, "http://192.168.23.1:12345/webservice.asmx/EditStudentProfile", studentEntity, "application/json", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                int b = response.getInt("d");

                                if (b == 1) {
                                    Intent studentIntent = new Intent(EditStudentProfile.this, StudentHome.class);
                                    startActivity(studentIntent);
                                } else {
                                    Snackbar.make(findViewById(R.id.editstudent), "Fill Details", Snackbar.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            studentProgressDialog = ProgressDialog.show(EditStudentProfile.this, "Loading", "Please Wait");
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
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.edit_student_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
            Intent mIntent=new Intent(EditStudentProfile.this,StudentHome.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent mIntent=new Intent(EditStudentProfile.this,About.class);
            startActivity(mIntent);

        } else if (id == R.id.nav_manage) {
            SharedPreferences mSharedPreferences=getSharedPreferences("system",Context.MODE_PRIVATE);
            mSharedPreferences.edit().clear().commit();
            Intent back=new Intent(EditStudentProfile.this,Login.class);
            startActivity(back);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
