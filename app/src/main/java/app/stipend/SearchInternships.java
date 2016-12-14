package app.stipend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class SearchInternships extends AppCompatActivity {
    ListView internships_listview2;
    ProgressDialog mProgressDialog;
    String field,query;
    ArrayList<InternshipsClass> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_internships);


        internships_listview2= (ListView) findViewById(R.id.internships_list2);
        Intent hIntent=getIntent();
        query=hIntent.getStringExtra("query");

        getSupportActionBar().setTitle(query);

        SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
        field=mSharedPreferences.getString("field",null);

        mArrayList=new ArrayList<>();
        final JSONObject newsJsonObject=new JSONObject();
        StringEntity stringEntity=null;

        try {
            newsJsonObject.put("query",query);
            newsJsonObject.put("field",field);
            stringEntity=new StringEntity(newsJsonObject.toString());
            AsyncHttpClient sClient=new AsyncHttpClient();
            sClient.addHeader("Accept","application/json");
            sClient.post(SearchInternships.this, "http://192.168.23.1:12345/webservice.asmx/SearchInternships", stringEntity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                  //  Toast.makeText(SearchInternships.this, "Success", Toast.LENGTH_LONG).show();
                    JSONArray mJsonArray = null;
                    try {
                        mJsonArray = response.getJSONArray("d");

                        JSONObject mJsonObject = null;

                        for (int i = 0; i < mJsonArray.length(); i++) {

                            mJsonObject = mJsonArray.getJSONObject(i);

                            mArrayList.add(new InternshipsClass(mJsonObject.getString("pk_internship_id"), mJsonObject.getString("post"), mJsonObject.getString("details"), mJsonObject.getString("stipend"), mJsonObject.getString("duration"), mJsonObject.getString("fk_company_email"), mJsonObject.getString("status"), mJsonObject.getString("city"), mJsonObject.getString("internship_field"), mJsonObject.getString("company_name"), mJsonObject.getString("description"), mJsonObject.getString("website"), mJsonObject.getString("address")));

                        }
                        InternshipsAdapter mAdapter = new InternshipsAdapter(SearchInternships.this, mArrayList);
                        internships_listview2.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(SearchInternships.this, "Fail", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStart() {
                    super.onStart();
                    mProgressDialog = ProgressDialog.show(SearchInternships.this, "Loading", "Please Wait");
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

        internships_listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InternshipsClass iObject= (InternshipsClass) parent.getItemAtPosition(position);
                Intent iIntent=new Intent(SearchInternships.this,Internship_Details.class);
/*                iIntent.putExtra("cname",iObject.getCompany_name()+"");
                iIntent.putExtra("cemail",iObject.getFk_company_email()+"");
                iIntent.putExtra("cwebsite",iObject.getWebsite()+"");
                iIntent.putExtra("cdesc",iObject.getDescription()+"");
                iIntent.putExtra("ipost",iObject.getPost()+"");
                iIntent.putExtra("caddress",iObject.getAddress()+"");
                iIntent.putExtra("idur",iObject.getDuration()+"");
                iIntent.putExtra("istipend",iObject.getStipend()+"");
                iIntent.putExtra("idetails",iObject.getDetails()+"");
                iIntent.putExtra("int_id",iObject.getPk_internship_id());*/


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

    }
}
