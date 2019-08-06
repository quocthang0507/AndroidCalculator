package vn.edu.itdlu.a1610207.calculator.Activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import vn.edu.itdlu.a1610207.calculator.R;
import vn.edu.itdlu.a1610207.calculator.myDialogFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private static final String ABOUT = "about";
	private static final String ANGLE_CONVERTER_MODE = "Angle Converter Mode";
	private static final String PRESSURE_CONVERTER_MODE = "Pressure Converter Mode";
	private static final String DATA_CONVERTER_MODE = "Data Converter Mode";
	private static final String POWER_CONVERTER_MODE = "Power Converter Mode";
	private static final String TIME_CONVERTER_MODE = "Time Converter Mode";
	private static final String SPEED_CONVERTER_MODE = "Speed Converter Mode";
	private static final String AREA_CONVERTER_MODE = "Area Converter Mode";
	private static final String ENERGY_CONVERTER_MODE = "Energy Converter Mode";
	private static final String TEMPERATURE_CONVERTER_MODE = "Temperature Converter Mode";
	private static final String WEIGHT_AND_MASS_CONVERTER_MODE = "Weight and Mass Converter Mode";
	private static final String LENGTH_CONVERTER_MODE = "Length Converter Mode";
	private static final String VOLUME_CONVERTER_MODE = "Volume Converter Mode";
	private static final String CURRENCY_CONVERTER_MODE = "Currency Converter Mode";
	private static final String DATE_CALCULATION_MODE = "Date Calculation Mode";
	private static final String PROGRAMMER_MODE = "Programmer Mode";
	private static final String SCIENTIFIC_MODE = "Scientific Mode";
	private static final String STANDARD_MODE = "Standard Mode";
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	private android.support.v7.widget.Toolbar toolbar;
	private ActionBar actionBar;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(getApplicationContext().getResources().getString(R.string.tag), "Opening MainActivity...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		map();
		addEventListener();
		setToolbar();
		JodaTimeAndroid.init(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		int length = navigationView.getMenu().size();
		for (int i = 0; i < length; i++) {
			if (navigationView.getMenu().getItem(i).isChecked()) {
				navigationView.getMenu().getItem(i).setChecked(false);
				return;
			}
		}
	}
	
	void map() {
		drawerLayout = findViewById(R.id.main);
		navigationView = findViewById(R.id.nav_view);
		toolbar = findViewById(R.id.toolbar);
	}
	
	void addEventListener() {
		navigationView.setNavigationItemSelectedListener(this);
	}
	
	/**
	 * Set the toolbar as the action bar and add nav drawer button
	 */
	void setToolbar() {
		setSupportActionBar(toolbar);
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
	}
	
	/**
	 * Handle navigation click events
	 */
	@Override
	public boolean onNavigationItemSelected(MenuItem menuItem) {
		// set item as selected to persist highlight
		menuItem.setChecked(true);
		// close drawer when item is tapped
		drawerLayout.closeDrawers();
		// remove the content of framelayout before it is used
		// For example, swap UI fragments here
		switch (menuItem.getItemId()) {
			//Calculator mode
			case R.id.cal_standard:
				setToast(STANDARD_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_standard.class);
				startActivity(intent);
				break;
			case R.id.cal_scientific:
				setToast(SCIENTIFIC_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_scientific.class);
				startActivity(intent);
				break;
			case R.id.cal_programmer:
				setToast(PROGRAMMER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_programmer.class);
				startActivity(intent);
				break;
			case R.id.cal_date:
				setToast(DATE_CALCULATION_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_date.class);
				startActivity(intent);
				break;
			
			//Converter mode
			case R.id.con_currency:
				setToast(CURRENCY_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_currency.class);
				startActivity(intent);
				break;
			case R.id.con_volume:
				setToast(VOLUME_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_volume.class);
				startActivity(intent);
				break;
			case R.id.con_length:
				setToast(LENGTH_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_length.class);
				startActivity(intent);
				break;
			case R.id.con_weight:
				setToast(WEIGHT_AND_MASS_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_weight.class);
				startActivity(intent);
				break;
			case R.id.con_temperature:
				setToast(TEMPERATURE_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_temperature.class);
				startActivity(intent);
				break;
			case R.id.con_energy:
				setToast(ENERGY_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_energy.class);
				startActivity(intent);
				break;
			case R.id.con_area:
				setToast(AREA_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_area.class);
				startActivity(intent);
				break;
			case R.id.con_speed:
				setToast(SPEED_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_speed.class);
				startActivity(intent);
				break;
			case R.id.con_time:
				setToast(TIME_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_time.class);
				startActivity(intent);
				break;
			case R.id.con_power:
				setToast(POWER_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_power.class);
				startActivity(intent);
				break;
			case R.id.con_data:
				setToast(DATA_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_data.class);
				startActivity(intent);
				break;
			case R.id.con_pressure:
				setToast(PRESSURE_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_pressure.class);
				startActivity(intent);
				break;
			case R.id.con_angle:
				setToast(ANGLE_CONVERTER_MODE, Toast.LENGTH_SHORT);
				intent = new Intent(MainActivity.this, activity_angle.class);
				startActivity(intent);
				break;
			case R.id.about:
				DialogFragment dialogFragment = new myDialogFragment();
				dialogFragment.show(getFragmentManager(), ABOUT);
				break;
			default:
				return false;
		}
		return true;
	}
	
	/**
	 * Open the drawer when the button is tapped
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case android.R.id.home:
				drawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(menuItem);
	}
	
	/**
	 * Display the toast notification within the duration
	 */
	void setToast(String string, int length) {
		Context context = getApplicationContext();
		Toast toast = Toast.makeText(context, string, length);
		toast.show();
	}
}
