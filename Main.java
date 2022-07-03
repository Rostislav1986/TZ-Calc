package org.example;


import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Integer> romanNumbers = Map.of("I", 1, "II", 2, "III", 3, "IV", 4, "V", 5, "VI", 6, "VII", 7, "VIII", 8, "IX", 9, "X", 10);
    private static final Map<Integer, String> tens = Map.of(1, "X", 2, "XX", 3, "XXX", 4, "XL", 5, "L", 6, "LX", 7, "LXX", 8, "LXXX", 9, "XC");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String result = calc(input);
        System.out.println(result);
    }


    private static String calc(String input) {
        String upper = input.toUpperCase(); // переводим из маленьких в заглавные

        String[] parts = upper.split(" ");// массив из трех эл. split расщепление на 3 строки

        if (parts.length != 3) {
            throw new InvalidParameterException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        char c = parts[0].charAt(0);
        boolean isArabic;
        if ( c == '-' || (c >= '0' && c <= '9')){
            isArabic = true;
        }else {
            isArabic = false;
        }
        c = parts[2].charAt(0);
        boolean isArabic2;
        if ( c == '-' || (c >= '0' && c <= '9')){
            isArabic2 = true;
        }else {
            isArabic2 = false;
        }

        if (isArabic != isArabic2) {
            throw new InvalidParameterException("т.к. используются одновременно разные системы счисления");
        }
        int a = parseInt(parts[0], isArabic);
        if (a > 10 || a < 1){
            throw new InvalidParameterException("число не может быть больше 10 или меньше 1");
        }
        String operator = parts[1];
        int b = parseInt(parts[2], isArabic);
        if (b > 10 || b < 1){
            throw new InvalidParameterException("число не может быть больше 10 или меньше 1");
        }
        int result = getResult(a, operator, b);
        if (isArabic) {
            return String.valueOf(result);
        }
        return toRoman(result);
    }


    private static String toRoman(int result) {
        if (result <= 0) {
            throw new InvalidParameterException("т.к. в римской системе нет отрицательных чисел");
        }

        if (result == 100) {
            return "C";
        }
        if (result < 10) {
            return getUnits(result);
        }
//      X I    X II    X III    X IV    X V     X VI    X VII   X VIII      X IX
        int a = result / 10;
        int b = result % 10;
        return tens.get(a) + getUnits(b);
    }

    private static String getUnits(int n) {
        if (n == 0) {
            return "";
        }
        //map - массив Entry, и каждая Entry хранит в себе ключ и значение
        for (Map.Entry<String, Integer> e : romanNumbers.entrySet()) {
            //проходим по всем элементам массива
            if (e.getValue() == n) {
//                если текущее значение равно n
                // то возвращаем ключ, соответствующий этому значению
                return e.getKey();
            }
        }
        throw new RuntimeException();
    }

    private static int getResult(int a, String operator, int b) {
        switch (operator) {
            case "+":
                return (a + b);
            case "-":
                return (a - b);
            case "*":
                return (a * b);
            case "/":
                return (a / b);
            default:
                throw new InvalidParameterException("строка не является математической операцией");
        }
    }

    private static int parseInt(String str, boolean isArabic) {
        if (isArabic) {
            return Integer.parseInt(str);
        }
        return romanNumbers.get(str);
    }
}