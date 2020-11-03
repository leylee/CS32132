package experiment_2;

import java.util.ArrayList;
import java.util.Scanner;

public class Experiment_2 {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int size = sc.nextInt();
    BinaryLinkedTree<Integer> tree;

    ArrayList<Integer> numberSequence = new ArrayList<>();
    ArrayList<Integer> preorderSequence = new ArrayList<>();
    for (int i = 0; i <= size; ++i) {
      numberSequence.add(i);
    }
    for (int i = 0; i <= size * 2; ++i) {
      preorderSequence.add(sc.nextInt());
    }

    tree = new BinaryLinkedTree<>(numberSequence, preorderSequence);
    tree.printTree();
    System.out.println(tree.recursionPreorderTraverse());
    System.out.println(tree.recursionInorderTraverse());
    System.out.println(tree.recursionPostorderTraverse());
  }
}

// test string:
// 10
// 1 2 4 8 0 0 9 0 0 5 0 0 3 6 0 10 0 0 7 0 0