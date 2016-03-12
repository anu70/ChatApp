package in.org.verkstad.chat_app;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * Created by anu on 3/9/2016.
 */

public class GCMIntentService extends GCMBaseIntentService {
    public GCMIntentService() {
        super("854708001979");
       // Toast.makeText(this,"intentservice",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onMessage(Context context, Intent intent) {
        Toast.makeText(getApplicationContext(),"msg received",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onError(Context context, String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onRegistered(Context context, String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onUnregistered(Context context, String s) {
        Toast.makeText(getApplicationContext(),"unregistered successfully",Toast.LENGTH_SHORT).show();


    }
}
