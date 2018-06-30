package generisches.lab.servicesdemo.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyIntentService extends IntentService {

    public static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        //Name of bg thread
        super("MyWorkerThread");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate, ThreadName = " + Thread.currentThread().getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleintent, ThreadName = " + Thread.currentThread().getName());

        int sleepTime = intent.getIntExtra("sleepTime", 1);

        ResultReceiver lResultReceiver = intent.getParcelableExtra("receiver");

        int ctr = 1;
        while (ctr <= sleepTime){
            Log.i(TAG, "Counter is now " + ctr);
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            ctr++;
        }
        Bundle bundle = new Bundle();
        bundle.putString("resultIntentService", "Counter stopped at " + ctr + "seconds.");
        lResultReceiver.send(18, bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy, ThreadName = " + Thread.currentThread().getName());
    }
}
