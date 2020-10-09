#include <vector>

using namespace std;

template <typename T>
class Stack {
 private:
  int _size;
  T* list;

 public:
  Stack(int size) {
    _size = 0;
    list = new T[size];
  }

  ~Stack() { delete list; }

  void push(T s) { list[_size++] = s; }
  bool pop() {
    if (_size) {
      --_size;
      return true;
    } else {
      return false;
    }
  }
  T* top() {
    if (_size) {
      return list + _size - 1;
    } else {
      return nullptr;
    }
  }
  int size() { return _size; }
  T* container() { return list; }
  T* begin() { return list; }
  T* end() { return list + _size; }
};