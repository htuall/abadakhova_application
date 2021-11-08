package com.example.newauth;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    String imageUrl, title, author, description, bookText;

    public Book() {
    }

    public Book(String imageUrl, String title, String author, String description, String bookText) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.description = description;
        this.bookText = bookText;
    }

    protected Book(Parcel in) {
        imageUrl = in.readString();
        title = in.readString();
        author = in.readString();
        description = in.readString();
        bookText = in.readString();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookText() {
        return bookText;
    }

    public void setBookText(String bookText) {
        this.bookText = bookText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(description);
        dest.writeString(bookText);
    }
}
