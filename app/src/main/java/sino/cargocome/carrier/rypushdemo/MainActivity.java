package sino.cargocome.carrier.rypushdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.push.RongPushClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RongIM.connect("pmZ2UGy6Ur9cRBR6GxlzHaleEAMS8labpkEKUPOQHoY6akTeXkpvrn2vpOiWz6QdSySUDwf3vhQ=", new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                System.out.println("aaa00");
            }

            @Override
            public void onSuccess(String s) {
                System.out.println("aaa11");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                System.out.println("aaa22");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        RongIM.getInstance().disconnect();
    }
}
