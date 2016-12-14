package app.stipend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Internship_Details extends AppCompatActivity {
TextView tv_website,tv_description,tv_address,tv_post,tv_stipend,tv_duration,tv_details;
    Button apply;
    ProgressDialog mProgressDialog,mDialog;
    String student_email;
     int applied;
    final String hasApplied[]=new String[1];
    final String hasReported[]=new String[1];
    String iid,email,nme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship__details);
        SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
        apply= (Button) findViewById(R.id.btn_apply);
        student_email=mSharedPreferences.getString("student_email", null);


        tv_website= (TextView) findViewById(R.id.tv_website);
        tv_description= (TextView) findViewById(R.id.tv_description);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_post= (TextView) findViewById(R.id.tv_post);
        tv_stipend= (TextView) findViewById(R.id.tv_stipend);
        tv_duration= (TextView) findViewById(R.id.tv_duration);
        tv_details= (TextView) findViewById(R.id.textView24);

        Intent iIntent=getIntent();
        getSupportActionBar().setTitle(iIntent.getStringExtra("cname"));
         iid=iIntent.getStringExtra("int_id");

        apply.setEnabled(true);
        hasApplied[0]=mSharedPreferences.getString(iid+"","NA");
        if(hasApplied[0].equals("NA"))
        {
            apply.setEnabled(true);
        }
        else
        {
            apply.setEnabled(false);
        }


   //     applied=iIntent.getIntExtra("int_id",0);
        email=iIntent.getStringExtra("cemail");
        hasReported[0]=mSharedPreferences.getString(email + "", "NA");
        tv_website.setText(iIntent.getStringExtra("cdesc"));
        tv_description.setText(iIntent.getStringExtra("cwebsite"));
        tv_address.setText(iIntent.getStringExtra("caddress"));
        tv_post.setText(iIntent.getStringExtra("ipost"));
        tv_stipend.setText("Rs. "+iIntent.getStringExtra("istipend"));
        tv_duration.setText(iIntent.getStringExtra("idur")+" Month/s");
        tv_details.setText(iIntent.getStringExtra("idetails"));
nme=iIntent.getStringExtra("cname");

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                JSONObject studentJsonObject=new JSONObject();
                StringEntity studentEntity=null;

                try {
                    studentJsonObject.put("sid",student_email);
                    studentJsonObject.put("cid",email);
                    studentJsonObject.put("iid",iid);


                    studentEntity=new StringEntity(studentJsonObject.toString());
                    AsyncHttpClient studentClient=new AsyncHttpClient();
                    studentClient.addHeader("Accept","application/json");
                    studentClient.post(Internship_Details.this,"http://192.168.23.1:12345/webservice.asmx/apply",studentEntity,"application/json",new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                int b=response.getInt("d");

                                if(b==1) {
                                    Snackbar.make(findViewById(R.id.intdetails), "Applied", Snackbar.LENGTH_LONG).show();
                                    SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=mSharedPreferences.edit();
                                    editor.putString(iid+"", "HA");
                                    editor.commit();

                                    apply.setEnabled(false);
                                }
                                else
                                {
                                   Snackbar.make(findViewById(R.id.intdetails), "Invalid", Snackbar.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Snackbar.make(findViewById(R.id.intdetails), "No Internet", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            mProgressDialog= ProgressDialog.show(Internship_Details.this, "Loading", "Please Wait");
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.internship_details, menu);
        MenuInflater inflater = getMenuInflater();
        //  inflater.inflate(R.menu.company_home2, menu);
        MenuItem menuItem = menu.findItem(R.id.action_report);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (hasReported[0].equals("HA")) {
                    Toast.makeText(Internship_Details.this, "You can't report "+nme+" more than once!", Toast.LENGTH_LONG).show();
                    return false;
                }
                else {
                    AlertDialog.Builder exitBuilder = new AlertDialog.Builder(Internship_Details.this);
                    exitBuilder.setMessage("Do you want to report this applicant?");
                    exitBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                /*android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);*/

                            JSONObject homeJsonObject = new JSONObject();
                            StringEntity homeEntity = null;

                            try {
                                homeJsonObject.put("id", email);
                                homeEntity = new StringEntity(homeJsonObject.toString());
                                AsyncHttpClient homeClient = new AsyncHttpClient();
                                homeClient.addHeader("Accept", "application/json");
                                homeClient.post(Internship_Details.this, "http://192.168.23.1:12345/webservice.asmx/reportcompany", homeEntity, "application/json", new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);

                                        try {
                                            int ans = response.getInt("d");

                                            if (ans == 1) {
                                                //Snackbar.make(findViewById(R.id.add), "Reported", Snackbar.LENGTH_LONG).show();
                                                Toast.makeText(Internship_Details.this, "Reported", Toast.LENGTH_LONG).show();
                                                SharedPreferences mSharedPreferences = getSharedPreferences("system", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                                editor.putString(email + "", "HA");
                                                editor.commit();

                                                Intent mIntent=new Intent(Internship_Details.this,StudentHome.class);
                                                startActivity(mIntent);
                                                finish();

                                            } else {
                                                // Snackbar.make(findViewById(R.id.add), "Try Again!!", Snackbar.LENGTH_LONG).show();
                                                Toast.makeText(Internship_Details.this, "Try Again!!", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        super.onFailure(statusCode, headers, throwable, errorResponse);
                                        //Snackbar.make(findViewById(R.id.home), "Failure", Snackbar.LENGTH_LONG).show();
                                        Toast.makeText(Internship_Details.this, "Failure", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                        mDialog = ProgressDialog.show(Internship_Details.this, "Loading", "Please Wait");
                                    }

                                    @Override
                                    public void onFinish() {
                                        super.onFinish();
                                        if (mDialog.isShowing()) {
                                            mDialog.dismiss();
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
                    exitBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    exitBuilder.setCancelable(false);
                    exitBuilder.setIcon(R.drawable.ic_launcher);
                    AlertDialog exitAlertDialog = exitBuilder.create();
                    exitAlertDialog.show();

                    return false;
                }
            }
        });


        return true;
    }
}

