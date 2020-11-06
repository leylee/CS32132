package chapter_3_2_static;

import java.util.ArrayList;

public class MyPriorityQueue<T extends Comparable<? super T>> {
  private ArrayList<T> data;

  MyPriorityQueue() {
    data = new ArrayList<>();
    data.add(null);
  }

  MyPriorityQueue(ArrayList<T> array) {
    this();
    for (T e : array) {
      offer(e);
    }
  }

  public boolean offer(T e) {
    int p = data.size();
    data.add(e);
    while (p > 1 && data.get(p / 2).compareTo(data.get(p)) > 0) {
      T tmp = data.get(p / 2);
      data.set(p / 2, data.get(p));
      data.set(p, tmp);
      p /= 2;
    }
    return true;
  }

  public T poll() {
    T result = data.get(1);

    if (data.size() > 2) {
      data.set(1, data.remove(data.size() - 1));
      int p = 1;
      while (true) {
        int ap, lp = p * 2, rp = p * 2 + 1;
        if (lp >= data.size()) {
          break;
        }
        T as, ls, rs, cs = data.get(p);

        if (rp >= data.size()) {
          ap = lp;
          as = data.get(lp);
        } else {
          ls = data.get(lp);
          rs = data.get(rp);
          if (ls.compareTo(rs) < 0) {
            ap = lp;
            as = ls;
          } else {
            ap = rp;
            as = rs;
          }
        }

        if (as.compareTo(cs) < 0) {
          data.set(p, as);
          data.set(ap, cs);
          p = ap;
        } else {
          break;
        }
      }
    } else {
      data.remove(data.size() - 1);
    }

    return result;
  }

  public boolean empty() {
    return data.size() <= 1;
  }

  public int size() {
    return data.size() - 1;
  }
}
