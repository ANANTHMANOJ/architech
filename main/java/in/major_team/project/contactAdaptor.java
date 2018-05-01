package in.major_team.project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AmNayak on 16-01-2018.
 */

public class contactAdaptor extends ArrayAdapter {
    List<contacts> list=new ArrayList();
    String updation="https://amnayak.000webhostapp.com/updating.php";
    String deletion="https://amnayak.000webhostapp.com/deletion.php";

    AlertDialog.Builder builder;
    Context context;
    singleton sin;
    Animation animation;


    public contactAdaptor(Context context, int resource, List<contacts> arrayList, singleton requester) {
        super(context, resource);
        this.context=context;
        sin=requester;
        builder=new AlertDialog.Builder(context);
        //Toast.makeText(context,String .valueOf(arrayList.size()),Toast.LENGTH_SHORT).show();
        list=arrayList;
    }


    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        animation= AnimationUtils.loadAnimation(getContext(),R.anim.list);

        final ContactHolder contactHolder;
        if(row==null)
        {

            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.list_layout,parent,false);
             contactHolder=new ContactHolder();
            contactHolder.tool=(TextView)row.findViewById(R.id.tool);
            contactHolder.status=(Switch)row.findViewById(R.id.status);
            contactHolder.data=(ImageButton) row.findViewById(R.id.graph);
            contactHolder.deletion=(ImageButton)row.findViewById(R.id.deletion);
            row.setTag(contactHolder);

        }
        else
        {
            contactHolder=(ContactHolder)row.getTag();
        }
        final contacts cont= (contacts) getItem(position);
        contactHolder.tool.setText(cont.getTool());

        int int_status=cont.getStatus().equals("1")?1:0;
        if (int_status == 1) {
            contactHolder.status.setOnCheckedChangeListener(null);
            contactHolder.status.setChecked(true);
            contactHolder.status.setText("ON");
        }
        else{
            contactHolder.status.setText("OFF");
            contactHolder.status.setChecked(false);
        }
        contactHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              //  Toast.makeText(getContext(),String.valueOf(isChecked)+ " Updated id: "+contactHolder.tool.getText(),Toast.LENGTH_SHORT).show();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, updation, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        builder.setTitle("Server part");
                        builder.setMessage("Response :" + response);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Error...",Toast.LENGTH_SHORT).show();
                    }


            }){
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError
                    {
                        Map<String,String>params=new HashMap<String, String>();
                        params.put("tools",contactHolder.tool.getText().toString());

                        params.put("status",contactHolder.status.getText().toString());
                       // params.put("data",contactHolder.data.getText().toString());
                        return params;
                    }

                };
                sin.addToRequestQueue(stringRequest);
                if(!contactHolder.status.isChecked())
                    contactHolder.status.setText("OFF");
                else
                    contactHolder.status.setText("ON");
            }
        });
       // contactHolder.data.setText(cont.getData());


      //deletion listener


        contactHolder.deletion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                StringRequest stringRequest=new StringRequest(Request.Method.POST, deletion, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        builder.setTitle("Server part");
                        builder.setMessage("Response :" + response);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restartActity();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Error...",Toast.LENGTH_SHORT).show();
                    }


                }){
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError
                    {
                        Map<String,String>params=new HashMap<String, String>();
                        params.put("tools",contactHolder.tool.getText().toString());

                        //params.put("status",contactHolder.status.getText().toString());
                        // params.put("data",contactHolder.data.getText().toString());
                        return params;
                    }

                };
                sin.addToRequestQueue(stringRequest);

            }
        });
        contactHolder.data.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(context, bar_webview.class);
                intent.putExtra("tool", contactHolder.tool.getText().toString());
                context.startActivity(intent);
            }
        });
        return row;
    }

    private void restartActity() {
        Intent i =new Intent(context,MainActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(i);
    }


    static class ContactHolder
    {
        TextView tool;
        ImageButton data,deletion;
        Switch status;
    }

}
