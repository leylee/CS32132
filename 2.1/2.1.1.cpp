/**
 * @author: 1190501001 李恩宇
 * @compile: g++ -std=c++11 2.1.1.cpp
 * @description: 已知一个单链表,
 *  1. 求倒数第k个元素;
 *  2. 求中点元素.
 * @input: 2 integers in a single line: n, k, seperated with space
 *  n: length of the list;
 *  k: the k-th last element
 * @output: 3 lines:
 *  The first line print all elements in the list;
 *  The second line print the k-th last element;
 *  The third line print the element in the middle;
 * @hint: solve 函数是解题核心函数.
 *  使用两个指针 pt1 和 pt2, 最初指向链表第一个元素:
 *  1. 让后一个指针先移动 k 次, 两个指针再一起移动, 直到 pt2 超出链表末尾;
 *  2. pt1 自增 1, pt2 自增 2, 直到 pt2 超出链表末尾;
 *  此时 pt1 即为答案.
 */

#include <iostream>

#include "mylist.hpp"

using namespace std;

void solve(int k, List<int>& list) {
  bool illegal;

  cout << "The elements are " << list << endl;

  // find the k-th last element
  auto pt1 = list.begin(), pt2 = list.begin();
  illegal = false;
  for (int i = 0; i < k; ++i) {
    if (pt2 == list.end()) {
      illegal = true;
      break;
    }
    pt2 = pt2->next;
  }
  if (illegal) {
    cout << "The value k is illegal" << endl;
  } else {
    while (pt2 != list.end()) {
      pt1 = pt1->next;
      pt2 = pt2->next;
    }
    cout << "The k-th last element is " << pt1->value << " @" << pt1 << endl;
  }

  // find the element in the middle
  cout << "The element in the middle is ";
  pt1 = pt2 = list.begin();
  while (true) {
    pt2 = pt2->next;
    if (pt2 == list.end()) {
      cout << pt1->value << " @" << pt1 << endl;
      break;
    }
    pt2 = pt2->next;
    if (pt2 == list.end()) {
      cout << pt1->value << " @" << pt1 << " and " << pt1->next->value << " @"
           << pt1->next << endl;
      break;
    }
    pt1 = pt1->next;
  }
}

int main() {
  typedef int ElementType;
  List<ElementType> list;
  int n, k;
  cin >> n >> k;
  for (int i = n - 1; i >= 0; --i) {
    if (!list.push_front(ElementType(i))) {
      cout << "Failed to allocate memory" << endl;
      return -1;
    }
  }

  solve(k, list);
  return 0;
}