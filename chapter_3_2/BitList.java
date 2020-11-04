package chapter_3_2;

import java.util.BitSet;
import java.util.List;

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
    addBytes((Byte[]) bytes.toArray());
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
}
