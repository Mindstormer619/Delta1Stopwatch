package project.mstorm.d1stopwatch;

import java.lang.ref.WeakReference;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity {
	TextView displayer;
	
	static class Mandler extends Handler {
		private final WeakReference<TextView> tDis;
		Mandler(TextView ts) {
			tDis = new WeakReference<TextView>(ts);
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle tBund = msg.getData();
    		String tCurr = String.valueOf(tBund.getInt("time"));
    		tDis.get().setText(tCurr);
		}
	}

    Mandler msgHandler = new Mandler(displayer);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayer = (TextView) findViewById(R.id.textView1);
    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	Thread backUpdate = new Thread( new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int tPass=0;
				while(true) {
					if (Thread.interrupted()) {
						return;
					}
					try {
						Thread.sleep(1000);
						if (Thread.interrupted()) {
							return;
						}
						Bundle b = new Bundle();
						tPass++;
						b.putInt("time", tPass);
						Message msg = new Message();
						msg.setData(b);
						msgHandler.sendMessage(msg);
					}
					catch(Exception e) {
						return;
					}
				}
			}
		});
    	backUpdate.start();
    }
}
