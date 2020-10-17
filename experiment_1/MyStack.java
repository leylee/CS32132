import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class MyStack<T> extends ArrayList<T> {

  private static final long serialVersionUID = 2074180720864108441L;

  public T push(T item) {
    add(item);
    return item;
  }

  public T peek() {
    return get(size() - 1);
  }

  public T pop() {
    T item = peek();
    remove(size() - 1);
    return item;
  }

  public boolean empty() {
    return isEmpty();
  }
}
