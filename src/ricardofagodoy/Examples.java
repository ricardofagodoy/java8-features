package ricardofagodoy;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Examples {

    public void runExamples() {

        // Functional interface + default methods
        Formula times = (a, b) -> a * b;

        print(times.plusOne(10));
        print(times.expression(10, 3));

        // Functional lambda to compare
        List<Integer> numberList = Arrays.asList(4, 5, 7, 2, 1, 9);

        print(numberList);

        numberList.sort((a, b) -> a.compareTo(b));

        print(numberList);

        // Method and constructor references
        Person jake = new Person("Jake", 19);

        Say saySomething = jake::saySomething;
        print(saySomething.say());

        PersonFactory personFactory = Person::new;
        Person kate = personFactory.create("Kate", 22);

        print(kate.getName());

        // Predicate
        Predicate<String> startsWithA = (str) -> str.startsWith("a");

        print(startsWithA.test("abc"));
        print(startsWithA.test("cba"));
        print(startsWithA.negate().test("cba"));

        // Functions (nice to chain)
        Function<String, Integer> fromStr = Integer::valueOf;
        Function<Integer, Integer> pow2 = (a) -> a * a;

        int result = fromStr.andThen(pow2).apply("4");
        print(result);

        // Suppliers
        Supplier<String> stringSupplier = String::new;
        String blank = stringSupplier.get();

        // Consumer
        Consumer<Person> greeter = (p) -> print("Hello, " + p.getName());
        greeter.accept(kate);

        Map<Integer, String> numberWord = Collections.singletonMap(1, "one");

        // Also a consumer accepted
        numberWord.forEach((key, val) -> print(key + ": " + val));

        // Optionals
        Optional<String> optional = Optional.of("bam");

        optional.isPresent();                 // true
        optional.get();                       // "bam"
        optional.orElse("fallback");    // "bam"

        optional.ifPresent((s) -> print(s.charAt(0)));     // "b"

        // Streams

        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(this::print);

        // Can also use stringCollection.parallelStream()

        // .sorted()
        // .map()
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .forEach(this::print);

        // .match
        // .count

        long count = stringCollection
                .stream()
                .filter((s) -> s.startsWith("b"))
                .count();

        print(count);

        // Reduce
        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);

        reduced.ifPresent(this::print);

        // Maps (putIfAbsent, forEach, getOrDefault, merge)
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++)
            map.putIfAbsent(i, "val" + i);

        map.putIfAbsent(10, "ten");

        print(map.getOrDefault(10, "default"));
        print(map.getOrDefault(11, "default!!"));

        // Also .compute and .merge

        // Clock
    }

    private void print(Object str) {
        System.out.println(str);
    }
}

@FunctionalInterface
interface Formula {

    int expression(int a, int b);

    default int plusOne(int a) {
        return a + 1;
    }
}

@FunctionalInterface
interface Say {
    String say();
}

@FunctionalInterface
interface PersonFactory<P extends Person> {
    P create(String name, int age);
}

class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String saySomething() {
        return "something!";
    }
}