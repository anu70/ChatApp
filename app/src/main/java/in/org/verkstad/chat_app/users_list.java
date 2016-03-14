package in.org.verkstad.chat_app;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class users_list extends AppCompatActivity {

    String[] name_list,email_list,regId_list;
    ListView users_list;
    AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        users_list = (ListView) findViewById(R.id.users_list);
        appConfig = new AppConfig();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, appConfig.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonArray = new JSONArray(s);

                    name_list = new String[jsonArray.length()];
                    email_list = new String[jsonArray.length()];
                    regId_list = new String[jsonArray.length()];

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject n = jsonArray.getJSONObject(i);
                        name_list[i] = n.getString("name");
                        email_list[i] = n.getString("email");
                        regId_list[i] = n.getString("gcm_reg_id");
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(in.org.verkstad.chat_app.users_list.this,R.layout.support_simple_spinner_dropdown_item,name_list);
                    users_list.setAdapter(arrayAdapter);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"exception"+e,Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("TAG","getusers");
                return params;
            }
        };
        requestQueue.add(stringRequest);


        users_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String listview_regID = regId_list[position];
                //Toast.makeText(getApplicationContext(),listview_regID,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(users_list.this,chat_window.class);
                intent.putExtra("listview_regID",listview_regID);
                startActivity(intent);
            }
        });


    }

    public void send_regIDs_to_server(final Context context,final String name, final String emailid, final String registration_id){
        appConfig = new AppConfig();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,appConfig.url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
              /**  try {
                    JSONArray jsonArray = new JSONArray(s);

                    name_list = new String[jsonArray.length()];
                    email_list = new String[jsonArray.length()];
                    regId_list = new String[jsonArray.length()];

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject n = jsonArray.getJSONObject(i);
                        name_list[i] = n.getString("name");
                        email_list[i] = n.getString("email");
                        regId_list[i] = n.getString("gcm_reg_id");
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,name_list);
                    users_list.setAdapter(arrayAdapter);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"exception"+e,Toast.LENGTH_SHORT).show();
                } **/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",emailid);
                params.put("regId", registration_id);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

}
