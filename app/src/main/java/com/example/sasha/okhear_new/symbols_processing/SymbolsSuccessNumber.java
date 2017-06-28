package com.example.sasha.okhear_new.symbols_processing;

import java.util.HashMap;
import java.util.Map;

public class SymbolsSuccessNumber {
    private final static Map<Character, Integer> successNumbers = new HashMap<>();

    static {
        successNumbers.put('0', 3);
        successNumbers.put('1', 3);
        successNumbers.put('2', 3);
        successNumbers.put('3', 3);
        successNumbers.put('4', 3);
        successNumbers.put('5', 3);
        successNumbers.put('6', 3);
        successNumbers.put('7', 3);
        successNumbers.put('8', 3);
        successNumbers.put('9', 3);
        successNumbers.put('А', 3);
        successNumbers.put('Б', 3);
        successNumbers.put('В', 3);
        successNumbers.put('Г', 3);
        successNumbers.put('Д', 3);
        successNumbers.put('Е', 3);
        successNumbers.put('Ж', 3);
        successNumbers.put('З', 3);
        successNumbers.put('И', 3);
        successNumbers.put('Й', 3);
        successNumbers.put('К', 3);
        successNumbers.put('Л', 3);
        successNumbers.put('М', 3);
        successNumbers.put('Н', 3);
        successNumbers.put('О', 3);
        successNumbers.put('П', 3);
        successNumbers.put('Р', 3);
        successNumbers.put('С', 3);
        successNumbers.put('Т', 3);
        successNumbers.put('У', 3);
        successNumbers.put('Ф', 3);
        successNumbers.put('Х', 3);
        successNumbers.put('Ц', 3);
        successNumbers.put('Ч', 3);
        successNumbers.put('Ш', 3);
        successNumbers.put('Ы', 3);
        successNumbers.put('Ь', 3);
        successNumbers.put('Э', 3);
        successNumbers.put('Ю', 3);
        successNumbers.put('Я', 3);
    }

    public static int getSuccessNumber(char symbol) {
        return successNumbers.get(symbol);
    }
}
