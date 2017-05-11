package com.example.sasha.okhear_new.camera;

import android.support.annotation.DrawableRes;

import com.example.sasha.okhear_new.R;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.Map;

@EBean(scope = EBean.Scope.Singleton)
public class Hints {
    Map<Character, Integer> hints = new HashMap<>();

    {
        hints.put('А', R.drawable.a);
        hints.put('Б', R.drawable.be);
        hints.put('В', R.drawable.ve);
        hints.put('Г', R.drawable.ge);
        hints.put('Д', R.drawable.de);
        hints.put('Е', R.drawable.e);
        hints.put('Ж', R.drawable.je);
        hints.put('З', R.drawable.ze);
        hints.put('И', R.drawable.i);
        hints.put('Й', R.drawable.ikratkaya);
        hints.put('0', R.drawable.nol);
        hints.put('1', R.drawable.one);
        hints.put('2', R.drawable.two);
        hints.put('3', R.drawable.three);
        hints.put('4', R.drawable.four);
        hints.put('5', R.drawable.five);
        hints.put('6', R.drawable.six);
        hints.put('7', R.drawable.seven);
        hints.put('8', R.drawable.eight);
        hints.put('9', R.drawable.nine);
    }

    public int get(char c) {
        String s = String.valueOf(c);
        s = s.toUpperCase();
        return  hints.get(s.charAt(0));
    }
}
