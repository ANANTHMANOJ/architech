package in.major_team.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddDevice extends AppCompatActivity {
    Button button;
    EditText tool,data;
    String server="https://amnayak.000webhostapp.com/update.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        button=(Button)findViewById(R.id.added);
        tool=(EditText)findViewById(R.id.addtool);
        data=(EditText)findViewById(R.id.data);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.5));



        builder=new AlertDialog.Builder(AddDevice.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String t,s,d;
                t=tool.getText().toString();
                d=data.getText().toString();
                final Intent intent=new Intent(AddDevice.this,MainActivity.class);

                StringRequest stringRequest=new StringRequest(Request.Method.POST, server,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                builder.setTitle("Server Response");
                                builder.setMessage("Response :" +response);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        tool.setText("");
                                        data.setText("");
                                    }
                                });
                                AlertDialog alertDialog=builder.create();
                                alertDialog.show();
                                startActivity(intent);
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddDevice.this,"Error.....",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<String, String>();
                        params.put("tool",t);
                        params.put("data",d);
                        return params;
                    }
                };
                singleton.getmInstance(AddDevice.this).addToRequestQueue(stringRequest);


            }
        });

    }
}
