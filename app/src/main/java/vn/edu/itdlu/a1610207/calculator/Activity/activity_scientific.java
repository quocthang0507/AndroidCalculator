package vn.edu.itdlu.a1610207.calculator.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import vn.edu.itdlu.a1610207.calculator.R;

public class activity_scientific extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getApplicationContext().getResources().getString(R.string.tag), "Opening activity_scientific...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //Remove activity label
    }

    public void btn_Shift_onClick(View v) {
        LinearLayout layout1 = findViewById(R.id.row1_shift1);
        LinearLayout layout2 = findViewById(R.id.row2_shift1);
        LinearLayout layout3 = findViewById(R.id.row1_shift2);
        LinearLayout layout4 = findViewById(R.id.row2_shift2);
        if (layout1.getVisibility() == View.GONE) {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
        } else {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.VISIBLE);
            layout4.setVisibility(View.VISIBLE);
        }
    }

    public void backToMainScreen_OnClick(View v) {
        finish();
    }

}
