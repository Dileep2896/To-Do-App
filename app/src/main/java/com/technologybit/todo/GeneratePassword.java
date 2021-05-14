package com.technologybit.todo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneratePassword {

    String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    String[] nonChar = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "=", ";", ":", "/",
                        "?", "_", "+", "~"};

    // Generates The Password
    public String generatePassword() {

        String upperLetter = getRandomItems(letters).toUpperCase();
        String lowerLetter = getRandomItems(letters).toLowerCase();
        String number = getRandomItems(numbers);
        String nonCharacter = getRandomItems(nonChar);

        String passwordNotShuffle = upperLetter + lowerLetter + nonCharacter + number;

        return shuffle(passwordNotShuffle);

    }

    // Gets Random Letters From The Array Strings
    public String getRandomItems(String[] array) {

        StringBuilder randomItem = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            Random r = new Random();
            int aRandomItem = r.nextInt(array.length);
            randomItem.append(array[aRandomItem]);
        }

        return randomItem.toString();

    }

    // Shuffles The Password Generated String
    public String shuffle(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        StringBuilder shuffled = new StringBuilder();
        for (String letter : letters) {
            shuffled.append(letter);
        }
        return shuffled.toString();
    }

}
