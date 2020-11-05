package chapter_3_2_static;

// import chapter_3_2.BitList;

public class Main {
  public static void main(String[] args) {
    String s = "aabc";
    byte[] bytes = s.getBytes();
    HuffmanTree tree = new HuffmanTree(bytes);
    byte[] encoded = tree.encode(bytes);
    System.out.println(new BitList(encoded));
  }
}
