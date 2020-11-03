package experiment_2;

import java.io.Serializable;
import java.util.ArrayList;

public class BinaryLinkedTree<DataType extends Comparable<? super DataType>> implements Serializable {

  private static final long serialVersionUID = -2176296641657153564L;

  private class BinaryLinkedNode<T extends Comparable<? super T>> {
    T value;
    BinaryLinkedNode<T> leftChild, rightChild;

    BinaryLinkedNode() {
    }

    BinaryLinkedNode(T value) {
      this.value = value;
    }

    BinaryLinkedNode(T value, BinaryLinkedNode<T> leftSon, BinaryLinkedNode<T> rightSon) {
      this(value);
      this.leftChild = leftSon;
      this.rightChild = rightSon;
    }
  }

  private enum Child {
    leftChild, rightChild
  };

  private int size;
  private BinaryLinkedNode<DataType> root = new BinaryLinkedNode<>();

  public BinaryLinkedTree(ArrayList<DataType> datas, ArrayList<Integer> preorderSequence) {
    class Status {
      Child child;
      BinaryLinkedNode<DataType> node;

      Status(Child child, BinaryLinkedNode<DataType> node) {
        this.child = child;
        this.node = node;
      }
    }

    MyStack<Status> stack = new MyStack<>();
    stack.push(new Status(Child.rightChild, root));

    for (int curID : preorderSequence) {
      Status curStatus = stack.peek();
      BinaryLinkedNode<DataType> nextNode;

      if (curID == 0) {
        nextNode = null;
      } else {
        nextNode = new BinaryLinkedNode<>(datas.get(curID));
      }

      if (curStatus.child == Child.leftChild) {
        curStatus.node.leftChild = nextNode;
        curStatus.child = Child.rightChild;
      } else {
        curStatus.node.rightChild = nextNode;
        stack.pop();
      }

      if (nextNode != null) {
        ++size;
        stack.push(new Status(Child.leftChild, nextNode));
      }
    }
  }

  private void printTree(BinaryLinkedNode<DataType> curNode, int depth) {
    System.out.print(" ".repeat(depth));
    if (curNode == null) {
      System.out.println("null");
    } else {
      System.out.println(curNode.value);
      if (curNode.leftChild != null || curNode.rightChild != null) {
        printTree(curNode.leftChild, depth + 1);
        printTree(curNode.rightChild, depth + 1);
      }
    }
  }

  public void printTree() {
    printTree(root.rightChild, 0);
  }

  private ArrayList<DataType> recursionPreorderTraverse(BinaryLinkedNode<DataType> curNode) {
    ArrayList<DataType> answer = new ArrayList<>();

    if (curNode != null) {
      answer.add(curNode.value);
      answer.addAll(recursionPreorderTraverse(curNode.leftChild));
      answer.addAll(recursionPreorderTraverse(curNode.rightChild));
    }

    return answer;
  }

  public ArrayList<DataType> recursionPreorderTraverse() {
    return recursionPreorderTraverse(root.rightChild);
  }

  private ArrayList<DataType> recursionPostorderTraverse(BinaryLinkedNode<DataType> curNode) {
    ArrayList<DataType> answer = new ArrayList<>();

    if (curNode != null) {
      answer.addAll(recursionPostorderTraverse(curNode.leftChild));
      answer.addAll(recursionPostorderTraverse(curNode.rightChild));
      answer.add(curNode.value);
    }

    return answer;
  }

  public ArrayList<DataType> recursionPostorderTraverse() {
    return recursionPostorderTraverse(root.rightChild);
  }

  private ArrayList<DataType> recursionInorderTraverse(BinaryLinkedNode<DataType> curNode) {
    ArrayList<DataType> answer = new ArrayList<>();

    if (curNode != null) {
      answer.addAll(recursionInorderTraverse(curNode.leftChild));
      answer.add(curNode.value);
      answer.addAll(recursionInorderTraverse(curNode.rightChild));
    }

    return answer;
  }

  public ArrayList<DataType> recursionInorderTraverse() {
    return recursionInorderTraverse(root.rightChild);
  }
}
