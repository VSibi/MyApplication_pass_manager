package com.sibich.myapplication_pass_manager;

import java.util.Random;

/**
 * Created by Slavon on 26.04.2017.
 */
public class PassGenerator {
    private Random mRandom;
    private String[] mSymbols = {
        "A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i",
            "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r",
            "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "!", "@", "#", "$", "%", "/", "?"
    };
    private String mPass ="";

    public String generate(int count) {
        mRandom = new Random();

        for (int i = 0; i < count; i++) {
            int index  = mRandom.nextInt(mSymbols.length);
            mPass += mSymbols[index];
        }

        return mPass;
    }
}
