package com.daimler.karthi.daimler;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class Insurance3 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance3);
        ((Button)findViewById(R.id.recheck)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=getString(R.string.baseURL)+"toClient.php";
                ArrayList param=new ArrayList();
                param.add(new BasicNameValuePair("userid","1"));
                new AsyncPost(url, param, new AsyncListener() {
                    @Override
                    public void onResponse(String response) {
                        if(response==null){
                            Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(response=="1"){
                            ImageView img=(ImageView)findViewById(R.id.verification);
                            img.setImageResource(R.drawable.correct);
                        }
                    }
                })
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insurance3, menu);
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
