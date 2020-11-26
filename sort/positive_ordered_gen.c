#include <stdio.h>

int main(void) {
  int n;
  while (scanf("%i", &n) == 1) {
    printf("%d\n", n);
    int cnt = 0;
    int i;
    for (i = 0; i < n; ++i) {
      if (cnt == 100) {
        cnt = 0;
        putchar('\n');
      }
      printf("%d ", i);
      ++cnt;
    }
    putchar('\n');
  }
}