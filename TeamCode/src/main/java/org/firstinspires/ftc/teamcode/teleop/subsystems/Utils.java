package org.firstinspires.ftc.teamcode.teleop.subsystems;

// TODO figure out where this should go
public class Utils {
    // use OBJECT types not PRIMITIVE types
    // thank you java for making the least descriptive error messages
    /**
     * Maps the location of `find` in `from` to a value in `to`
     * Will throw an error if `find` is not in `from`
     * @param find an object to find in `from`
     * @param from an array of `find`-like objects
     * @param to an array of outputs
     * @return the object at the same index of `to` as `find` is to `from`
     */
    public static <T, S> S map(T find, T[] from, S[] to) {
        if (from.length != to.length) throw new IllegalArgumentException("Both arrays should be the same length");
        for (int i = 0; i < from.length; i++) {
            T item = from[i];
            if (find == item) return to[i];
        }

        // If this is Exception java says that I need to handle this exception
        throw new IllegalArgumentException("Item not found");
    }

    /**
     * Returns an object `adv` away from the index of `item` in `arr`
     * Wraps around if the index is less than zero or past the array bounds
     * @param item an object to find in `arr`
     * @param arr an array of `item`-like objects
     * @param adv the offset to get
     * @return an object `adv` away from the index of `item` in `arr`, wrapped around
     */
    public static <T> T advance(T item, T[] arr, int adv) {
        for (int i = 0; i < arr.length; i++) {
            T obj = arr[i];
            if (item == obj) {
                // Java doesn't wrap around beyond 0 but instead -arr.length
                int r1 = (i + adv) % arr.length;
                int r2 = (r1 + arr.length) % arr.length;
                return arr[r2];
            }
        }

        // If this is Exception java says that I need to handle this exception
        throw new IllegalArgumentException("Item not found");
    }
}