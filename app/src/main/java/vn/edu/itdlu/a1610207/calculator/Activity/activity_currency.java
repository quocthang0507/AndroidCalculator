package vn.edu.itdlu.a1610207.calculator.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import vn.edu.itdlu.a1610207.calculator.Core.CoreFunctions;
import vn.edu.itdlu.a1610207.calculator.R;

public class activity_currency extends AppCompatActivity implements View.OnClickListener {
	private final String ENSURE_INTERNET = "Please ensure that you have a stable internet connection";
	private ArrayList<String> listCurrency;
	private CoreFunctions functions = new CoreFunctions();
	private Spinner spinner1, spinner2;
	private EditText editText1, editText2;
	private TextView textView;
	private ImageButton button1, button2;
	private String str1, str2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(getApplicationContext().getResources().getString(R.string.tag), "Opening activity_currency...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);    //Remove activity label
		map();
		loadSpinner();
		//addOnClickListener();
		Toast.makeText(this, "Tính năng này không khả dụng...", Toast.LENGTH_SHORT).show();
	}
	
	void map() {
		spinner1 = findViewById(R.id.spinner_currency_1);
		spinner2 = findViewById(R.id.spinner_currency_2);
		editText1 = findViewById(R.id.et_currency_1);
		editText2 = findViewById(R.id.et_currency_2);
		button1 = findViewById(R.id.btn_down);
		button2 = findViewById(R.id.btn_up);
		textView = findViewById(R.id.textView_exchange);
	}
	
	void loadSpinner() {
		listCurrency = new ArrayList<>(Arrays.asList(functions.Currency));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_row, listCurrency);
		spinner1.setAdapter(adapter);
		spinner2.setAdapter(adapter);
	}
	
	public void backToMainScreen_OnClick(View v) {
		finish();
	}
	
	void addOnClickListener() {
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		double rate, from;
		getSpinner();
		switch (view.getId()) {
			case R.id.btn_down:
				rate = functions.getExchangeRate(getApplicationContext(), str1, str2);
				textView.setText("1 " + str1 + " = " + rate + str2);
				from = functions.convertToDouble(editText1.getText().toString());
				editText2.setText("" + from * rate);
				break;
			case R.id.btn_up:
				rate = functions.getExchangeRate(getApplicationContext(), str2, str1);
				textView.setText("1 " + str2 + " = " + rate + str1);
				from = functions.convertToDouble(editText2.getText().toString());
				editText1.setText("" + from * rate);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Lấy chữ của 2 spinner cho vào str1, str2
	 */
	void getSpinner() {
		str1 = spinner1.getSelectedItem().toString();
		str2 = spinner2.getSelectedItem().toString();
		if (str1.contains(" ")) str1 = str1.split(" ")[0];
		if (str2.contains(" ")) str2 = str2.split(" ")[0];
	}
	
	/*
	boolean checkInternetConnection() {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
			int exitValue = process.waitFor();
			return (exitValue == 0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	*/
}
