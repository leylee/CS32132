#include <stdio.h>

int main(void) {
  int n;
  while (scanf("%i", &n) == 1) {
    printf("%d\n", n);
    int cnt = 0;
    while (n--) {
      if (cnt == 100) {
        cnt = 0;
        putchar('\n');
      }
      printf("%d ", n);
      ++cnt;
    }
    putchar('\n');
  }
}