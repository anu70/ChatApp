package in.org.verkstad.chat_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * Created by anu on 3/9/2016.
 */

public class GCMIntentService extends GCMBaseIntentService {
    static final String DISPLAY_MESSAGE_ACTION =
            "in.org.verkstad.chat_app.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";
    public GCMIntentService() {
        super("854708001979");
    }
    @Override
    protected void onMessage(Context context, Intent intent) {
        //Toast.makeText(getApplicationContext(),"msg received",Toast.LENGTH_SHORT).show();
        String message = intent.getExtras().getString("msg");
        String sender = intent.getExtras().getString("sender");
       // Log.d("sendername",sender);
        displayMessage(context, message);
        generateNotification(context, message, sender);
        Log.d("notify","onmessage called");

    }

    @Override
    protected void onError(Context context, String s) {
        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        Log.d("error","error occured");

    }

    @Override
    protected void onRegistered(Context context, String s) {
        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        Log.d("onregisteredcalled",s);
        MainActivity mainActivity = new MainActivity();
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("regID",s);
        editor.commit();


        users_list users_list = new users_list();
        users_list.send_regIDs_to_server(GCMIntentService.this,sharedPreferences.getString("name",""),sharedPreferences.getString("emailid",""),s);

    }

    @Override
    protected void onUnregistered(Context context, String s) {
       // Toast.makeText(getApplicationContext(),"unregistered successfully",Toast.LENGTH_SHORT).show();
    }

    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
    private static void generateNotification(Context context, String message, String sender) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(sender);
        mBuilder.setContentText(message);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(12, mBuilder.build());
    }
}

