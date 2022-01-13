package com.example.isbn_scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static EditText isbn_no;
    Button scan_btn,submit_btn;
    private final int CAMERA_PERMISSION_CODE=101;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isbn_no=(EditText)(findViewById(R.id.inputText));
        scan_btn=(Button)(findViewById(R.id.scan_btn));
        submit_btn=(Button)(findViewById(R.id.submit_btn));

        setupPermission();
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan_code();
            }
        });
        requestQueue=VolleySingletonQueue.getNew_instance(this).getRequestQueue();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isbn_no.toString().length()==0)
                {
                    Toast.makeText(MainActivity.this,"Please enter a valid ISBN Number",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    fetchBookData();
                }
            }
        });
    }

    private void fetchBookData() {
        String url="https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn_no.getText().toString();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.length()<=0)
                {
                    Toast.makeText(MainActivity.this,"Book not found",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    JSONArray all_book_data=response.getJSONArray("items");
                    if(all_book_data.length()<=0)
                    {
                        Toast.makeText(MainActivity.this,"Book not found!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        JSONObject jsonObject= (JSONObject) all_book_data.get(0);
                        String book_url=jsonObject.getString("selfLink");
                        JSONObject book_info=jsonObject.getJSONObject("volumeInfo");
//                        System.out.println(book_url);
                        Book book=new Book("Nil","Not Found","Not Found","Not Found","Not Found","Not Found","Not Found","Not Found","Not Found");
                        if(book_info.has("title"))
                        {
                            book.setTitle(book_info.getString("title"));
                        }
                        if(book_info.has("authors"))
                        {
                            JSONArray all_authors=book_info.getJSONArray("authors");
                            book.setAuthor(all_authors.getString(0));
                        }
                        if(book_info.has("publisher"))
                        {
                            book.setPublisher(book_info.getString("publisher"));
                        }
                        if(book_info.has("publishedDate"))
                        {
                            book.setPublished_date(book_info.getString("publishedDate"));
                        }
                        if(book_info.has("description"))
                        {
                            book.setDescription(book_info.getString("description"));
                        }
                        if(book_info.has("pageCount"))
                        {
                            book.setPageCount(book_info.getString("pageCount"));
                        }
                        if(book_info.has("categories"))
                        {
                            JSONArray category=book_info.getJSONArray("categories");
                            StringBuilder all_categories= new StringBuilder();
                            for(int i=0;i<Math.min(3,category.length());i++)
                            {
                                all_categories.append(new String(category.getString(i)+" "));
                            }
                            book.setCategory(all_categories.toString().trim());
                        }

                        if(book_info.has("averageRating"))
                        {
                            book.setAverageRating(book_info.getString("averageRating"));
                        }
                        if(book_info.has("imageLinks"))
                        {
                            JSONObject images=book_info.getJSONObject("imageLinks");
                            if(images.has("thumbnail"))
                            {
                                String str="https"+images.getString("thumbnail").substring(4);
                                book.setUrl(str);
                            }
                        }

                        Intent intent=new Intent(MainActivity.this,Display_Info_Activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("BookInfo",book);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void setupPermission() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)!=
                PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA))
            {
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("Scanner need camera permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,"You need to give camera permission to use this",Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void scan_code()
    {
        Intent intent=new Intent(this,Scan_Code_Activity.class);
        startActivity(intent);
    }
}