package br.com.davividal.conversormoeda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
    private static final Float ExangeRate = (float) 3.5;
    private static final String ServiceUrl = "http://ec2-52-67-202-68.sa-east-1.compute.amazonaws.com/conversao.php?moeda=%s&valor=%f";
    private String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void convert(View view) {
        EditText originalValueText = (EditText) findViewById(R.id.originalValue);
        RadioGroup currency = (RadioGroup) findViewById(R.id.currency);
        Float originalValue;

        String currencyName = "";

        assert null != currency && null != originalValueText;

        originalValue = Float.parseFloat(String.valueOf(originalValueText.getText()));

        if (R.id.dollar == currency.getCheckedRadioButtonId()) {
            currencyName = "dolar";
            code = "US$";
        } else if (R.id.euro == currency.getCheckedRadioButtonId()) {
            currencyName = "euro";
            code = "€";
        }

        String url = String.format(new Locale("en", "us"), ServiceUrl, currencyName, originalValue);
        RequestQueue queue = CustomVolleyRequestQueue.getInstance(getApplicationContext()).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this, this);
        queue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Ocorreu um erro!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        Float newValue = Float.parseFloat(response);

        Toast.makeText(
                this,
                String.format(new Locale("pt", "br"), "Novo valor: %s %.2f", code, newValue),
                Toast.LENGTH_LONG
        ).show();
    }
}
