package com.hai.tang.util;

import java.util.ArrayList;
import java.util.List;

public class CalculateUtil {
    /**
     * 计算两个整数的积,num1*num2
     */
    public static String mul(String num1, String num2) {
        String symbol = "";
        if (num1.startsWith("-") && !num2.startsWith("-")) {
            symbol = "-";
            num1 = num1.substring(1);
        } else if (!num1.startsWith("-") && num2.startsWith("-")) {
            symbol = "-";
            num2 = num2.substring(1);
        } else if (num1.startsWith("-") && num2.startsWith("-")) {
            num1 = num1.substring(1);
            num2 = num2.substring(1);
        }

        if ("0".equals(num1) || "0".equals(num2)) return "0";
        if ("1".equals(num1)) return num2;
        if ("1".equals(num2)) return num1;

        char[] num1Ch = num1.toCharArray();
        char[] num2Ch = num2.toCharArray();
        if (num1Ch.length == 1 && num2Ch.length == 1) {
            return String.valueOf((num1Ch[0] - '0') * (num2Ch[0] - '0'));
        }
        String fillZero = "";
        String result = "0";
        for (int i = num2Ch.length - 1; i >= 0; i--) {
            int n2 = num2Ch[i] - '0';
            int nextAdd = 0;
            StringBuilder sb = new StringBuilder();
            for (int j = num1Ch.length - 1; j >= 0; j--) {
                int n1 = num1Ch[j] - '0';
                int mul = n1 * n2;
                int sum = mul + nextAdd;
                if (sum >= 9) {
                    if (j != 0) {
                        nextAdd = sum / 10;
                        sb.append(sum % 10);
                    } else {
                        sb.append(new StringBuilder(String.valueOf(sum)).reverse());
                    }
                } else {
                    nextAdd = 0;
                    sb.append(sum);
                }
            }
            StringBuilder sbNew = sb.reverse();
            sbNew.append(fillZero);
            result = sum(result, sbNew.toString());
            fillZero += "0";
        }
        return symbol + result;
    }


    /**
     * 计算两个整数的和,n1+n2
     */
    public static String sum(String n1, String n2) {
        String symbol = "";
        if (n1.startsWith("-") && !n2.startsWith("-")) {
            return sub(n2, n1.substring(1));
        } else if (!n1.startsWith("-") && n2.startsWith("-")) {
            return sub(n1, n2.substring(1));
        } else if (n1.startsWith("-") && n2.startsWith("-")) {
            symbol = "-";
            n1 = n1.substring(1);
            n2 = n2.substring(1);
        }

        //若n1和n2位数不一样，则将少的数补0
        int puchZero = Math.abs(n1.length() - n2.length());
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < puchZero; i++) {
            newStr.append(0);
        }
        if (n1.length() > n2.length()) {
            n2 = newStr + n2;
        } else {
            n1 = newStr + n1;
        }
        //从个位开始将n1和n2相加，结果放入list中
        char[] ch1 = n1.toCharArray();
        char[] ch2 = n2.toCharArray();
        //下一位进位要加的数
        int nextAdd = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = ch1.length - 1; i >= 0; i--) {
            int num1 = ch1[i] - '0';
            int num2 = ch2[i] - '0';
            int sum = num1 + num2 + nextAdd;
            if (sum > 9) {
                if (i != 0) {
                    nextAdd = 1;
                    list.add(sum % 10);
                } else {
                    list.add(sum);
                }
            } else {
                nextAdd = 0;
                list.add(sum);
            }
        }
        //翻转list,输出结果
        StringBuilder result = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.append(list.get(i));
        }
        return symbol + result;
    }

    /**
     * 计算两个整数的差，n1-n2
     */
    public static String sub(String n1, String n2) {
        String symbol = "";
        if (n1.startsWith("-") && !n2.startsWith("-")) {
            return "-" + sum(n1.substring(1), n2);
        } else if (!n1.startsWith("-") && n2.startsWith("-")) {
            return sum(n1, n2.substring(1));
        } else if (n1.startsWith("-") && n2.startsWith("-")) {
            return sub(n2.substring(1), n1.substring(1));
        }

        if (n1.equals(n2)) return "0";

        //n1Moren2标记n1是否大于n2
        boolean n1Moren2 = n1.length() > n2.length();
        if (n1.length() == n2.length() && !n1.equals(n2)) {
            for (int i = 0; i < n2.length(); i++) {
                int temN1 = n1.charAt(i) - '0';
                int temN2 = n2.charAt(i) - '0';
                if (temN1 != temN2) {
                    n1Moren2 = temN1 > temN2;
                    break;
                }
            }
        }
        //n1小于n2则交换n1和n2,使n1大于n2
        if (!n1Moren2) {
            symbol = "-";
            String temp = n1;
            n1 = n2;
            n2 = temp;
        }
        //交换后如果长度不等，则对n2前面补0，使n1和n2的长度相等
        if (n1.length() != n2.length()) {
            int puchZero = n1.length() - n2.length();
            StringBuilder newStr = new StringBuilder();
            for (int i = 0; i < puchZero; i++) {
                newStr.append(0);
            }
            n2 = newStr + n2;
        }
        char[] ch1 = n1.toCharArray();
        char[] ch2 = n2.toCharArray();
        StringBuilder result = new StringBuilder();
        boolean nextExcuse = false;
        List<Integer> list = new ArrayList<>();
        for (int i = ch1.length - 1; i >= 0; i--) {
            int num1 = ch1[i] - '0';
            int num2 = ch2[i] - '0';
            if (!nextExcuse) {//该位没被借位过
                if (num1 >= num2) {
                    list.add(num1 - num2);
                } else {
                    nextExcuse = true;
                    list.add(10 + num1 - num2);
                }
            } else {//该位被借位过
                if (num1 > num2) {
                    list.add(num1 - 1 - num2);
                    nextExcuse = false;
                } else {
                    list.add(9 + num1 - num2);
                }
            }
        }
        //去除最开始的连续0，输出结果
        int zeroPostion = -1;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) != 0) {
                break;
            }
            if (i >= 1 && list.get(i - 1) != 0) {
                zeroPostion = i;
                break;
            }
        }
        int startPostion = zeroPostion == -1 ? list.size() - 1 : zeroPostion - 1;
        for (int i = startPostion; i >= 0; i--) {
            result.append(list.get(i));
        }
        return symbol + result;
    }
}

