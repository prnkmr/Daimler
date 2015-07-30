package com.daimler.karthi.daimler;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class RegisterActivity extends ActionBarActivity {
    EditText username,password,retypePassword,email;
    String baseURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseURL=getString(R.string.baseURL);
        setContentView(R.layout.activity_register);
        username=(EditText)findViewById(R.id.rusername);
        password=(EditText)findViewById(R.id.rpassword);
        retypePassword=(EditText)findViewById(R.id.retypepassword);
        email=(EditText)findViewById(R.id.email);
        Button register=(Button)findViewById(R.id.rregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("")){
                    myToast("Please Give Username");
                    return;
                }
                if(password.getText().toString().equals("")){
                    myToast("Please Give a valid Password");
                    return;
                }
                if(!password.getText().toString().equals(retypePassword.getText().toString())){
                    myToast("Password Mismatch");
                    return;
                }

                register();
            }
        });
    }

    private void register() {
        final ProgressDialog loading=new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Checking...");
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.show();

        String url=baseURL+"register.php";
        ArrayList param = new ArrayList();
        param.add(new BasicNameValuePair("username",username.getText().toString()));
        param.add(new BasicNameValuePair("password",password.getText().toString()));
        param.add(new BasicNameValuePair("email",email.getText().toString()));

        new AsyncPost(url, param, new AsyncListener() {
            @Override
            public void onResponse(String response) {
                loading.cancel();
                if(response==null){
                    myToast("Try Again");
                    return;
                }
                if(response.equals("success")){

                    myToast("Registration Success");
                    finish();
                }else if(response.equals("available")){
                    myToast("Try with different username");
                    return;
                }else{
                    myToast("Server Error");
                    return;
                }
            }
        });
    }

    private void myToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
}
