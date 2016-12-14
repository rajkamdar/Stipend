package app.stipend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class EditCompanyProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView cdetails;
    EditText cname,location,aboutcompany,mno,mno1,website,cpwd;
    TextView cemail;
    Button register;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company_profile2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00868b")));

        cdetails= (TextView) findViewById(R.id.edit_cdetails);
        cname= (EditText) findViewById(R.id.edit_cname);
        location= (EditText) findViewById(R.id.edit_location);
        aboutcompany= (EditText) findViewById(R.id.edit_aboutcompany);
        mno= (EditText) findViewById(R.id.edit_cmno);
        mno1= (EditText) findViewById(R.id.edit_mno1);
        website= (EditText) findViewById(R.id.edit_website);
        register= (Button) findViewById(R.id.cregister);
        cemail= (TextView) findViewById(R.id.edit_cemail);
        cpwd= (EditText) findViewById(R.id.edit_cpwd);


        SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
        cemail.setText(mSharedPreferences.getString("company_email", null));
        cpwd.setText(mSharedPreferences.getString("company_password",null));
        cname.setText(mSharedPreferences.getString("company_name", null));
        mno.setText(mSharedPreferences.getString("company_number", null));
        mno1.setText(mSharedPreferences.getString("company_number2", null));
        aboutcompany.setText(mSharedPreferences.getString("description", null));
        website.setText(mSharedPreferences.getString("website", null));
        mSharedPreferences.getString("company_report", null);
        location.setText(mSharedPreferences.getString("address", null));


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JSONObject studentJsonObject = new JSONObject();
                StringEntity studentEntity = null;

                try {
                    studentJsonObject.put("name", cname.getText() + "");
                    studentJsonObject.put("id", cemail.getText() + "");
                    studentJsonObject.put("pwd", cpwd.getText() + "");
                    studentJsonObject.put("num", mno.getText());
                    studentJsonObject.put("num2", mno1.getText());
                    studentJsonObject.put("website", website.getText() + "");
                    studentJsonObject.put("desc", aboutcompany.getText() + "");
                    studentJsonObject.put("address", location.getText() + "");

                    studentEntity = new StringEntity(studentJsonObject.toString());
                    AsyncHttpClient studentClient = new AsyncHttpClient();
                    studentClient.addHeader("Accept", "application/json");
                    studentClient.post(EditCompanyProfile.this, "http://192.168.23.1:12345/webservice.asmx/editcompanyprofile", studentEntity, "application/json", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                int b = response.getInt("d");

                                if (b == 1) {
                                    Intent studentIntent = new Intent(EditCompanyProfile.this, CompanyHome2.class);
                                    startActivity(studentIntent);
                                } else {
                                    Snackbar.make(findViewById(R.id.editcompanies), "Fill Details", Snackbar.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Snackbar.make(findViewById(R.id.editcompanies), "No Internet", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            mProgressDialog = ProgressDialog.show(EditCompanyProfile.this, "Loading", "Please Wait");
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
       // getMenuInflater().inflate(R.menu.edit_company_profile2, menu);
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
            Intent mIntent=new Intent(EditCompanyProfile.this,CompanyHome2.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent mIntent=new Intent(EditCompanyProfile.this,About.class);
            startActivity(mIntent);

        } else if (id == R.id.nav_manage) {
            SharedPreferences mSharedPreferences=getSharedPreferences("system",Context.MODE_PRIVATE);
            mSharedPreferences.edit().clear().commit();
            Intent back=new Intent(EditCompanyProfile.this,Login.class);
            startActivity(back);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
