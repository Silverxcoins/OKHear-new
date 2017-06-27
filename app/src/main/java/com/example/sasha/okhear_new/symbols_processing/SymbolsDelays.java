package com.example.sasha.okhear_new.symbols_processing;

import java.util.HashMap;
import java.util.Map;

public class SymbolsDelays {
    private final static Map<Character, Integer> delays = new HashMap<>();
    
    static {
        delays.put('0', 4000);
        delays.put('1', 4000);
        delays.put('2', 4000);
        delays.put('3', 4000);
        delays.put('4', 4000);
        delays.put('5', 4000);
        delays.put('6', 4000);
        delays.put('7', 4000);
        delays.put('8', 4000);
        delays.put('9', 4000);
        delays.put('А', 4000);
        delays.put('Б', 4000);
        delays.put('В', 4000);
        delays.put('Г', 4000);
        delays.put('Д', 4000);
        delays.put('Е', 4000);
        delays.put('Ж', 4000);
        delays.put('З', 4000);
        delays.put('И', 4000);
        delays.put('Й', 4000);
        delays.put('К', 4000);
        delays.put('Л', 4000);
        delays.put('М', 4000);
        delays.put('Н', 4000);
        delays.put('О', 4000);
        delays.put('П', 4000);
        delays.put('Р', 4000);
        delays.put('С', 4000);
        delays.put('Т', 4000);
        delays.put('У', 4000);
        delays.put('Ф', 4000);
        delays.put('Х', 4000);
        delays.put('Ц', 4000);
        delays.put('Ч', 4000);
        delays.put('Ш', 4000);
        delays.put('Ы', 4000);
        delays.put('Ь', 4000);
        delays.put('Э', 4000);
        delays.put('Ю', 4000);
        delays.put('Я', 5000);
    }

    public static int getDelay(char symbol) {
        return delays.get(symbol);
    }
}
