package app.stipend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
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

public class StudentDetails extends AppCompatActivity {

    TextView emailid, mno, field, year, sscmarks, hscmarks, clgmarks, desc;
    String id, num, fieldd, yr, ten, twelve, clggrade, details,sid,sname,internship_id;
    ProgressDialog mDialog;
    Button contact;
    final String hasReported[]=new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);


        emailid = (TextView) findViewById(R.id.email1);
        mno = (TextView) findViewById(R.id.mno1);
        field = (TextView) findViewById(R.id.field1);
        year = (TextView) findViewById(R.id.year1);
        sscmarks = (TextView) findViewById(R.id.ten1);
        hscmarks = (TextView) findViewById(R.id.twelve1);
        clgmarks = (TextView) findViewById(R.id.clgmarks1);
        desc = (TextView) findViewById(R.id.detail1);
        contact= (Button) findViewById(R.id.contact);


        SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
        sid=mSharedPreferences.getString("student_email", null);

       // sname=mSharedPreferences.getString("student_name", sname);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00868B")));

        Intent hIntent = getIntent();
        internship_id=hIntent.getStringExtra("internship_id");
        id = hIntent.getStringExtra("id");
        hasReported[0]=mSharedPreferences.getString(id+"","NA");
        num = hIntent.getStringExtra("num");
        fieldd = hIntent.getStringExtra("field");
        yr = hIntent.getStringExtra("year");
        ten = hIntent.getStringExtra("ten");
        twelve = hIntent.getStringExtra("twelve");
        clggrade = hIntent.getStringExtra("clggrade");
        details = hIntent.getStringExtra("details");
        sname=hIntent.getStringExtra("sname");
        getSupportActionBar().setTitle(sname);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{id});
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

      //  emailid.setText(id+"");
        emailid.setText(id+"");
        mno.setText(num+"");
        field.setText(fieldd+"");
        year.setText(yr+"");
        sscmarks.setText(ten+"");
        hscmarks.setText(twelve+"");
        clgmarks.setText(clggrade+"");
        desc.setText(details+"");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.company_home2, menu);
        MenuInflater inflater = getMenuInflater();
      //  inflater.inflate(R.menu.company_home2, menu);
        MenuItem menuItem = menu.findItem(R.id.action_report);
        MenuItem menuItem1 = menu.findItem(R.id.close);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (hasReported[0].equals("HA")) {
                    Toast.makeText(StudentDetails.this, "You can't report "+sname+" more than once!", Toast.LENGTH_LONG).show();
                    return false;
                } else {



                    AlertDialog.Builder exitBuilder = new AlertDialog.Builder(StudentDetails.this);
                    exitBuilder.setMessage("Do you want to report this applicant?");
                    exitBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                /*android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);*/

                            JSONObject homeJsonObject = new JSONObject();
                            StringEntity homeEntity = null;

                            try {
                                homeJsonObject.put("id", id);
                                homeEntity = new StringEntity(homeJsonObject.toString());
                                AsyncHttpClient homeClient = new AsyncHttpClient();
                                homeClient.addHeader("Accept", "application/json");
                                homeClient.post(StudentDetails.this, "http://192.168.23.1:12345/webservice.asmx/reportstudent", homeEntity, "application/json", new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);

                                        try {
                                            int ans = response.getInt("d");

                                            if (ans == 1) {
                                                //Snackbar.make(findViewById(R.id.add), "Reported", Snackbar.LENGTH_LONG).show();
                                                Toast.makeText(StudentDetails.this, "Reported", Toast.LENGTH_LONG).show();
                                                SharedPreferences mSharedPreferences = getSharedPreferences("system", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                                editor.putString(id + "", "HA");
                                                editor.commit();

                                                Intent mIntent=new Intent(StudentDetails.this,CompanyHome2.class);
                                                startActivity(mIntent);
                                                finish();

                                            } else {
                                                // Snackbar.make(findViewById(R.id.add), "Try Again!!", Snackbar.LENGTH_LONG).show();
                                                Toast.makeText(StudentDetails.this, "Try Again!!", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        super.onFailure(statusCode, headers, throwable, errorResponse);
                                        //Snackbar.make(findViewById(R.id.home), "Failure", Snackbar.LENGTH_LONG).show();
                                        Toast.makeText(StudentDetails.this, "Failure", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                        mDialog = ProgressDialog.show(StudentDetails.this, "Loading", "Please Wait");
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

        menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder exitBuilder = new AlertDialog.Builder(StudentDetails.this);
                exitBuilder.setMessage("Close applications for this post? Students won't be able to apply to a Post declared as closed by the company..");
                exitBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject homeJsonObject1=new JSONObject();
                        StringEntity homeEntity1=null;

                        try {
                            homeJsonObject1.put("id",internship_id);
                            homeEntity1=new StringEntity(homeJsonObject1.toString());
                            AsyncHttpClient homeClient1=new AsyncHttpClient();
                            homeClient1.addHeader("Accept","application/json");
                            homeClient1.post(StudentDetails.this,"http://192.168.23.1:12345/webservice.asmx/closeinternship",homeEntity1,"application/json",new JsonHttpResponseHandler(){
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);

                                    try {
                                        int ans=response.getInt("d");

                                        if(ans==1)
                                        {
                                            //Snackbar.make(findViewById(R.id.add), "Reported", Snackbar.LENGTH_LONG).show();
                                          Intent nIntent=new Intent(StudentDetails.this,CompanyHome2.class);
                                            startActivity(nIntent);
                                        }
                                        else
                                        {
                                            // Snackbar.make(findViewById(R.id.add), "Try Again!!", Snackbar.LENGTH_LONG).show();
                                            Toast.makeText(StudentDetails.this, "Try Again!!", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    super.onFailure(statusCode, headers, throwable, errorResponse);
                                    //Snackbar.make(findViewById(R.id.home), "Failure", Snackbar.LENGTH_LONG).show();
                                    Toast.makeText(StudentDetails.this, "Failure", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mDialog= ProgressDialog.show(StudentDetails.this, "Loading", "Please Wait");
                                }

                                @Override
                                public void onFinish() {
                                    super.onFinish();
                                    if(mDialog.isShowing())
                                    {
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
        });


        return true;
    }
}