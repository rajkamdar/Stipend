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
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CompanyHome2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView companylistview;
    Spinner post;
    ArrayList<CompanyHomeClass> mList;
    ProgressDialog homeDialog;
    String id,name,ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent(CompanyHome2.this,AddInternship.class);
                startActivity(mIntent);
                    }
        });

        companylistview= (ListView) findViewById(R.id.companyhomelistview);
//        post= (Spinner) findViewById(R.id.spinner_post);

        SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
        id=mSharedPreferences.getString("company_email", null);
        name=mSharedPreferences.getString("company_name",name);

        final String field[] = new String[15];
        /*field[0] = "Clerk";
        field[1] = "Manager";
        field[2] = "Supervisor";
        field[3] = "Analyst";
        field[4]="friend";*/

        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(CompanyHome2.this, R.layout.support_simple_spinner_dropdown_item, field);
        Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        post.setAdapter(Adapter);


  /*      post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ans = "Clerk";
                        break;
                    case 1:
                        ans = "Manager";
                        break;
                    case 2:
                        ans = "Supervisor";
                        break;
                    case 3:
                        ans = "Analyst";
                        break;
                    case 4:
                        mList.clear();
                        JSONObject homeJsonObject=new JSONObject();
                        StringEntity homeEntity=null;

                        try {


                            homeJsonObject.put("post","friend");

                            homeEntity=new StringEntity(homeJsonObject.toString());
                            AsyncHttpClient homeClient=new AsyncHttpClient();
                            homeClient.addHeader("Accept","application/json");
                            homeClient.post(CompanyHome2.this,"http://192.168.23.1:12345/webservice.asmx/getapplicationbypost",homeEntity,"application/json",new JsonHttpResponseHandler(){

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    homeDialog=ProgressDialog.show(CompanyHome2.this,"Loading","Please Wait");
                                }

                                @Override
                                public void onFinish() {
                                    super.onFinish();
                                    if(homeDialog.isShowing())
                                    {
                                        homeDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);

                                    try {
                                        JSONArray homeJsonArray=response.getJSONArray("d");
                                        JSONObject newJsonObject=null;
                                        for(int i=0;i<homeJsonArray.length();i++)
                                        {
                                            newJsonObject=homeJsonArray.getJSONObject(i);
                                            mList.add(new CompanyHomeClass(newJsonObject.getString("pk_internship_id"),newJsonObject.getString("post"), newJsonObject.getString("pk_student_email"),newJsonObject.getString("student_name"),newJsonObject.getString("student_mobile"),newJsonObject.getString("field"),newJsonObject.getString("ssc_marks"),newJsonObject.getString("hsc_marks"),newJsonObject.getString("year"),newJsonObject.getString("college_marks"),newJsonObject.getString("student_report"),newJsonObject.getString("student_description")));
                                        }
                                        CompanyHomeAdapter newAdapter=new CompanyHomeAdapter(CompanyHome2.this,mList);
                                        companylistview.setAdapter(newAdapter);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    super.onFailure(statusCode, headers, throwable, errorResponse);
                                    Snackbar.make(findViewById(R.id.home),"Failure",Snackbar.LENGTH_LONG).show();
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

*/


        getSupportActionBar().setTitle(name);
        mList=new ArrayList<>();
        JSONObject homeJsonObject=new JSONObject();
        StringEntity homeEntity=null;

        try {
                homeJsonObject.put("id",id);
                homeEntity=new StringEntity(homeJsonObject.toString());
                AsyncHttpClient homeClient=new AsyncHttpClient();
                homeClient.addHeader("Accept","application/json");
                homeClient.post(CompanyHome2.this,"http://192.168.23.1:12345/webservice.asmx/getapplicationbycompany",homeEntity,"application/json",new JsonHttpResponseHandler(){

                    @Override
                    public void onStart() {
                        super.onStart();
                        homeDialog=ProgressDialog.show(CompanyHome2.this,"Loading","Please Wait");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if(homeDialog.isShowing())
                        {
                            homeDialog.dismiss();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONArray homeJsonArray=response.getJSONArray("d");
                            JSONObject newJsonObject=null;
                            for(int i=0;i<homeJsonArray.length();i++)
                            {
                                newJsonObject=homeJsonArray.getJSONObject(i);
                                for(int j=0;j<homeJsonArray.length();j++)
                                {
                                    if(field[j]!="" || field[j]!=newJsonObject.getString("post"))
                                    {
                                        field[i]=newJsonObject.getString("post");
                                    }

                                }

                                mList.add(new CompanyHomeClass(newJsonObject.getString("pk_internship_id"),newJsonObject.getString("post"), newJsonObject.getString("pk_student_email"),newJsonObject.getString("student_name"),newJsonObject.getString("student_mobile"),newJsonObject.getString("field"),newJsonObject.getString("ssc_marks"),newJsonObject.getString("hsc_marks"),newJsonObject.getString("year"),newJsonObject.getString("college_marks"),newJsonObject.getString("student_report"),newJsonObject.getString("student_description")));
                            }
                            CompanyHomeAdapter newAdapter=new CompanyHomeAdapter(CompanyHome2.this,mList);
                            companylistview.setAdapter(newAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Snackbar.make(findViewById(R.id.home),"Failure",Snackbar.LENGTH_LONG).show();
                    }
                });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        companylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompanyHomeClass obj= (CompanyHomeClass) parent.getItemAtPosition(position);
                Intent homeIntent=new Intent(CompanyHome2.this,StudentDetails.class);
                homeIntent.putExtra("internship_id",obj.getPk_internship_id());
                homeIntent.putExtra("id",obj.getPk_student_email());
                homeIntent.putExtra("num",obj.getStudent_mobile());
                homeIntent.putExtra("field",obj.getField());
                homeIntent.putExtra("year",obj.getYear());
                homeIntent.putExtra("ten",obj.getSsc_marks());
                homeIntent.putExtra("twelve",obj.getHsc_marks());
                homeIntent.putExtra("clggrade",obj.getCollege_marks());
                homeIntent.putExtra("details",obj.getStudent_description());
                homeIntent.putExtra("sname",obj.getStudent_name());
                startActivity(homeIntent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00868B")));
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
            Intent mIntent=new Intent(CompanyHome2.this,EditCompanyProfile.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_slideshow) {
            Intent mIntent=new Intent(CompanyHome2.this,About.class);
            startActivity(mIntent);

        } else if (id == R.id.nav_manage) {
            SharedPreferences mSharedPreferences=getSharedPreferences("system",Context.MODE_PRIVATE);
            mSharedPreferences.edit().clear().commit();
            Intent back=new Intent(CompanyHome2.this,Login.class);
            startActivity(back);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chome, menu);
        MenuItem menusearch = menu.findItem(R.id.srch);



        SearchView sv = (SearchView) MenuItemCompat.getActionView(menusearch);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent SearchIntent = new Intent(CompanyHome2.this, Search.class);
                SearchIntent.putExtra("query", query);
                SearchIntent.putExtra("id", id);
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
}
