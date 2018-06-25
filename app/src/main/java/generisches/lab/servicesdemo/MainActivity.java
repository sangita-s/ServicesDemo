package generisches.lab.servicesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startStartedService(View view) {
        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
        intent.putExtra("sleepTime", 1000);
        startService(intent);
    }

    public void stopStartedService(View view) {
//        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
//        stopService(intent);
    }
}