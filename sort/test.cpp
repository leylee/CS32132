#include <cstdio>
#include <cstring>
#include <ctime>

#include "sort.hpp"
#define print(x)                  \
  printf("\n%-15s: %10.3fs\n", x, \
         (double)(end_clock - start_clock) / CLOCKS_PER_SEC)

using std::clock;
using std::memcpy;
using std::printf;
using std::scanf;

void print_array(int *begin, int *end) {
  putchar('[');
  while (begin != end) {
    printf("%i, ", *begin++);
  }
  putchar(']');
}

void test(int n, int *array, int *sorted, void (*sort)(int *begin, int *end),
          const char *name) {
  clock_t start_clock, end_clock;
  memcpy(sorted, array, sizeof(int) * n);
  start_clock = clock();
  sort(sorted, sorted + n);
  end_clock = clock();
  print(name);
  print_array(sorted, sorted + n);
  putchar('\n');
}

int main(void) {
  int n;
  while (scanf("%i", &n) == 1) {
    int *array = new int[n];
    int *sorted = new int[n];

    // clock_t start_clock, end_clock;

    for (int i = 0; i < n; ++i) {
      scanf("%i", array + i);
    }
    printf("n = %d\n", n);
    puts("Original array:");
    print_array(array, array + n);
    putchar('\n');

    test(n, array, sorted, radix_sort, "radix_sort");
    test(n, array, sorted, heap_sort, "heap_sort");
    test(n, array, sorted, quick_sort, "quick_sort");
    test(n, array, sorted, bubble_sort, "bubble_sort");
    test(n, array, sorted, selection_sort, "selection_sort");
    test(n, array, sorted, insertion_sort, "insertion_sort");
    test(n, array, sorted, merge_sort, "merge_sort");

    putchar('\n');

    delete array;
    delete sorted;
  }
}
