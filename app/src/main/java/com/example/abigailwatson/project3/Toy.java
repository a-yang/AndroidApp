/**
 * Created by abigailwatson on 3/30/16.
 */

package com.example.abigailwatson.project3;

//import java.awt.image.BufferedImage;

import android.graphics.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import android.os.Parcelable;
import android.os.Parcel;




public class Toy implements Parcelable {
    String toyName = null;
    Bitmap image = null;
    int price = 0;

    public Toy() {
    }

    public Toy(String toyName, int price, Bitmap image) {
        this.toyName = toyName;
        this.image = image;
        this.price = price;
    }

    public Toy(byte[] byteArray) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);

        int nameLength = buffer.getInt();
        byte[] nameBuffer = new byte[nameLength ];
        buffer.get(nameBuffer, 0, nameLength);
        this.toyName = new String(nameBuffer);

        this.price = buffer.getInt();

        int bitMapLength = buffer.getInt();
        byte[] bitMapData = new byte[bitMapLength];
        buffer.get(bitMapData, 0, bitMapLength);
        this.image = BitmapFactory.decodeByteArray(bitMapData, 0, bitMapData.length);

    }

    static Toy getToyInfo(byte[] byteArray) throws IOException {
        Toy toy = new Toy(byteArray);
        return toy;
    }

    public int getSizeInBytes() {
        int size = 0;
        try {
        size += 4 + toyName.length();
        size += 4;
            size += 4 + getImageSize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }

    public String getToyName() {
        return toyName;
    }

    public int getPrice() {
        return price;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getImageSize() throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();

        Bitmap bmp = image;
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        return baos.toByteArray().length;
    }

    void putIntToByteArray(int number, ByteArrayOutputStream baos) throws IOException {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(number);
        baos.write(b.array());
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            putIntToByteArray(toyName.length(), baos);
            baos.write(toyName.getBytes());
            putIntToByteArray(price, baos);
            putIntToByteArray(getImageSize(), baos);
            Bitmap bmp = image;
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return baos.toByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toyName);
        dest.writeInt(price);
    }

    public void readFromParcel(Parcel in) {
        toyName = in.readString();
        price = in.readInt();
    }

    public int describeContents() {return 0;}

    public static Creator<Toy> CREATOR = new Creator<Toy>() {

        @Override
        public Toy createFromParcel(Parcel source) {
            Toy oneToy = new Toy();
            oneToy.toyName = source.readString();
            oneToy.price = source.readInt();
            return oneToy;
        }

        @Override
        public Toy[] newArray(int size) {
            return new Toy[size];
        }

    };

}
