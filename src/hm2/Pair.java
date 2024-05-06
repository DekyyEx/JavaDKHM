package hm2;

public class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }
}

class GenericMethodExample {
    public static <T> String combine(Pair<T, T> pair) {
        return pair.getFirst().toString() + pair.getSecond().toString();
    }

    public static void main(String[] args) {
        Pair<Integer, Integer> intPair = new Pair<>(10, 20);
        String sum = combine(intPair);
        System.out.println("Sum: " + sum);

        Pair<String, String> stringPair = new Pair<>("Hello", "World");
        String concat = combine(stringPair);
        System.out.println("Concatenation: " + concat);
    }
}