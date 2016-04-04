/**
 * Created by abigailwatson on 3/30/16.
 */

package toyApp;

//import java.awt.image.BufferedImage;

import android.graphics.*;
import android.media.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;



public class Toy {
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
        size += Integer.byteValue() + toyName.length();
        size += Integer.byteValue();
        size += Integer.byteValue() + getImageSize();

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

    public int getImageSize() {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            ImageWriter.write(image, "jpg", baos);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray().length;
    }

    void putIntToByteArray(int number, ByteArrayOutputStream baos) throws IOException {
        ByteBuffer b = ByteBuffer.allocate(Integer.byteValue());
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
            ImageWriter.write(image, "jpg", baos);
        }
        catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return baos.toByteArray();
    }
}
