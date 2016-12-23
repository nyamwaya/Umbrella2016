package com.example.aleckson.umbrella;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aleckson.umbrella.networking.model.WeatherClient;
import com.example.aleckson.umbrella.networking.model.WeatherResults;
import com.example.aleckson.umbrella.viewmodel.WeatherViewModel;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getClass().getName();
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherViewModel = new WeatherViewModel(new WeatherClient(), AndroidSchedulers.mainThread());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        subscriptions.unsubscribe();
        super.onDestroy();
    }

    private void fetchWeather(){

       // String formatUserInput = getUserInput().trim().replaceAll("\\s+", "+");
        weatherViewModel.getWeather("55428")
                .subscribe(new Subscriber<WeatherResults>() {
                    @Override
                    public void onCompleted() {
                        Log.v(TAG , "Good work Alex");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG , "Fuck");

                    }

                    @Override
                    public void onNext(WeatherResults weatherResults) {
                        Log.v(TAG , "Great work Alex");
                    }
                });
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
