
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Product {
  public String name;
  public String brand;
  public double price;
  public int quantity;

}

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

class Inventory {
  private static final String DATA_FILE = "inventory.dat";
  private MyList<Product> list = new MyList<Product>();

  public static class OutOfStockException extends Exception {
    private static final long serialVersionUID = -5422073231991141527L;

    public OutOfStockException(String message) {
      super(message);
    }

    public OutOfStockException() {
      this("Cannot sell more goods than stock");
    }
  }

  static public class NegetiveQuantityException extends Exception {
    private static final long serialVersionUID = -7696763976553701780L;

    public NegetiveQuantityException(String message) {
      super(message);
    }

    public NegetiveQuantityException() {
      this("Quantity should be greater than 0");
    }
  }

  static public class ProductNotFoundException extends Exception {
    private static final long serialVersionUID = -23939467884069524L;

    public ProductNotFoundException(String message) {
      super(message);
    }

    public ProductNotFoundException() {
      this("Cannot find this product");
    }
  }

  public Product find(String name) {
    return findPos(name).getValue();
  }

  public MyList<Product>.MyNode findPos(String name) {
    MyList<Product>.MyNode result = null;
    for (MyList<Product>.MyNode node = list.begin(); node != list.end(); node = node.next) {
      if (node.getValue().name.equals(name)) {
        result = node;
        break;
      }
    }
    return result;
  }

  public Product purchase(String name, int quantity) throws ProductNotFoundException {
    Product product = find(name);
    if (product == null) {
      throw new ProductNotFoundException();
    }
    product.quantity += quantity;
    return product;
  }

  public Product sell(String name, int quantity) throws ProductNotFoundException, OutOfStockException {
    Product product = find(name);
    if (product == null) {
      throw new ProductNotFoundException();
    }
    if (product.quantity < quantity) {
      throw new OutOfStockException();
    }
    product.quantity -= quantity;
    return product;
  }

  public void save() throws IOException {
    File file = new File(DATA_FILE);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
    objectOutputStream.writeObject(list);
    objectOutputStream.close();
  }

  @SuppressWarnings("unchecked")
  public void loadFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
    File file = new File(DATA_FILE);
    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
    list = (MyList<Product>) objectInputStream.readObject();
    objectInputStream.close();
  }

  public MyList<Product> getList() {
    return list;
  }
}

class CommandLineIO {
  private final String FORMAT_ERROR_MESSAGE = "数据格式错误, 请重新输入:";
  private final String VALUE_ERROR_MESSAGE = "输入有误, 请重试";
  private BufferedReader br;

  public CommandLineIO(BufferedReader br) {
    this.br = br;
  }

  public int getInt() {
    while (true) {
      try {
        int result = Integer.valueOf(br.readLine());
        return result;
      } catch (NumberFormatException | IOException e) {
        System.out.println(FORMAT_ERROR_MESSAGE);
      }
    }
  }

  public double getDouble() {
    while (true) {
      try {
        double result = Integer.valueOf(br.readLine());
        return result;
      } catch (NumberFormatException | IOException e) {
        System.out.println(FORMAT_ERROR_MESSAGE);
      }
    }
  }

  public String getString() {
    while (true) {
      try {
        String result = br.readLine().strip();
        return result;
      } catch (IOException e) {
        System.out.println(FORMAT_ERROR_MESSAGE);
      }
    }
  }

  public String getName() {
    while (true) {
      String name = getString();
      if (name.length() != 0) {
        return name;
      }
      println(VALUE_ERROR_MESSAGE);
    }
  }

  public int getOpt(int max) {
    while (true) {
      int opt = getInt();
      if (opt >= 0 && opt <= max) {
        return opt;
      }
      println(VALUE_ERROR_MESSAGE);
    }
  }

  public int getQuantity() {
    while (true) {
      int quantity = getInt();
      if (quantity > 0) {
        return quantity;
      }
      println(VALUE_ERROR_MESSAGE);
    }
  }

  public double getPrice() {
    while (true) {
      double price = getDouble();
      if (price > 0.) {
        return price;
      }
      println(VALUE_ERROR_MESSAGE);
    }
  }

  public void pause() {
    System.out.println("请按回车继续...");
    try {
      br.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void clear() {
    final String CLEAR_COMMAND = System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0 ? "cmd.exe /c CLS"
        : "sh -c clear";
    try {
      Runtime.getRuntime().exec(CLEAR_COMMAND);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public <E> void println(E obj) {
    System.out.println(obj);
  }

  private void printTitle() {
    println(" - 商品名称  品牌  单价  数量");
  }

  public void println(Inventory inventory) {
    printTitle();
    int number = 0;
    for (MyList<Product>.MyNode node = inventory.getList().begin(); node != inventory.getList()
        .end(); node = node.next) {
      Product product = node.getValue();
      System.out.format("%i. %s  %s  %.2d %i\n", number++, product.name, product.brand, product.price,
          product.quantity);
    }
  }
}

public class Chapter_2_2 {
  static final String VALUE_ERROR_MESSAGE = "输入有误, 请重试";

  // private static void println(String obj) {
  // System.out.println(obj);
  // }

  // private static void println(int obj) {
  // System.out.println(obj);
  // }

  // private static void println(double obj) {
  // System.out.println(obj);
  // }

  // private static void println() {
  // System.out.println();
  // }

  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Inventory inventory = new Inventory();
    CommandLineIO io = new CommandLineIO(br);

    while (true) {
      // 营业即将开始
      io.println("营业即将开始, 是否加载已保存的数据? (0: 否; 1: 是; 2: 退出程序)");
      int opt = io.getOpt(2);
      if (opt == 2) {
        return;
      }
      if (opt == 1) {
        try {
          inventory.loadFromFile();
          io.println("成功载入已保存的数据");
        } catch (FileNotFoundException e) {
          io.println("历史数据文件不存在, 未载入");
        } catch (IOException | ClassNotFoundException e) {
          io.println("数据文件损坏, 未载入");
        }
      }

      // 营业中
      while (true) {
        io.println("功能列表:");
        io.println("0. 营业结束, 保存数据");
        io.println("1. 输出所有库存");
        io.println("2. 进货");
        io.println("3. 出货");
        io.println("4. 查询商品信息");
        io.println("5. 更新商品信息");

        opt = io.getOpt(5);

        if (opt == 0) {
          try {
            inventory.save();
            io.println("保存成功");
          } catch (IOException e) {
            e.printStackTrace();
            io.println("保存失败");
          }
          break;
        } else if (opt == 1) {
          io.println(inventory);
        } else if (opt == 2) {
          io.println(inventory);
        } else if (opt == 3) {
          Product newProduct = new Product();
          io.println("商品名称");
          newProduct.name = io.getName();
          io.println("进货数量");
          newProduct.quantity = io.getQuantity();
          try {
            inventory.purchase(newProduct.name, newProduct.quantity);
          } catch (Inventory.ProductNotFoundException e) {
            io.println("库存中没有此商品, 请补充商品信息");
          }
        }
      }
    }
  }
}