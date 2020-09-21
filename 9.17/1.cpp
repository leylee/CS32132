#include <iostream>

using namespace std;

template <class T>
struct Node {
    T data;
    Node* next;
    Node() { next = nullptr; }

    Node(T t)
    {
        data = t;
        next = nullptr;
    }
};

template <class T>
struct List {
    typedef Node<T> NodeT;
    typedef NodeT* NodeTptr;
    int size;
    NodeTptr headptr;

    List()
    {
        headptr = new NodeT();
        headptr->next = headptr;
        size = 0;
    }

    NodeTptr push_front(T t) { return insert(headptr, t); }

    NodeTptr insert(NodeTptr indexptr, T t)
    {
        ++size;
        NodeTptr newptr = new NodeT(t);
        newptr->next = indexptr->next;
        indexptr->next = newptr;
        return newptr;
    }

    NodeTptr begin() { return headptr->next; }

    NodeTptr end() { return headptr; }

    NodeTptr pop_front() { return }
};

typedef int Type;
typedef Node<Type> NodeT;
typedef NodeT* NodeTptr;

void solve(int k, List<int>& list)
{
    NodeTptr pt1, pt2;
    bool illegal;

    // find the k-th last element
    pt1 = pt2 = list.begin();
    illegal = false;
    for (int i = 0; i < k; ++i) {
        if (pt2 == list.end()) {
            illegal = true;
            break;
        }
        pt2 = pt2->next;
    }
    if (illegal) {
        cout << "The value k is illegal" << endl;
    } else {
        while (pt2 != list.end()) {
            pt1 = pt1->next;
            pt2 = pt2->next;
        }
        cout << "The k-th last element is " << pt1->data << " @" << pt1 << endl;
    }

    // find the element in the middle
}

int main()
{
    List<int> list;
    int n, k;
    cin >> n >> k;
    for (int i = n - 1; i >= 0; --i) {
        list.push_front(Type(i));
    }

    solve(k, list);
    return 0;
}