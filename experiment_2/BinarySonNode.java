package experiment_2;

public class BinarySonNode<T> {
  protected T value;
  protected BinarySonNode<T> leftSon, rightSon;

  BinarySonNode() {
  }

  BinarySonNode(T value) {
    this.value = value;
  }

  BinarySonNode(BinarySonNode<T> leftSon, BinarySonNode<T> rightSon) {
    this.leftSon = leftSon;
    this.rightSon = rightSon;
  }

  BinarySonNode(T value, BinarySonNode<T> leftSon, BinarySonNode<T> rightSon) {
    this.leftSon = leftSon;
    this.rightSon = rightSon;
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public BinarySonNode<T> getLeftSon() {
    return leftSon;
  }

  public void setLeftSon(BinarySonNode<T> leftSon) {
    this.leftSon = leftSon;
  }

  public BinarySonNode<T> getRightSon() {
    return rightSon;
  }

  public void setRightSon(BinarySonNode<T> rightSon) {
    this.rightSon = rightSon;
  }
}
