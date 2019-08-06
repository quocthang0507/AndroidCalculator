package vn.edu.itdlu.a1610207.calculator.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.itdlu.a1610207.calculator.Core.CoreFunctions;
import vn.edu.itdlu.a1610207.calculator.Core.PolishNotation;
import vn.edu.itdlu.a1610207.calculator.R;

public class activity_programmer extends AppCompatActivity implements TextWatcher {
	private static final String RO_R = "RoR(";
	private static final String RO_L = "RoL(";
	private static final String ID = "id";
	private static final String BTN = "btn_";
	private static final String MISSING_OPEN = "Thiếu dấu mở ngoặc";
	private static final String MISSING_CLOSE = "Thiếu dấu đóng ngoặc";
	private static final String RSH = "Rsh";
	private static final String LSH = "Lsh";
	private static final String AND = "And";
	private static final String XOR = "Xor";
	private static final String OR = "Or";
	private static final String OCT = "OCT";
	private static final String HEX = "HEX";
	private static final String DEC = "DEC";
	private static final String BIN = "BIN";
	private Button btn_hex, btn_dec, btn_bin, btn_oct;
	private String currentBase = "";
	private int _currentBase;
	private TextView result, exp, tv_dec, tv_oct, tv_hex, tv_bin, temp;
	private PolishNotation notation = new PolishNotation();
	private boolean completed = false;
	private List<Integer> listID = new ArrayList<>();
	private CoreFunctions functions = new CoreFunctions();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(getApplicationContext().getResources().getString(R.string.tag), "Opening activity_programmer...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_programmer);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);    //Remove activity label
		map();
		getAllNumberButtons();
		findViewById(R.id.btn_dec).performClick();  //Đưa chế độ DECimal làm mặc định
	}
	
	void map() {
		btn_bin = findViewById(R.id.btn_bin);
		btn_dec = findViewById(R.id.btn_dec);
		btn_hex = findViewById(R.id.btn_hex);
		btn_oct = findViewById(R.id.btn_oct);
		result = findViewById(R.id.tv_result);
		exp = findViewById(R.id.tv_exp);
		tv_bin = findViewById(R.id.tv_bin);
		tv_dec = findViewById(R.id.tv_dec);
		tv_hex = findViewById(R.id.tv_hex);
		tv_oct = findViewById(R.id.tv_oct);
	}
	
	public void backToMainScreen_OnClick(View v) {
		finish();
	}
	
	/**
	 * Khôi phục trang thái của chế độ nhập liệu (Định dạng nhập liệu dành cho DEC, HEX, OCT, BIN)
	 */
	void resetMode() {
		btn_bin.setTypeface(Typeface.DEFAULT);
		btn_oct.setTypeface(Typeface.DEFAULT);
		btn_hex.setTypeface(Typeface.DEFAULT);
		btn_dec.setTypeface(Typeface.DEFAULT);
		tv_oct.removeTextChangedListener(this);
		tv_hex.removeTextChangedListener(this);
		tv_dec.removeTextChangedListener(this);
		tv_bin.removeTextChangedListener(this);
	}
	
	public void btn_base_OnClick(View v) {
		resetMode();
		Button button = findViewById(v.getId());
		currentBase = button.getText().toString();
		if (currentBase.equals(DEC))
			_currentBase = 10;
		else if (currentBase.equals(HEX))
			_currentBase = 16;
		else if (currentBase.equals(OCT))
			_currentBase = 8;
		else _currentBase = 2;
		changeStatusButtons(currentBase);
	}
	
	/**
	 * Lấy tất cả các nút số vào danh sách (Bao gồm A->F)
	 */
	void getAllNumberButtons() {
		LinearLayout row1, row2, row3;
		row1 = findViewById(R.id.row_1);
		row2 = findViewById(R.id.row_2);
		row3 = findViewById(R.id.row_3);
		int len = row1.getChildCount();
		for (int i = 0; i < len; i++) {
			listID.add((row1.getChildAt(i).getId()));
			listID.add((row2.getChildAt(i).getId()));
			listID.add((row3.getChildAt(i).getId()));
		}
	}
	
	/**
	 * Vô hiệu hóa các nút số
	 */
	void makeAllNumberButtonsDisable() {
		for (int id : listID) {
			View view = findViewById(id);
			view.setEnabled(false);
			view.setAlpha(0.3f);    //Chế độ mờ
		}
	}
	
	/**
	 * Bật lại các nút thích hợp với chế độ hiện tại khỏi bị vô hiệu hóa
	 */
	void changeStatusButtons(String stt) {
		makeAllNumberButtonsDisable();
		Button button;
		switch (stt) {
			case DEC:
				btn_dec.setTypeface(Typeface.DEFAULT_BOLD);
				temp = findViewById(R.id.tv_dec);
				for (int id : listID) {
					button = findViewById(id);
					//Các nút số từ 0 -> 9 thì OK, còn lại thì disable nó đi
					if (button.getText().charAt(0) >= '0' && button.getText().charAt(0) <= '9') {
						button.setAlpha(1f);
						button.setEnabled(true);
					}
				}
				break;
			case HEX:
				btn_hex.setTypeface(Typeface.DEFAULT_BOLD);
				temp = findViewById(R.id.tv_hex);
				//Enable tất cả các nút
				for (int id : listID) {
					button = findViewById(id);
					button.setEnabled(true);
					button.setAlpha(1f);
				}
				break;
			case OCT:
				btn_oct.setTypeface(Typeface.DEFAULT_BOLD);
				temp = findViewById(R.id.tv_oct);
				for (int id : listID) {
					button = findViewById(id);
					//Các nút số từ 0 -> 7 thì OK, còn lại thì disable
					if (button.getText().charAt(0) >= '0' && button.getText().charAt(0) <= '7') {
						button.setAlpha(1f);
						button.setEnabled(true);
					}
				}
				break;
			case BIN:
				btn_bin.setTypeface(Typeface.DEFAULT_BOLD);
				temp = findViewById(R.id.tv_bin);
				/*
				for (int id : listID) {
					button = findViewById(id);
					//Chỉ 0 với 1
					if (button.getText().equals("0") || button.getText().equals("1")) {
						button.setAlpha(1f);
						button.setEnabled(true);
					}
				}
				*/
				Button btn_0 = findViewById(R.id.btn_0);
				Button btn_1 = findViewById(R.id.btn_1);
				btn_0.setAlpha(1f);
				btn_1.setAlpha(1f);
				btn_0.setEnabled(true);
				btn_1.setEnabled(true);
				break;
			default:
				break;
		}
		result.setText(temp.getText());
		temp.addTextChangedListener(this);
	}
	
	public void btn_number_OnClick(View v) {
		//Chỉ nhập được số khi biểu thức chưa hoàn thành
		if (!completed) {
			String input = result.getText().toString();
			String expression = exp.getText().toString();
			if (input.equals("0"))      //Xóa số 0 ở đầu
				input = "";
			else if (!containDigit(input)) {    //Nếu có toán tử thì đưa vào biểu thức
				expression += input;
				input = "";
			}
			input += ((Button) v).getText().toString();
			result.setText(input);
			temp.setText(input);
			exp.setText(expression);
		}
	}
	
	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	
	}
	
	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
		String number = temp.getText().toString();
		if (!number.equals(""))
			if (currentBase.equals(DEC)) {
				tv_bin.setText(functions.convertFromBaseToBase(number, 10, 2));
				tv_hex.setText(functions.convertFromBaseToBase(number, 10, 16));
				tv_oct.setText(functions.convertFromBaseToBase(number, 10, 8));
			} else if (currentBase.equals(HEX)) {
				tv_bin.setText(functions.convertFromBaseToBase(number, 16, 2));
				tv_dec.setText(functions.convertFromBaseToBase(number, 16, 10));
				tv_oct.setText(functions.convertFromBaseToBase(number, 16, 8));
			} else if (currentBase.equals(OCT)) {
				tv_bin.setText(functions.convertFromBaseToBase(number, 8, 2));
				tv_hex.setText(functions.convertFromBaseToBase(number, 8, 16));
				tv_dec.setText(functions.convertFromBaseToBase(number, 8, 10));
			} else {
				tv_dec.setText(functions.convertFromBaseToBase(number, 2, 10));
				tv_hex.setText(functions.convertFromBaseToBase(number, 2, 16));
				tv_oct.setText(functions.convertFromBaseToBase(number, 2, 8));
			}
	}
	
	@Override
	public void afterTextChanged(Editable editable) {
	
	}
	
	/**
	 * Kiểm tra chuỗi có chứa các ký tự từ 0->9, A->F
	 */
	boolean containDigit(String str) {
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (!Character.isDigit(str.charAt(i)) && (str.charAt(i) < 'A' || str.charAt(i) > 'F'))
				return false;
		}
		return true;
	}
	
	public void btn_opr_OnClick(View v) {
		String input = result.getText().toString();
		String expression = exp.getText().toString();
		Button button = (Button) v;
		if (completed) {        //Nếu biểu thức đã được tính toán rồi thì thay đổi trạng thái để sẵn sàng nhập tiếp
			completed = false;
			expression = input;
		} else if (containDigit(input) || input.contains(")"))  //Đưa số vào tv_exp
			expression += input;
		String name = button.getText().toString();
		if (name.equals(OR)) { //Các toán tử đặc biệt
			input = "|";
		} else if (name.equals(XOR)) {
			input = "^";
		} else if (name.equals(AND)) {
			input = "&";
		} else if (name.equals(LSH)) {
			input = "<<";
		} else if (name.equals(RSH)) {
			input = ">>";
		}
		exp.setText(expression);
		result.setText(input);
	}
	
	public void btn_equal_OnClick(View v) {
		if (!completed) {
			String input = result.getText().toString();
			String expression = exp.getText().toString();
			String last = expression.length() > 0 ? expression.substring(expression.length() - 1) : "";
			if (containDigit(input) && !containDigit(last) || input.equals(")")) {  //Nếu infix hiện tại là số hoặc có dấu ")" thì tiến hành xử lý
				expression += input;
				notation.release();
				notation.setExpression(expression);
				int flag = notation.checkExpression();   //Kiểm tra cú pháp
				if (flag == -1)
					Toast.makeText(getApplicationContext(), MISSING_CLOSE, Toast.LENGTH_LONG).show();
				else if (flag == -2)
					Toast.makeText(getApplicationContext(), MISSING_OPEN, Toast.LENGTH_LONG).show();
				else {  //Nếu nhập đúng
					exp.setText(expression);
					completed = true;
					notation.infixToPostfix();
					result.setText(functions.convertFromBaseToBase(notation.compute_postFix(_currentBase).toString(), 10, _currentBase));
					temp.setText(result.getText());
				}
			}
		}
	}
	
	public void btn_delete_onClick(View v) {
		completed = false;
		result.setText("0");
		temp.setText("0");
		int id = getResources().getIdentifier(BTN + currentBase.toLowerCase(), ID, getApplicationContext().getPackageName());
		Button button = findViewById(id);
		button.performClick();
		exp.setText("");
		notation.release();
	}
	
	public void btn_backspace_onClick(View v) {
		if (!completed) {
			String input = result.getText().toString();
			input = input.substring(0, input.length() - 1);
			result.setText(input);
			temp.setText(input);
		}
	}
	
	public void btn_not_onClick(View v) {
		String expression = exp.getText().toString();
		String input = result.getText().toString();
		if (completed) {
			exp.setText("~(" + expression + ")");
			result.setText("" + functions.convertFromBaseToBase("" + ~Long.parseLong(input, _currentBase), 10, _currentBase));
			temp.setText(result.getText());
		} else if (containDigit(input)) {
			result.setText("" + functions.convertFromBaseToBase("" + ~Long.parseLong(input, _currentBase), 10, _currentBase));
			temp.setText(result.getText());
		}
	}
	
	public void btn_neg_onClick(View v) {
		String expression = exp.getText().toString();
		String input = result.getText().toString();
		if (completed) {
			exp.setText("-(" + expression + ")");
			result.setText("" + functions.convertFromBaseToBase("" + -Long.parseLong(input, _currentBase), 10, _currentBase));
			temp.setText(result.getText());
		} else if (containDigit(input)) {
			result.setText("" + functions.convertFromBaseToBase("" + -Long.parseLong(input, _currentBase), 10, _currentBase));
			temp.setText(result.getText());
		}
	}
	
	public void btn_rotateLeft_onClick(View v) {
		String expression = exp.getText().toString();
		String input = result.getText().toString();
		if (completed) {
			exp.setText(RO_L + expression + ")");
			result.setText("" + functions.rotateLeft(input, _currentBase));
			temp.setText(result.getText());
		} else if (containDigit(input)) {
			result.setText("" + functions.rotateLeft(input, _currentBase));
			temp.setText(result.getText());
		}
	}
	
	public void btn_rotateRight_onClick(View v) {
		String expression = exp.getText().toString();
		String input = result.getText().toString();
		if (completed) {
			exp.setText(RO_R + expression + ")");
			result.setText("" + functions.rotateRight(input, _currentBase));
			temp.setText(result.getText());
		} else if (containDigit(input)) {
			result.setText("" + functions.rotateRight(input, _currentBase));
			temp.setText(result.getText());
		}
	}
	
	public void btn_parenthesis_onClick(View v) {
		String input = result.getText().toString();
		String expression = exp.getText().toString();
		if (!containDigit(input) && ((Button) v).getText().toString().equals("(")) {    //Chỉ nhập được khi trước nó là toán tử
			expression += input;
			input = "(";
		} else if (((Button) v).getText().toString().equals(")") && expression.contains("(")) { //Chỉ nhập được nếu như có dấu mở ngoặc ở phía trước
			expression += input;
			input = ")";
		}
		result.setText(input);
		exp.setText(expression);
	}
}
