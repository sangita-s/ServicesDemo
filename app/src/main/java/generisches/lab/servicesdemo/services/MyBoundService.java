package generisches.lab.servicesdemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MyBoundService extends Service {

    private MyLocalBinder mMyLocalBinder = new MyLocalBinder();

    public class MyLocalBinder extends Binder{
        public MyBoundService getService(){
            return MyBoundService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //Must return instance of bind object
        //Goes to OnServiceConnected in MyBoundActivity
        return mMyLocalBinder;
    }
    public int add(int a, int b){
        return a+b;
    }

    public int sub(int a, int b){
        return a-b;
    }

    public int mul(int a, int b){
        return a*b;
    }

    public float div(int a, int b){
        return (float)a/(float)b;
    }
}
