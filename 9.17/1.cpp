#include <iostream>

using namespace std;

template <class T> 
struct Node {
    T data;
    Node *next;
    Node() {
        next = nullptr;
    }

    Node(T t) {
        data = t;
        next = nullptr;
    }
};

typedef Node<T> NodeT;
typedef NodeT* NodeTptr;

template <class T>
struct List {
    int size;
    NodeTptr headptr, tailptr;

    List() {
        headptr = new NodeT();
        tailptr = new NodeT();
        headptr->next = tailptr;
        tailptr->next = nullptr;
        size = 0;
    }

    NodeTptr push_front(T t) {
        insert(headptr);
        return nodeptr;
    }

    NodeTptr insert(NodeTptr indexptr, T t) {
        NodeTptr newptr = new NodeT(t);
        newptr->next = indexptr->next;
        indexptr->next = newptr;
        return newptr;
    }

    NodeTptr begin() {
        return headptr;
    }

    NodeTptr end() {
        return tailptr();
    }
};

void solve(int k, List<int> &list) {
    NodeTptr pt1, pt2;
    pt1 = pt2 = list.begin();
    for (; pt1 < 2; ++i)
}

int main() {
    List<int> list;
    int n, k;
    cin >> n >> k;
    for (int i = 0; i < n; ++i) {
        list.push_front(i);
    }

    solve(k, list);
    return 0;
}