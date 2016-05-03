package br.com.davividal.conversormoeda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final Float ExangeRate = (float) 3.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void convert(View view) {
        EditText originalValueText = (EditText) findViewById(R.id.originalValue);
        TextView newValueText = (TextView) findViewById(R.id.newValue);
        RadioGroup currency = (RadioGroup) findViewById(R.id.currency);
        Float originalValue, newValue = null;
        String code = "";

        assert null != currency && null != originalValueText && null != newValueText;

        originalValue = Float.parseFloat(String.valueOf(originalValueText.getText()));

        if (R.id.dollar == currency.getCheckedRadioButtonId()) {
            newValue = originalValue * ExangeRate;
            code = "US$";
        } else if (R.id.real == currency.getCheckedRadioButtonId()) {
            newValue = originalValue / ExangeRate;
            code = "R$";
        }

        assert null != newValue;

        newValueText.setText(String.format(new Locale("pt", "br"), "%s %.2f", code, newValue));
    }
}
