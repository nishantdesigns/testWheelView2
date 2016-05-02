package com.example.sid.wheelviewtest2;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class MainActivity extends AppCompatActivity {


    // Wheel scrolled flag
    private boolean wheelScrolled = false;



    TextView finalPriceTV, unitPriceTV;
    final static String wheelMenu1[] = new String[1001];
    String wheelMenu2[] = new String[]{"00","25","50","75"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int a = 0; a < wheelMenu1.length; a++) {
            wheelMenu1[a] = (a+"");
        }


        finalPriceTV = (TextView)findViewById(R.id.FinalPriceTV);
        unitPriceTV = (TextView)findViewById(R.id.unitPriceTV);
        initWheel(R.id.productQuantitywheel1, wheelMenu1);
        initWheel(R.id.productQuantitywheel2, wheelMenu1);
    }

    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener()  {
        @Override
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        @Override
        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            updateStatus();
        }

    };

    // Wheel changed listener
    private final OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            Log.d("Tag ", "onChanged, wheelScrolled = " + wheelScrolled);
            if (!wheelScrolled) {
                updateStatus();
            }
        }
    };
    private void updateStatus() {
        Double unitPrice = Double.parseDouble(unitPriceTV.getText().toString().substring(1));

        Double val1=Double.parseDouble(wheelMenu1[getWheel(R.id.productQuantitywheel1).getCurrentItem()])*unitPrice;

        Double val2 = (Double.parseDouble(wheelMenu2[getWheel(R.id.productQuantitywheel2).getCurrentItem()])/100)*unitPrice;
        Double finalValue = val1 + val2;

        finalPriceTV.setText("\u20B9"+finalValue.toString());



    }
    private void initWheel(int id, String[] wheelMenu1) {
        WheelView wheel = (WheelView) findViewById(id);

        if(id == R.id.productQuantitywheel1){
            wheel.setViewAdapter(new ArrayWheelAdapter<String>(this, wheelMenu1));
        }else{
            wheel.setViewAdapter(new ArrayWheelAdapter<String>(this, wheelMenu2));

        }


        wheel.setVisibleItems(3);
        wheel.setCurrentItem(0);

        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
    }

    private WheelView getWheel(int id) {
        return (WheelView) findViewById(id);
    }
}
