package vn.edu.itdlu.a1610207.calculator.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolishNotation {
	private String expression;
	private List<String> infix = new ArrayList<String>();   //Đầu vào (chuỗi trung tố)
	private Stack<String> stack = new Stack<String>();  //Chứa các toản tử
	private List<String> postfix = new ArrayList<String>(); //Đầu ra (chuối hậu tố)
	
	/**
	 * Kiểm tra một ký tự c có phải là một toán tử hay không?
	 */
	static public boolean isOperator(String c) {
		return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/") || c.equals("%") ||
				c.equals("^") || c.equals("&") || c.equals("|") || c.equals(">>") || c.equals("<<") ||
				c.equals("^^");
	}
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public List<String> getInfix() {
		return this.infix;
	}
	
	public void setInfix(List<String> infix) {
		this.infix = infix;
	}
	
	/***
	 * Tách số tại vị trí i trong chuỗi str
	 */
	public String extractNumber(String str, int i) {
		StringBuilder t = new StringBuilder();
		int len = str.length();
		do {        //Lặp cho đến khi ký tự đó không còn là số
			t.append(str.charAt(i));
			i++;
		} while (i < len && (isOperand("" + str.charAt(i)) || str.charAt(i) == '.'));
		return t.toString();
	}
	
	/***
	 * Chuyển biểu thức (một chuỗi) thành một danh sách
	 */
	public void convertToList() {
		int length = expression.length();
		for (int i = 0; i < length; i++) {
			if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9' || expression.charAt(i) >= 'A' && expression.charAt(i) <= 'F') { //Nếu nó là số
				String t = extractNumber(expression, i);
				infix.add(t);
				i += t.length() - 1;
			} else if (expression.charAt(i) == '>') {//Nếu nó là toán tử của phép dịch bit
				infix.add(">>");
				i++;
			} else if (expression.charAt(i) == '<') {
				infix.add("<<");
				i++;
			} else if (expression.charAt(i) == '^' && expression.charAt(i + 1) == '^') {
				infix.add("^^");    //Tránh nhầm lẫn với phép xor (^) và lũy thừa (^^)
				i++;
			} else infix.add(Character.toString(expression.charAt(i)));
		}
	}
	
	/***
	 * Trả về -1 nếu thiếu dấu đóng ngoặc
	 * Trả về -2 nếu thiếu dấu mở ngoặc
	 * Trả về 1 nếu nhập đúng
	 */
	public int checkExpression() {
		int length = expression.length();
		int counter = 0;
		for (int i = 0; i < length; i++) {
			if (expression.charAt(i) == '(')
				counter++;
			else if (expression.charAt(i) == ')')
				counter--;
		}
		return counter == 0 ? 1 : counter > 0 ? -1 : -2;
	}
	
	/***
	 * Độ ưu tiên của toán tử
	 */
	public int getPriority(String str) {
		if (str.equals("^^"))   //Phân biệt mũ và phép xor
			return 5;
		else if (str.equals("*") || str.equals("/") || str.equals("%"))
			return 4;
		else if (str.equals("+") || str.equals("-"))
			return 3;
		else if (str.equals("&"))
			return 2;
		else if (str.equals("|") || str.equals("^"))
			return 1;
		else return 0;
	}
	
	/***
	 * Kiểm tra s có phải là một số hay không?
	 */
	public boolean isOperand(String str) {
		try {
			Long.parseLong(str, 16);     //Lấy cơ số cao nhất để kiểm tra nó có phải là số nguyên
		} catch (NumberFormatException e) {
			try {
				Double.parseDouble(str);       //Nó có phải là số thực
			} catch (NumberFormatException _e) {
				return false;                   //Nó là chuỗi
			}
		}
		return true;
	}
	
	/***
	 * Thực hiện việc chuyển đổi biểu thức trung tố sang biểu thức hậu tố
	 * Sử dụng Thuật toán Ký pháp Ba Lan Polish Notation
	 */
	public void infixToPostfix() {
		convertToList();
		String x;
		for (String item : this.infix) {
			if (isOperand(item))    //Nếu là toán hạng
				postfix.add(item);
			else if (item.equals("("))
				stack.push(item);
			else if (item.equals(")")) {
				x = stack.pop();
				while (!x.equals("(")) {    //Pop các toán tử cho vào postfix đến khi gặp dấu '('
					postfix.add(x);
					x = stack.pop();
				}
			} else {
				//Nếu là toán tử
				//Thực hiện vòng lặp kiểm tra, nếu ở đỉnh stack là toán tử có độ ưu tiên >=
				//toán tử hiện tại thì lấy toán tử đó trong stack (pop) cho vào postfix
				while (!stack.empty() && isOperator(stack.peek()))
					if (getPriority(stack.peek()) >= getPriority(item)) {
						x = stack.pop();
						postfix.add(x);
					} else break;
				stack.push(item);   //Cho toán tử hiện tại vào stack
			}
		}   //Lấy toán tử còn lại trong stack cho vào postfix
		while (!stack.empty()) {
			x = stack.pop();
			postfix.add(x);
		}
	}
	
	/***
	 * Tính giá trị biểu thức hậu tố, trả về cơ số 10
	 * Các phép liên quan đến bit chỉ dùng được cho số hạng là số nguyên,
	 * do đó phải phân biệt được các số nguyên với cơ số khác nhau và số thực
	 * Radix là cơ số hiện tại đang thực hiện
	 */
	public Object compute_postFix(int radix) {
		Object t;
		double x, y;
		Stack<Object> s = new Stack<>();
		for (String item : this.postfix) {
			if (isOperand(item)) {
				if (radix == 10)
					t = Double.parseDouble(item);
				else
					t = Long.parseLong(item, radix);
				s.push(t);
			} else {
				//Vì mỗi lần thực hiện là một cơ số nên chỉ cần kiểm tra một số bất kỳ là được
				//Nếu ép kiểu double cho long sẽ bị lỗi nên chia ra 2 trường hợp
				if (s.peek() instanceof Long) {
					x = (long) s.pop();
					y = (long) s.pop();
				} else {
					x = (double) s.pop();
					y = (double) s.pop();
				}
				switch (item) {
					case "+":
						s.push(y + x);
						break;
					case "-":
						s.push(y - x);
						break;
					case "*":
						s.push(y * x);
						break;
					case "/":
						if (x == 0f)
							return Double.POSITIVE_INFINITY;
						else s.push(y / x);
						break;
					case "&":
						s.push((long) y & (long) x);
						break;
					case "^":
						s.push((long) y ^ (long) x);
						break;
					case "|":
						s.push((long) y | (long) x);
						break;
					case ">>":
						s.push((long) y >> (long) x);
						break;
					case "<<":
						s.push((long) y << (long) x);
						break;
					case "^^":
						if ((long) y == 0 && (long) x == -1)
							return Double.POSITIVE_INFINITY;
						s.push(Math.pow(y, x));
					default:
						break;
				}
			}
		}
		return s.peek();
	}
	
	/***
	 * Giải phóng các thuộc tính của lớp
	 */
	public void release() {
		this.expression = "";
		this.infix.clear();
		this.postfix.clear();
		this.stack.clear();
	}
}
