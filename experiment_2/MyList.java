package experiment_2;

import java.io.Serializable;
import java.util.Collection;

class MyList<T> implements Collection<T> {
  private static final long serialVersionUID = -8874735282081053376L;

  public class MyNode implements Serializable {
    private static final long serialVersionUID = 1579267065797586472L;
    private T value;
    public MyNode next, prev;

    public MyNode() {
      next = prev = null;
    }

    public MyNode(T t) {
      value = t;
      next = prev = null;
    }

    public T getValue() {
      return value;
    }

    public void setValue(T value) {
      this.value = value;
    }
  }

  private MyNode head;
  private int size;

  public MyList() {
    head = new MyNode();
    head.next = head.prev = head;
    size = 0;
  }

  public void insert(MyNode pos, T value) {
    MyNode newNode = new MyNode(value);
    ++size;
    newNode.next = pos;
    newNode.prev = pos.prev;
    newNode.prev.next = newNode;
    newNode.next.prev = newNode;
  }

  public void pushFront(T value) {
    insert(head.next, value);
  }

  public void pushBack(T value) {
    insert(head, value);
  }

  public boolean remove(MyNode pos) {
    if (pos == null || pos == head) {
      return false;
    } else {
      --size;
      pos.next.prev = pos.prev;
      pos.prev.next = pos.next;
      return true;
    }
  }

  public boolean popFront() {
    return remove(head.next);
  }

  public boolean popBack() {
    return remove(head.prev);
  }

  public int size() {
    return size;
  }

  public T front() {
    if (size == 0) {
      return null;
    }
    return head.next.value;
  }

  public T back() {
    if (size == 0) {
      return null;
    }
    return head.prev.value;
  }

  public MyNode begin() {
    return head.next;
  }

  public MyNode end() {
    return head;
  }

  public class Iterator implements java.util.Iterator<T> {
    private MyNode head, cur;

    Iterator(MyNode head) {
      this.head = head;
    }

    @Override
    public boolean hasNext() {
      return cur.next != head;
    }

    @Override
    public T next() {
      if (hasNext()) {
        cur = cur.next;
        return cur.value;
      } else {
        return null;
      }
    }
  }

  public Iterator iterator() {
    return new Iterator(head);
  }

  @Override
  public boolean add(T e) {
    pushBack(e);
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    for (T item : c) {
      add(item);
    }
    return true;
  }

  @Override
  public void clear() {
    while (!isEmpty()) {
      popBack();
    }
  }

  @Override
  public boolean contains(Object o) {
    for (T item : this) {
      if (item == o) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    next: for (Object o : c) {
      for (T item : this) {
        if (item == o) {
          continue next;
        }
      }
      return false;
    }
    return true;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean remove(Object o) {
    for (MyNode pos = begin(); pos != end(); pos = pos.next) {
      if (pos.value == o) {
        remove(pos);
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Object[] toArray() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T[] toArray(T[] a) {
    // TODO Auto-generated method stub
    return null;
  }
}