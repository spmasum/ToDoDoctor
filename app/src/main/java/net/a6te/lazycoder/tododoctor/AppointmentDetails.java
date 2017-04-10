package net.a6te.lazycoder.tododoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AppointmentDetails extends AppCompatActivity {

    private AppointmentModel appointment;

    private TextView docNameTv;
    private TextView docDetailsTv;
    private TextView docAppointmentTv;
    private TextView docPhoneTv;
    private TextView docEmailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        initializeAll();
        docNameTv.setText(appointment.getDoctorName());
        docDetailsTv.setText(appointment.getDoctorSpecialist());
        docAppointmentTv.setText(appointment.getDoctorAppointment());
        docPhoneTv.setText(appointment.getDoctorPhone());
        docEmailTv.setText(appointment.getDoctorEmail());
    }

    private void initializeAll() {
        appointment = (AppointmentModel) getIntent().getSerializableExtra("appointment");
        docNameTv = (TextView) findViewById(R.id.details_docNameTv);
        docDetailsTv = (TextView) findViewById(R.id.details_docDetails);
        docAppointmentTv = (TextView) findViewById(R.id.details_docAppointment);
        docPhoneTv = (TextView) findViewById(R.id.details_docPhone);
        docEmailTv = (TextView) findViewById(R.id.details_docEmail);
    }

    public void update(View view) {
        startActivity(new Intent(AppointmentDetails.this,AddDoctor.class).putExtra("updateAppointment",appointment));
    }

    public void addPrescriptionBtn(View view) {
        startActivity(new Intent(AppointmentDetails.this,Prescription.class).putExtra("appointment",appointment));
    }
}
