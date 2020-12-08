package com.example.todolistapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {
    private String name;
    private boolean checked;

    public Todo(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public Todo() {}

    protected Todo(Parcel in) {
        name = in.readString();
        checked = in.readByte() != 0;
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "name='" + name + '\'' +
                ", checked=" + checked +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (checked ? 1 : 0));
    }
}
