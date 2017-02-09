package com.example.aleckson.umbrella.activity;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.aleckson.umbrella.IWeather;
import com.example.aleckson.umbrella.R;
import com.example.aleckson.umbrella.model.CurrentObservation;
import com.example.aleckson.umbrella.model.Day;
import com.example.aleckson.umbrella.viewmodel.WeatherViewModel;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements IWeather{

    private static final String TAG = MainActivity.class.getClass().getName();
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private WeatherViewModel weatherViewModel;

    private TextView mTemperature;
    private TextView mWeatheer;
    private Toolbar toolbar;



    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rendderLayout();

        weatherViewModel = new WeatherViewModel(this);


    }

    private void rendderLayout() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTemperature = (TextView) findViewById(R.id.temp);
        mWeatheer = (TextView) findViewById(R.id.weather);


    }

    @Override
    protected void onResume() {
        super.onResume();
       if (weatherViewModel != null){
           weatherViewModel.onResume();
       }
    }

    @Override
    protected void onDestroy() {
        subscriptions.unsubscribe();
        super.onDestroy();
    }

    private boolean isInternetConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTemp(String temp) {
        mTemperature.setText(temp);
    }

    @Override
    public void setWeather(String weather) {
        mWeatheer.setText(weather);
    }

    @Override
    public void setAppColor(int temp, int basetemp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);

            if (temp > basetemp) {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.weather_warm));
                collapsingToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.weather_warm));
                window.setStatusBarColor(getResources().getColor(R.color.weather_warmDark));


            } else {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.weather_cool));
                collapsingToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.weather_cool));
                window.setStatusBarColor(getResources().getColor(R.color.weather_coolDark));

            }
        }
    }

    @Override
    public void setCity(String city) {
        collapsingToolbar.setTitle(city);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedToolBar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedToolBar);
    }

    @Override
    public void setHourlyConitions(List<Day> days, boolean metricMode) {
        if (days != null) {
           // setList(days, metricMode);
        }else{
            Log.v(TAG, "days list is null");
        }
    }


}
