// package experiment_1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

class ExpressionUtil {
  // 原始输入表达式
  protected String string;
  // 经过分割的表达式, 每个 String 存储一个数或运算符
  protected ArrayList<String> strs = new ArrayList<>();
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

  // 各个运算符优先级

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
    // 记录当前括号层数
    int leftBracketsCount = 0;

    // 按照从前到后的顺序, 判断下一个元素是否可匹配该类型. 若可匹配, 则直接 continue, 进行下一轮匹配.
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
            throw new Exception(String.format("Closing brackets doesn't match opening brackets in position %d", i));
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
          throw new Exception(String.format("Wrong or no operator in position %d", i));
        }
      }

      // 如果当前位置可以是数字
      if ((type & NUMBERS) != 0) {
        String number = ""; // 最终生成的数值字符串

        // 如果有符号, 则添加符号
        if (string.charAt(i) == '-') {
          number = "-";
          ++i;
          // 去除负号后的空白
          while (i < string.length() && string.charAt(i) == ' ') {
            ++i;
          }
          // 如果读取负号后到达表达式末尾, 则表达式非法
          if (i == string.length()) {
            throw new Exception("Expression ended unexpectedly");
          }
        }
        // 如果数值第一位不是小数点或数字, 则表达式非法
        char first = string.charAt(i);
        if (!(first == '.' || Character.isDigit(first))) {
          throw new Exception(String.format("Bad input in position %d", i));
        }

        // 记录是否出现过小数点
        boolean decimal = false;
        while (true) {
          if (i == string.length()) {
            break;
          }
          char c = string.charAt(i);
          if (c == '.') { // 判断小数点
            if (decimal == true) {
              throw new Exception(String.format("Unexpected decimal point in position %d", i));
            } else {
              decimal = true;
              number += c;
              ++i;
            }
          } else if (Character.isDigit(c)) { // 判断数字
            number += c;
            ++i;
          } else {
            break;
          }
        }
        strs.add(number);
        type = END | RIGHT_BRACKET | OPERATORS;
        continue;
      }

      // 如果都没有匹配上, 则表达式非法
      throw new Exception(String.format("Bad input in position %d", i));
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

  public ArrayList<String> getStrs() {
    return strs;
  }

}

public class Experiment_1 {
  private static Map<String, Integer> operatorsPriority = Map.of("+", 1, "-", 1, "*", 2, "/", 2, "%", 2);

  public static void main(String[] args) {

    BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
    while (true) {
      try {
        // 生成后缀表达式时, 用于存储运算符
        Stack<String> operatorsStack = new Stack<>();
        // 生成的后缀表达式
        ArrayList<String> postfixNotation = new ArrayList<>();

        String str;
        System.out.print(">>> ");
        str = br.readLine();
        ExpressionUtil expression = new ExpressionUtil(str);
        ArrayList<String> elementList = expression.getStrs();
        // System.out.println(elementList);
        final Set<String> operatorsSet = operatorsPriority.keySet();

        // 生成后缀表达式
        for (String element : elementList) {
          // System.out.println(operatorsStack);
          // System.out.println(postfixStack);
          if (element.equals("(")) {
            operatorsStack.push(element);
          } else if (element.equals(")")) {
            String operator;
            while (true) {
              operator = operatorsStack.pop();
              if (operator.equals("(")) {
                break;
              }
              postfixNotation.add(operator);
            }
          } else if (operatorsSet.contains(element)) {
            while (!operatorsStack.empty() && operatorsSet.contains(operatorsStack.peek())
                && operatorsPriority.get(operatorsStack.peek()) >= operatorsPriority.get(element)) {
              postfixNotation.add(operatorsStack.pop());
            }
            operatorsStack.push(element);
          } else {
            postfixNotation.add(element);
          }
        }
        while (!operatorsStack.empty()) {
          postfixNotation.add(operatorsStack.pop());
        }

        System.out.println("Postfix notation:");
        for (String element : postfixNotation) {
          System.out.print(element);
          System.out.print(" ");
        }
        System.out.println();

        // 计算后缀表达式时, 用于存储数值
        Stack<Number> numbersStack = new Stack<>();
        // 计算后缀表达式时, 用于存储数值类型
        Stack<Boolean> doubleTypeStack = new Stack<>();

        for (String element : postfixNotation) {
          if (operatorsSet.contains(element)) { // 如果当前元素是运算符
            Number rnumber = numbersStack.pop(), lnumber = numbersStack.pop();
            boolean doubleType = doubleTypeStack.pop() || doubleTypeStack.pop();
            doubleTypeStack.push(doubleType);
            if (doubleType) {
              double lDouble = lnumber.doubleValue();
              double rDouble = rnumber.doubleValue();
              switch (element) {
                case "+":
                  numbersStack.push(lDouble + rDouble);
                  break;
                case "-":
                  numbersStack.push(lDouble - rDouble);
                  break;
                case "*":
                  numbersStack.push(lDouble * rDouble);
                  break;
                case "/":
                  numbersStack.push(lDouble / rDouble);
                  break;
                case "%":
                  numbersStack.push(lDouble % rDouble);
                  break;
              }
            } else {
              int lInteger = lnumber.intValue();
              int rInteger = rnumber.intValue();
              switch (element) {
                case "+":
                  numbersStack.push(lInteger + rInteger);
                  break;
                case "-":
                  numbersStack.push(lInteger - rInteger);
                  break;
                case "*":
                  numbersStack.push(lInteger * rInteger);
                  break;
                case "/":
                  numbersStack.push(lInteger / rInteger);
                  break;
                case "%":
                  numbersStack.push(lInteger % rInteger);
                  break;
              }
            }
          } else { // 如果是数字
            if (element.contains(".")) {
              numbersStack.push(Double.valueOf(element));
              doubleTypeStack.push(true);
            } else {
              numbersStack.push(Integer.valueOf(element));
              doubleTypeStack.push(false);
            }
          }
        }

        System.out.println("The result is " + numbersStack.pop() + "\n");
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }
}
