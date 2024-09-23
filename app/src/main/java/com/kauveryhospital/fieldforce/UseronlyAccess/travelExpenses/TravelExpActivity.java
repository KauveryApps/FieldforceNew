package com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.OHCOnly.Globaldeclare.APIClientgohc;
import com.kauveryhospital.fieldforce.OHCOnly.Globaldeclare.APIInterfacegohc;
import com.kauveryhospital.fieldforce.OHCOnly.Globaldeclare.Examplegohc;
import com.kauveryhospital.fieldforce.OHCOnly.ListOhcAdapter;
import com.kauveryhospital.fieldforce.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.getdata.APIClientTrvPlan;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.getdata.APIInterfaceTrvPlan;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.getdata.GetcheckoutTrv;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.getdata.Result;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.APIClientSavetrv;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.APIInterfaceSavetrv;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.ExampleSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.ResultSaves;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.uploadfile.APIClientSaveupl;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.uploadfile.APIInterfaceSaveupl;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.uploadfile.ExampleSaveupl;
import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class TravelExpActivity extends Activity implements AdapterView.OnItemSelectedListener, NetworkChangeCallback {
    ImageView backarrow;
    String image,encImage,answer;
    ImageView imageviews,imageviews1;
    private Button btnSelectImage;
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    String imgPath = null;
    int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    TextView txtview;
    Calendar upDateFrom,upDateFroms;
    String imgphoto=null;
    APIInterfacegohc apiInterfacegohc;
    APIInterfaceSavetrv apiInterfaceSavetrv;

    List<ResultSaves> listsave = new ArrayList<>();
    private static final int pic_id = 123;
    String imageString;
    String encodedImages;
    private int year, month, day;
    int mDay;
    String encodedImage = "";
    Spinner spinner;
    long  tomindate;
    DatePickerDialog mDatePicker;
    byte[] imageBytes,buff;
    Button btnsave;
    String item,alterdates, mfrmDate,sessions, uname, pswd,  message, kilometers, svamount;
    TextView frmbtnDatePicker;
    public static final String PREFS_NAME = "loginpref";
    TextView edkilometers;
    private NetworkChangeReceiver networkChangeReceiver;
    List<Result> listups = new ArrayList<>();
    ImageView captured_image, open_camera,fromdate;

    Bitmap photo;
    APIInterfaceSaveupl apiInterfaceupl;
    int rannumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_exp);
        Random rand = new Random();
        rannumber = rand.nextInt(20) + 1;
        networkChangeReceiver = new NetworkChangeReceiver(this);
        captured_image = findViewById(R.id.closed_camera);
        open_camera = findViewById(R.id.closed_camera_open);
        edkilometers = findViewById(R.id.edkilometers);
        frmbtnDatePicker=findViewById(R.id.frmbtnDatePicker);
        btnsave = findViewById(R.id.btnsave);
        fromdate=findViewById(R.id.fromdate);
   //     txtvisitdate = findViewById(R.id.txtvisitdate);

        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        sessions = set.getString("session", "");
        Log.d(TAG, "onActivityResultddd: " + sessions);
        backarrow = findViewById(R.id.backarrow);





      //  svempname = bundle.getString("empname");
      //  svvisitdate = bundle.getString("visitdate");
      //  svaddress = bundle.getString("address");
       // svcustomer = bundle.getString("customer");
      //  svcheckin = bundle.getString("checkin");
      //  empname.setText(uname);
      //  txtaddress.setText(svaddress);
     //   txtcusname.setText(svcustomer);
      //  txtvisitdate.setText(svvisitdate);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {

                    try {
                        user();
                        validation();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "check Internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TravelExpActivity.this, ListTravelExpensesActivity.class);
                startActivity(intent);
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelExpActivity.this, TabbedActivity.class);
                startActivity(intent);
            }
        });
        captured_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //   startActivityForResult(camera_intent, pic_id);
                selectImage();
            }
        });
        fromdate.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Calendar mcurrentDates = Calendar.getInstance();
                year = mcurrentDates.get(Calendar.YEAR);
                month = mcurrentDates.get(Calendar.MONTH);
                day = mcurrentDates.get(Calendar.DAY_OF_MONTH);
                mcurrentDates.set(Calendar.DAY_OF_MONTH, mDay);
                mDatePicker = new DatePickerDialog(TravelExpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        upDateFrom = Calendar.getInstance();
                        upDateFroms = Calendar.getInstance();
                        year = selectedyear;
                        month = selectedmonth;
                        day = selectedday;
                        upDateFrom.set(year, month, day);
                        mfrmDate = convertDate(convertToMillis(day, month, year));
                        tomindate=convertToMillis(day,month,year);
                        alterdates = convertDate1(convertToMillis(day, month, year));
                        frmbtnDatePicker.setText(alterdates);
                        postdatastate();
                    }

                }, year, month, day);

                mDatePicker.setTitle("Please select From Date");
                //   mDatePicker.getDatePicker().setMinDate(lngfromsdate);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

                mDatePicker.show();

            }
        });
    }

    private void validation() {

            try {
                postData();
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }
    public String convertDate(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(mTime);
        return formattedDate;

    }
    public String convertDate1(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(mTime);
        return formattedDate;

    }
    public long convertToMillis(int day, int month, int year) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, year);
        calendarStart.set(Calendar.MONTH, month);
        calendarStart.set(Calendar.DAY_OF_MONTH, day);
        return calendarStart.getTimeInMillis();
    }
    private void selectImage() {
        try {

            final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Option");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        dialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, PICK_IMAGE_CAMERA);

                    } else if (options[item].equals("Choose From Gallery")) {
                        dialog.dismiss();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                //  String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File("/sdcard/FieldForceTravelExpenses/", rannumber + ".png");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
                captured_image.setImageBitmap(bitmap);
                encodeImage(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                imgPath = getRealPathFromURI(selectedImage);
                destination = new File(imgPath.toString());
                captured_image.setImageBitmap(bitmap);
                encodeImage(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        encImage = Base64.encodeToString(b, Base64.DEFAULT);
        toimag(encImage);
        answer=encImage.replaceAll("\\s+","");
        //  Log.i("strrr",answer);

        return encImage;
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public void toimag(String encodedImage)
    {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        //  imageviews1.setImageBitmap(decodedByte);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        photo = (Bitmap) data.getExtras().get("data");
//
//          captured_image.setImageBitmap(photo);
//        //  encodeImage(photo);
//      // ConvertBitmapToString(photo);
//
//        open_camera.setVisibility(View.INVISIBLE);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        byte[] imageBytes = byteArrayOutputStream.toByteArray();
//        Bitmap compressedimg=BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
//
//        //    String blobString = new String(imageBytes);
//
//        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        //  final String ConvertImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        File destination = new File(Environment.getExternalStorageDirectory() + "/FieldForceTravelExpenses", System.currentTimeMillis() + ".png");
//        System.out.println("FILE LOCATION" + destination);
//        image = destination.toString();
//        buff = image.getBytes();
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            photo.compress(Bitmap.CompressFormat.JPEG,100,fo);
//            fo.write(byteArrayOutputStream.toByteArray());
//            Log.d(TAG, "onActivityResult: "+fo);
//            fo.flush();
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        File file = new File("/sdcard/FieldForceTravelExpenses/", System.currentTimeMillis() + ".png");
//        if (file.exists()) {
//            file.delete();
//        }
//        try {
//            FileOutputStream out=new FileOutputStream(file);
//            photo.compress(Bitmap.CompressFormat.JPEG,100,out);
//            out.flush();
//            out.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
////        try {
////            InputStream inputStream=getAssets().open(System.currentTimeMillis() + ".png");
////            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
////            captured_image.setImageBitmap(bitmap);
////            ByteArrayOutputStream stream=new ByteArrayOutputStream();
////            bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        Bitmap bm = BitmapFactory.decodeFile(String.valueOf(destination));
////        ByteArrayOutputStream baos = new ByteArrayOutputStream();
////        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
////        byte[] b = baos.toByteArray();
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    public void postData() throws JSONException {
       // kilometers = edkilometers.getText().toString();

        final ProgressDialog loading = new ProgressDialog(TravelExpActivity.this);
        loading.setMessage("Saving Travel Expenses...");
        loading.setCancelable(false);
        loading.show();
        // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        apiInterfaceSavetrv = APIClientSavetrv.getClient().create(APIInterfaceSavetrv.class);
        JsonObject object = new JsonObject();
        JsonObject object1 = new JsonObject();

        JsonObject jsonObject = new JsonObject();

        JsonObject jsonObject2 = new JsonObject();

        JsonArray array2 = new JsonArray();

        JsonArray array5 = new JsonArray();
        JsonArray array = new JsonArray();
        JsonArray array6 = new JsonArray();

        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams3 = new JsonObject();
        JsonObject jsonParams4= new JsonObject();
        JsonObject jsonParams5= new JsonObject();


//        jsonParams1.addProperty("employeeid", svempname);
//        jsonParams1.addProperty("vechicle", item);
//        jsonParams1.addProperty("kilometers", kilometers);
//        jsonParams1.addProperty("applying_date", svvisitdate);
//        jsonParams1.addProperty("contact_name", svcustomer);
//        jsonParams1.addProperty("address", svaddress);
//        jsonParams1.addProperty("expense", svamount);
//        jsonParams1.addProperty("status", "pending");

        jsonParams1.addProperty("dc2_image",answer);
        jsonParams1.addProperty("filename",rannumber+".png");
        object.addProperty("rowno", "001");
        object.addProperty("text", "0");
        object.add("columns", jsonParams1);


        jsonParams4.addProperty("dc1_image",answer);
        jsonParams4.addProperty("filename",rannumber+".png");
        object1.addProperty("rowno","001");
        object1.addProperty("text","0");
        object1.add("columns",jsonParams4);


        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", sessions);
        // jsonParams2.addProperty("username", uname);
        //   jsonParams2.addProperty("password", pswd);

        // jsonParams2.addProperty("transid", "travl");
        jsonParams2.addProperty("transid", "expim");
        jsonParams2.addProperty("recordid", "0");
      
        array5.add(object);
        array6.add(object1);

        jsonParams3.add("axp_recid1",array6);
        jsonParams3.add("axp_recid2", array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata", array);
       //  jsonParams2.addProperty("afiles",rannumber+".png");
       //  jsonParams2.addProperty("attachmode","fs");
        jsonObject.add("savedata", jsonParams2);

        array2.add(jsonObject);
        jsonObject2.add("_parameters", array2);


        // Enter the correct url for your api service site
        apiInterfaceSavetrv =  APIClientSavetrv.getClient().create(APIInterfaceSavetrv.class);
        Call<ExampleSave> call2 = apiInterfaceSavetrv.getResult(jsonObject2);
        call2.enqueue(new Callback<ExampleSave>() {
            @Override
            public void onResponse(Call<ExampleSave> call, Response<ExampleSave> response) {
                loading.dismiss();

                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                       // UpdateCout();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(TravelExpActivity.this, TabbedActivity.class));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ExampleSave> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(TravelExpActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity", "Status: " + status);
        if (status == false)
            Toast.makeText(TravelExpActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }


    private void postdatastate() {


        edkilometers.setText(" ");
        apiInterfacegohc = APIClientgohc.getClient().create(APIInterfacegohc.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s"," ");
        jsonObject5.addProperty("sql","select kilometer from work_start where username='"+uname+"' and  sdate='"+alterdates+"'");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);
            JsonArray array1=new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters",array1);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Call<Examplegohc> call4=apiInterfacegohc.getResult(jsonObject7);
        call4.enqueue(new Callback<Examplegohc>() {

            @Override
            public void onResponse(Call<Examplegohc> call, Response<Examplegohc> response) {

                      kilometers= response.body().getResult().get(0).getResult().getRow().get(0).getKilometer();
                edkilometers.setText(kilometers);

            }

            @Override
            public void onFailure(Call<Examplegohc> call, Throwable t) {


                Toast.makeText(TravelExpActivity.this, "No Values Found", Toast.LENGTH_LONG).show();
                edkilometers.setText("No Kilometers Found");
            }

        });

    }

    public void user() throws JSONException {
        //for storing encrypt char//
        //  Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
        final ProgressDialog loading = new ProgressDialog(TravelExpActivity.this);
        loading.setMessage("Loading ...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceupl = APIClientSaveupl.getClient().create(APIInterfaceSaveupl.class);
        OkHttpClient httpClient = new OkHttpClient();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();

        JsonObject jsonParams = new JsonObject();
        jsonParams.addProperty("axpapp", "fieldforce");
        jsonParams.addProperty("s", sessions);
        jsonParams.addProperty("filedata", answer);
        jsonParams.addProperty("filename", rannumber + ".png");
        array.put(jsonParams);
        try {
            jsonObject6.add("uploadfile", jsonParams);
            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<ExampleSaveupl> call4 = apiInterfaceupl.getResult(jsonObject7);
        call4.enqueue(new Callback<ExampleSaveupl>() {
            @Override
            public void onResponse(Call<ExampleSaveupl> call, Response<ExampleSaveupl> response) {
                loading.dismiss();
                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResult().get(0).getMessage() != null) {

                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TravelExpActivity.this, TabbedActivity.class));

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ExampleSaveupl> call, Throwable t) {
                Log.e("====", "Something gone wrong");
            }
        });
    }
}


