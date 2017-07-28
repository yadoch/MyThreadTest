package tw.com.abc.mythreadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private  MyThread mt1, mt2;
    private TextView tv;
    private UIHandler handler;
    private int i;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=(TextView)findViewById(R.id.tv);
    }

    public void test1(View v){
        mt1 = new MyThread("A");
        //mt2 = new MyThread("B");
        mt1.start();
        //mt2.start();
    }

    public void test2(View v){
        // 執行序
        mt1.start();

        //普通方法
        mt1.run();
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Log.i("brad", "i = " + i++);
        }
    }

    private class MyThread extends Thread {
        String name;
        MyThread(String name){this.name = name;}
        @Override
        public void run() {

            for (int i=0;i<20;i++){
                Log.i("geoff", name + "=> " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //tv.setText(name + " = " + i);

                Message mesg = new Message();
                Bundle data = new Bundle();
                data.putString("mesg", name + " = " + i);
                mesg.setData(data);
                mesg.what = 0 ;
                handler.sendMessage(mesg);

            }

        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;
            String mesg = msg.getData().getString("mesg");
            tv.setText(mesg);

        }
    }
}
