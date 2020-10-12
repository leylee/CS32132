import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ExpressionUtil {
  private String string;
  private ArrayList<String> strs;
  boolean illegal = true;

  final int LEFT_BRACKET = 0b0000001;
  final int RIGHT_BRACKET = 0b0000010;
  final int OPERATORS = 0b0000100;
  final int NUMBERS = 0b0001000;
  // final int END = 0b0010000;

  private int match() throws Exception {
    return match(0, 0);
  }

  private int match(int index, int leftBrackets) throws Exception {
    return match(index, LEFT_BRACKET | NUMBERS, leftBrackets);
  }

  private int match(int index, int type, int leftBrackets) throws Exception {
    Pattern regex;
    Matcher matches;
    int i = index;
    while (true) {
      while (i < string.length() && string.charAt(i) == ' ') {
        ++i;
      }
      // Reach the end of string
      if (i == string.length()) {
        if ((type & RIGHT_BRACKET) != 0 && leftBrackets == 0) {
          return i;
        } else {
          throw new Exception("Closing brackets doesn't match opening brackets");
        }
      }
      // If left bracket is acceptable
      if ((type & LEFT_BRACKET) != 0) {
        if (string.charAt(i) == '(') {
          strs.add("(");
          i = match(i + 1, leftBrackets + 1);
          type = LEFT_BRACKET | NUMBERS | RIGHT_BRACKET;
          continue;
        }
      }
      if ((type & RIGHT_BRACKET) != 0) {
        if (string.charAt(i) == ')') {
          if (leftBrackets == 0) {
            throw new Exception("Closing brackets doesn't match opening brackets");
          }
          strs.add(")");
          return i + 1;
        }
      }
      if ((type & OPERATORS) != 0) {
        switch (string.charAt(i)) {
          case '+':

        }
      }
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

        }
      }
    }
  }

  public ExpressionUtil(String expression) {
    string = expression;
    match();
  }
}

public class Experiment_1 {
  public static void main(String[] args) {

  }
}
