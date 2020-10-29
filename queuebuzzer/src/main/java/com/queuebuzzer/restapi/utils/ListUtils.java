package com.queuebuzzer.restapi.utils;

import java.util.List;

public class ListUtils {
    public static <T> T randomChoice(List<T> list) {
        return list.get((int)(Math.random() * (list.size() - 1)));
    }
}
