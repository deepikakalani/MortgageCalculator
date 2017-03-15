package com.example.deepika.mortgagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button bcalculate;
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
        final TextView m_payment = (TextView)findViewById(R.id.text_monthly_payment);

        bcalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp = Integer.parseInt(property_price.getText().toString());
                dp = Integer.parseInt(down_payment.getText().toString());
                rate = Integer.parseInt(annual_rate.getText().toString());
                period = Integer.parseInt(term.getText().toString());

                sum = (pp-dp)*rate*period/100;
                m_payment.setText(String.valueOf(sum));
            }
        });



    }
}
