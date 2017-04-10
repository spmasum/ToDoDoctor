package net.a6te.lazycoder.tododoctor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Prescription extends AppCompatActivity {

    private AppointmentModel appointment;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        img = (ImageView) findViewById(R.id.image);
        appointment = (AppointmentModel) getIntent().getSerializableExtra("appointment");
    }

    public void addPrescriptionOnClickBtn(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,1);
        }
    }

    String FolderName = "ashraf";
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

        if (resultCode == RESULT_OK && resultCode == 1){

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);
            String extr = Environment.getExternalStorageDirectory().toString()
                    + File.separator + "YourFolderName";
            File myPath = new File(extr, FolderName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(myPath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                MediaStore.Images.Media.insertImage(this.getContentResolver(),
                        bitmap, myPath.getPath(), "fileName");

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
