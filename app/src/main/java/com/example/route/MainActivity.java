package com.example.route;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.route.Adapter.Sessiondata;
import com.example.route.Database.DB_sourceDestionation_Adapter;
import com.example.route.Model.Source_Destination;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button bt_search;


    Dialog searchDialog;
    AutoCompleteTextView source;
    AutoCompleteTextView destination;
    Button D_btSeaech;
    ArrayList<Source_Destination> list = new ArrayList<Source_Destination>();

    DB_sourceDestionation_Adapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_search = (Button) findViewById(R.id.bt_search);

        dbAdapter = new DB_sourceDestionation_Adapter(MainActivity.this);
        list = dbAdapter.getAll();

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_Dialog();
            }
        });


    }


    private void search_Dialog() {

        searchDialog = new Dialog(MainActivity.this);
        searchDialog.setCancelable(true);
        searchDialog.setCanceledOnTouchOutside(true);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.setContentView(R.layout.dialog_search);
//        searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.circleshape));

//        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
//        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.5);
        searchDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


        source = (AutoCompleteTextView) searchDialog.findViewById(R.id.source);
        destination = (AutoCompleteTextView) searchDialog.findViewById(R.id.destination);
        D_btSeaech = (Button) searchDialog.findViewById(R.id.D_btSeaech);


        ArrayList<String> source_list = new ArrayList<String>();

        for (Source_Destination o : list)
        {
            source_list.add(o.getSource());

        }

        ArrayList<String> destination_list = new ArrayList<String>();

        for (Source_Destination o : list)
        {
            destination_list.add(o.getDestination());

        }


        ArrayAdapter<String> arrayAdapter_source = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item, source_list);
        ArrayAdapter<String> arrayAdapter_destination = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item, destination_list);
        source.setAdapter(arrayAdapter_source);
        destination.setAdapter(arrayAdapter_destination);
        source.setThreshold(1);
        destination.setThreshold(1);


        D_btSeaech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String src = source.getText().toString();
                final String des = destination.getText().toString();

                if (dbAdapter.getCount(src, des) > 0) {

                    int id = dbAdapter.getCount(src, des);

                    startActivity(new Intent(MainActivity.this, Stops.class));
                    Sessiondata.getInstance().setSrc_and_des(src+ " to " + des);
                    Sessiondata.getInstance().setSrc_and_des_ID(String.valueOf(id));
                    searchDialog.dismiss();

                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Did you want to add new");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int i) {

                                    Boolean falg = dbAdapter.insert(src, des);
                                    if (falg)
                                    {
                                        int id = dbAdapter.getCount(src, des);

                                        Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                                        Sessiondata.getInstance().setSrc_and_des(src+ " to " + des);
                                        Sessiondata.getInstance().setSrc_and_des_ID(String.valueOf(id));
                                        startActivity(new Intent(MainActivity.this, Stops.class));

                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                                    }


                                    searchDialog.dismiss();


                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    searchDialog.dismiss();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }


//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Please enter the Source and Destination", Toast.LENGTH_SHORT).show();
//                }


            }
        });


        if (searchDialog != null)
            searchDialog.show();


    }


}