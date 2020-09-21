/**
 * @author: 1190501001 李恩宇
 * @description: 单链表逆置问题
 * @input: an integer n: length of the list
 * @output: 2 lines:
 *  The first line print the original list;
 *  The second line print the list after modified.
 * @hint: solve 函数是解题核心函数.
 *  从原链表头依次取元素, 并将新元素插入到原链表头部.
 */

#include <iostream>

using namespace std;

template <typename T>
struct Node {
  T value;
  Node* next;
  Node() { next = nullptr; }

  Node(const T& t) {
    value = t;
    next = nullptr;
  }
};

template <typename T>
struct List;
template <typename T>
ostream& operator<<(ostream& os, const List<T>& list);
template <typename T>
List<T>& solve(List<T>& list);

template <typename T>
struct List {
  typedef Node<T> NodeT;
  typedef NodeT* NodeTptr;
  int size;
  NodeTptr headptr;

  List() {
    headptr = new NodeT();
    headptr->next = headptr;
    size = 0;
  }

  NodeTptr push_front(const T& t) { return insert(headptr, t); }

  NodeTptr insert(NodeTptr indexptr, const T& t) {
    ++size;
    NodeTptr newptr = new NodeT(t);
    newptr->next = indexptr->next;
    indexptr->next = newptr;
    return newptr;
  }

  NodeTptr begin() const { return headptr->next; }

  NodeTptr end() const { return headptr; }

  bool pop_front() { return remove(headptr->next); }

  bool remove(NodeTptr removedptr) {
    if (removedptr == headptr) return false;
    NodeTptr nextptr = removedptr->next;
    removedptr->value = nextptr->value;
    removedptr->next = nextptr->next;
    delete nextptr;
    return true;
  }

  // void print() {
  //   for (NodeTptr ptr = begin(); ptr != end(); ptr = ptr->next) {
  //     cout << ptr->value << ' ';
  //   }
  //   cout << endl <<
  // }

  friend ostream& operator<<<T>(ostream& os, const List<T>& list);
  friend List<T>& solve<T>(List<T>& list);
};

template <typename T>
ostream& operator<<(ostream& os, const List<T>& list) {
  typedef Node<T>* NodeTptr;
  os << '[';
  bool first = true;
  for (NodeTptr ptr = list.begin(); ptr != list.end(); ptr = ptr->next) {
    if (!first) {
      os << ", ";
    } else {
      first = false;
    }

    os << ptr->value;
  }
  os << ']';
  return os;
}

template <typename T>
List<T>& solve(List<T>& list) {
  auto oldptr = list.begin();
  list.headptr->next = list.headptr;
  list.size = 0;
  while (oldptr != list.end()) {
    list.push_front(oldptr->value);
    auto delptr = oldptr;
    oldptr = oldptr->next;
    delete delptr;
  }
  return list;
}

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
  solve(list);
  cout << "The modified list is " << list << endl;

  return 0;
}