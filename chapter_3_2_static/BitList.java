package chapter_3_2_static;

import java.util.Arrays;

public class BitList {
  private int length;
  private int size;
  private int capacity;
  private byte[] bytes;

  BitList() {
    this(new byte[0]);
  }

  BitList(byte[] bytes) {
    setBytes(bytes);
  }

  private void updateCapacity(int n) {
    if (capacity < n) {
      size = (n - 1) / 8 + 1;
      capacity = size * 8;
      bytes = Arrays.copyOf(bytes, size);
    }
  }

  public boolean get(int index) {
    updateCapacity(index + 1);
    return 0 != (bytes[index / 8] & (1 << (index % 8)));
  }

  public void flip(int index) {
    updateCapacity(index + 1);
    if (length < index + 1) {
      length = index + 1;
    }
    bytes[index / 8] = (byte) (bytes[index / 8] ^ (1 << (index % 8)));
  }

  public void set(int index, boolean value) {
    updateCapacity(index + 1);
    if (get(index) != value) {
      flip(index);
    } else {
      if (length < index + 1) {
        length = index + 1;
      }
    }
  }

  public void add(boolean value) {
    set(length, value);
  }

  public void add(BitList list) {
    for (int i = 0; i < list.length; ++i) {
      set(length, list.get(i));
    }
  }

  public int getLength() {
    return length;
  }

  public int getSize() {
    return size;
  }

  public int getCapacity() {
    return capacity;
  }

  public byte[] getBytes() {
    return bytes.clone();
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes.clone();
    this.size = bytes.length;
    this.capacity = this.size * 8;
    this.length = this.capacity;
  }

  public void reverse() {
    for (int i = 0; i < length / 2; ++i) {
      boolean l = get(i), r = get(length - 1 - i);
      set(i, r);
      set(length - i - 1, l);
    }
  }

  @Override
  public String toString() {
    String string = "";
    for (int i = 0; i < length; ++i) {
      string += get(i) ? "1" : "0";
    }
    return string;
  }
}
