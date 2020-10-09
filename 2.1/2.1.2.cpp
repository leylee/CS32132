/**
 * @author: 1190501001 李恩宇
 * @build command: g++ -std=c++11 2.1.2.cpp
 * @description: 单链表逆置问题
 * @input: an integer n: length of the list
 * @output: 2 lines:
 *  The first line print the original list;
 *  The second line print the list after modified.
 * @hint: solve 函数是解题核心函数.
 *  从原链表头依次取元素, 并将新元素插入到原链表头部.
 */

#include <iostream>

#include "mylist.hpp"

using namespace std;

typedef int Type;
typedef Node<Type> NodeT;
typedef NodeT* NodeTptr;

int main() {
  typedef int ElementType;

  int n;
  List<ElementType> list;
  cin >> n;
  for (int i = n - 1; i >= 0; --i) {
    list.push_front(ElementType(i));
  }

  cout << "The original list is " << list << endl;
  list.reverse();
  cout << "The modified list is " << list << endl;

  return 0;
}