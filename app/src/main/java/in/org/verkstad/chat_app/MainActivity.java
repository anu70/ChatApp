package in.org.verkstad.chat_app;

;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends AppCompatActivity {
    EditText username,email;
    String name,emailid;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        if(!sharedPreferences.getString("regID","").equals("")){
            Intent intent = new Intent(MainActivity.this,users_list.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        register = (Button) findViewById(R.id.Register_user);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = username.getText().toString();
                emailid = email.getText().toString();

                GCMRegistrar.checkDevice(MainActivity.this);
                GCMRegistrar.checkManifest(MainActivity.this);
                final String registration_id = GCMRegistrar.getRegistrationId(MainActivity.this);

                if (registration_id.equals("")) {
                    GCMRegistrar.register(MainActivity.this, "854708001979");
                    SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putString("name",name);
                    editor.putString("emailid",emailid);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this,users_list.class);
                    startActivity(intent);
                }

            }
        });
    }

}