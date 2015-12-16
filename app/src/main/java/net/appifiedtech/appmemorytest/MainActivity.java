package net.appifiedtech.appmemorytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String FILE_NAME = "elephant_fight.jpg";
    private String FILE_PATH = "priyabrat";
    private ImageView imageView;
    private File fileRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        File file = getMyFile();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.elephant_fight);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readImage(View view) {
        File file = getMyFile();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public File getMyFile(){
        fileRoot = getDir(FILE_PATH, Context.MODE_PRIVATE);
        return new File(fileRoot,FILE_NAME);
    }
}
