import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

class ExpressionUtil {
  // 原始输入表达式
  private String string;
  // 经过分割的表达式, 每个 String 存储一个数或运算符
  private ArrayList<String> strs;
  // 表达式是否合法
  boolean illegal = true;

  // 用于标记表达式下一个元素类型的值
  final int LEFT_BRACKET = 0b0000001;
  final int RIGHT_BRACKET = 0b0000010;
  final int END = RIGHT_BRACKET; // 到达右大括号和到达表达式末尾是同一个类型
  final int OPERATORS = 0b0000100;
  final int NUMBERS = 0b0001000;
  // 合法的二元运算符
  private static final Set<Character> operators = new TreeSet<>(Arrays.asList('+', '-', '*', '/', '%'));

  /**
   * 匹配表达式, 并将表达式分割存入 strs
   * 
   * @param index 当前匹配到的位置
   * @param type  可以接受的类型
   * @return
   * @throws Exception
   */
  private int match() throws Exception {
    int type = NUMBERS | LEFT_BRACKET;
    int i = 0;
    int leftBracketsCount = 0;

    while (true) {
      // 去除前导空白字符
      while (i < string.length() && string.charAt(i) == ' ') {
        ++i;
      }

      // 如果达到了字符串末尾
      if (i == string.length()) {
        if ((type & END) != 0 && leftBracketsCount == 0) {
          return i;
        } else {
          throw new Exception("Expression ended unexpectedly");
        }
      }

      // 如果当前位置可以是左括号
      if ((type & LEFT_BRACKET) != 0) {
        if (string.charAt(i) == '(') {
          strs.add("(");
          ++i;
          ++leftBracketsCount;
          type = LEFT_BRACKET | NUMBERS | RIGHT_BRACKET;
          continue;
        }
      }

      // 如果当前位置可以是右括号
      if ((type & RIGHT_BRACKET) != 0) {
        if (string.charAt(i) == ')') {
          if (leftBracketsCount == 0) {
            throw new Exception(String.format("Closing brackets doesn't match opening brackets in position %i", i));
          }
          strs.add(")");
          ++i;
          --leftBracketsCount;
          type = END | RIGHT_BRACKET | OPERATORS;
          continue;
        }
      }

      // 如果当前位置可以是运算符
      if ((type & OPERATORS) != 0) {
        if (operators.contains(string.charAt(i))) {
          strs.add(String.valueOf(string.charAt(i)));
          ++i;
          type = LEFT_BRACKET | NUMBERS;
          continue;
        } else {
          throw new Exception(String.format("Wrong or no operator in position %i", i));
        }
      }

      // 如果当前位置可以是数字
      if ((type & NUMBERS) != 0) {
        String number = "";
        if (string.charAt(i) == '-') {
          number = "-";
          ++i;
        }
        while (i < string.length() && string.charAt(i) == ' ') {
          ++i;
        }
        if (i == string.length()) {
          throw new Exception("Expression ended unexpectedly");
        }
        boolean decimal = false;
        while (true) {
          char c = string.charAt(i);
          if (c == '.') {
            if (decimal == true) {
              throw new Exception(String.format("Unexpected decimal in position %i", i));
            } else {
              number += c;
            }
          } else if (Character.isDigit(c)) {
            number += c;
          } else {
            break;
          }
          ++i;
        }
        type = END | RIGHT_BRACKET | OPERATORS;
      }
    }
  }

  /**
   * 构造函数, 同时判断表达式是否合法
   * 
   * @param expression
   */
  public ExpressionUtil(String expression) throws Exception {
    string = expression;
    match();
  }
}

public class Experiment_1 {
  public static void main(String[] args) {
    try {
      System.out.println(new ExpressionUtil("1 + 2 - 3 * (345 + 789)"));
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
