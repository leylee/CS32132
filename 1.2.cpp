/**
 * @author: 1190501001 李恩宇
 * @build_command: g++ -std=c++11 path/to/source.cpp
 * @description: 有一个包括 100 个元素的数组, 每个元素的值都是实数,
 * 请写出求最大元素的值及其位置的算法, 讨论它可能采取的数据结构.
 * @input: a line contains 100 real numbers
 * @output: first output the array consists of all elements; then output the
 * maximum element and its position.
 * @hint: solve 函数是解题核心函数.
 */

#include <iostream>
#include <vector>

using namespace std;

const int n = 100;

void solve(const vector<double>& array) {
  double maxValue = array[0];
  int maxPosition = 0;

  for (int i = 1; i < array.size(); ++i) {
    if (array[i] > maxValue) {
      maxValue = array[i];
      maxPosition = i;
    }
  }

  cout << "The maximum value is " << maxValue << " @" << maxPosition << endl;
}

int main() {
  vector<double> array;

  for (int i = 0; i < n; ++i) {
    double value;
    cin >> value;
    array.push_back(value);
  }

  cout << "The array is:" << endl;
  for (auto i = array.begin(); i != array.end(); ++i) {
    cout << *i << ' ';
  }
  cout << endl;

  solve(array);
  return 0;
}