/**
 * @author: 1190501001 李恩宇
 * @build_command: g++ -std=c++11 path/to/source.cpp
 * @description: 一个迷宫可用上图所示方阵 [m, n] 表示, 0 表示能通过, 1
 * 表示不能通过. 现假设耗子从左上角 [1, 1] 进入迷宫, 编写算法, 寻求一条从右下角
 * [m, n] 出去的路径.
 * @input: 2 integers n, m in line 1 showing that the maze is n x m;
 * then the following n lines, in each line there are a string of m 0 or 1s
 * representing the map.
 * @output: if there is a route, output all points on the route;
 * if not, print "The route doesn't exist."
 * @hint: Solution 类是算法核心.
 */

#include <iostream>
#include <vector>

using namespace std;

struct Point {
  int x, y;

  Point() {}
  Point(int _x, int _y) : x(_x), y(_y) {}

  bool operator==(const Point& p) const { return p.y == y && p.x == x; }

  Point operator+(const Point& p) const { return Point(p.x + x, p.y + y); }
};

struct Status {
  Point pos;
  int dir;

  Status() {}
  Status(int _x, int _y, int _dir = 0) : pos(_x, _y), dir(_dir) {}
  Status(Point p, int _dir = 0) : pos(p), dir(_dir) {}
};

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

void solve(int n, int m, Point s, Point e, vector<vector<int>>& map) {
  Stack<Status> stack(n * m);
  vector<vector<int>> visited(n + 2, vector<int>(m + 2, 0));
  const Point dirs[8] = {{-1, 0}, {-1, 1}, {0, 1},  {1, 1},
                         {1, 0},  {1, -1}, {0, -1}, {-1, -1}};

  stack.push(Status(s, 0));
  while (stack.size()) {
    Status* top = stack.top();
    if (top->pos == e) {
      break;
    } else if (top->dir == 8) {
      stack.pop();
    } else {
      Point nxtPoint = top->pos + dirs[top->dir++];
      if (!(visited[nxtPoint.x][nxtPoint.y] || map[nxtPoint.x][nxtPoint.y])) {
        visited[nxtPoint.x][nxtPoint.y] = 1;
        stack.push(Status(nxtPoint));
      }
    }
  }

  if (stack.size()) {
    for (auto i = stack.begin(); i != stack.end(); ++i) {
      cout << '(' << i->pos.x << ", " << i->pos.y << ')' << endl;
    }
  } else {
    cout << "The route doesn't exist." << endl;
  }
}

int main() {
  int n, m;
  cin >> n >> m;
  Point begin(1, 1), end(n, m);
  // cin >> begin.x >> begin.y >> end.x >> end.y;

  vector<vector<int>> map(n + 2, vector<int>(m + 2, 1));
  for (int i = 1; i <= n; ++i) {
    for (int j = 1; j <= m; ++j) {
      int grid;
      scanf("%1i", &grid);
      map[i][j] = grid;
    }
  }
  solve(n, m, begin, end, map);

  return 0;
}