package chapter_3_2;

public class Chapter_3_2 {
  public static void main(String[] args) {
    byte[] bytes = { (byte) 0, (byte) 1, (byte) 1 };
    for (byte b : bytes) {
      System.out.print(b);
    }
    System.out.println();
    BitList list = AdaptiveHuffmanTree.encode(bytes);
    System.out.println(list.toString());
  }
}