#include "sort.hpp"

#include <queue>

inline void swap(int *a, int *b) {
  int k = *a;
  *a = *b;
  *b = k;
}

void quick_sort(int *begin, int *end) {
  if (end - begin <= 1) {
    return;
  } else {
    int *l, *r, *m;
    m = begin;
    l = begin;
    r = end - 1;
    while (l < r) {
      while (l < r && *(l + 1) < *m) {
        ++l;
      }
      while (l < r && *r >= *m) {
        --r;
      }
      if (l >= r) {
        break;
      }
      ++l;
      swap(l, r);
    }

    swap(l, m);
    quick_sort(begin, l);
    quick_sort(l + 1, end);
  }
}

inline void push_down(int *heap, int index, int size) {
  while (true) {
    int l = index * 2 + 1;
    int r = l + 1;
    int max_i;

    if (l >= size) {
      return;
    } else if (r >= size) {
      max_i = l;
    } else {
      if (heap[l] > heap[r]) {
        max_i = l;
      } else {
        max_i = r;
      }
    }

    if (heap[max_i] > heap[index]) {
      int k = heap[max_i];
      heap[max_i] = heap[index];
      heap[index] = k;

      index = max_i;
    } else {
      return;
    }
  }
}

void heap_sort(int *begin, int *end) {
  int n = end - begin;
  int *array = begin;
  int *heap = array;

  for (int i = n - 1; i >= 0; --i) {
    push_down(heap, i, n);
  }

  for (int tail = n - 1; tail > 0; --tail) {
    int top = heap[0];
    heap[0] = heap[tail];
    heap[tail] = top;
    push_down(heap, 0, tail);
  }
}

void radix_sort(int *begin, int *end) {
  const int INT_SIZE = sizeof(int);
  int exp = 8;
  int radix = 1 << exp;
  int mask = radix - 1;
  using std::queue;

  queue<int> *last_trip = new queue<int>[radix];
  queue<int> *next_trip = new queue<int>[radix];

  for (int *p = begin; p != end; ++p) {
    last_trip[*p & mask].push(*p);
  }

  for (int trip = 1; trip < INT_SIZE; ++trip) {
    int cur_exp = exp * trip;
    for (int r = 0; r < radix; ++r) {
      queue<int> &q = last_trip[r];
      while (!q.empty()) {
        int item = q.front();
        q.pop();
        next_trip[(item >> cur_exp) & mask].push(item);
      }
    }
    queue<int> *tmp = last_trip;
    last_trip = next_trip;
    next_trip = tmp;
  }

  int *p = begin;
  for (int r = 0; r < radix; ++r) {
    queue<int> &q = last_trip[r];
    while (!q.empty()) {
      *p++ = q.front();
      q.pop();
    }
  }
}

void bubble_sort(int *begin, int *end) {
  for (int *head = begin; head < end; ++head) {
    bool swapped = false;
    for (int *bubble = end - 1; bubble > head; --bubble) {
      if (*bubble < *(bubble - 1)) {
        swapped = true;
        swap(bubble, bubble - 1);
      }
    }
    if (!swapped) {
      break;
    }
  }
}

void select_sort(int *begin, int *end) {
  for (int *tail = end - 1; tail > begin; --tail) {
    int *maxp = tail;
    for (int *cursor = begin; cursor < tail; ++cursor) {
      if (*cursor > *maxp) {
        maxp = cursor;
      }
    }
    swap(maxp, tail);
  }
}

void insert_sort(int *begin, int *end) {
  for (int *tail = begin + 1; tail < end; ++tail) {
    int *cursor;
    for (cursor = begin; cursor < tail && *cursor <= *tail; ++cursor)
      ;
    for (; cursor < tail; ++cursor) {
      swap(cursor, tail);
    }
  }
}
