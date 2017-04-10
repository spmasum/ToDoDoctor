package net.a6te.lazycoder.tododoctor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PorterDuff;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mobile App Develop on 4/5/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ToDo Doctor";
    private static final String TABLE_NAME_APPOINTMENT = "AppointmentDetails";
    private static final String TABLE_NAME_PRESCRIPTION = "PrescriptionDetails";
    private static final String COL_APP_DOC_ID = "appId";
    private static final String COL_APP_DOC_NAME = "docName";
    private static final String COL_APP_DOC_DETAILS = "docDetail";
    private static final String COL_APP_DOC_APPOINTMENT = "docAppointment";
    private static final String COL_APP_DOC_PHONE = "docPhone";
    private static final String COL_APP_DOC_EMAIL = "docEmail";

    private static final String appointmentTaleCreateQuery = "Create Table "+
            TABLE_NAME_APPOINTMENT+" ( "+
            COL_APP_DOC_ID+" Integer primary key, "+
            COL_APP_DOC_NAME+" Text, "+
            COL_APP_DOC_DETAILS+" Text, "+
            COL_APP_DOC_APPOINTMENT+" text, "+
            COL_APP_DOC_PHONE+" Text, "+
            COL_APP_DOC_EMAIL+" Text);";
    private SQLiteDatabase database;

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(appointmentTaleCreateQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        database.execSQL("Drop table if exist "+TABLE_NAME_APPOINTMENT);
        onCreate(sqLiteDatabase);
    }

    public void openDatabase(){
        database = this.getReadableDatabase();
    }
    public void closeDatabase(){
        database.close();
    }

    public boolean addAppointment(AppointmentModel appointment){
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_APP_DOC_NAME,appointment.getDoctorName());
        contentValues.put(COL_APP_DOC_DETAILS,appointment.getDoctorSpecialist());
        contentValues.put(COL_APP_DOC_APPOINTMENT,appointment.getDoctorAppointment());
        contentValues.put(COL_APP_DOC_EMAIL,appointment.getDoctorEmail());
        contentValues.put(COL_APP_DOC_PHONE,appointment.getDoctorPhone());
        long status = database.insert(TABLE_NAME_APPOINTMENT,null,contentValues);
        closeDatabase();
        return (status > 0 ? true : false);
    }


    public ArrayList<AppointmentModel> getAppointmetList(){
        openDatabase();
        ArrayList<AppointmentModel > appointments = new ArrayList<>();

        Cursor cursor = database.rawQuery("Select *From "+TABLE_NAME_APPOINTMENT,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){

            appointments.add(new AppointmentModel(cursor.getInt(cursor.getColumnIndex(COL_APP_DOC_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_DETAILS)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_APPOINTMENT)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_PHONE))
                    ));
        }
        closeDatabase();
        return appointments;
    }

    //delete appointment
    public boolean deleteAppointment(int id){
        openDatabase();
        long status = database.delete(TABLE_NAME_APPOINTMENT,COL_APP_DOC_ID+" = ?",new String []{String.valueOf(id)});
        closeDatabase();
        return status > 0 ? true : false;//if status > 0 then it will return true else it will return false
    }
    //update doctor appointment
    public boolean updateAppointment(AppointmentModel appointment){
        openDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_APP_DOC_NAME,appointment.getDoctorName());
        contentValues.put(COL_APP_DOC_DETAILS,appointment.getDoctorSpecialist());
        contentValues.put(COL_APP_DOC_APPOINTMENT,appointment.getDoctorAppointment());
        contentValues.put(COL_APP_DOC_PHONE,appointment.getDoctorPhone());
        contentValues.put(COL_APP_DOC_EMAIL,appointment.getDoctorEmail());

        long status = database.update(TABLE_NAME_APPOINTMENT,contentValues,COL_APP_DOC_ID +" = ?",new String []{String.valueOf(appointment.getAppointmentId())});
        closeDatabase();
        return status > 0 ? true:false;
    }
}
