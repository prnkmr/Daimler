package com.daimler.karthi.daimler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LoginActivity extends ActionBarActivity {

    String baseURL;
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!getSharedPreferences("daimler",MODE_PRIVATE).getString("userid","").equals("")){
            onLogin();
        }
        baseURL=getString(R.string.baseURL);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        Button register=(Button)findViewById(R.id.signup);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        Button login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("")){
                    myToast("Give an username");
                    return;
                }
                if(password.getText().toString().equals("")){
                    myToast("Give a proper password");
                    return;
                }
                final ProgressDialog loading=new ProgressDialog(LoginActivity.this);
                loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loading.setMessage("Verifiying...");
                loading.setIndeterminate(true);
                loading.setCancelable(false);
                loading.show();
                String url=baseURL+"checkLogin.php";
                ArrayList param = new ArrayList();

                param.add(new BasicNameValuePair("username", username.getText().toString()));
                param.add(new BasicNameValuePair("password", password.getText().toString()));
                new AsyncPost(url, param, new AsyncListener() {
                @Override
                public void onResponse(String response) {
                    loading.cancel();
                    if(response==null){
                        myToast("Try Again");
                        return;
                    }
                    try {
                        JSONObject respJson=new JSONObject(response);
                        if(respJson.getString("status").equals("success")){
                            String userid=respJson.getString("userid");
                            myToast(userid);
                            SharedPreferences.Editor editor=getSharedPreferences("daimler",MODE_PRIVATE).edit();
                            editor.putString("userid",userid);
                            editor.putString("nextPremium",respJson.getString("nextPremium"));
                            editor.putString("date",respJson.getString("date"));
                            editor.putString("remainingDues",respJson.getString("remainingDues"));
                            editor.putString("date",respJson.getString("date"));
                            editor.putString("date",respJson.getString("date"));

                            editor.commit();
                            onLogin();
                        }else if(respJson.getString("status").equals("fail")){
                            myToast("Wrong Username/Password");
                            return;


                        }else{
                            myToast("Server Error");
                            return;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
                });
            }
        });

    }

    private void myToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    void onLogin(){
        startActivity(new Intent(this,Switcher.class));
    }
}
