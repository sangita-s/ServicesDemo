package generisches.lab.servicesdemo.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import generisches.lab.servicesdemo.R;
import generisches.lab.servicesdemo.services.MyBoundService;

public class MyBoundActivity extends AppCompatActivity {

    Boolean isBound = false;
    private MyBoundService myBoundService;

    //Service connection class to establish link between this and myboundservice
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            //iBinder from onBind in MyBoundService
            MyBoundService.MyLocalBinder lMyLocalBinder = (MyBoundService.MyLocalBinder) iBinder;
            //To get current instance of service - which we have written.
            myBoundService = lMyLocalBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(this, MyBoundService.class);
        bindService(i, mConnection, BIND_AUTO_CREATE); //Auto create serice as long as the components are bound to it
        //BIND_DEBUG_UNBIND
        //BIND_NOT_FOREGROUND
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBound){
            unbindService(mConnection);
            isBound = false;
        }
    }

    public void onClickEvent(View view) {
        EditText edtNumOne = findViewById(R.id.etNumOne);
        EditText edtNumTwo = findViewById(R.id.etNumTwo);
        TextView txvResult = findViewById(R.id.txvResult);

        int numOne = Integer.valueOf(edtNumOne.getText().toString());
        int numTwo = Integer.valueOf(edtNumTwo.getText().toString());

        String resultString = "";

//        Button btnAdd = view.findViewById(R.id.btnadd);
//        Button btnSub = view.findViewById(R.id.btnsub);
//        Button btnMul = view.findViewById(R.id.btnmul);
//        Button btnDiv = view.findViewById(R.id.btnDiv);

        if(isBound){
            switch (view.getId()) {

                case R.id.btnadd:
                    resultString = String.valueOf(myBoundService.add(numOne, numTwo));
                    break;

                case R.id.btnsub:
                    resultString = String.valueOf(myBoundService.sub(numOne, numTwo));
                    break;

                case R.id.btnmul:
                    resultString = String.valueOf(myBoundService.mul(numOne, numTwo));
                    break;

                case R.id.btnDiv:
                    resultString = String.valueOf(myBoundService.div(numOne, numTwo));
                    break;
            }
            txvResult.setText(resultString);
        }
    }
}
