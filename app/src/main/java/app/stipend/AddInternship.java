package app.stipend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AddInternship extends AppCompatActivity {

    Button register;
    EditText stipend,duration,aboutinternship,post,ifield;
    Spinner city,field1;
    ProgressDialog mDialog;
    String cid,name,ans,ans1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_internship);

        register= (Button) findViewById(R.id.register);
        stipend= (EditText) findViewById(R.id.stipend);
        duration= (EditText) findViewById(R.id.duration);
        aboutinternship= (EditText) findViewById(R.id.aboutinternship);
        post= (EditText) findViewById(R.id.post);
        city= (Spinner) findViewById(R.id.city);
        field1= (Spinner) findViewById(R.id.ifield);

        SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
        cid=mSharedPreferences.getString("company_email", null);
        name=mSharedPreferences.getString("company_name",name);

        getSupportActionBar().setTitle(name);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00868B")));

        final String fields[] = new String[10];
        fields[0] = "Information Technology";
        fields[1] = "Management";
        fields[2] = "Science";
        fields[3] = "Accounting/Commerce";
        fields[4] = "Arts and Literature";
        fields[5]="Medical";
        fields[6]="Pharmacy";
        fields[7]="Liberal Studies";
        fields[8]="Mass Media/Film Production";
        fields[9]="Other";


        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(AddInternship.this, R.layout.support_simple_spinner_dropdown_item, fields);
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



        final String field[] = new String[7];
        field[0] = "Ahmedabad";
        field[1] = "Baroda";
        field[2] = "Surat";
        field[3] = "Rajkot";
        field[4] = "Nadiad";
        field[5]="Valsad";
        field[6]="Patan";

        Adapter = new ArrayAdapter<String>(AddInternship.this, R.layout.support_simple_spinner_dropdown_item, field);
        Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        city.setAdapter(Adapter);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ans1 = "Ahmedabad";
                        break;
                    case 1:
                        ans1 = "Baroda";
                        break;
                    case 2:
                        ans1 = "Surat";
                        break;
                    case 3:
                        ans1 = "Rajkot";
                        break;
                    case 4:
                        ans1 = "Nadiad";
                        break;
                    case 5:
                        ans1 = "Valsad";
                        break;
                    case 6:
                        ans1 = "Patan";
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
                if(post.getText().length()==0)
                {
                    Snackbar.make(findViewById(R.id.add), "Please add a designation", Snackbar.LENGTH_LONG).show();
                }
                else if(stipend.getText().length()==0)
                {
                    Snackbar.make(findViewById(R.id.add), "Please specify the stipend", Snackbar.LENGTH_LONG).show();
                }
                else if(duration.getText().length()==0)
                {
                    Snackbar.make(findViewById(R.id.add), "Please mention the duration", Snackbar.LENGTH_LONG).show();
                }
                else {
                    JSONObject homeJsonObject = new JSONObject();
                    StringEntity homeEntity = null;

                    try {
                        homeJsonObject.put("post", post.getText());
                        homeJsonObject.put("details", aboutinternship.getText());
                        homeJsonObject.put("stipend", stipend.getText());
                        homeJsonObject.put("duration", duration.getText());
                        homeJsonObject.put("company_id", cid);
                        homeJsonObject.put("city", ans1 + "");
                        homeJsonObject.put("field", ans + "");
                        homeEntity = new StringEntity(homeJsonObject.toString());
                        AsyncHttpClient homeClient = new AsyncHttpClient();
                        homeClient.addHeader("Accept", "application/json");
                        homeClient.post(AddInternship.this, "http://192.168.23.1:12345/webservice.asmx/CreateInternship", homeEntity, "application/json", new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);

                                try {
                                    int ans = response.getInt("d");

                                    if (ans == 1) {
                                        Snackbar.make(findViewById(R.id.add), "Added Successfully", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(findViewById(R.id.add), "Failureee", Snackbar.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);

                                Snackbar.make(findViewById(R.id.add), "Failure", Snackbar.LENGTH_LONG).show();
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                mDialog = ProgressDialog.show(AddInternship.this, "Loading", "Please Wait");
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
            }
        });
    }
}
