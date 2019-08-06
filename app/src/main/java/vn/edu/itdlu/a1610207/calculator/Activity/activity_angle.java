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

import java.util.ArrayList;

import vn.edu.itdlu.a1610207.calculator.Core.CoreFunctions;
import vn.edu.itdlu.a1610207.calculator.R;

public class activity_angle extends AppCompatActivity implements View.OnClickListener {
	private Spinner spinner1, spinner2;
	private EditText editText1, editText2;
	private ImageButton button1, button2;
	private ArrayList<String> listAngle;
	private CoreFunctions functions = new CoreFunctions();
	private String str1, str2;
	private int id1, id2;
	private Object value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(getApplicationContext().getResources().getString(R.string.tag), "Opening activity_angle...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_angle);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);    //Remove activity label
		map();
		loadSpinner();
		addOnClickListener();
	}
	
	void map() {
		spinner1 = findViewById(R.id.spinner_angle_1);
		spinner2 = findViewById(R.id.spinner_angle_2);
		editText1 = findViewById(R.id.et_angle_1);
		editText2 = findViewById(R.id.et_angle_2);
		button1 = findViewById(R.id.btn_down);
		button2 = findViewById(R.id.btn_up);
	}
	
	void addOnClickListener() {
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}
	
	public void backToMainScreen_OnClick(View v) {
		finish();
	}
	
	@Override
	public void onClick(View view) {
		getID();
		switch (view.getId()) {
			case R.id.btn_down: //Chuyển từ đơn vị 1 sang đơn vị 2
				value = functions.convertToDouble(editText1.getText().toString());
				editText2.setText(functions.otherConverter(functions.Angle, id1, value, id2).toString());
				break;
			case R.id.btn_up:   //Chuyển từ đơn vị 2 sang đơn vị 1
				value = functions.convertToDouble(editText2.getText().toString());
				editText1.setText(functions.otherConverter(functions.Angle, id2, value, id1).toString());
				break;
			default:
				break;
		}
	}
	
	/**
	 * Vì mảng Angle bao gồm tên đơn vị và giá trị chuyển đổi
	 * nên phải lấy phần tử chẵn cho vào mảng
	 */
	void array2List() {
		String[] temp = functions.Angle;
		listAngle = new ArrayList<>();
		for (int i = 0; i < temp.length; i++) {
			if (i % 2 == 0)
				listAngle.add(temp[i]);
		}
	}
	
	void loadSpinner() {
		array2List();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_row, listAngle);
		spinner1.setAdapter(adapter);
		spinner2.setAdapter(adapter);
	}
	
	void getSpinner() {
		str1 = spinner1.getSelectedItem().toString();
		str2 = spinner2.getSelectedItem().toString();
	}
	
	/**
	 * Lấy chỉ số của phần tử đang được chọn trong mảng Angle
	 */
	void getID() {
		getSpinner();
		id1 = functions.findIndexInArray(functions.Angle, str1);
		id2 = functions.findIndexInArray(functions.Angle, str2);
	}
}
