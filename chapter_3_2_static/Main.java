package chapter_3_2_static;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

// import chapter_3_2.BitList;

public class Main {
  public static void main(String[] args) throws IOException {
    // String s = "aabc";
    // byte[] bytes = s.getBytes();
    byte[] bytes;
    FileInputStream oriStream = new FileInputStream(new File("content.txt"));
    bytes = oriStream.readAllBytes();

    HuffmanTree tree = new HuffmanTree(bytes);
    byte[] encoded = tree.encode(bytes);

    System.out.println("原始文本");
    System.out.println(new String(bytes));
    System.out.println("压缩后的 bits");
    System.out.println(new BitList(encoded));
    System.out.println("压缩率: " + ((double) (encoded.length + Integer.BYTES) / bytes.length * 100) + "%");

    File file = new File("encoded.dat");
    FileOutputStream outputStream = new FileOutputStream(file);
    outputStream.write(ByteBuffer.allocate(4).putInt(bytes.length).array());
    outputStream.write(encoded);
    outputStream.close();
    FileInputStream inputStream = new FileInputStream(file);
    byte[] intBytes = new byte[Integer.BYTES];
    inputStream.read(intBytes);
    int textLength = ByteBuffer.wrap(intBytes).getInt();
    byte[] otherBytes = inputStream.readAllBytes();
    inputStream.close();

    System.out.println("解压后的数据");
    byte[] decoded = tree.decode(textLength, otherBytes);
    System.out.println(new String(decoded));
  }
}
