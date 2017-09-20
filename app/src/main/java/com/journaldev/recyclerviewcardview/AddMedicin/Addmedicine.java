package com.journaldev.recyclerviewcardview.AddMedicin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.journaldev.recyclerviewcardview.R;
import com.journaldev.recyclerviewcardview.Reminder.SetReminder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Addmedicine extends AppCompatActivity {
    Spinner Spinner_unit;
    TextView TextView_as_needed;
    Button b1, b2;
    ImageView imageView;
    private Uri fileUri;
    String picturePath, imagePath;
    ImageSwitcher imageSwitcher;
    private static final int SELECT_FILE = 100;
    private Integer images[] = {R.drawable.cupcake, R.drawable.donut, R.drawable.eclair,R.drawable.froyo,R.drawable.honeycomb,R.drawable.gingerbread};
    int image_count=images.length;
    private int currImage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmedicine);


        b1 = (Button) findViewById(R.id.Button_left);
        b2 = (Button) findViewById(R.id.Button_right);

        bindcontrols();
        selectImage();
        initializeImageSwitcher();
        setInitialImage();
        setImageRotateListener();

        String[] Measurement = new String[]{
                "Select Unit",
                "tabs",
                "mL",
                "drops",
                "mg",
                "puffs",

        };
        final List<String> unit = new ArrayList<>(Arrays.asList(Measurement));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.simple_list_item_2, unit) {


            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.grey));
                    //tv.setVisibility(GONE);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.textcolour));
                }
                return view;
            }
        };
// Specify the layout to use when the list of choices appears
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_list_item_1);
// Apply the adapter to the spinner
        Spinner_unit.setAdapter(spinnerArrayAdapter);


        TextView_as_needed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i2 = new Intent(Addmedicine.this, SetReminder.class);
                startActivity(i2);
                return false;
            }
        });

    }

    private void selectImage() {


        imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Log.e("imageView_click","imageView_click");
                final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Addmedicine.this);
                TextView t1 = new TextView(Addmedicine.this);

                ViewGroup.LayoutParams prama = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //t1.setLayoutParams(prama);
               // t1.setTextColor(getResources().getColor(R.color.colorPrimary));
                t1.setText("Add Photo!");

                t1.setPadding(20, 20, 20, 20);
                t1.setGravity(Gravity.CENTER);

                t1.setTextSize(25);
               // t1.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_300));
                builder.setCustomTitle(t1);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            imageView.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            startActivityForResult(intent, 1);

                        } else if (items[item].equals("Choose from Library")) {
                            imageView.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();


            }
        });

imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.e("imageView_click","imageView_click");
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Addmedicine.this);
        TextView t1 = new TextView(Addmedicine.this);

        ViewGroup.LayoutParams prama = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //t1.setLayoutParams(prama);
        // t1.setTextColor(getResources().getColor(R.color.colorPrimary));
        t1.setText("Add Photo!");

        t1.setPadding(20, 20, 20, 20);
        t1.setGravity(Gravity.CENTER);

        t1.setTextSize(25);
        // t1.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_300));
        builder.setCustomTitle(t1);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    imageView.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);

                } else if (items[item].equals("Choose from Library")) {
                    imageView.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

                } else if (items[item].equals("Cancel")) {
                    imageSwitcher.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    //dialog.dismiss();
                   // initializeImageSwitcher();
//                    setImageRotateListener();
                }
            }
        });
        builder.show();


    }
});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == 1)
            {
                Bitmap bitmap;

                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                picturePath=f.getAbsolutePath();
                System.out.println(picturePath+" Picture path in side the Onactivity method");

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),

                        bitmapOptions);

                MediaStore.Images.Media.insertImage(getContentResolver(),
                        bitmap,
                        String.valueOf(System.currentTimeMillis()),
                        "Description");
                SaveImage(bitmap);
                imageSwitcher.setVisibility(View.GONE);
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));
                f.delete();
            }
            else {
                try {
                    Uri selectedImage = data.getData();

                    String[] filePath = { MediaStore.Images.Media.DATA };

                    Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);

                    c.moveToFirst();

                    int columnIndex = c.getColumnIndex(filePath[0]);

                    picturePath = c.getString(columnIndex);

                    c.close();

                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                    SaveImage(thumbnail);
                    imageSwitcher.setVisibility(View.GONE);
                    imageView.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, 120, 120, false));
                } catch (Exception e) {
                    Log.e("ChoosePhoto", e.getMessage().toString());
                }
            }
        }
    }
    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Medicine Reminder");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(this, new String[] { file.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    void bindcontrols() {
        Spinner_unit = (Spinner) findViewById(R.id.Spinner_unit);
        TextView_as_needed = (TextView) findViewById(R.id.TextView_as_needed);
        imageView= (ImageView) findViewById(R.id.imageView);
       imageSwitcher= (ImageSwitcher) findViewById(R.id.imageSwitcher);

    }

    private void initializeImageSwitcher() {
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView1 = new ImageView(Addmedicine.this);
                return imageView1;
            }
        });

        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
    }

    private void setImageRotateListener() {

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currImage++;

                if (currImage == image_count) {
                    currImage = 0;
                }
                setCurrentImage();
            }
        });
    }


    private void setInitialImage() {
        setCurrentImage();
    }

    private void setCurrentImage() {
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setImageResource(images[currImage]);
    }
}
