package in.org.verkstad.chat_app;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class chat_window extends AppCompatActivity {
    EditText msg;
    AppConfig appConfig;
    String message,users_regID,user_name,listview_regID;
    String[] sender_id,message_chat;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        msg = (EditText) findViewById(R.id.msg);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_chat_window);
        appConfig = new AppConfig();

        Intent intent = getIntent();
        listview_regID = intent.getStringExtra("listview_regID");
        //Toast.makeText(getApplicationContext(), listview_regID, Toast.LENGTH_SHORT).show();
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        users_regID =  sharedPreferences.getString("regID", null);
        user_name = sharedPreferences.getString("name",null);

        fetch_chat();
    }
    public void fetch_chat(){
        RequestQueue requestQueue=Volley.newRequestQueue(chat_window.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, appConfig.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonArray=new JSONArray(s);

                    sender_id = new String[jsonArray.length()];
                    message_chat = new String[jsonArray.length()];

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject n = jsonArray.getJSONObject(i);
                        sender_id[i] = n.getString("sender_regid");
                        message_chat[i] = n.getString("message");
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), Arrays.toString(sender_id),Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(new LinearLayoutManager(chat_window.this));
                RecyclerViewAdaptor recyclerViewAdaptor = new RecyclerViewAdaptor(chat_window.this,message_chat,sender_id);
                recyclerView.setAdapter(recyclerViewAdaptor);

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
                params.put("TAG","chat_window");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    public void send(View view){
        Log.d("send", "called");
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
                params.put("sender_name",user_name);
                params.put("receiver_regid",listview_regID);
                params.put("message",message);
                return params;
            }
        };

        requestQueue.add(stringRequest);

        msg.setText("");
    }
}
