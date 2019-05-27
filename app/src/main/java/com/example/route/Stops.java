package com.example.route;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.route.Adapter.Sessiondata;
import com.example.route.Database.DB_Stops_Adapter;
import com.example.route.Database.DB_sourceDestionation_Adapter;
import com.example.route.Model.Source_Destination;
import com.example.route.Model.StopsModel;

import java.util.ArrayList;

public class Stops extends AppCompatActivity {

    FloatingActionButton fab_add;
    TextView src_des;
    ArrayList<String> stoplist = new ArrayList<String>();

    Button bt_add;
    EditText ed_stops;
    Dialog addStopDialog;
    ListView listView;


    ArrayList<StopsModel> list = new ArrayList<StopsModel>();

    DB_Stops_Adapter db;
    StopsAdapter adapter;
    private String id;
    private String src_txt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);

        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        listView = (ListView) findViewById(R.id.listView);
        src_des = (TextView) findViewById(R.id.src_des);

        src_txt = Sessiondata.getInstance().getSrc_and_des();
        id = Sessiondata.getInstance().getSrc_and_des_ID();
        src_des.setText(src_txt);
        Sessiondata.getInstance().setSrc_and_des("");
        Sessiondata.getInstance().setSrc_and_des_ID("");


        db = new DB_Stops_Adapter(Stops.this);
        list = db.getAll(id);

        adapter = new StopsAdapter(Stops.this, list);
        listView.setAdapter(adapter);


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_Stops();

            }
        });


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String id_stops = list.get(position).getId();
//                String name_stops = list.get(position).getStops_name();
//
//                Sessiondata.getInstance().setId_stops(id_stops);
//                Sessiondata.getInstance().setName_stops(name_stops);
//
//                startActivity(new Intent(Stops.this, Bus.class));
//
//
//            }
//        });


    }

    private void add_Stops() {


        addStopDialog = new Dialog(Stops.this);
        addStopDialog.setCancelable(true);
        addStopDialog.setCanceledOnTouchOutside(true);
        addStopDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addStopDialog.setContentView(R.layout.dialog_add_stops);
        addStopDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        addStopDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circleshape));
        bt_add = (Button) addStopDialog.findViewById(R.id.bt_add);
        ed_stops = (EditText) addStopDialog.findViewById(R.id.ed_stops);


        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String s1 = ed_stops.getText().toString();

                if (TextUtils.isEmpty(s1)) {
                    Toast.makeText(Stops.this, "Please enter the field", Toast.LENGTH_SHORT).show();

                }
                else {


                    if (db.getCount(s1, id) > 0) {

                        addStopDialog.dismiss();
                        Toast.makeText(Stops.this, "Already Exists", Toast.LENGTH_SHORT).show();


                    } else {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Stops.this);
                        builder1.setMessage("Did you want to add new");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int i) {

                                        Boolean falg = db.insert(s1, id);
                                        if (falg)
                                        {
                                            Toast.makeText(Stops.this, "Inserted", Toast.LENGTH_SHORT).show();
                                            list = db.getAll(id);

                                            adapter.notifyDataSetChanged();
                                            adapter = new StopsAdapter(Stops.this, list);
                                            listView.setAdapter(adapter);

                                        }
                                        else
                                        {
                                            Toast.makeText(Stops.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                                        }


                                        addStopDialog.dismiss();


                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        addStopDialog.dismiss();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }







                }


            }
        });


        if (addStopDialog != null)
            addStopDialog.show();

    }


    public class StopsAdapter extends BaseAdapter {

        Context context;
        ArrayList<StopsModel> list = new ArrayList<StopsModel>();
        ViewHolder v;


        public StopsAdapter(Context context, ArrayList<StopsModel> list) {
            this.context = context;
            this.list = list;
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
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            v= new ViewHolder();

            if (convertView == null)
            {
                convertView= LayoutInflater.from(context).inflate(R.layout.child_stops, null);
                convertView.setTag(v);
            }
            else
            {
                v= (ViewHolder) convertView.getTag();
            }

            v.ed_stops = (TextView) convertView.findViewById(R.id.ed_stops);
            v.delete = (Button) convertView.findViewById(R.id.delete);

            v.ed_stops.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id_stops = list.get(position).getId();
                    String name_stops = list.get(position).getStops_name();

                    Sessiondata.getInstance().setId_stops(id_stops);
                    Sessiondata.getInstance().setName_stops(name_stops);

                    startActivity(new Intent(Stops.this, Bus.class));

                }
            });

            v.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id = list.get(position).getId();
                    list.remove(position);
                    db.delete(id);
                    adapter.notifyDataSetChanged();

                }
            });

            v.ed_stops.setText(list.get(position).getStops_name());


            return convertView;
        }

        class ViewHolder{

            TextView ed_stops;
            Button delete;

        }
    }

}
