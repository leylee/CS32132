package chapter_3_2;

import java.util.Collections;
import java.util.LinkedList;

public class AdaptiveHuffmanTree {
  private class Node {
    byte data;
    int weight;
    Node[] childs = new Node[2];
    Node parent;
    boolean leaf;
    int index;

    Node() {
    }

    public Node(int weight) {
      this.weight = weight;
    }

    public boolean isLeaf() {
      return leaf;
    }

    public int getIntData() {
      return 0xFF & data;
    }

    public Node(byte data, int weight, Node parent, boolean leaf, int index) {
      this.data = data;
      this.weight = weight;
      this.parent = parent;
      this.leaf = leaf;
      this.index = index;
    }
  }

  private Node[] byteToNodes = new Node[256];
  private Node[] nodeList = new Node[258];
  private Node nytNode;

  AdaptiveHuffmanTree() {
    nodeList[257] = new Node(Integer.MAX_VALUE);
    nodeList[257].index = 257;
    nytNode = new Node((byte) 0, 0, null, true, 256);
    nodeList[256] = nytNode;
  }

  private static int byteToInt(byte b) {
    return b & 0xFF;
  }

  private void swapNode(Node n1, Node n2) {
    byte data;
    int weight;
    Node[] childs;
    boolean leaf;
    int index;

    data = n1.data;
    n1.data = n2.data;
    n2.data = data;

    weight = n1.weight;
    n1.weight = n2.weight;
    n2.weight = weight;

    leaf = n1.leaf;
    n1.leaf = n2.leaf;
    n2.leaf = leaf;

    childs = n1.childs;
    n1.childs = n2.childs;
    n2.childs = childs;

    nodeList[n1.index] = n1;
    nodeList[n2.index] = n2;
    nodeList[n1.getIntData()] = n1;
    nodeList[n2.getIntData()] = n2;
  }

  // private Node slide(Node p) {
  // int index = p.index;
  // Node previousParent = p.parent;
  // Node next;
  // while (true) {
  // next = nodeList[++index];
  // if (next.weight == p.weight && !next.isLeaf() || next.weight > p.weight) {
  // break;
  // }
  // swapNode(p, next);
  // p = next;
  // }

  // return p.isLeaf() ? p.parent : previousParent;
  // }

  private Node incrementAndSlide(Node p) {
    if (p.isLeaf()) {
      int index = p.index;
      Node next;
      while (true) {
        next = nodeList[++index];
        if (next.weight > p.weight) {
          break;
        }
        swapNode(p, next);
        p = next;
      }

      ++p.weight;
      return p.parent;
    } else {
      ++p.weight;

      int index = p.index;
      Node previousParent = p.parent;
      Node next;
      while (true) {
        next = nodeList[++index];
        if (next.weight == p.weight && !next.isLeaf() || next.weight > p.weight) {
          break;
        }
        swapNode(p, next);
        p = next;
      }

      return previousParent;
    }
  }

  private Node addNode(byte b) {
    Node lc, rc;
    lc = new Node((byte) 0, 0, nytNode, true, nytNode.index - 2);
    rc = new Node(b, 0, nytNode, true, nytNode.index - 1);
    nytNode.childs[0] = lc;
    nytNode.childs[1] = rc;
    nodeList[lc.index] = lc;
    nodeList[rc.index] = rc;
    byteToNodes[byteToInt(b)] = lc;

    nytNode.leaf = false;
    nytNode = lc;
    return rc;
  }

  private void addOne(Node p) {
    while (p != null) {
      p = incrementAndSlide(p);
    }
  }

  private LinkedList<Byte> codeOf(Node p) {
    LinkedList<Byte> list = new LinkedList<>();
    while (p.parent != null) {
      list.addFirst(p.parent.childs[0] == p ? (byte) 0 : (byte) 1);
      p = p.parent;
    }
    return list;
  }

  public static BitList encode(byte[] text) {
    BitList result = new BitList();
    AdaptiveHuffmanTree tree = new AdaptiveHuffmanTree();

    tree.printTree();
    for (byte b : text) {
      Node p = tree.byteToNodes[byteToInt(b)];
      if (p == null) {
        result.addBytes(tree.codeOf(tree.nytNode));
        p = tree.addNode(b);
      } else {
        result.addBytes(tree.codeOf(p));
      }
      tree.addOne(p);
      System.out.println(result);
      tree.printTree();
    }

    return result;
  }

  private void printTree(Node curNode, int depth) {
    System.out.print(" ".repeat(depth));
    if (curNode == null) {
      System.out.println("null");
    } else {
      System.out.println(curNode.data);
      if (!curNode.isLeaf()) {
        printTree(curNode.childs[0], depth + 1);
        printTree(curNode.childs[1], depth + 1);
      }
    }
  }

  public void printTree() {
    printTree(nodeList[256], 0);
  }
}
