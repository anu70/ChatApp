package in.org.verkstad.chat_app;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class chat_window extends AppCompatActivity {
    EditText msg;
    AppConfig appConfig;
    String message,users_regID,listview_regID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        msg = (EditText) findViewById(R.id.msg);

        appConfig = new AppConfig();

        Intent intent = getIntent();
        listview_regID = intent.getStringExtra("listview_regID");
        //Toast.makeText(getApplicationContext(), listview_regID, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        users_regID =  sharedPreferences.getString("users_regID", null);
    }


    public void send(View view){
        message = msg.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("sender_regid",users_regID);
                params.put("receiver_regid",listview_regID);
                params.put("message",message);
                return params;
            }
        };

        requestQueue.add(stringRequest);

        msg.setText("");
    }
}
