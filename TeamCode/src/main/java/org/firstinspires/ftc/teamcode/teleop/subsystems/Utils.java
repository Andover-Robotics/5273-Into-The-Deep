package org.firstinspires.ftc.teamcode.teleop.subsystems;

// TODO figure out where this should go
public class Utils {
    public static <T, S> S map(T find, T[] from, S[] to) {
        if (from.length != to.length) throw new IllegalArgumentException("Both arrays should be the same length");
        for (int i = 0; i < from.length; i++) {
            T item = from[i];
            if (find == item) return to[i];
        }

        // If this is Exception java says that I need to handle this exception
        throw new IllegalArgumentException("Item not found");
    }

    public static <T> T advance(T item, T[] arr, int adv) {
        for (int i = 0; i < arr.length; i++) {
            T obj = arr[i];
            if (item == obj) return arr[(i + adv) % arr.length];
        }

        // If this is Exception java says that I need to handle this exception
        throw new IllegalArgumentException("Item not found");
    }
}