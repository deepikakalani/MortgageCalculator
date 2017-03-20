package com.example.deepika.mortgagecalculator;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Context;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

/**
 * Created by deepika on 3/15/17.
 */

public class SaveActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        final Context ctx = this;
        final String monthly_payment;

        final EditText e_street = (EditText) findViewById(R.id.address);
        final EditText e_city = (EditText) findViewById(R.id.city);
        final EditText e_state = (EditText) findViewById(R.id.state);
        final EditText e_zip = (EditText) findViewById(R.id.zip);

        final String addresskey = "addressKey";
        final String citykey = "cityKey";
        final String statekey = "stateKey";
        final String zipkey = "zipKey";

        Intent intent = getIntent();

        monthly_payment = intent.getStringExtra("monthly_amount");
        //Log.d("Save Activity ", text);

        Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        String[] list = new String[]{"House", "TownHouse", "Condo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SaveActivity.this, R.layout.support_simple_spinner_dropdown_item, list);
        dropdown.setAdapter(adapter);

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);

        final DatabaseOperations handler = new DatabaseOperations(getBaseContext(), TableData.TableInfo.DATABASE_NAME, null, 1);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String addr = e_street.getText().toString();
                String city = e_city.getText().toString();
                String state = e_state.getText().toString();
                String zip = e_zip.getText().toString();
                List<Address> addresses = null;
                try
                {
                    addresses = validate_address(addr, city, state, zip);
                }
                catch (Exception e){e.printStackTrace();}

                if (addresses.size() >= 1)
                {
                    handler.getWritableDatabase();
                    long k = handler.insertInfo(handler, addr, city, state, monthly_payment);
                    /*Cursor c = handler.getInfo(handler);
                    int count = c.getCount();
                    c.moveToFirst();
                    String demo = c.getString(0);
                    String demo1 = c.getString(1);
                    String demo3 = c.getString(2);*/
                    handler.close();
                    Log.d("Save", String.valueOf(k));

                }
            }

        });
    }

    public List<Address> validate_address(String addr, String city, String state, String zip) throws Exception{
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocationName(addr + city + state, 3);
        return addresses;

    }

}