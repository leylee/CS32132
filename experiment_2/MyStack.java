package experiment_2;

public class MyStack<T> extends MyList<T> {

  private static final long serialVersionUID = 2074180720864108441L;

  public T push(T item) {
    pushBack(item);
    return item;
  }

  public T peek() {
    return back();
  }

  public T pop() {
    T item = peek();
    popBack();
    return item;
  }

  public boolean empty() {
    return isEmpty();
  }
}
