package vn.edu.itdlu.a1610207.calculator.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.itdlu.a1610207.calculator.Core.CoreFunctions;
import vn.edu.itdlu.a1610207.calculator.Core.PolishNotation;
import vn.edu.itdlu.a1610207.calculator.Database.Calculation;
import vn.edu.itdlu.a1610207.calculator.Database.CustomAdapter;
import vn.edu.itdlu.a1610207.calculator.Database.Database;
import vn.edu.itdlu.a1610207.calculator.R;

public class activity_standard extends AppCompatActivity implements AdapterView.OnItemClickListener {
	private static final String MISSING_OPEN = "Missing \")\"";
	private static final String MISSING_CLOSE = "Missing \"(\"";
	private static final String ALERT = "Are you sure want to delete?";
	private static final int MENU_ITEM_SELECT = 111;
	private static final int MENU_ITEM_DELETE = 222;
	private static final int MENU_ITEM_DELETE_OTHERS = 333;
	private static final int MENU_ITEM_DELETE_ALL = 444;
	private TextView tv_result, tv_exp;
	private String operators = "['+']|['×']|['÷']", special = "['%']|['²']|['⁻¹']|[')']";
	private PolishNotation notation = new PolishNotation();
	private CoreFunctions functions = new CoreFunctions();
	private boolean completed = false;
	private ListView listView;
	private List<Calculation> listCalculations = new ArrayList<>();
	private CustomAdapter adapter;
	private DrawerLayout drawerLayout;
	private Vibrator vibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(getApplicationContext().getResources().getString(R.string.tag), "Opening activity_standard...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standard);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);    //Remove activity label
		map();
		loadDB2ListView();
		registerForContextMenu(listView);
	}
	
	void map() {
		tv_result = findViewById(R.id.result);
		tv_exp = findViewById(R.id.formula);
		listView = findViewById(R.id.list_history);
		listView.setOnItemClickListener(this);
		drawerLayout = findViewById(R.id.layout_history);
		vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	void loadDB2ListView() {
		Database db = new Database(this);
		listCalculations.clear();
		List<Calculation> list = db.getAll();
		listCalculations.addAll(list);
		adapter = new CustomAdapter(getApplicationContext(), listCalculations);
		listView.setAdapter(adapter);
	}
	
	public void backToMainScreen_OnClick(View v) {
		finish();
	}
	
	String replaceString(String str) {
		str = str.replace("%", "/100");
		str = str.replace("²", "^^2");
		str = str.replace("⁻¹", "^^-1");
		str = str.replace('×', '*');
		str = str.replace('÷', '/');
		return str;
	}
	
	String replaceSqrt(String str) {
		str = str.substring(1, str.length());
		str += "^^(1/2)";
		return str;
	}
	
	String replaceAllSqrt(String str) {
		int length = str.length();
		int count = 0;
		int i, j = 0;
		for (i = 0; i < length; i++) {
			if (str.charAt(i) == '√') {
				boolean flag = false;
				for (j = i + 1; !flag || count != 0; j++)
					if (str.charAt(j) == '(') {
						count++;
						flag = true;
					} else if (str.charAt(j) == ')')
						count--;
				str = str.replace(str.substring(i, j), replaceSqrt(str.substring(i, j)));
				length += 6;
			}
		}
		return str;
	}
	
	String replaceAllNegative(String str) {
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) == '-' && str.charAt(i + 1) == '(') {
				if (i == 0) { //-(x)
					str = "(0" + str + ")"; //0-(x)
					length++;
				} else if (i > 0 && str.charAt(i - 1) == '(') { //(-(x))^2
					str = str.substring(0, i) + "0" + str.substring(i); //(0-(x))^2
					length++;
				} else if (i > 0) { //2--(1)
					str = str.substring(0, i) + "(0" + str.substring(i, i + 1) + str.substring(i + 2);    //2-(0-1)
					length++;
				}
			} else if (str.charAt(i) == '-' && i > 0 && str.charAt(i - 1) == '^') { //x^-1
				str = str.substring(0, i) + "(0-1)" + str.substring(i + 2); //x^(0-1)
				length += 3;
			}
		}
		return str;
	}
	
	boolean containDigit(String str) {
		return !str.matches(operators) && !str.equals("-");
	}
	
	boolean endWithSpecial(String str) {
		String last = str.substring(str.length() - 1);
		return last.matches(special);
	}
	
	public void btn_number_OnClick(View v) {
		String input = tv_result.getText().toString();
		String expression = tv_exp.getText().toString();
		if (completed) {
			expression = "";
			completed = false;
		}
		if (input.equals("0") || endWithSpecial(input))
			input = "";
		else if (input.matches(operators) || input.equals("-")) {
			expression += input;
			input = "";
		}
		input += ((Button) v).getText().toString();
		tv_result.setText(input);
		tv_exp.setText(expression);
	}
	
	public void btn_neg_OnClick(View v) {
		String input = tv_result.getText().toString();
		if (completed) {
			tv_exp.setText("");
			completed = false;
			notation.release();
		}
		if (containDigit(input)) {
			if (input.charAt(0) == '-') {
				input = input.substring(2, input.length() - 1);
			} else {
				input = "-(" + input + ")";
			}
		}
		tv_result.setText(input);
		
	}
	
	public void btn_dot_OnClick(View v) {
		String input = tv_result.getText().toString();
		if (Character.isDigit(input.charAt(input.length() - 1)) && !completed) {
			input += ".";
		}
		tv_result.setText(input);
	}
	
	public void btn_equal_OnClick(View v) {
		if (completed)
			return;
		String exp = tv_exp.getText().toString();
		String input = tv_result.getText().toString();
		Database db = new Database(this);
		if (containDigit(input))
			exp += input;
		String newStr = replaceAllNegative(replaceAllSqrt(replaceString(exp)));
		notation.setExpression(newStr);
		int flag = notation.checkExpression();
		if (flag == -1)
			Toast.makeText(getApplicationContext(), MISSING_OPEN, Toast.LENGTH_LONG).show();
		else if (flag == -2)
			Toast.makeText(getApplicationContext(), MISSING_CLOSE, Toast.LENGTH_LONG).show();
		else {
			vibrator.vibrate(150);
			completed = true;
			notation.infixToPostfix();
			String result = notation.compute_postFix(10).toString();
			input = functions.fixType(Double.parseDouble(result)).toString();
			tv_result.setText(input);
			tv_exp.setText(exp);
			Calculation newItem = new Calculation(exp, input);
			listCalculations.add(newItem);
			adapter.notifyDataSetChanged();
			db.add(newItem);
		}
	}
	
	public void btn_opr_OnClick(View v) {
		String input = tv_result.getText().toString();
		String expression = tv_exp.getText().toString();
		if (completed) {
			expression = "";
			completed = false;
			notation.release();
		}
		if (containDigit(input))
			expression += input;
		int id = v.getId();
		if (id == R.id.btn_plus)
			input = "+";
		else if (id == R.id.btn_mul)
			input = "×";
		else if (id == R.id.btn_sub)
			input = "-";
		else input = "÷";
		tv_exp.setText(expression);
		tv_result.setText(input);
	}
	
	public void btn_deleteAll_OnClick(View v) {
		tv_exp.setText("");
		tv_result.setText("0");
		notation.release();
		completed = false;
	}
	
	public void btn_delete_OnClick(View v) {
		if (completed)
			btn_deleteAll_OnClick(v);
		else tv_result.setText("0");
	}
	
	public void btn_backspace_OnClick(View v) {
		if (!completed) {
			String input = tv_result.getText().toString();
			input = input.substring(0, input.length() - 1);
			if (input.equals(""))
				input = "0";
			tv_result.setText(input);
		}
	}
	
	public void btn_sqrt_OnClick(View v) {
		String input = tv_result.getText().toString();
		if (completed) {
			tv_exp.setText("");
			completed = false;
			notation.release();
		}
		if (containDigit(input)) {
			input = "√(" + input + ")";
			tv_result.setText(input);
		}
	}
	
	public void btn_percent_OnClick(View v) {
		String input = tv_result.getText().toString();
		if (completed) {
			tv_exp.setText("");
			completed = false;
			notation.release();
		}
		if (endWithSpecial(input)) {
			input = "(" + input + ")%";
		} else if (containDigit(input)) {
			input += "%";
		}
		tv_result.setText(input);
	}
	
	public void btn_square_OnClick(View v) {
		String input = tv_result.getText().toString();
		if (completed) {
			tv_exp.setText("");
			completed = false;
			notation.release();
		}
		if (endWithSpecial(input)) {
			input = "(" + input + ")²";
		} else if (containDigit(input)) {
			input += "²";
		}
		tv_result.setText(input);
	}
	
	public void btn_reverse_OnClick(View v) {
		String input = tv_result.getText().toString();
		if (completed) {
			tv_exp.setText("");
			completed = false;
			notation.release();
		}
		if (endWithSpecial(input)) {
			input = "(" + input + ")⁻¹";
		} else if (containDigit(input)) {
			input += "⁻¹";
		}
		tv_result.setText(input);
	}
	
	public void btn_history_OnClick(View v) {
		drawerLayout.openDrawer(GravityCompat.END);
	}
	
	@Override
	public void onItemClick(final AdapterView<?> list, final View v, final int position, final long id) {
		selectItem(listCalculations.get(position));
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		menu.setHeaderTitle(R.string.title_context_menu);
		menu.add(0, MENU_ITEM_SELECT, 0, R.string.context_menu_select);
		menu.add(0, MENU_ITEM_DELETE, 1, R.string.context_menu_delete);
		menu.add(0, MENU_ITEM_DELETE_ALL, 2, R.string.context_menu_delete_all);
		menu.add(0, MENU_ITEM_DELETE_OTHERS, 3, R.string.context_menu_delete_but_others);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		final Calculation calc = (Calculation) this.listView.getItemAtPosition(info.position);
		switch (item.getItemId()) {
			case MENU_ITEM_SELECT:
				selectItem(calc);
				break;
			case MENU_ITEM_DELETE:
				new AlertDialog.Builder(this).
						setMessage(ALERT)
						.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteItem(calc);
					}
				}).setNegativeButton("No", null).show();
				break;
			case MENU_ITEM_DELETE_ALL:
				new AlertDialog.Builder(this).
						setMessage(ALERT)
						.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteAll();
					}
				}).setNegativeButton("No", null).show();
				break;
			case MENU_ITEM_DELETE_OTHERS:
				new AlertDialog.Builder(this).
						setMessage(ALERT)
						.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteOthers(calc);
					}
				}).setNegativeButton("No", null).show();
				break;
			default:
				return false;
		}
		return true;
	}
	
	void selectItem(Calculation calc) {
		tv_exp.setText(calc.getExpression());
		tv_result.setText(calc.getResult());
		drawerLayout.closeDrawers();
	}
	
	void deleteItem(Calculation calc) {
		this.listCalculations.clear();
		Database db = new Database(this);
		db.delete(calc);
		List<Calculation> list = db.getAll();
		this.listCalculations.addAll(list);
		loadDB2ListView();
	}
	
	void deleteAll() {
		this.listCalculations.clear();
		Database db = new Database(this);
		db.deleteAll();
		List<Calculation> list = db.getAll();
		this.listCalculations.addAll(list);
		loadDB2ListView();
	}
	
	void deleteOthers(Calculation calc) {
		Database db = new Database(this);
		this.listCalculations.clear();
		db.deleteOthers(calc);
		List<Calculation> list = db.getAll();
		this.listCalculations.addAll(list);
		loadDB2ListView();
	}
}
