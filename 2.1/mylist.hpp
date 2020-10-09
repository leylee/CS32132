#include <iostream>

using namespace std;

template <typename T>
struct Node;
template <typename T>
struct List;

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

  friend ostream& operator<<(ostream& os, const List<T>& list) {
    os << '[';
    bool first = true;
    for (auto ptr = list.begin(); ptr != list.end(); ptr = ptr->next) {
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

  void reverse() {
    auto oldptr = begin();
    headptr->next = headptr;
    size = 0;
    while (oldptr != end()) {
      push_front(oldptr->value);
      auto delptr = oldptr;
      oldptr = oldptr->next;
      delete delptr;
    }
  }
};