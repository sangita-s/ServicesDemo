package generisches.lab.servicesdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView txvIntentServiceResult, txvStartedServiceResult;

    Handler handler = new Handler();//To handle data of onReceiveResult (since it runs in worker thread)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvIntentServiceResult = findViewById(R.id.txvIntentServiceResult);
        txvStartedServiceResult = findViewById(R.id.txvStartedServiceResult);

    }

    public void startStartedService(View view) {
        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
        intent.putExtra("sleepTime", 10);
        startService(intent);
    }

    public void stopStartedService(View view) {
//        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
//        stopService(intent);
    }

    public void startIntentService(View view) {

        ResultReceiver myResultReceiver = new ResultReceiver(null);

        Intent i = new Intent(this, MyIntentService.class);
        i.putExtra("sleepTime", 10); // Will be managed by HandleIntent
        i.putExtra("receiver", myResultReceiver);
        startService(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.service.to.activity");
        registerReceiver(myStartedServiceReceiver, intentFilter);
    }

    //Created BR dynamically. - Intent filter dynamically as well => done in onResume
    private BroadcastReceiver myStartedServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra("startServiceResult");
            txvStartedServiceResult.setText(result);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myStartedServiceReceiver);
    }


    //To receive data from MyIntentService.java using ResultReceiver
    private class MyResultReceiver extends ResultReceiver{
        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        //This works inside a worker thread.
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Log.i("MyResultReceiver", Thread.currentThread().getName());
            if(resultCode == 18 && resultData != null){
                final String result = resultData.getString("resultIntentService");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //access ui element from worker thread - handler.post
                        Log.i("MyHandler", Thread.currentThread().getName());
                        txvIntentServiceResult.setText(result);
                    }
                });
            }
        }
    }
}
