package com.example.deepika.mortgagecalculator;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    Button bcalculate, bclear;
    int pp, dp, rate, period, sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText property_price = (EditText)findViewById(R.id.propertyprice);
        final EditText down_payment = (EditText)findViewById(R.id.downpayment);
        final EditText annual_rate = (EditText)findViewById(R.id.rate);
        final EditText term = (EditText)findViewById(R.id.period);
        bcalculate = (Button)findViewById(R.id.bcalculate);
        bclear = (Button)findViewById(R.id.bclear);
        final TextView m_payment = (TextView)findViewById(R.id.text_monthly_payment);
        final Button bsave = new Button(this);
        bsave.setText("Save");
        final RelativeLayout rl = (RelativeLayout)findViewById(R.id.activity_main);
        final ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final Intent intent = new Intent(getBaseContext(), SaveActivity.class);


        bcalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp = Integer.parseInt(property_price.getText().toString());
                dp = Integer.parseInt(down_payment.getText().toString());
                rate = Integer.parseInt(annual_rate.getText().toString());
                period = Integer.parseInt(term.getText().toString());


                int payment = calculate_mortgage(pp, dp, rate, period);

                //sum = (pp-dp)*rate*period/100;
                m_payment.setText(String.valueOf(payment));

                rl.addView(bsave, lp);
            }
        });

        bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                property_price.setText("");
                down_payment.setText("");
                annual_rate.setText("");
                term.setText("");
                m_payment.setText("");
                rl.removeView(bsave);
            }
        });

        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("monthly_amount", String.valueOf(sum));
                startActivity(intent);
            }
        });
    }

    public int calculate_mortgage(int pp, int dp, int rate, int period){


        int principal = pp - dp;
        int r = (rate/12)/100;
        int n = period*12;
        int monthly_payment;
        monthly_payment = principal*(((r*(1+r)^n))/((1+r)^n -1));
        return monthly_payment;
    }

}