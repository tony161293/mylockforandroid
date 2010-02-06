package i4nc4mp.myLock;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
//if user has the start at boot pref, we queue the user present detection service
public class BootHandler extends Service {
	
	@Override
	public IBinder onBind(Intent intent) {
		// we don't bind it, just call start from the widget
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(getClass().getSimpleName(),"BootHandler onCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences settings = getSharedPreferences("myLock", 0);
		boolean boot = settings.getBoolean("boot", false);//retrieve user's start at boot pref
		
		
		
		if (!boot) {
			stopSelf();//destroy the process because user doesn't have start at boot enabled
			return 1;
		}
		
		//boolean custom = settings.getBoolean("welcome", false);//retrieve user's mode pref
		
		Intent i = new Intent();
		
		//if (!custom) i.setClassName("i4nc4mp.myLock", "i4nc4mp.myLock.NoLockService");
		//else i.setClassName("i4nc4mp.myLock", "i4nc4mp.myLock.CustomLockService");
		
		i.setClassName("i4nc4mp.myLock", "i4nc4mp.myLock.UserPresentService");
		//the service will wait for user to complete the first lockscreen - this protects phone from a restart security circumvention
		
		startService(i);
		
		stopSelf();
		
		return 1;
	}
}