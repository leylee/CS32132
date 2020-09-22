/**
 * @author: 1190501001 李恩宇
 * @build_command: g++ -std=c++11 path/to/source.cpp
 * @description: 设字符集为字符和数字的集合, 字符的顺序为A, B, C, …, Z, 0, 1, 2,
 * …, 9, 请将下列字符串按字典顺序排列、存储: PAB, 5C, PABC, CXY, CRSI, 7, B899,
 * B9, 并分析可以采取的存储方案.
 * @input: None
 * @output: print the original array and the sorted array.
 * @hint: solve 函数是解题核心函数. 使用 <string> 中的 std::string 存储字符串,
 * 利用 <vector> 中的 std::vector (顺序表) 存储所有字符串.
 * 若使用链表存储所有字符串, 可使用选择, 冒泡或插入排序.
 * 还可以使用二叉搜索树存储, 仍然可以保证字符串的字典序.
 * 使用 Trie 树, 可以大大降低时间和空间复杂度.
 */

#include <cctype>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

class MyString : public string {
 private:
  inline static int charcmp(int a, int b) {
    if (isdigit(a) && isalpha(b)) {
      return 1;
    } else if (isalpha(a) && isdigit(b)) {
      return -1;
    } else {
      return a - b;
    }
  }

 public:
  MyString(const string& str) : string(str) {}
  int compare(const string& str) const {
    auto i = begin(), j = str.begin();
    while (true) {
      if (i == end() && j == str.end()) {
        return 0;
      } else if (i == end()) {
        return -1;
      } else if (j == str.end()) {
        return 1;
      } else if (charcmp(*i, *j) != 0) {
        return charcmp(*i, *j);
      } else {
        ++i, ++j;
      }
    }
  }

  inline bool operator<(const string& rhs) const { return compare(rhs) < 0; }

  inline bool operator>(const string& rhs) const { return compare(rhs) > 0; }
};

template <typename T>
void printVector(const vector<T> v) {
  cout << '[';
  bool first = true;
  for (auto i = v.cbegin(); i != v.cend(); ++i) {
    if (first) {
      first = false;
    } else {
      cout << ", ";
    }
    cout << *i;
  }
  cout << ']' << endl;
}

void solve(vector<MyString>& array) {
  for (unsigned long i = 0; i < array.size(); ++i) {
    int minindex = i;
    for (unsigned long j = i + 1; j < array.size(); ++j) {
      if (array[j] < array[minindex]) {
        minindex = j;
      }
    }
    swap(array[i], array[minindex]);
  }
}

int main() {
  vector<string> stringArray = {"PAB",  "5C", "PABC", "CXY",
                                "CRSI", "7",  "B899", "B9"};
  vector<MyString> array(stringArray.begin(), stringArray.end());
  cout << "Original order: " << endl;
  printVector(array);
  solve(array);
  cout << "Sorted:" << endl;
  printVector(array);
  return 0;
}