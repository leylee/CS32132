package experiment_2;

import java.io.Serializable;
import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;

import javax.xml.crypto.Data;

public class BinaryLinkedTree<DataType extends Comparable<? super DataType>> implements Serializable {

  private static final long serialVersionUID = -2176296641657153564L;

  private enum Child {
    leftChild, rightChild, self
  };

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

    @Override
    public String toString() {
      // TODO Auto-generated method stub
      return value == null ? "null" : value.toString();
    }

  }

  private class Status<T extends Comparable<? super T>> {
    Child child;
    BinaryLinkedNode<T> node;

    Status(Child child, BinaryLinkedNode<T> node) {
      this.child = child;
      this.node = node;
    }

    @Override
    public String toString() {
      return "Status [child=" + child + ", node=" + node + "]";
    }
  }

  private int size;
  private BinaryLinkedNode<DataType> root;

  public BinaryLinkedTree(ArrayList<DataType> datas, ArrayList<Integer> preorderSequence) {

    MyStack<Status> stack = new MyStack<>();
    BinaryLinkedNode<DataType> head = new BinaryLinkedNode<>();
    stack.push(new Status(Child.rightChild, head));

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

  public ArrayList<DataType> iterationPreorderTraverse() {
    ArrayList<DataType> answer = new ArrayList<>();
    MyStack<Status<DataType>> stack = new MyStack<>();

    stack.push(new Status<DataType>(Child.leftChild, root.rightChild));

    while (!stack.empty()) {
      Status<DataType> curStatus = stack.peek();
      // System.out.println(stack);

      if (curStatus.node == null) {
        stack.pop();
        continue;
      }

      if (curStatus.child == Child.leftChild) {
        answer.add(curStatus.node.value);
        curStatus.child = Child.rightChild;
        stack.push(new Status(Child.leftChild, curStatus.node.leftChild));
      } else {
        stack.pop();
        stack.push(new Status(Child.leftChild, curStatus.node.rightChild));
      }
    }

    return answer;
  }

  public ArrayList<DataType> iterationInorderTraverse() {
    ArrayList<DataType> answer = new ArrayList<>();
    MyStack<Status<DataType>> stack = new MyStack<>();

    stack.push(new Status<DataType>(Child.leftChild, root.rightChild));

    while (!stack.empty()) {
      Status<DataType> curStatus = stack.peek();
      // System.out.println(stack);

      if (curStatus.node == null) {
        stack.pop();
        continue;
      }

      if (curStatus.child == Child.leftChild) {
        curStatus.child = Child.rightChild;
        stack.push(new Status(Child.leftChild, curStatus.node.leftChild));
      } else {
        answer.add(curStatus.node.value);
        stack.pop();
        stack.push(new Status(Child.leftChild, curStatus.node.rightChild));
      }
    }

    return answer;
  }

  public ArrayList<DataType> iterationPostorderTraverse() {
    ArrayList<DataType> answer = new ArrayList<>();
    MyStack<Status<DataType>> stack = new MyStack<>();

    stack.push(new Status<DataType>(Child.leftChild, root.rightChild));

    while (!stack.empty()) {
      Status<DataType> curStatus = stack.peek();
      // System.out.println(stack);

      if (curStatus.node == null) {
        stack.pop();
        continue;
      }

      if (curStatus.child == Child.leftChild) {
        curStatus.child = Child.rightChild;
        stack.push(new Status(Child.leftChild, curStatus.node.leftChild));
      } else if (curStatus.child == Child.rightChild) {
        curStatus.child = Child.self;
        stack.push(new Status(Child.leftChild, curStatus.node.rightChild));
      } else {
        answer.add(curStatus.node.value);
        stack.pop();
      }
    }

    return answer;
  }
}
