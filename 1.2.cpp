/**
 * @author: 1190501001 李恩宇
 * @build_command: g++ -std=c++11 path/to/source.cpp
 * @description: 有一个包括 100 个元素的数组, 每个元素的值都是实数,
 * 请写出求最大元素的值及其位置的算法, 讨论它可能采取的数据结构.
 * @input: a line contains 100 real numbers
 * @output: first output the array consists of all elements; then output the
 * maximum element and its position.
 * @hint: solve 函数是解题核心函数. 可能采取的存储结构有多种:
 * - O(n) 做法
 * 1. 线性表 (顺序表或链表), 表中元素为对应位置上的实数本身. 只需扫一遍表,
 * 即可得到答案.
 * 2. 堆, 堆中元素为 key-value 键值对, 其中 key 是元素位置, value 是元素值. 以
 * key 为关键字, 将所有元素建立最大堆后, 堆顶元素即为答案.
 * - O(n lg n) 做法 (这么一道题真的用得到这么复杂的数据结构吗...)
 * 3. 二叉搜索树, 树中元素为 key-value 键值对, 以 key 为关键字. 插入所有节点后,
 * 最右侧的节点即为答案.
 * 4. 维护区间最大值的线段树或树状数组
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