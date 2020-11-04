package experiment_2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class BinaryLinkedParentTree<DataType extends Comparable<? super DataType>> implements Serializable {

  private static final long serialVersionUID = -2176296641657153564L;

  private enum Child {
    leftChild, rightChild, self
  };

  private class BinaryLinkedNode<T extends Comparable<? super T>> {
    T value;
    BinaryLinkedNode<T> leftChild, rightChild, parent;
    int depth;

    BinaryLinkedNode() {
    }

    BinaryLinkedNode(T value) {
      this.value = value;
    }

    BinaryLinkedNode(T value, BinaryLinkedNode<T> parent, int depth) {
      this.value = value;
      this.parent = parent;
      this.depth = depth;
    }

    BinaryLinkedNode(T value, BinaryLinkedNode<T> leftSon, BinaryLinkedNode<T> rightSon) {
      this(value);
      this.leftChild = leftSon;
      this.rightChild = rightSon;
    }

    @Override
    public String toString() {
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

  private ArrayList<BinaryLinkedNode<DataType>> idToNode;

  public BinaryLinkedParentTree(ArrayList<DataType> datas, ArrayList<Integer> preorderSequence) {

    MyStack<Status<DataType>> stack = new MyStack<>();
    BinaryLinkedNode<DataType> head = new BinaryLinkedNode<>(null, null, 0);
    idToNode = new ArrayList<>(Collections.nCopies(datas.size(), null));
    stack.push(new Status<>(Child.rightChild, head));

    for (int curID : preorderSequence) {
      Status<DataType> curStatus = stack.peek();
      BinaryLinkedNode<DataType> nextNode;

      if (curID == 0) {
        nextNode = null;
      } else {
        nextNode = new BinaryLinkedNode<>(datas.get(curID - 1), curStatus.node, curStatus.node.depth + 1);
        idToNode.set(curID - 1, nextNode);
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
        stack.push(new Status<>(Child.leftChild, nextNode));
      }
    }

    root = head.rightChild;
    root.parent = null;
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
    printTree(root, 0);
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
    return recursionPreorderTraverse(root);
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
    return recursionPostorderTraverse(root);
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
    return recursionInorderTraverse(root);
  }

  public ArrayList<DataType> iterationPreorderTraverse() {
    ArrayList<DataType> answer = new ArrayList<>();
    MyStack<Status<DataType>> stack = new MyStack<>();

    stack.push(new Status<DataType>(Child.leftChild, root));

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
        stack.push(new Status<>(Child.leftChild, curStatus.node.leftChild));
      } else {
        stack.pop();
        stack.push(new Status<>(Child.leftChild, curStatus.node.rightChild));
      }
    }

    return answer;
  }

  public ArrayList<DataType> iterationInorderTraverse() {
    ArrayList<DataType> answer = new ArrayList<>();
    MyStack<Status<DataType>> stack = new MyStack<>();

    stack.push(new Status<DataType>(Child.leftChild, root));

    while (!stack.empty()) {
      Status<DataType> curStatus = stack.peek();
      // System.out.println(stack);

      if (curStatus.node == null) {
        stack.pop();
        continue;
      }

      if (curStatus.child == Child.leftChild) {
        curStatus.child = Child.rightChild;
        stack.push(new Status<>(Child.leftChild, curStatus.node.leftChild));
      } else {
        answer.add(curStatus.node.value);
        stack.pop();
        stack.push(new Status<>(Child.leftChild, curStatus.node.rightChild));
      }
    }

    return answer;
  }

  public ArrayList<DataType> iterationPostorderTraverse() {
    ArrayList<DataType> answer = new ArrayList<>();
    MyStack<Status<DataType>> stack = new MyStack<>();

    stack.push(new Status<DataType>(Child.leftChild, root));

    while (!stack.empty()) {
      Status<DataType> curStatus = stack.peek();
      // System.out.println(stack);

      if (curStatus.node == null) {
        stack.pop();
        continue;
      }

      if (curStatus.child == Child.leftChild) {
        curStatus.child = Child.rightChild;
        stack.push(new Status<>(Child.leftChild, curStatus.node.leftChild));
      } else if (curStatus.child == Child.rightChild) {
        curStatus.child = Child.self;
        stack.push(new Status<>(Child.leftChild, curStatus.node.rightChild));
      } else {
        answer.add(curStatus.node.value);
        stack.pop();
      }
    }

    return answer;
  }

  private static class heightResult {
    int min, max;
    boolean legal;

    heightResult(int min, int max, boolean legal) {
      this.max = max;
      this.min = min;
      this.legal = legal;
    }

    boolean illegal() {
      return !legal;
    }

    boolean legal() {
      return legal;
    }

    @Override
    public String toString() {
      return "heightResult [legal=" + legal + ", max=" + max + ", min=" + min + "]";
    }
  }

  private heightResult checkComplete(BinaryLinkedNode<DataType> node, int depth) {
    if (node == null) {
      return new heightResult(depth, depth, true);
    }

    heightResult leftResult = checkComplete(node.leftChild, depth + 1);
    heightResult rightResult = checkComplete(node.rightChild, depth + 1);

    // System.out.println(node.toString() + leftResult + rightResult);

    if (leftResult.illegal() || rightResult.illegal() || leftResult.max - rightResult.min > 1
        || leftResult.min < rightResult.max) {
      return new heightResult(0, 0, false);
    } else {
      return new heightResult(rightResult.min, leftResult.max, true);
    }
  }

  public boolean checkComplete() {
    return checkComplete(root, 0).legal();
  }

  public MyList<DataType> findCommonAncestor(int id1, int id2) {
    MyList<DataType> result = new MyList<>();

    BinaryLinkedNode<DataType> node1 = idToNode.get(id1 - 1), node2 = idToNode.get(id2 - 1);
    // System.out.println(node1.toString() + node2.toString());
    while (node1.depth > node2.depth) {
      node1 = node1.parent;
    }
    while (node2.depth > node1.depth) {
      node2 = node2.parent;
    }
    while (node1 != node2) {
      node1 = node1.parent;
      node2 = node2.parent;
    }
    while (node1 != null) {
      // System.out.println("a");
      result.pushFront(node1.value);
      node1 = node1.parent;
      node2 = node2.parent;
    }

    return result;
  }
}
