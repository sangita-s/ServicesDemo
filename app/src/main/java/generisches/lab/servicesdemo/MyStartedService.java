package generisches.lab.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MyStartedService extends Service {

    private static final String TAG = MyStartedService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate, Thread name " + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand, Thread name " + Thread.currentThread().getName());
//        return super.onStartCommand(intent, flags, startId);
        int sleepTime = intent.getIntExtra("sleepTime", 1);
        new MyAsyncTask().execute(sleepTime);
        return START_STICKY;
//        return START_REDELIVER_INTENT;
//        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind, Thread name " + Thread.currentThread().getName());
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDelete, Thread name " + Thread.currentThread().getName());
    }

    class MyAsyncTask extends AsyncTask<Integer, String, Void>{
        private final String TAG = MyAsyncTask.class.getSimpleName();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.i(TAG, "onPreExecute, Thread Name : " + Thread.currentThread().getName());
        }

        @Override
        protected Void doInBackground(Integer... params) {
            Log.i(TAG, "doInBackground, Thread Name : " + Thread.currentThread().getName());
            int sleepTime = params[0];
            int ctr = 1;
            while (ctr <= sleepTime){
                publishProgress("Counter is now " + ctr);
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                ctr++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(MyStartedService.this, values[0], Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Counter value"+values[0] + "onProgressUpdate, Thread Name : "
                    + Thread.currentThread().getName());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            stopSelf();
            Log.i(TAG, "onPostExecute, Thread Name : " + Thread.currentThread().getName());
        }
    }
}