package com.example.savephotowithgallerytodrawable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btnLoadImage;
    TextView textSource;
    ImageView imageResult;

    final int RQS_IMAGE1 = 1;
    Bitmap tempBitmap;
    Uri source;

    Canvas canvasMaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoadImage = (Button) findViewById(R.id.loadimage);
        textSource = (TextView) findViewById(R.id.sourceuri);
        imageResult = (ImageView) findViewById(R.id.result);

        //btnLoadImage.setOnClickListener(new View.OnClickListener() {

           // @Override
           // public void onClick(View arg0) {
               // Intent intent = new Intent(
                        //Intent.ACTION_PICK,
                       // android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               // startActivityForResult(intent, RQS_IMAGE1);
            //}
        //});
    }
    public void onClickGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Choose Picture"),RQS_IMAGE1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_IMAGE1:
                    source = data.getData();
                    //use this code with MediaStore

                    /*try {
                        tempBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                                source);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //imageView1.setImageBitmap(galleryBitmap);
                    //imageView.setImageURI(selectedImageUri);
                    writeImage();*/
                    //BitmapFactory good only for small photo
                    try {
                        tempBitmap = BitmapFactory
                                .decodeStream(getContentResolver().openInputStream(
                                        source));

                        // Convert bitmap to drawable
                       // Drawable drawable = new BitmapDrawable(getResources(),tempBitmap);

                        //imageResult.setImageDrawable(drawable);
                        //imageResult.setImageBitmap(tempBitmap);

                        //textSource.setText(drawable.getClass().toString());

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    writeImage();

                    break;
            }
        }
    }
    private File createImageFile() throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String imageFileName = "IMAGE_" + simpleDateFormat.format(new Date()) + ".jpg";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDirectory, imageFileName);
        return imageFile;
    }

    private void writeImage() {
        File galleryPhotoFile = null;
        try {
            galleryPhotoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fileOutputStream;
        try{
            fileOutputStream = new FileOutputStream(galleryPhotoFile);
            //imageView1.setDrawingCacheEnabled(true);
            //Bitmap bitmap = imageView1.getDrawingCache();
            tempBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}