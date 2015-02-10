package com.app.ashish.util;

import com.app.ashish.constants.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashis_000 on 2/6/2015.
 */
public class Utility {
    private static Map<String, String> alphabetMap = new HashMap<String, String>();
    public static void initAlphabetMap(){
        alphabetMap.put("A", "A for Apple");
        alphabetMap.put("B", "B for Balloon");
        alphabetMap.put("C", "C for Cat");
        alphabetMap.put("D", "D for Dog");
        alphabetMap.put("E", "E for Elephant");
        alphabetMap.put("F", "F for Fish");
        alphabetMap.put("G", "G for Grapes");
        alphabetMap.put("H", "H for Horse");
        alphabetMap.put("I", "I for Ink");
        alphabetMap.put("J", "J for Jackal");
        alphabetMap.put("K", "K for Kite");
        alphabetMap.put("L", "L for Lion");
        alphabetMap.put("M", "M for Mango");
        alphabetMap.put("N", "N for Nose");
        alphabetMap.put("O", "O for Orange");
        alphabetMap.put("P", "P for Pigeon");
        alphabetMap.put("Q", "Q for Queen");
        alphabetMap.put("R", "R for Rose");
        alphabetMap.put("S", "S for Ship");
        alphabetMap.put("T", "T for Tiger");
        alphabetMap.put("U", "U for Umbrella");
        alphabetMap.put("V", "V for Vegetables");
        alphabetMap.put("W", "W for Watch");
        alphabetMap.put("X", "X for X-Mas tree");
        alphabetMap.put("Y", "Y for Yolk");
        alphabetMap.put("Z", "Z for Zebra");

        alphabetMap.put("1", "One Shoe");
        alphabetMap.put("2", "Two Balloons");
        alphabetMap.put("3", "Three Biscuits");
        alphabetMap.put("4", "Four Fishes");
        alphabetMap.put("5", "Five Balls");
        alphabetMap.put("6", "Six Dogs");
        alphabetMap.put("7", "Seven Oranges");
        alphabetMap.put("8", "Eight Mangoes");
        alphabetMap.put("9", "Nine Cats");
        alphabetMap.put("10", "Ten Babies");
        alphabetMap.put("11", "Eleven Fishes");
        alphabetMap.put("12", "Twelves Biscuits");
        alphabetMap.put("13", "Thirteen Dogs");
        alphabetMap.put("14", "Fourteen Horses");
        alphabetMap.put("15", "Fifteen Red Balls");
        alphabetMap.put("16", "Sixteen Mangoes");
        alphabetMap.put("17", "Seventeen Biscuits");
        alphabetMap.put("18", "Eighteen Balls");
        alphabetMap.put("19", "Nineteen Balls");
        alphabetMap.put("20", "Twenty Balls");

        for(int i = 21; i <= Constants.SELECTED_NUM_VALUE_100; i++) {
            alphabetMap.put("" + i, "" + i);
        }

    }
    public static String getTextByAlphabet(String alphabet) {
        String text = "";
        if(alphabet != null) {
            initAlphabetMap();
            text = alphabetMap.get(alphabet.toUpperCase().trim());
            if(text == null) {
                text = "";
            }
        }
        return text;
    }
}
