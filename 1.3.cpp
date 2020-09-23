/**
 * @author: 1190501001 李恩宇
 * @build_command: g++ -std=c++11 path/to/source.cpp
 * @description: 在有 n 个选手 P1, P2, P3, …, Pn参加的单循环赛中,
 * 每对选手之间非胜即负. 现要求求出一个选手序列: P1’, P2’, P3’, …, Pn’, 其满足
 * Pi’胜 Pi+1’ (i=1,… ,n-1).
 * @input: Line 1: an integer n, showing that there are n players.
 * The following n * (n - 1) / 2 lines: two integers a, b showing a beats b.
 * @output: An eligible integer sequence.
 * @hint: solve 函数是解题核心函数.
 */

#include <bitset>
#include <cassert>
#include <iostream>
#include <vector>

using namespace std;

vector<bool> mark;
vector<int> answer;
vector<vector<bool>> graph;
int n;

bool check() {
  for (auto i = answer.begin(); i + 1 != answer.end(); ++i) {
    if (!graph[*i][*(i + 1)]) {
      return false;
    }
  }
  return true;
}

bool dfs(int depth = 0) {
  if (depth == n) {
    return check();
  } else {
    for (int i = 1; i <= n; ++i) {
      if (mark[i] == false) {
        answer[depth] = i;
        mark[i] = true;
        if (dfs(depth + 1)) return true;
        mark[i] = false;
      }
    }
    return false;
  }
}

void solve() {
  if (dfs()) {
    for (auto i = answer.begin(); i != answer.end(); ++i) {
      cout << *i << ' ';
    }
    cout << endl;
  } else {
    cout << "Invalid data" << endl;
  }
}

int main() {
  cin >> n;
  mark.resize(n + 1, false);
  answer.resize(n);
  graph = vector<vector<bool>>(n + 1, vector<bool>(n + 1, false));
  for (int i = 0; i < n * (n - 1) / 2; ++i) {
    int a, b;
    cin >> a >> b;
    assert(a >= 1 && a <= n && b >= 1 && b <= n);
    graph[a][b] = true;
  }
  solve();
  return 0;
}