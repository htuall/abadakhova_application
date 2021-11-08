package com.example.newauth;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
    String word, translation;

    public Card() {
    }

    public Card(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    protected Card(Parcel in) {
        word = in.readString();
        translation = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(translation);
    }
}
