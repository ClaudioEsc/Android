package com.claudio.temperatura;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    private EditText text;
    private TextView tview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (EditText)findViewById(R.id.txtValue);
        tview = (TextView)findViewById(R.id.tvwResult);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnCalc:
                RadioButton celsiusButton = (RadioButton) findViewById(R.id.rdbCelcius);
                RadioButton fahrenheitButton = (RadioButton) findViewById(R.id.rdbFahrenheit);

                if (text.getText().length() == 0) {
                    Toast.makeText(this, "Debe ingresar un valor", Toast.LENGTH_LONG).show();
                    return;
                }

                float inputValue = Float.parseFloat(text.getText().toString());

                if (celsiusButton.isChecked()) {
                    tview.setText(String.valueOf(convertFC(inputValue)));
                } else {
                    tview.setText(String.valueOf(convertCF(inputValue)));
                }
                break;
        }
    }

    private float convertFC(float f) {
        return (f-32)*5/9;
    }

    private float convertCF(float c) {
        return (c*9/5) +32;
    }
}
