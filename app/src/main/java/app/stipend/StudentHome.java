package app.stipend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class StudentHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
ListView internships_listview;
    Spinner internships_filter;
    ProgressDialog mProgressDialog;
    ArrayList<InternshipsClass> mArrayList,mArrayList1;
    String field;
    Spinner filter;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
        field=mSharedPreferences.getString("field",null);
        mArrayList=new ArrayList<>();
        mArrayList1=new ArrayList<>();
        internships_listview= (ListView) findViewById(R.id.internships_list);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
filter= (Spinner) findViewById(R.id.spinner_student);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        final String fields[] = new String[7];
       fields[0]="ALL CITIES";
        fields[1]="Ahmedabad";
        fields[2]="Vadodara";
        fields[3]="Surat";
        fields[4]="Rajkot";
        fields[5]="Gandhinagar";
        fields[6]="Surendranagar";

        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(StudentHome.this, R.layout.support_simple_spinner_dropdown_item, fields);
        Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        filter.setAdapter(Adapter);

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mArrayList.clear();
                        final JSONObject JsonObject = new JSONObject();
                        StringEntity enteity = null;


                        try {
                            JsonObject.put("field", field);


                            enteity = new StringEntity(JsonObject.toString());
                            AsyncHttpClient studentClient = new AsyncHttpClient();
                            studentClient.addHeader("Accept", "application/json");
                            studentClient.post(StudentHome.this, "http://192.168.23.1:12345/webservice.asmx/GetInternshipsByField", enteity, "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                  // Toast.makeText(StudentHome.this, "Success", Toast.LENGTH_LONG).show();
                                    JSONArray mJsonArray = null;
                                    try {
                                        mJsonArray = response.getJSONArray("d");

                                        JSONObject mJsonObject = null;

                                        for (int i = 0; i < mJsonArray.length(); i++) {

                                            mJsonObject = mJsonArray.getJSONObject(i);

                                            mArrayList.add(new InternshipsClass(mJsonObject.getString("pk_internship_id"), mJsonObject.getString("post"), mJsonObject.getString("details"), mJsonObject.getString("stipend"), mJsonObject.getString("duration"), mJsonObject.getString("fk_company_email"), mJsonObject.getString("status"), mJsonObject.getString("city"), mJsonObject.getString("internship_field"), mJsonObject.getString("company_name"), mJsonObject.getString("website"), mJsonObject.getString("description"), mJsonObject.getString("address")));

                                        }
                                        InternshipsAdapter mAdapter = new InternshipsAdapter(StudentHome.this, mArrayList);
                                        internships_listview.setAdapter(mAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                                    Toast.makeText(StudentHome.this, "Fail", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(StudentHome.this, "Loading", "Please Wait");
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
//run karine jo to ahmedabad nu filter kaam kare che?ha wait
                        break;

                    case 1:

mArrayList.clear();
                        JSONObject mJsonObject = new JSONObject();
                        enteity = null;


                        try {
                            mJsonObject.put("field", field);
                            mJsonObject.put("city", "Ahmedabad");

                            enteity = new StringEntity(mJsonObject.toString());
                            AsyncHttpClient studentClient = new AsyncHttpClient();
                            studentClient.addHeader("Accept", "application/json");
                            studentClient.post(StudentHome.this, "http://192.168.23.1:12345/webservice.asmx/GetInternshipsByCity", enteity, "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                           //         Toast.makeText(StudentHome.this, "Success", Toast.LENGTH_LONG).show();
                                    JSONArray mJsonArray = null;
                                    try {
                                        mJsonArray = response.getJSONArray("d");

                                        JSONObject mJsonObject = null;

                                        for (int i = 0; i < mJsonArray.length(); i++) {

                                            mJsonObject = mJsonArray.getJSONObject(i);

                                            mArrayList.add(new InternshipsClass(mJsonObject.getString("pk_internship_id"), mJsonObject.getString("post"), mJsonObject.getString("details"), mJsonObject.getString("stipend"), mJsonObject.getString("duration"), mJsonObject.getString("fk_company_email"), mJsonObject.getString("status"), mJsonObject.getString("city"), mJsonObject.getString("internship_field"), mJsonObject.getString("company_name"), mJsonObject.getString("website"), mJsonObject.getString("description"), mJsonObject.getString("address")));

                                        }
                                        InternshipsAdapter mAdapter = new InternshipsAdapter(StudentHome.this, mArrayList);
                                        internships_listview.setAdapter(mAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                                    Toast.makeText(StudentHome.this, "Fail", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(StudentHome.this, "Loading", "Please Wait");
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

                        break;
                    case 2:
                        mArrayList.clear();
                         mJsonObject = new JSONObject();
                        enteity = null;


                        try {
                            mJsonObject.put("field", field);
                            mJsonObject.put("city", "Vadodara");

                            enteity = new StringEntity(mJsonObject.toString());
                            AsyncHttpClient studentClient = new AsyncHttpClient();
                            studentClient.addHeader("Accept", "application/json");
                            studentClient.post(StudentHome.this, "http://192.168.23.1:12345/webservice.asmx/GetInternshipsByCity", enteity, "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                        //            Toast.makeText(StudentHome.this, "Success", Toast.LENGTH_LONG).show();
                                    JSONArray mJsonArray = null;
                                    try {
                                        mJsonArray = response.getJSONArray("d");

                                        JSONObject mJsonObject = null;

                                        for (int i = 0; i < mJsonArray.length(); i++) {

                                            mJsonObject = mJsonArray.getJSONObject(i);

                                            mArrayList.add(new InternshipsClass(mJsonObject.getString("pk_internship_id"), mJsonObject.getString("post"), mJsonObject.getString("details"), mJsonObject.getString("stipend"), mJsonObject.getString("duration"), mJsonObject.getString("fk_company_email"), mJsonObject.getString("status"), mJsonObject.getString("city"), mJsonObject.getString("internship_field"), mJsonObject.getString("company_name"), mJsonObject.getString("website"), mJsonObject.getString("description"), mJsonObject.getString("address")));

                                        }
                                        InternshipsAdapter mAdapter = new InternshipsAdapter(StudentHome.this, mArrayList);
                                        internships_listview.setAdapter(mAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                                    Toast.makeText(StudentHome.this, "Fail", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(StudentHome.this, "Loading", "Please Wait");
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

                        break;


                    case 3:

                        mArrayList.clear();
                        mJsonObject = new JSONObject();
                        enteity = null;


                        try {
                            mJsonObject.put("field", field);
                            mJsonObject.put("city", "Surat");

                            enteity = new StringEntity(mJsonObject.toString());
                            AsyncHttpClient studentClient = new AsyncHttpClient();
                            studentClient.addHeader("Accept", "application/json");
                            studentClient.post(StudentHome.this, "http://192.168.23.1:12345/webservice.asmx/GetInternshipsByCity", enteity, "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                         //           Toast.makeText(StudentHome.this, "Success", Toast.LENGTH_LONG).show();
                                    JSONArray mJsonArray = null;
                                    try {
                                        mJsonArray = response.getJSONArray("d");

                                        JSONObject mJsonObject = null;

                                        for (int i = 0; i < mJsonArray.length(); i++) {

                                            mJsonObject = mJsonArray.getJSONObject(i);

                                            mArrayList.add(new InternshipsClass(mJsonObject.getString("pk_internship_id"), mJsonObject.getString("post"), mJsonObject.getString("details"), mJsonObject.getString("stipend"), mJsonObject.getString("duration"), mJsonObject.getString("fk_company_email"), mJsonObject.getString("status"), mJsonObject.getString("city"), mJsonObject.getString("internship_field"), mJsonObject.getString("company_name"), mJsonObject.getString("website"), mJsonObject.getString("description"), mJsonObject.getString("address")));

                                        }
                                        InternshipsAdapter mAdapter = new InternshipsAdapter(StudentHome.this, mArrayList);
                                        internships_listview.setAdapter(mAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                                    Toast.makeText(StudentHome.this, "Fail", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(StudentHome.this, "Loading", "Please Wait");
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


                        break;

                    case 4:
                        mArrayList.clear();
                        final JSONObject mJsonObject1 = new JSONObject();
                        StringEntity enteity6 = null;


                        try {
                            mJsonObject1.put("field", field);
                            mJsonObject1.put("city", "Rajkot");

                            enteity6 = new StringEntity(mJsonObject1.toString());
                            AsyncHttpClient studentClient = new AsyncHttpClient();
                            studentClient.addHeader("Accept", "application/json");
                            studentClient.post(StudentHome.this, "http://192.168.23.1:12345/webservice.asmx/GetInternshipsByCity", enteity6, "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                          //          Toast.makeText(StudentHome.this, "Success", Toast.LENGTH_LONG).show();
                                    JSONArray mJsonArray = null;
                                    try {
                                        mJsonArray = response.getJSONArray("d");

                                        JSONObject mJsonObject = null;

                                        for (int i = 0; i < mJsonArray.length(); i++) {

                                            mJsonObject = mJsonArray.getJSONObject(i);

                                            mArrayList.add(new InternshipsClass(mJsonObject.getString("pk_internship_id"), mJsonObject.getString("post"), mJsonObject.getString("details"), mJsonObject.getString("stipend"), mJsonObject.getString("duration"), mJsonObject.getString("fk_company_email"), mJsonObject.getString("status"), mJsonObject.getString("city"), mJsonObject.getString("internship_field"), mJsonObject.getString("company_name"), mJsonObject.getString("website"), mJsonObject.getString("description"), mJsonObject.getString("address")));

                                        }
                                        InternshipsAdapter mAdapter = new InternshipsAdapter(StudentHome.this, mArrayList);
                                        internships_listview.setAdapter(mAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                                    Toast.makeText(StudentHome.this, "Fail", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(StudentHome.this, "Loading", "Please Wait");
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
                        break;

                    case 5:
                        mArrayList.clear();
                        mJsonObject = new JSONObject();
                        enteity = null;


                        try {
                            mJsonObject.put("field", field);
                           mJsonObject.put("city", "Gandhinagar");

                            enteity = new StringEntity(mJsonObject.toString());
                            AsyncHttpClient studentClient = new AsyncHttpClient();
                            studentClient.addHeader("Accept", "application/json");
                            studentClient.post(StudentHome.this, "http://192.168.23.1:12345/webservice.asmx/GetInternshipsByCity", enteity, "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                            //        Toast.makeText(StudentHome.this, "Success", Toast.LENGTH_LONG).show();
                                    JSONArray mJsonArray = null;
                                    try {
                                        mJsonArray = response.getJSONArray("d");

                                        JSONObject mJsonObject = null;

                                        for (int i = 0; i < mJsonArray.length(); i++) {

                                            mJsonObject = mJsonArray.getJSONObject(i);

                                            mArrayList.add(new InternshipsClass(mJsonObject.getString("pk_internship_id"), mJsonObject.getString("post"), mJsonObject.getString("details"), mJsonObject.getString("stipend"), mJsonObject.getString("duration"), mJsonObject.getString("fk_company_email"), mJsonObject.getString("status"), mJsonObject.getString("city"), mJsonObject.getString("internship_field"), mJsonObject.getString("company_name"), mJsonObject.getString("website"), mJsonObject.getString("description"), mJsonObject.getString("address")));

                                        }
                                        InternshipsAdapter mAdapter = new InternshipsAdapter(StudentHome.this, mArrayList);
                                        internships_listview.setAdapter(mAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                                    Toast.makeText(StudentHome.this, "Fail", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(StudentHome.this, "Loading", "Please Wait");
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

                        break;

                    case 6:
                        mArrayList.clear();
                        mJsonObject = new JSONObject();
                        enteity = null;


                        try {
                            mJsonObject.put("field", field);
                            mJsonObject.put("city", "Surendranagar");

                            enteity = new StringEntity(mJsonObject.toString());
                            AsyncHttpClient studentClient = new AsyncHttpClient();
                            studentClient.addHeader("Accept", "application/json");
                            studentClient.post(StudentHome.this, "http://192.168.23.1:12345/webservice.asmx/GetInternshipsByCity", enteity, "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                            //        Toast.makeText(StudentHome.this, "Success", Toast.LENGTH_LONG).show();
                                    JSONArray mJsonArray = null;
                                    try {
                                        mJsonArray = response.getJSONArray("d");

                                        JSONObject mJsonObject = null;

                                        for (int i = 0; i < mJsonArray.length(); i++) {

                                            mJsonObject = mJsonArray.getJSONObject(i);

                                            mArrayList.add(new InternshipsClass(mJsonObject.getString("pk_internship_id"), mJsonObject.getString("post"), mJsonObject.getString("details"), mJsonObject.getString("stipend"), mJsonObject.getString("duration"), mJsonObject.getString("fk_company_email"), mJsonObject.getString("status"), mJsonObject.getString("city"), mJsonObject.getString("internship_field"), mJsonObject.getString("company_name"), mJsonObject.getString("website"), mJsonObject.getString("description"), mJsonObject.getString("address")));

                                        }
                                        InternshipsAdapter mAdapter = new InternshipsAdapter(StudentHome.this, mArrayList);
                                        internships_listview.setAdapter(mAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                                    Toast.makeText(StudentHome.this, "Fail", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(StudentHome.this, "Loading", "Please Wait");
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

                        break;

                    default:

                        mArrayList.clear();
                        final JSONObject JsonObject2 = new JSONObject();
                        StringEntity enteity2 = null;


                        try {
                            JsonObject2.put("field", field);


                            enteity2 = new StringEntity(JsonObject2.toString());
                            AsyncHttpClient studentClient = new AsyncHttpClient();
                            studentClient.addHeader("Accept", "application/json");
                            studentClient.post(StudentHome.this, "http://192.168.23.1:12345/webservice.asmx/GetInternshipsByField", enteity2, "application/json", new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                 //   Toast.makeText(StudentHome.this, "Success", Toast.LENGTH_LONG).show();
                                    JSONArray mJsonArray = null;
                                    try {
                                        mJsonArray = response.getJSONArray("d");

                                        JSONObject mJsonObject = null;

                                        for (int i = 0; i < mJsonArray.length(); i++) {

                                            mJsonObject = mJsonArray.getJSONObject(i);

                                            mArrayList.add(new InternshipsClass(mJsonObject.getString("pk_internship_id"), mJsonObject.getString("post"), mJsonObject.getString("details"), mJsonObject.getString("stipend"), mJsonObject.getString("duration"), mJsonObject.getString("fk_company_email"), mJsonObject.getString("status"), mJsonObject.getString("city"), mJsonObject.getString("internship_field"), mJsonObject.getString("company_name"), mJsonObject.getString("website"), mJsonObject.getString("description"), mJsonObject.getString("address")));

                                        }
                                        InternshipsAdapter mAdapter = new InternshipsAdapter(StudentHome.this, mArrayList);
                                        internships_listview.setAdapter(mAdapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                                    Toast.makeText(StudentHome.this, "Fail", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(StudentHome.this, "Loading", "Please Wait");
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

                        break;


                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


internships_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InternshipsClass iObject= (InternshipsClass) parent.getItemAtPosition(position);
        Intent iIntent=new Intent(StudentHome.this,Internship_Details.class);
        iIntent.putExtra("cname",iObject.getCompany_name()+"");
        iIntent.putExtra("cemail",iObject.getFk_company_email()+"");
        iIntent.putExtra("cwebsite",iObject.getWebsite()+"");
        iIntent.putExtra("cdesc",iObject.getDescription()+"");
        iIntent.putExtra("ipost",iObject.getPost()+"");
        iIntent.putExtra("caddress",iObject.getAddress()+"");
        iIntent.putExtra("idur",iObject.getDuration()+"");
        iIntent.putExtra("istipend",iObject.getStipend()+"");
        iIntent.putExtra("idetails",iObject.getDetails()+"");
        iIntent.putExtra("int_id",iObject.getPk_internship_id());
        startActivity(iIntent);
       // iIntent.putExtra("cname",iObject.getCompany_name()+"");
       // iIntent.putExtra("cname",iObject.getCompany_name()+"");
    }
});

/*                    */



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
        getMenuInflater().inflate(R.menu.student_home, menu);
        MenuItem menusearch = menu.findItem(R.id.srch);



        SearchView sv = (SearchView) MenuItemCompat.getActionView(menusearch);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent SearchIntent = new Intent(StudentHome.this, SearchInternships.class);
                SearchIntent.putExtra("query", query);
                SearchIntent.putExtra("field", field);
                startActivity(SearchIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_home, menu);

        MenuItem menusearch = menu.findItem(R.id.action_settings);



        SearchView sv = (SearchView) MenuItemCompat.getActionView(menusearch);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent SearchIntent = new Intent(StudentHome.this, SearchInternships.class);
                SearchIntent.putExtra("query", query);
                SearchIntent.putExtra("field",field);
                startActivity(SearchIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return true;


    }*/

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
        } else if (id == R.id.nav_gallery) {
            Intent mIntent=new Intent(StudentHome.this,EditStudentProfile.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_slideshow) {
            Intent mIntent=new Intent(StudentHome.this,About.class);
            startActivity(mIntent);

        } else if (id == R.id.nav_manage) {
            SharedPreferences mSharedPreferences=getSharedPreferences("system",Context.MODE_PRIVATE);
            mSharedPreferences.edit().clear().commit();
            Intent back=new Intent(StudentHome.this,Login.class);
            startActivity(back);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
