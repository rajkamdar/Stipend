package app.stipend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        final String[] isDone=new String[1];
        final String[] isLogin=new String[1];
        final String[] userid=new String[1];

        Thread timerThread=new Thread(){
            public void run()
            {
                try
                {
                    sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    SharedPreferences mSharedPreferences=getSharedPreferences("system", Context.MODE_PRIVATE);
                    // isDone[0]=mSharedPreferences.getString("overview_status","N/A");
                    isLogin[0]=mSharedPreferences.getString("login_status","N/A");
                    userid[0]=mSharedPreferences.getString("student_email","NA");


                    if(isLogin[0]=="N/A")
                    {
                        Intent i=new Intent(Splash.this,Login.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {

                        if(userid[0]=="NA") {
                            Intent x = new Intent(Splash.this, CompanyHome2.class);


                            startActivity(x);
                            finish();
                        }
                        else
                        {
                            Intent x = new Intent(Splash.this, StudentHome.class);


                            startActivity(x);
                            finish();
                        }
                    }

                }

            }
        };

        timerThread.start();




    }
}
