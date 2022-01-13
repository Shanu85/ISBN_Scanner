package com.example.isbn_scanner;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Book implements Parcelable {
    private String url;
    private String title;
    private String author;
    private String publisher;
    private String published_date;
    private String description;
    private String pageCount;
    private String category;
    private String averageRating;

    public Book(String url, String title, String author, String publisher, String published_date, String description, String pageCount, String category, String averageRating) {
        this.url = url;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.published_date = published_date;
        this.description = description;
        this.pageCount = pageCount;
        this.category = category;
        this.averageRating = averageRating;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    protected Book(Parcel in) {
        url = in.readString();
        title = in.readString();
        author = in.readString();
        publisher = in.readString();
        published_date = in.readString();
        description = in.readString();
        pageCount = in.readString();
        category = in.readString();
        averageRating = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublished_date() {
        return published_date;
    }

    public String getDescription() {
        return description;
    }

    public String getPageCount() {
        return pageCount;
    }

    public String getCategory() {
        return category;
    }

    public String getAverageRating() {
        return averageRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public @NotNull String toString() {
        return "Book{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", published_date='" + published_date + '\'' +
                ", description='" + description + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", category='" + category + '\'' +
                ", averageRating='" + averageRating + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(publisher);
        parcel.writeString(published_date);
        parcel.writeString(description);
        parcel.writeString(pageCount);
        parcel.writeString(category);
        parcel.writeString(averageRating);
    }
}
