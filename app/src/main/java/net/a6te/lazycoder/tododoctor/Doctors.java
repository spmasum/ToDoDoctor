package net.a6te.lazycoder.tododoctor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Doctors extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper database;
    private ArrayList<AppointmentModel> appointments;
    private ListViewCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        listView = (ListView) findViewById(R.id.doctorLV);
        loadListView();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Doctors.this);
                builder.setMessage("Are you sure you want to delete this?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.deleteAppointment(appointments.get(position).getAppointmentId());
                        loadListView();
                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Doctors.this,AppointmentDetails.class).putExtra("appointment",appointments.get(position)));
            }
        });

        loadListView();

    }

    public void loadListView(){
        database = new DatabaseHelper(this);
        appointments = new ArrayList<>();
        appointments = database.getAppointmetList();
        adapter = new ListViewCustomAdapter(this,appointments);
        listView.setAdapter(adapter);
    }


    public void addDoctorFB(View view) {
        startActivity(new Intent(Doctors.this,AddDoctor.class));
    }
}