package vn.edu.itdlu.a1610207.calculator.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import vn.edu.itdlu.a1610207.calculator.Core.CoreFunctions;
import vn.edu.itdlu.a1610207.calculator.R;

public class activity_date extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
	
	private static final String format = "dd/MM/yyyy";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
	private final String ADD = "Add";
	private Spinner spinner;
	private ConstraintLayout constraintLayout1, constraintLayout2;
	private ImageButton imageButton1, imageButton2;
	private Calendar calendar_from, calendar_to;
	private DatePickerDialog.OnDateSetListener date;
	private TextView textView_Result, textView_Button;
	private CoreFunctions functions = new CoreFunctions();
	private EditText etYear, etMonth, etDay;
	private RadioGroup group;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(getApplicationContext().getResources().getString(R.string.tag), "Opening activity_date...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);    //Remove activity label
		map();
		loadSpinner();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	
	void map() {
		spinner = findViewById(R.id.spinner_datemode);
		constraintLayout1 = findViewById(R.id.diff_mode);
		constraintLayout2 = findViewById(R.id.addsubtract_mode);
		imageButton1 = findViewById(R.id.btn_from_diff);
		imageButton2 = findViewById(R.id.btn_to_diff);
		calendar_from = Calendar.getInstance();
		calendar_to = Calendar.getInstance();
		updateDate((TextView) findViewById(R.id.tv_date_from_diff));
		updateDate((TextView) findViewById(R.id.tv_date_to_diff));
		updateDate((TextView) findViewById(R.id.tv_date_from));
		etYear = findViewById(R.id.et_years);
		etMonth = findViewById(R.id.et_months);
		etDay = findViewById(R.id.et_days);
		group = findViewById(R.id.radiogroup);
	}
	
	void loadSpinner() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dateMode, R.layout.spinner_row);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}
	
	public void backToMainScreen_OnClick(View v) {
		finish();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		if (pos == 0) {
			constraintLayout1.setVisibility(View.VISIBLE);
			constraintLayout2.setVisibility(View.GONE);
			imageButton1 = findViewById(R.id.btn_from_diff);
			imageButton2 = findViewById(R.id.btn_to_diff);
			textView_Result = findViewById(R.id.tv_difference_result);
			textView_Button = findViewById(R.id.textView2);
		} else {
			constraintLayout1.setVisibility(View.GONE);
			constraintLayout2.setVisibility(View.VISIBLE);
			imageButton1 = findViewById(R.id.btn_from);
			textView_Result = findViewById(R.id.tv_addsubtract_result);
			textView_Button = findViewById(R.id.textView7);
			
		}
		textView_Button.setOnClickListener(this);
		addOnClickListener();
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	
	}
	
	void addOnClickListener() {
		imageButton1.setOnClickListener(this);
		imageButton2.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_from_diff:
				date = new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
						calendar_from.set(Calendar.YEAR, i);
						calendar_from.set(Calendar.MONTH, i1);
						calendar_from.set(Calendar.DAY_OF_MONTH, i2);
						updateDate((TextView) findViewById(R.id.tv_date_from_diff));
					}
				};
				new DatePickerDialog(activity_date.this, date, calendar_from
						.get(Calendar.YEAR), calendar_from.get(Calendar.MONTH),
						calendar_from.get(Calendar.DAY_OF_MONTH)).show();
				break;
			case R.id.btn_to_diff:
				date = new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
						calendar_to.set(Calendar.YEAR, i);
						calendar_to.set(Calendar.MONTH, i1);
						calendar_to.set(Calendar.DAY_OF_MONTH, i2);
						updateDate((TextView) findViewById(R.id.tv_date_to_diff));
					}
				};
				new DatePickerDialog(activity_date.this, date, calendar_to
						.get(Calendar.YEAR), calendar_to.get(Calendar.MONTH),
						calendar_to.get(Calendar.DAY_OF_MONTH)).show();
				break;
			case R.id.btn_from:
				date = new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
						calendar_from.set(Calendar.YEAR, i);
						calendar_from.set(Calendar.MONTH, i1);
						calendar_from.set(Calendar.DAY_OF_MONTH, i2);
						updateDate((TextView) findViewById(R.id.tv_date_from));
					}
				};
				new DatePickerDialog(activity_date.this, date, calendar_from
						.get(Calendar.YEAR), calendar_from.get(Calendar.MONTH),
						calendar_from.get(Calendar.DAY_OF_MONTH)).show();
				break;
			case R.id.textView2:
				textView_Result.setText(functions.different(
						((TextView) findViewById(R.id.tv_date_from_diff)).getText().toString(),
						((TextView) findViewById(R.id.tv_date_to_diff)).getText().toString()));
				break;
			case R.id.textView7:
				char c = ((RadioButton) findViewById(group.getCheckedRadioButtonId())).getText().equals(ADD) ? '+' : '-';
				textView_Result.setText(functions.changeDay(((TextView) findViewById(R.id.tv_date_from)).getText().toString(), getDateFromEditTexts(), c));
			default:
				break;
		}
	}
	
	void updateDate(TextView textView) {
		switch (textView.getId()) {
			case R.id.tv_date_from_diff:
				textView.setText(simpleDateFormat.format(calendar_from.getTime()));
				break;
			case R.id.tv_date_to_diff:
				textView.setText(simpleDateFormat.format(calendar_to.getTime()));
				break;
			case R.id.tv_date_from:
				textView.setText(simpleDateFormat.format(calendar_from.getTime()));
				break;
			default:
				break;
		}
	}
	
	Map<Character, Integer> getDateFromEditTexts() {
		Map<Character, Integer> date = new HashMap<>();
		int d, m, y;
		String D = etDay.getText().toString(), M = etMonth.getText().toString(), Y = etYear.getText().toString();
		if (Y.equals(""))
			y = 0;
		else y = Integer.parseInt(Y);
		if (M.equals(""))
			m = 0;
		else m = Integer.parseInt(M);
		if (D.equals(""))
			d = 0;
		else d = Integer.parseInt(D);
		date.put('y', y);
		date.put('m', m);
		date.put('d', d);
		return date;
	}
}
