package app.stipend;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.entity.StringEntityHC4;
import org.apache.http.entity.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ForgotPassword extends AppCompatActivity {
    Button forgot_submit;
    EditText forgot_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setTitle("Forgot Password");



        forgot_number= (EditText) findViewById(R.id.forgot_number);
        forgot_submit= (Button) findViewById(R.id.forgot_submit);

forgot_submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        JSONObject mJsonObject = new JSONObject();
        StringEntity mStringEntity = null;
        try {
            mJsonObject.put("mno", forgot_number.getText()+"");
            mStringEntity = new StringEntity(mJsonObject.toString());


        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");


        client.post(ForgotPassword.this, "http://192.168.23.1:12345/webservice.asmx/forgotpassword", mStringEntity, "application/json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONArray mJsonArray = response.getJSONArray("d");
                            JSONObject jsonObject = mJsonArray.getJSONObject(0);
                            Intent mIntent = new Intent(getApplicationContext(), ForgotPassword.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0, mIntent, 0);
                            SmsManager smsManager = SmsManager.getDefault();
                            if(jsonObject.getString("company_password")=="null") {
                                smsManager.sendTextMessage(forgot_number.getText().toString() + "", null, "Your password is " + jsonObject.getString("student_password"), pendingIntent, null);
                            }
                            else
                            {
                                smsManager.sendTextMessage(forgot_number.getText().toString() + "", null, "Your password is " + jsonObject.getString("company_password"), pendingIntent, null);
                            }
                            Toast.makeText(ForgotPassword.this, "Message sent.", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(ForgotPassword.this, "Please check the number", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                }
        );

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
});
    }
}
