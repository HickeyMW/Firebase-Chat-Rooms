package com.wickeddevs.firebasechatrooms.utility;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class TestHelper {

    //////////////////// Other ////////////////////

    public static ArrayList<String> randomArrayListOfStrings(int numberOfStrings) {
        ArrayList<String> randomStrings = new ArrayList<>();
        for (int i = 0; i < numberOfStrings; i++) {
            randomStrings.add(randomString());
        }
        return randomStrings;
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static String randomString(String string) {
        return string + " " + UUID.randomUUID().toString();
    }

    public static ArrayList<String> randomStringList() {
        int size = randomInt(3, 5);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            strings.add(randomString());
        }
        return strings;
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static boolean randomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
