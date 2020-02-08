package com.example.currency_data;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Httphandler {
    public Httphandler() {
    }
    public String makeServiceCall(String url)
    {
        String response= null;
        try {
            URL mUrl =new URL(url);
            HttpsURLConnection con =(HttpsURLConnection) mUrl.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type","application/json");
            InputStream in=new BufferedInputStream(con.getInputStream());
            response = convertStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String convertStream(InputStream is)
    {
        BufferedReader read= new BufferedReader((new InputStreamReader(is)));
        StringBuilder sb=new StringBuilder();
        String line;
        try
        {
            while ( (line =read.readLine())!=null)
            {
                sb.append(line+"\n");
            }
        }catch (IOException e){
            Log.e("CoveryStream Method",e.getMessage());
        }
        finally {
            try {
                is.close();
            } catch (IOException e)
            {
                Log.e("COnvertStream F Method",e.getMessage());
            }
        }
        return sb.toString();
    }

}
