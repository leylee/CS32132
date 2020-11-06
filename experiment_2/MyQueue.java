package experiment_2;

public class MyQueue<T> extends MyList<T> {
  public T push(T item) {
    pushFront(item);
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
