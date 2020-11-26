#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(void) {
  int n;
  srand(time(NULL));
  while (scanf("%i", &n) == 1) {
    printf("%d\n", n);
    int cnt = 0;
    while (n--) {
      if (cnt == 100) {
        cnt = 0;
        putchar('\n');
      }
      printf("%d ", rand());
      ++cnt;
    }
    putchar('\n');
  }
}