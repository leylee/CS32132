package chapter_3_2_static;

import java.util.ArrayList;
import java.util.PriorityQueue;

// import chapter_3_2.BitList;

public class HuffmanTree {
  private class Node implements Comparable<Node> {
    byte data;
    int weight;
    Node parent;
    Node[] childs;

    private Node() {
      // childs = new Node[2];
    }

    Node(int weight, int data) {
      this();
      this.weight = weight;
      this.data = (byte) data;
    }

    Node(int weight, Node leftChild, Node rightChild) {
      this();
      this.weight = weight;
      this.childs = new Node[2];
      this.childs[0] = leftChild;
      this.childs[1] = rightChild;
    }

    @Override
    public int compareTo(Node o) {
      return weight < o.weight ? -1 : weight == o.weight ? 0 : 1;
    }
  }

  private class DecodeResult {
    byte[] result;
    Node nextNode;

    DecodeResult(byte[] result, Node next) {
      this.result = result;
      this.nextNode = next;
    }

    DecodeResult(ArrayList<Byte> result, Node next) {
      this.result = new byte[result.size()];
      for (int i = 0; i < result.size(); ++i) {
        this.result[i] = result.get(i);
      }
      this.nextNode = next;
    }
  }

  ArrayList<Node> leaves;
  ArrayList<BitList> code;
  ArrayList<DecodeResult> decodeTable;
  Node root;

  private HuffmanTree() {
    leaves = new ArrayList<>();
    code = new ArrayList<>();
    decodeTable = new ArrayList<>();

    for (int i = 0; i < 256; ++i) {
      leaves.add(new Node(0, i));
    }
  }

  public HuffmanTree(byte[] bytes) {
    this();
    for (byte b : bytes) {
      ++getNode(b).weight;
    }

    PriorityQueue<Node> heap = new PriorityQueue<>(leaves);
    while (heap.size() > 1) {
      Node leftchild = heap.poll();
      Node rightChild = heap.poll();
      Node p = new Node(leftchild.weight + rightChild.weight, leftchild, rightChild);
      leftchild.parent = p;
      rightChild.parent = p;
      heap.offer(p);
    }
    root = heap.poll();

    for (Node node : leaves) {
      BitList bits = getCode(node);
      bits.reverse();
      code.add(bits);
    }

    // System.out.println(this);

    generateDecodeTable();
  }

  private void generateDecodeTable() {
    generateDecodeTable(root, new ArrayList<>(), 0, 0);
  }

  private void generateDecodeTable(Node p, ArrayList<Byte> result, int data, int depth) {
    if (depth == 8) {
      decodeTable.add(new DecodeResult(result, p));
    } else {
      Node leftChild = p.childs[0];
      int leftData = data;
      if (leftChild.childs == null) {
        result.add(leftChild.data);
        generateDecodeTable(root, result, leftData, depth + 1);
        result.remove(result.size() - 1);
      } else {
        generateDecodeTable(leftChild, result, leftData, depth + 1);
      }

      Node rightChild = p.childs[1];
      int rightData = data |= (1 << (7 - depth));
      if (rightChild.childs == null) {
        result.add(rightChild.data);
        generateDecodeTable(root, result, rightData, depth + 1);
        result.remove(result.size() - 1);
      } else {
        generateDecodeTable(rightChild, result, rightData, depth + 1);
      }
    }
  }

  private Node getNode(int b) {
    return leaves.get(b & 0xFF);
  }

  private BitList getCode(int b) {
    return code.get(b & 0xFF);
  }

  private BitList getCode(Node p) {
    BitList result = new BitList();
    while (p.parent != null) {
      result.add(p.parent.childs[1] == p);
      p = p.parent;
    }
    return result;
  }

  public byte[] encode(byte[] bytes) {
    BitList result = new BitList();
    for (byte b : bytes) {
      result.add(code.get(b));
    }
    return result.getBytes();
  }

  @Override
  public String toString() {
    return "HuffmanTree [code=" + code + "]";
  }

  public byte[] decode(int length, byte[] bytes) {
    byte[] result = new byte[length];
    int index = 0;
    Node p = root;
    BitList bits = new BitList(bytes);
    for (int i = 0; i < bits.getLength(); ++i) {
      int childId = bits.get(i) ? 1 : 0;
      p = p.childs[childId];
      if (p.childs == null) {
        result[index++] = p.data;
        p = root;
        if (index == length) {
          break;
        }
      }
    }
    return result;
  }
}
