package in.org.verkstad.chat_app;

;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends AppCompatActivity {
    EditText username,email;
    String name,emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
    }

    public void Register_user(View view){
        name=username.getText().toString();
        emailid = email.getText().toString();
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        final String registration_id = GCMRegistrar.getRegistrationId(this);

        if(registration_id.equals("")){
            GCMRegistrar.register(this,"854708001979");
        }
        Toast.makeText(getApplicationContext(), registration_id, Toast.LENGTH_SHORT).show();
        if(!registration_id.equals("")){
            SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("users_regID",registration_id);
            editor.commit();
            Intent i = new Intent(this,users_list.class);
            i.putExtra("name",name);
            i.putExtra("emailid",emailid);
            i.putExtra("registration_id",registration_id);
            startActivity(i);
        }

    }
    public void unregister(View view){
        GCMRegistrar.unregister(this);
        //final String registration_id = GCMRegistrar.getRegistrationId(this);
        //Toast.makeText(getApplicationContext(),registration_id,Toast.LENGTH_SHORT).show();
    }
}