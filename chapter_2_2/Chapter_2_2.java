
/**
 * @author: 1190501001 李恩宇
 * @build_command: javac --release 11 path/to/source.java
 * @description: 链表的维护与文件形式的保存
 * 用链表结构的有序表表示某商场家电的库存模型.
 * 当有提货或进货时需要对该链表进行维护. 每个工作日结束之后,
 * 将该链表中的数据以文件形式保存, 每日开始营业之前,
 * 需将以文件形式保存的数据恢复成链表结构的有序表.
 * 链表结点的数据域包括家电名称, 品牌, 单价和数量, 以单价的升序体现链表的有序性.
 * 程序功能包括: 创建表, 营业开始 (读入文件恢复链表数据), 进货 (插入), 提货
 * (更新或删除), 查询信息, 更新信息, 营业结束 (链表数据存入文件) 等.
 * @input: 按照提示输入
 * @output:
 * @hint: 实现的功能:
 * 1. 从文件读入数据, 将数据保存在文件
 * 2. 商品的唯一标识符是商品名称, 按照价格升序排列
 * 3. 输入商品名称和进出货数量, 可以进货或出货. 若进货时商品不存在, 则要求补全商品信息; 若出货后库存清空, 则删除该商品信息
 * 4. 根据商品名称精确查询库存信息
 * 5. 更新已经存在的商品信息
 */

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

class Product implements Serializable {
  private static final long serialVersionUID = 5791402997806683378L;
  public String name;
  public String brand;
  public double price;
  public int quantity;
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

  static public class ProductExistsError extends Exception {
    private static final long serialVersionUID = -2464719080946839145L;

    public ProductExistsError(String message) {
      super(message);
    }

    public ProductExistsError() {
      this("Cannot find this product");
    }
  }

  public Product find(String name) {
    MyList<Product>.MyNode pos = findPos(name);
    return pos == null ? null : pos.getValue();
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

  public void add(Product product) throws ProductExistsError {
    MyList<Product>.MyNode node = list.begin();
    while (node != list.end()) {
      if (node.getValue().name.equals(product.name)) {
        throw new ProductExistsError();
      }
      if (node.getValue().price > product.price) {
        break;
      }
      node = node.next;
    }
    list.insert(node, product);
  }

  public void remove(String name) throws ProductNotFoundException {
    try {
      list.remove(findPos(name));
    } catch (NullPointerException e) {
      throw new ProductNotFoundException();
    }
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

  private int getInt() {
    while (true) {
      try {
        int result = Integer.valueOf(br.readLine());
        return result;
      } catch (NumberFormatException | IOException e) {
        System.out.println(FORMAT_ERROR_MESSAGE);
      }
    }
  }

  private double getDouble() {
    while (true) {
      try {
        double result = Double.valueOf(br.readLine());
        return result;
      } catch (NumberFormatException | IOException e) {
        System.out.println(FORMAT_ERROR_MESSAGE);
      }
    }
  }

  private String getString() {
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

  public int getOption(int max) {
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

  private void println(Product product, int number) {
    System.out.format("%d. %s  %s  %.2f  %d\n", number, product.name, product.brand, product.price, product.quantity);
  }

  public void println(Product product) {
    printTitle();
    println(product, 0);
  }

  public void println(Inventory inventory) {
    printTitle();
    int number = 0;
    for (MyList<Product>.MyNode node = inventory.getList().begin(); node != inventory.getList()
        .end(); node = node.next) {
      Product product = node.getValue();
      println(product, number++);
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
      int opt = io.getOption(2);
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
        io.println("0. 营业结束");
        io.println("1. 输出所有库存");
        io.println("2. 进货");
        io.println("3. 出货");
        io.println("4. 查询商品信息");
        io.println("5. 更新商品信息");

        opt = io.getOption(5);

        if (opt == 0) {
          io.println("保存数据? (0: 不保存; 1: 保存)");
          opt = io.getOption(1);
          if (opt == 1) {
            try {
              inventory.save();
              io.println("保存成功");
            } catch (IOException e) {
              e.printStackTrace();
              io.println("保存失败");
            }
          }
          break;
        } else if (opt == 1) {
          io.println(inventory);
        } else if (opt == 2) {
          Product newProduct = new Product();
          io.println("商品名称");
          newProduct.name = io.getName();
          io.println("进货数量");
          newProduct.quantity = io.getQuantity();
          try {
            inventory.purchase(newProduct.name, newProduct.quantity);
          } catch (Inventory.ProductNotFoundException e) {
            io.println("库存中没有此商品, 请补充商品信息");
            io.println("商品品牌");
            newProduct.brand = io.getName();
            io.println("商品单价");
            newProduct.price = io.getPrice();
            try {
              inventory.add(newProduct);
            } catch (Inventory.ProductExistsError e1) {
              e1.printStackTrace();
            }
          }
        } else if (opt == 3) {
          String name;
          int quantity;
          try {
            io.println("商品名称");
            name = io.getName();
            if (inventory.find(name) == null) {
              throw new Inventory.ProductNotFoundException();
            }
            io.println("商品数量");
            quantity = io.getQuantity();
            Product product = inventory.sell(name, quantity);
            io.println("出货成功");
            if (product.quantity == 0) {
              inventory.remove(name);
            }
          } catch (Inventory.ProductNotFoundException e) {
            io.println("未找到该商品");
          } catch (Inventory.OutOfStockException e) {
            io.println("库存不足");
          }
        } else if (opt == 4) {
          String name;
          try {
            io.println("商品名称");
            name = io.getName();
            Product product = inventory.find(name);
            if (product == null) {
              throw new Inventory.ProductNotFoundException();
            }
            io.println(product);
          } catch (Exception e) {
            io.println("未找到该商品");
          }
        } else if (opt == 5) {
          try {
            String name;
            io.println("原商品名称");
            name = io.getName();
            Product product = inventory.find(name);
            if (product == null) {
              throw new Inventory.ProductNotFoundException();
            }
            inventory.remove(name);
            io.println("新商品名称");
            product.name = io.getName();
            io.println("新商品品牌");
            product.brand = io.getName();
            io.println("新商品单价");
            product.price = io.getPrice();
            io.println("新商品数量");
            product.quantity = io.getQuantity();
            inventory.add(product);
            io.println("商品信息更新成功");
          } catch (Inventory.ProductNotFoundException e) {
            io.println("未找到该商品");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}