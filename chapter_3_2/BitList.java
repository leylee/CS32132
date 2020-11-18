package chapter_3_2;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

// public class BitList {
//   private int length;
//   private int size;
//   private int capacity;
//   private byte[] bytes;

//   BitList() {
//     size = 0;
//     length = 0;
//     capacity = 0;
//   }

//   BitList(byte[] bytes) {
//     setBytes(bytes);
//   }

//   private void updateCapacity(int n) {
//     if (capacity < n) {
//       size = (n - 1) / 8 + 1;
//       capacity = size * 8;
//       bytes = Arrays.copyOf(bytes, size);
//     }
//   }

//   public boolean get(int index) {
//     updateCapacity(index + 1);
//     return 0 != (bytes[index / 8] & (1 << (index % 8)));
//   }

//   public void flip(int index) {
//     updateCapacity(index + 1);
//     if (length < index + 1) {
//       length = index + 1;
//     }
//     bytes[index / 8] = (byte) (bytes[index / 8] ^ (1 << (index % 8)));
//   }

//   public void set(int index, boolean value) {
//     updateCapacity(index + 1);
//     if (get(index) != value) {
//       flip(index);
//     }
//   }

//   public void add(boolean value) {
//     set(getLength(), value);
//   }

//   public void add(BitList list)

//   public int getLength() {
//     return length;
//   }

//   public int getSize() {
//     return size;
//   }

//   public int getCapacity() {
//     return capacity;
//   }

//   public byte[] getBytes() {
//     return bytes.clone();
//   }

//   public void setBytes(byte[] bytes) {
//     this.bytes = bytes.clone();
//     this.size = bytes.length;
//     this.capacity = this.size * 8;
//     this.length = this.capacity;
//   }
// }

public class BitList extends BitSet {

  /**
  *
  */
  private static final long serialVersionUID = -4067306152358233262L;
  private int bitPos = 0;

  public void addBytes(Byte[] bytes) {
    for (byte aByte : bytes) {
      if (aByte == 0) {
        clear(length());
      } else {
        set(length());
      }
    }
  }

  public void addBytes(List<Byte> bytes) {
    addBytes((Byte[]) bytes.toArray(new Byte[0]));
  }

  public void resetBitPos() {
    bitPos = 0;
  }

  public byte nextBit() {
    return get(bitPos++) ? (byte) 1 : (byte) 0;
  }

  public BitSet nextBits(int n) {
    return get(bitPos, bitPos + n);
  }

  public int getBitPos() {
    return bitPos;
  }

  public void setBitPos(int bitPos) {
    this.bitPos = bitPos;
  }

  @Override
  public String toString() {
    String s = "";
    for (int i = 0; i < length(); ++i) {
      s += get(i) ? "1" : "0";
    }
    return s;
  }

  public void add(boolean bit) {
    if (bit) {
      set(length());
    } else {
      set(length(), false);
    }
  }

  public void add(BitList bits) {
    for (int i = 0; i < bits.length(); ++i) {
      add(bits.get(i));
    }
  }
}
