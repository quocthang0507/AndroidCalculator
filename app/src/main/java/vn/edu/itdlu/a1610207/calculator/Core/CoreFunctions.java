package vn.edu.itdlu.a1610207.calculator.Core;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class CoreFunctions {
	private static final String format = "dd/MM/yyyy";
	
	/**
	 * Volume converter
	 * According to the SI System, the base unit for measuring volume is the metre and
	 * volumes are thus measured in cubic metres
	 */
	public String[] Volume =
			new String[]{"Milliliters", "10^-6", "Cubic centimeters", "10^-6",
					"Liters", "10^-3", "Cubic meters", "1", "Cubic inches", "0.000016387064",
					"Cubic feet", "0.0283168466", "Cubic yards", "0.764554858"};
	/**
	 * Length converter
	 * The base unit in the International System of Units (SI) is the metre (or meter)
	 */
	public String[] Length =
			new String[]{"Nanometers", "10^-9", "Microns", "10^-6", "Millimeters", "10^-3",
					"Centimeters", "10^-2", "Meters", "1", "Kilometers", "10^3",
					"Inches", "0.0254", "Feet", "0.3048", "Yards", "0.91", "Miles", "1610"};
	/**
	 * Mass converter
	 * The basic SI unit of mass is the kilogram (kg)
	 */
	public String[] Weight_Mass =
			new String[]{"Carats", "0.0002", "Milligrams", "10^-6", "Centigrams", "10^-5", "Decigrams", "10^-4",
					"Grams", "10^-3", "Dekagrams", "10^-2", "Hectograms", "10^-1", "Kilograms", "1",
					"Ounces", "0.0283495231", "Pounds", "0.45359237"};
	/**
	 * Temperature converter
	 * The basic unit of temperature in the International System of Units (SI) is the kelvin
	 */
	public String[] Temperature = new String[]{"Celsius", "Fahrenheit", "Kelvin"};
	/**
	 * Energy converter
	 * the SI unit for energy is the same as the unit of work – the joule (J)
	 */
	public String[] Energy =
			new String[]{"Electron volts", "1.60217662×10^-19", "Joules", "1", "Kilojoules", "1000",
					"Thermal calories", "4184", "Food calories", "4184", "Foot-pounds", "1.35581795"};
	/**
	 * Data converter
	 * The most commonly used units of data storage capacity are the bit
	 */
	public String[] Data = new String[]{"Bits", "1", "Bytes", "8",
			"Kilobits", "1000", "Kibibits", "1024", "Kilobytes", "8000", "Kibibytes", "8192",
			"Megabits", "10^6", "Mebibits", "2^20", "Megabytes", "8×10^6", "Mebibytes", "8×2^20",
			"Gigabits", "10^9", "Gibibits", "2^30", "Gigabytes", "8×10^9", "Gibibytes", "8×2^30",
			"Terabits", "10^12", "Tebibits", "2^40", "Terabytes", "8×10^12", "Tebibytes", "8×2^40",
			"Petabits", "10^15", "Pebibits", "2^50", "Petabytes", "8×10^15", "Pebibytes", "8×2^50",
			"Exabits", "10^18", "Exbibits", "2^60", "Exabytes", "8×10^18", "Exbibytes", "8×2^60",
			"Zetabits", "10^21", "Zebibits", "2^70", "Zetabytes", "8×10^21", "Zebibytes", "8×2^70",
			"Yottabits", "10^24", "Yotbibits", "2^80", "Yottabytes", "8×10^24", "Yottbibytes", "8×2^80"};
	/**
	 * Area converter
	 * In the International System of Units (SI), the standard unit of area is the square metre
	 */
	public String[] Area =
			new String[]{"Square millimeters", "10^-6", "Square centimeters", "0.0001", "Square meters", "1",
					"Hectares", "10^4", "Square kilometers", "10^6", "Square inches", "0.00064516",
					"Square feet", "0.09290304", "Square yards", "0.83612736", "Acres", "4046.85642",
					"Square miles", "2589988.11"};
	/**
	 * Speed converter
	 * The SI unit of speed is the metre per second
	 */
	public String[] Speed =
			new String[]{"Centimeters per second", "0.01", "Meters per second", "1",
					"Kilometers per hour", "0.277777778", "Feet per second", "0.3048",
					"Miles per hour", "0.44704", "Knots", "0.514444444", "Mach", "340.29"};
	/**
	 * Time converter
	 * The base unit of time in the International System of Units (SI), and by extension most of the Western world, is the second
	 */
	public String[] Time =
			new String[]{"Microseconds", "10^-6", "Milliseconds", "0.001", "Seconds", "1", "Minutes", "60",
					"Hours", "3600", "Days", "86400", "Weeks", "604800", "Years", "31556926"};
	/**
	 * Power converter
	 * In the International System of Units, the unit of power is the joule per second (J/s), known as the watt in honour of James Watt
	 */
	public String[] Power =
			new String[]{"Watts", "1", "Kilowatts", "1000", "Horsepower (US)", "745.699872",
					"Foot-pounds/minute", "0.0225969658", "BTUs/minute", "17.5842642"};
	/**
	 * Pressure converter
	 * The unit of pressure in the SI system is the pascal (Pa), defined as a force of one Newton per square meter
	 */
	public String[] Pressure =
			new String[]{"Atmospheres", "101325", "Bars", "10^5", "Kilopascals", "10^3",
					"Millimeters of mercury", "133.322368", "Pascals", "1",
					"Pounds per square inch", "6894.75729"};
	/**
	 * Angle converter
	 * The SI unit of angular measure is the radian
	 */
	public String[] Angle = new String[]{"Degrees", "pi/180", "Radians", "1", "Gradians", "pi/200"};
	/**
	 * List of currencies of the world
	 */
	public String[] Currency =
			new String[]{"AFN ؋", "ALL Lek", "AMD", "ANG ƒ", "AOA", "ARS $", "AUD $", "AWG ƒ", "AZN \u20BC", "BAM KM", "BBD $", "BDT",
					"BGN лв", "BHD", "BIF", "BND $", "BOB $b", "BRL R$", "BSD $", "BTC", "BTN", "BWP P", "BYN Br",
					"BYR", "BZD BZ$", "CAD $", "CDF", "CHF CHF", "CLP $", "CNY ¥", "COP $", "CRC ₡", "CUP ₱", "CVE",
					"CZK Kč", "DJF", "DKK kr", "DOP RD$", "DZD", "EGP £", "ERN", "ETB", "EUR €", "FJD $", "FKP £",
					"GBP £", "GEL", "GHS ¢", "GIP £", "GMD", "GNF", "GTQ Q", "GYD $", "HKD $", "HNL L", "HRK kn",
					"HTG", "HUF Ft", "IDR Rp", "ILS ₪", "INR ₹", "IQD", "IRR ﷼", "ISK kr", "JMD J$", "JOD", "JPY ¥",
					"KES", "KGS лв", "KHR ៛", "KMF", "KPW ₩", "KRW ₩", "KWD", "KYD $", "KZT лв", "LAK ₭", "LBP £",
					"LKR ₨", "LRD $", "LSL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD ден", "MMK", "MNT ₮",
					"MOP", "MRO", "MUR ₨", "MVR", "MWK", "MXN $", "MYR RM", "MZN MT", "NAD $", "NGN ₦", "NIO C$",
					"NOK kr", "NPR ₨", "NZD $", "OMR ﷼", "PAB B/.", "PEN S/.", "PGK", "PHP ₱", "PKR ₨", "PLN zł", "PYG Gs",
					"QAR ﷼", "RON lei", "RSD Дин.", "RUB \u20BD", "RWF", "SAR ﷼", "SBD $", "SCR ₨", "SDG", "SEK kr", "SGD $",
					"SHP £", "SLL", "SOS S", "SRD $", "STD", "SYP £", "SZL", "THB ฿", "TJS", "TMT", "TND",
					"TOP", "TRY ₺", "TTD TT$", "TWD NT$", "TZS", "UAH ₴", "UGX", "USD $", "UYU $U", "UZS лв", "VEF Bs",
					"VND ₫", "VUV", "WST", "XAF", "XCD $", "XDR", "XOF", "XPF", "YER ﷼", "ZAR R", "ZMW"};
	/**
	 * String contains website's content
	 */
	private String rate = null;
	private String url;
	
	/**************************************Calculator**************************************/
	
	/**
	 * x^y
	 * reciprocal 1/x (x^-1)
	 * sqr x^2
	 * 10^x
	 * e^x
	 */
	public Object pow(double x, double y) {
		return fixType(Math.pow(x, y));
	}
	
	/**
	 * Percentage (%)
	 */
	public Object percentage(double n) {
		return fixType(n / 100);
	}
	
	/**
	 * The mathematical constant π
	 */
	public double pi() {
		return format(Math.PI);
	}
	
	/**
	 * The mathematical constant e
	 */
	public double e() {
		return Math.E; //return exp(1.0);
	}
	
	/**
	 * Rotate left the specified long value
	 * Convert another base to decimal before rotate left
	 */
	public String rotateLeft(String input, int currentBase) {
		Long n = Long.parseLong(convertFromBaseToBase(input, currentBase, 10));
		String result = "" + Long.rotateLeft(n, 1);
		return convertFromBaseToBase(result, 10, currentBase);
	}
	
	/**
	 * Rotate right the specified long value
	 * Convert another base to decimal before rotate right
	 */
	public String rotateRight(String input, int currentBase) {
		Long n = Long.parseLong(convertFromBaseToBase(input, currentBase, 10));
		String result = "" + Long.rotateRight(n, 1);
		return convertFromBaseToBase(result, 10, currentBase);
	}
	
	/**
	 * Convert from base to another base
	 */
	public String convertFromBaseToBase(String str, int fromBase, int toBase) {
		return Long.toString(Long.parseLong(str, fromBase), toBase).toUpperCase();
	}
	
	/**
	 * Calculate difference between 2 dates
	 */
	public String different(String start, String end) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		String value = "";
		try {
			DateTime dt1 = formatter.parseDateTime(start);
			DateTime dt2 = formatter.parseDateTime(end);
			if (dt1.equals(dt2))
				value = "Same day";
			else {
				int days, months, years;
				Period period = new Period(dt1, dt2, PeriodType.yearMonthDayTime());
				days = period.getDays();
				months = period.getMonths();
				years = period.getYears();
				value = days + " days, " + months + " months, " + years + " years";
			}
		} catch (Exception e) {
			return null;
		}
		return value;
	}
	
	/**
	 * Date1 add or subtract date2
	 */
	public String changeDay(String date1, Map<Character, Integer> change, char c) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		DateTime dt1;
		try {
			dt1 = formatter.parseDateTime(date1);
			switch (c) {
				case '+':
					dt1 = dt1.plusDays(change.get('d'));
					dt1 = dt1.plusMonths(change.get('m'));
					dt1 = dt1.plusYears(change.get('y'));
					break;
				case '-':
					dt1 = dt1.minusDays(change.get('d'));
					dt1 = dt1.minusMonths(change.get('m'));
					dt1 = dt1.minusYears(change.get('y'));
					break;
			}
		} catch (Exception e) {
			return null;
		}
		return formatter.print(dt1);
	}
	
	/**************************************Converter**************************************/
	
	/**
	 * Try to fix proper value type
	 */
	public Object fixType(double value) {
		double decimal = value - (long) value;
		if (decimal == 0f)
			return (long) value;
		else if (Double.isInfinite(decimal))
			return Double.POSITIVE_INFINITY;
		else return format(value);
	}
	
	/**
	 * Return a string whose value was formatted by decimalFormat
	 */
	public double format(double value) {
		Double result = BigDecimal.valueOf(value).setScale(10, RoundingMode.HALF_UP)
				.doubleValue();
		return result;
	}
	
	/**
	 * Get the exchange rate from website
	 */
	public double getExchangeRate(Context context, String unit1, String unit2) {
		String tag = "" + unit1 + "_" + unit2;
		url = "https://free.currencyconverterapi.com/api/v6/convert?q=" + tag + "&compact=y";
		if (checkInternetConnection()) {
			new GetExchangeRates().execute();
		} else {
			new AlertDialog.Builder(context)
					.setTitle("Internet Connection")
					.setMessage("Please check your internet connection")
					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
			rate = "0";
		}
		double value = Double.parseDouble(rate);
		return value;
	}
	
	
	/**
	 * Convert string to double, especially if it have power symbol
	 */
	public Object parseString(String string) {
		try {
			if (string.contains("^")) {
				if (string.contains("×")) {     //s1 × 10 ^ s2
					String s1 = string.split("\\^")[0].split("×")[0];
					String s2 = string.split("\\^")[1];
					double power = (double) pow(10, Double.parseDouble(s2));
					return fixType(Double.parseDouble(s1) * power);
				} else {    //10 ^ s2
					String t = string.split("\\^")[1];
					return pow(10, Double.parseDouble(t));
				}
			} else if (string.contains("/")) {  //pi/180
				String[] temp = string.split("/");
				return pi() / Integer.parseInt(temp[1]);
			} else return fixType(Double.parseDouble(string));  //123456789
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * Convert a string to double, return null if str can't convert to double value
	 */
	public double convertToDouble(String str) {
		if (str.equals("") || str.equals(null))
			return 0;
		else return Double.parseDouble(str);
	}
	
	/**
	 * The temperature converter function
	 * id == 0 : Celsius
	 * id == 1: Fahrenheit
	 * id == 2 : Kelvin
	 */
	public Object temperatureConverter(int id1, Object value, int id2) {
		double result;
		double _value = Double.parseDouble("" + value);
		if (id1 == id2) {
			result = _value;
		} else {
			if (id1 == 0) {
				result = id2 == 1 ? 9d / 5d * _value + 32d : _value + 273d;
			} else if (id1 == 1) {
				result = id2 == 0 ? 5d / 9d * (_value - 32d) : 5d / 9d * (_value - 32d) + 273d;
			} else {
				result = id2 == 0 ? _value - 273d : 9d / 5d * (_value - 273d) + 32d;
			}
		}
		return fixType(result);
	}
	
	/**
	 * Find index of text in array
	 */
	public int findIndexInArray(String[] array, String text) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (array[i].equals(text))
				return i;
		}
		return -1;
	}
	
	/**
	 * Convert other unit converters
	 */
	public Object otherConverter(String[] array, int id1, Object value, int id2) {
		if (id1 == id2)
			return value;
		else {
			//1 unit of id1 = ? unit of base
			double base1 = Double.parseDouble(parseString(array[id1 + 1]).toString());
			//1 unit of id2 = ? unit of base
			double base2 = Double.parseDouble(parseString(array[id2 + 1]).toString());
			//Get crosshair value
			return fixType(Double.parseDouble(value.toString()) * base1 / base2);
		}
	}
	
	/**
	 * Check that internet connection exists
	 */
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
	
	private class GetExchangeRates extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			ServiceHandler sh = new ServiceHandler();
			String jsonResult = sh.makeServiceCall(url, ServiceHandler.GET);
			JSONObject object = null;
			try {
				object = new JSONObject(jsonResult);
				rate = object.getJSONObject("val").toString();
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			String temp = rate;
		}
	}
	
}