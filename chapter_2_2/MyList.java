import java.io.Serializable;

class MyList<T> implements Serializable {
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
      pos.next.prev = pos.prev;
      pos.prev.next = pos.next;
      return true;
    }
  }

  public boolean popFront(MyNode pos) {
    return remove(head.next);
  }

  public boolean popBack(MyNode pos) {
    return remove(head.prev);
  }

  public int getSize() {
    return size;
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
}