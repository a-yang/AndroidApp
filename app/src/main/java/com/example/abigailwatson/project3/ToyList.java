/**
 * Created by abigailwatson on 3/30/16.
 */

package com.example.abigailwatson.project3;

import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import android.os.Parcel;


public class ToyList implements Parcelable {

    private ArrayList<Toy> toyList = new ArrayList<Toy>();

    public ToyList() {
    }

    public ToyList(byte[] byteArray, int length) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        int cursor = 0;
        while (cursor < length) {
            int toyLength = buffer.getInt();

            byte[] toyBuffer = new byte[toyLength];
            buffer.get(toyBuffer, 0, toyLength);
            Toy toy = new Toy (toyBuffer);

            toyList.add(toy);
            cursor += 4 + toyLength;
        }
    }

    public void addToy(Toy toy) {
        toyList.add(toy);
    }

    public Toy getToy(int index) {
        return toyList.get(index);
    }

    public ArrayList<Toy> getToyList() {
        return toyList;
    }

    public int getNumOfToys() {
        return toyList.size();
    }

    public int getSizeInBytes() {
        int size = 0;
        for (int i = 0; i < toyList.size(); i++) {
            size += toyList.get(i).getSizeInBytes();
        }
        return size;
    }

    void putIntToByteArray(int number, ByteArrayOutputStream baos) throws IOException {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(number);
        baos.write(b.array());
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            for (int i = 0; i < toyList.size(); i++) {
                Toy toy = toyList.get(i);
                byte[] toyBuffer = toy.toByteArray();
                putIntToByteArray(toyBuffer.length, baos);
                baos.write(toyBuffer);
            }
        }
        catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return baos.toByteArray();
    }

    public static void readFromFile(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "r");
            int length = (int) file.length();
            byte[] temp = new byte[length];
            file.read(temp);
            file.close();
            ToyList toyList = new ToyList(temp, length);
            toyList.getNumOfToys();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(toyList);
    }


    public int describeContents() {return 0;}

    public static Creator<ToyList> CREATOR = new Creator<ToyList>() {

        @Override
        public ToyList createFromParcel(Parcel source) {
            ToyList toyList = new ToyList();
            toyList.toyList = source.readArrayList(Toy.class.getClassLoader());
            return toyList;
        }

        @Override
        public ToyList[] newArray(int size) {
            return new ToyList[size];
        }

    };

}
