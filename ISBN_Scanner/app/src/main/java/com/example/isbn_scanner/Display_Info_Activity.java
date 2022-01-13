package com.example.isbn_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class Display_Info_Activity extends AppCompatActivity {

    ImageView book_image;
    TextView book_name;
    TextView author_name;
    TextView Publisher;
    TextView Publish_date;
    TextView Description;
    TextView Average_Rating;
    TextView Page_count;
    TextView Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info);

        book_image=(ImageView)findViewById(R.id.book_image);
        book_name=(TextView)findViewById(R.id.book_name);
        author_name=(TextView)findViewById(R.id.author_name);
        Publisher=(TextView)findViewById(R.id.publisher_name);
        Description=(TextView)findViewById(R.id.description);
        Publish_date=(TextView)findViewById(R.id.published_date);
        Average_Rating=(TextView)findViewById(R.id.Average_Rating);
        Page_count=(TextView)findViewById(R.id.Page_count);
        Category=(TextView)findViewById(R.id.Category);


        Book book=(Book)getIntent().getParcelableExtra("BookInfo");

        setData(book);
        //System.out.println(book.toString());
    }

    public void setData(Book book)
    {
        Picasso.get().load(book.getUrl()).into(book_image);
        book_name.setText(book.getTitle());
        author_name.setText(new String("Author : "+book.getAuthor()));
        Publisher.setText(new String("Publisher : "+book.getPublisher()));
        Category.setText(new String("Categories : "+book.getCategory()));
        Average_Rating.setText(new String("Average Rating : "+book.getAverageRating()));
        Page_count.setText(new String("Page Count : "+book.getPageCount()));
        Publish_date.setText(new String("Published Date : "+book.getPublished_date()));
        Description.setText(book.getDescription());

        Description.setMovementMethod(new ScrollingMovementMethod());
    }
}