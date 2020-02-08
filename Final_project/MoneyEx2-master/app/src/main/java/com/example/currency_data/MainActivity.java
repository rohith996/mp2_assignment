package com.example.currency_data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button go;
    TextView trail;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        go=findViewById(R.id.button);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trail=findViewById(R.id.Data);
                String cad=null,usd=null,inr=null;
                String currency_data="https://api.exchangeratesapi.io/"+editText.getText().toString();
                try{
                    String data=new Asyncdata().execute(currency_data).get();
                    JSONObject mainObj = new JSONObject(data);
                    JSONObject currdat=mainObj.getJSONObject("rates");
                    cad = currdat.getString("CAD");
                    usd = currdat.getString("USD");
                    inr = currdat.getString("INR");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            trail.setText("CAD: "+cad+" INR: "+inr+" USD: "+usd);
            }
        });


    }
}


