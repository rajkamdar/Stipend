package app.stipend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class Search extends AppCompatActivity {

    ListView internships_listview;
    ProgressDialog mProgressDialog;
    String id,query;
    ArrayList<CompanyHomeClass> mArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        internships_listview= (ListView) findViewById(R.id.internships_list);

        Intent hIntent=getIntent();
        query=hIntent.getStringExtra("query");

        SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
        id=mSharedPreferences.getString("company_email",null);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00868B")));
        getSupportActionBar().setTitle(query);
        mArrayList=new ArrayList<>();
        final JSONObject newsJsonObject=new JSONObject();
        StringEntity stringEntity=null;

        try {
            newsJsonObject.put("query",query);
            newsJsonObject.put("id",id);
            stringEntity=new StringEntity(newsJsonObject.toString());
            AsyncHttpClient sClient=new AsyncHttpClient();
            sClient.addHeader("Accept","application/json");
            sClient.post(Search.this, "http://192.168.23.1:12345/webservice.asmx/SearchApplicants", stringEntity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Toast.makeText(Search.this, "Success", Toast.LENGTH_LONG).show();
                    JSONArray mJsonArray = null;
                    try {
                        mJsonArray = response.getJSONArray("d");

                        JSONObject newJsonObject = null;

                        for (int i = 0; i < mJsonArray.length(); i++) {

                            newJsonObject = mJsonArray.getJSONObject(i);

                            mArrayList.add(new CompanyHomeClass(newJsonObject.getString("pk_internship_id"),newJsonObject.getString("post"), newJsonObject.getString("pk_student_email"),newJsonObject.getString("student_name"),newJsonObject.getString("student_mobile"),newJsonObject.getString("field"),newJsonObject.getString("ssc_marks"),newJsonObject.getString("hsc_marks"),newJsonObject.getString("year"),newJsonObject.getString("college_marks"),newJsonObject.getString("student_report"),newJsonObject.getString("student_description")));

                        }
                        CompanyHomeAdapter mAdapter = new CompanyHomeAdapter(Search.this, mArrayList);
                        internships_listview.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse);
//                    Snackbar.make(findViewById(R.id.editstudent), "No Internet", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(Search.this, "Fail", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStart() {
                    super.onStart();
                    mProgressDialog = ProgressDialog.show(Search.this, "Loading", "Please Wait");
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

        internships_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompanyHomeClass obj= (CompanyHomeClass) parent.getItemAtPosition(position);
                Intent homeIntent=new Intent(Search.this,StudentDetails.class);
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

    }
}
