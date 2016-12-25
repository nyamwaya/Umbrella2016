package com.example.aleckson.umbrella;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleckson.umbrella.networking.model.CurrentObservation;
import com.example.aleckson.umbrella.networking.model.WeatherImpl;
import com.example.aleckson.umbrella.networking.model.WeatherResults;
import com.example.aleckson.umbrella.viewmodel.WeatherViewModel;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getClass().getName();
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private WeatherViewModel weatherViewModel;

    private TextView mTemperature;
    private TextView mWeatheer;

    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rendderLayout();

        weatherViewModel = new WeatherViewModel(new WeatherImpl(), AndroidSchedulers.mainThread());


    }

    private void rendderLayout(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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
        if (isInternetConnectionAvailable()) {
            performSearch();
        } else {
            Toast.makeText(MainActivity.this, R.string.error_no_internet,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        subscriptions.unsubscribe();
        super.onDestroy();
    }

    private void performSearch(){
        subscriptions.add(weatherViewModel.getWeather("55376")
        .subscribe(new Observer<WeatherResults>() {
            @Override
            public void onCompleted() {
                Log.v(TAG, "Received network data successfully");

            }

            @Override
            public void onError(Throwable e) {
                Log.v(TAG, "Error receivingIts network data" + e);
            }

            @Override
            public void onNext(WeatherResults weatherResults) {
                //updateUi
                Log.v(TAG, "It's" + weatherResults.currentObservation.getTempFahrenheit() + "in"
                + weatherResults.currentObservation.getDisplayLocation().getFull());

                updateUi(weatherResults.currentObservation);

            }
        }));

    }

    private void updateUi(CurrentObservation currentObservation) {

        collapsingToolbar.setTitle(currentObservation.getDisplayLocation().getFull());
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedToolBar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedToolBar);



        mTemperature.setText(String.valueOf(currentObservation.getTempCelsius()));
        mWeatheer.setText(currentObservation.getWeather());

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
}
