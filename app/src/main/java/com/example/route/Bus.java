package com.example.route;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.example.route.Database.DB_BusAdapter;
import com.example.route.Database.DB_Stops_Adapter;
import com.example.route.Model.BusModel;

import java.util.ArrayList;

public class Bus extends AppCompatActivity {

    FloatingActionButton fab_add;

    Dialog add_BusDialog;
    Dialog detailsDialog;

    DB_BusAdapter db;
    BusAdapter adapter;

    ListView listView;
    private String id_stops ;
    private String name_stops ;

    ArrayList<BusModel> list = new ArrayList<BusModel>();
    private EditText ed_bus;
    private EditText ed_outTime;
    private EditText ed_inTime;
    private Button bt_add;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);


        id_stops = Sessiondata.getInstance().getId_stops();
        name_stops = Sessiondata.getInstance().getName_stops();


        Sessiondata.getInstance().setId_stops("");
        Sessiondata.getInstance().setName_stops("");

        listView = (ListView) findViewById(R.id.listView);
        db = new DB_BusAdapter(Bus.this);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);

        list = db.getAll(id_stops);
        adapter = new BusAdapter(Bus.this, list);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialogDetails(position);

            }
        });


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_Bus();

            }
        });

    }

    private void dialogDetails(final int pos) {

        detailsDialog = new Dialog(Bus.this);
        detailsDialog.setCancelable(true);
        detailsDialog.setCanceledOnTouchOutside(true);
        detailsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        detailsDialog.setContentView(R.layout.dialog_bus_details);
        detailsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        detailsDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circleshape));


        TextView outTime = (TextView) detailsDialog.findViewById(R.id.outTime);
        TextView inTime = (TextView) detailsDialog.findViewById(R.id.inTime);
        TextView busNo = (TextView) detailsDialog.findViewById(R.id.busNo);
        Button deleteBt = (Button) detailsDialog.findViewById(R.id.deleteBt);

        busNo.setText(list.get(pos).getBus_name());
        outTime.setText(list.get(pos).getOut_time());
        inTime.setText(list.get(pos).getIn_time());

        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id = list.get(pos).getId();
                list.remove(pos);
                db.delete(id);
                adapter.notifyDataSetChanged();
                detailsDialog.dismiss();

            }
        });

        if (detailsDialog != null)
            detailsDialog.show();





    }

    private void add_Bus() {

        add_BusDialog = new Dialog(Bus.this);
        add_BusDialog.setCancelable(true);
        add_BusDialog.setCanceledOnTouchOutside(true);
        add_BusDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        add_BusDialog.setContentView(R.layout.dialog_add_bus);
        add_BusDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        add_BusDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circleshape));


        ed_bus = (EditText) add_BusDialog.findViewById(R.id.ed_bus);
        ed_outTime = (EditText) add_BusDialog.findViewById(R.id.ed_OutTime);
        ed_inTime = (EditText) add_BusDialog.findViewById(R.id.ed_InTime);
        bt_add = (Button) add_BusDialog.findViewById(R.id.bt_add);

       bt_add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final String txt_ed_bus = ed_bus.getText().toString();
               final String txt_ed_outTime = ed_outTime.getText().toString();
               final String txt_ed_inTime = ed_inTime.getText().toString();

               if (TextUtils.isEmpty(txt_ed_bus) ) {
                   Toast.makeText(Bus.this, "Please enter the field", Toast.LENGTH_SHORT).show();

               }
               else {


                   if (db.getCount(txt_ed_bus, id_stops) > 0) {

                       add_BusDialog.dismiss();
                       Toast.makeText(Bus.this, "Already Exists", Toast.LENGTH_SHORT).show();


                   } else {
                       AlertDialog.Builder builder1 = new AlertDialog.Builder(Bus.this);
                       builder1.setMessage("Did you want to add new");
                       builder1.setCancelable(true);

                       builder1.setPositiveButton(
                               "Yes",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int i) {

                                       BusModel obj = new BusModel();
                                       obj.setBus_name(txt_ed_bus);
                                       obj.setIn_time(txt_ed_inTime);
                                       obj.setOut_time(txt_ed_outTime);
                                       obj.setStop_id(id_stops);



                                       Boolean falg = db.insert(obj);
                                       if (falg)
                                       {
                                           Toast.makeText(Bus.this, "Inserted", Toast.LENGTH_SHORT).show();
                                           list = db.getAll(id_stops);

                                           adapter.notifyDataSetChanged();
                                           adapter = new BusAdapter(Bus.this, list);
                                           listView.setAdapter(adapter);

                                       }
                                       else
                                       {
                                           Toast.makeText(Bus.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                                       }


                                       add_BusDialog.dismiss();


                                   }
                               });

                       builder1.setNegativeButton(
                               "No",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                       dialog.cancel();
                                       add_BusDialog.dismiss();
                                   }
                               });

                       AlertDialog alert11 = builder1.create();
                       alert11.show();

                   }



               }


           }
       });


       if (add_BusDialog != null)
           add_BusDialog.show();

    }





    public class BusAdapter extends BaseAdapter {

        Context context;
        ArrayList<BusModel> list = new ArrayList<BusModel>();
        ViewHolder v;


        public BusAdapter(Context context, ArrayList<BusModel> list) {
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
        public View getView(int position, View convertView, ViewGroup parent) {

            v= new ViewHolder();

            if (convertView == null)
            {
                convertView= LayoutInflater.from(context).inflate(R.layout.child_bus, null);
                convertView.setTag(v);
            }
            else
            {
                v= (ViewHolder) convertView.getTag();
            }

            v.busNo = (TextView) convertView.findViewById(R.id.busNo);
            v.tv_OutTime = (TextView) convertView.findViewById(R.id.tv_OutTime);
            v.tv_InTime = (TextView) convertView.findViewById(R.id.tv_InTime);

            v.busNo.setText(list.get(position).getBus_name());
            v.tv_OutTime.setText(list.get(position).getOut_time());
            v.tv_InTime.setText(list.get(position).getIn_time());

            return convertView;
        }

        class ViewHolder{

            TextView tv_OutTime, tv_InTime, busNo;

        }
    }





}
