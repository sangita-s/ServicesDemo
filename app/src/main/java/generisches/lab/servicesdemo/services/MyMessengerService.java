package generisches.lab.servicesdemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MyMessengerService extends Service {

    //TO pass msg from thread of main activity to thread of remote service
    private class IncomingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 43:
                    Bundle lBundle = msg.getData();
                    int numOne = lBundle.getInt("numOne",0);
                    int numTwo = lBundle.getInt("numTwo",0);
                    int result = addNumbers(numOne, numTwo);
                    Toast.makeText(getApplicationContext(), "Result: " + result, Toast.LENGTH_SHORT).show();

                    Messenger incomingMessenger = msg.replyTo;
                    Message msgToActivity = Message.obtain(null, 23);
                    Bundle bundleToActivity = new Bundle();
                    bundleToActivity.putInt("result", result);
                    msgToActivity.setData(bundleToActivity);
                    try {
                        incomingMessenger.send(msgToActivity);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }

    Messenger mMessenger = new Messenger(new IncomingHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        //below should never return null
        //comm b/w activity and service
        return mMessenger.getBinder();
    }
    public int addNumbers(int a, int b){
        return a+b;
    }
}
