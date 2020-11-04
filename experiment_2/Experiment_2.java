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
    for (int i = 0; i < size; ++i) {
      numberSequence.add(i + 1);
    }
    for (int i = 0; i <= size * 2; ++i) {
      preorderSequence.add(sc.nextInt());
    }

    tree = new BinaryLinkedTree<>(numberSequence, preorderSequence);
    System.out.println("可视化输出树的结构");
    tree.printTree();
    System.out.println("递归前中后序遍历");
    System.out.println(tree.recursionPreorderTraverse());
    System.out.println(tree.recursionInorderTraverse());
    System.out.println(tree.recursionPostorderTraverse());
    System.out.println("非递归前中后序遍历");
    System.out.println(tree.iterationPreorderTraverse());
    System.out.println(tree.iterationInorderTraverse());
    System.out.println(tree.iterationPostorderTraverse());
    System.out.println("是否是满二叉树: " + tree.checkComplete());

    BinaryLinkedParentTree<Integer> parentTree = new BinaryLinkedParentTree<>(numberSequence, preorderSequence);
    // parentTree.printTree();
    int id1, id2;
    while (true) {
      System.out.print("查找公共祖先: ");
      id1 = sc.nextInt();
      id2 = sc.nextInt();
      System.out.println("最近公共祖先是");
      System.out.println(parentTree.findCommonAncestor(id1, id2));
    }
  }
}

// test string:
// 10
// 1 2 4 8 0 0 9 0 0 5 0 0 3 6 0 10 0 0 7 0 0