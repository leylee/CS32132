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
};

template <typename T>
ostream& operator<<(ostream& os, const List<T>& list) {
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
  pt1 = pt2 = list.begin();
  while (true) {
    pt2 = pt2->next;
    if (pt2 == list.end()) {
      break;
    }
    pt2 = pt2->next;
    if (pt2 == list.end()) {
      break;
    }
    pt1 = pt1->next;
  }
  cout << "The element in the middle is " << pt1->value << " @" << pt1 << endl;
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