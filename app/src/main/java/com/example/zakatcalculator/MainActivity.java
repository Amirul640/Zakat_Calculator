package com.example.zakatcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText gram, value;
    Button calcBTN, resetBTN;
    TextView output, outputt, outputtt;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    float gweight, gvalue;

    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;
    private Menu menu;

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        gram = (EditText) findViewById(R.id.amount);
        value = (EditText) findViewById(R.id.cgold);
        output = (TextView) findViewById(R.id.totalGold);
        outputt = (TextView) findViewById(R.id.zakatPay);
        outputtt = (TextView) findViewById(R.id.totalZakat);
        calcBTN = (Button) findViewById(R.id.btncal);
        resetBTN = (Button) findViewById(R.id.btnreset);

        calcBTN.setOnClickListener(this);
        resetBTN.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gweight = sharedPref.getFloat("weight", 0.0F);

        sharedPref2 = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gvalue = sharedPref2.getFloat("weight", 0.0F);

        gram.setText(""+gweight);
        value.setText(""+gvalue);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {


        try {
            switch (v.getId()){

                case R.id.btncal:
                    calc();
                    break;

                case R.id.btnreset:
                    gram.setText("");
                    value.setText("");
                    output.setText("Total Gold Value: RM");
                    outputt.setText("Zakat Payable    : RM");
                    outputtt.setText("Total Zakat         : RM");

                    break;
            }
        }catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Input Missing!", Toast.LENGTH_SHORT).show();

        }catch (Exception exp) {
            Toast.makeText(this, "Unknown Exception" + exp.getMessage(), Toast.LENGTH_SHORT).show();

            Log.d("Exception",exp.getMessage());
        }
    }

    public void calc(){
        DecimalFormat df = new DecimalFormat("##.00");
        float gweight = Float.parseFloat(gram.getText().toString());
        float gvalue = Float.parseFloat(value.getText().toString());
        String stat = spinner.getSelectedItem().toString();
        double tValue, uruf, zakatpay, tZakat;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("weight", gweight);
        editor.apply();
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", gvalue);
        editor2.apply();

        if (stat.equals("Keep")){
            tValue = gweight * gvalue;
            uruf = gweight - 85;

            if (uruf>=0.0) {
                zakatpay = uruf * gvalue;
                tZakat = zakatpay *0.025;
            }

            else{
                zakatpay = 0.0;
                tZakat = zakatpay * 0.025;
            }

            output.setText("Total Gold Value: RM"+ df.format(tValue));
            outputt.setText("Zakat Payable    : RM"+ df.format(zakatpay));
            outputtt.setText("Total Zakat         : RM"+ df.format(tZakat));
        }

        else{
            tValue = gweight * gvalue;
            uruf = gweight - 200;

            if (uruf>=0.0) {
                zakatpay = uruf * gvalue;
                tZakat = zakatpay *0.025;
            }

            else{
                zakatpay = 0.0;
                tZakat = zakatpay * 0.025;
            }

            output.setText("Total Gold Value: RM"+ df.format(tValue));
            outputt.setText("Zakat Payable    : RM"+ df.format(zakatpay));
            outputtt.setText("Total Zakat         : RM"+ df.format(tZakat));
        }
    }
}