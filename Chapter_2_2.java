class Product {
  public String name;
  public String brand;
  public int price;
  public int quantity;
}

class MyList<T> {
  public class MyNode {
    public T value;
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
  }

  private MyNode head;
  private int size;

  public MyList() {
    head = new MyNode();
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

class Inventory {
  MyList<Product> inventory = new MyList<Product>();

  public Product purchase(String name, int quantity) throws Exception {
    if (quantity <= 0) {
      throw new Exception("Quantity should be greater than 0");
    }
    for (MyList<Product>.Iterator iterator = inventory.iterator(); iterator.hasNext();) {
      Product product = iterator.next();
      if (product.name.equals(name)) {
        product.quantity += quantity;
        return product;
      }
    }
    throw new Exception("Cannot find this product");
  }

  public Product sell(String name, int quantity) throws Exception {
    if (quantity <= 0) {
      throw new Exception("Quantity should be greater than 0");
    }
    for (MyList<Product>.Iterator iterator = inventory.iterator(); iterator.hasNext();) {
      Product product = iterator.next();
      if (product.name.equals(name)) {
        if (product.quantity < quantity) {
          throw new Exception("Cannot sell more goods than stock");
        }
        product.quantity += quantity;
        return product;
      }
    }
    throw new Exception("Cannot find this product");
  }
}

public class Chapter_2_2 {
  public static void main(String[] args) {

  }
}