package ricardofagodoy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Examples {

    @Test
    public void functionalInterface() {

        Formula times = (a, b) -> a * b;

        assertEquals(30, times.expression(10, 3));
    }

    @Test
    public void interfaceDefaultMethod() {

        Formula times = (a, b) -> a * b;

        assertEquals(11, times.plusOne(10));
    }

    @Test
    public void methodAndConstructorReferences() {

        Person jake = new Person("Jake", 19);
        Say saySomething = jake::saySomething;

        assertEquals(jake.saySomething(), saySomething.say());

        PersonFactory personFactory = Person::new;
        Person kate = personFactory.create("Kate", 22);

        assertEquals("Kate", kate.getName());
    }

    @Test
    public void predicate() {

        Predicate<String> startsWithA = (str) -> str.startsWith("a");

        assertTrue(startsWithA.test("abc"));
        assertFalse(startsWithA.test("cba"));

        assertTrue(startsWithA.negate().test("cba"));
    }

    @Test
    public void functions() {

        Function<String, Integer> fromStr = Integer::valueOf;
        Function<Integer, Integer> pow2 = (a) -> a * a;

        int result = fromStr.andThen(pow2).apply("4");

        assertEquals(16, result);
    }

    @Test
    public void suppliers() {

        Supplier<String> stringSupplier = String::new;

        String blank = stringSupplier.get();

        assertEquals("", blank);
    }

    @Test
    public void consumers() {

        Person kate = new Person("Kate", 19);

        Consumer<Person> greeter = (p) -> assertEquals("Kate", p.getName());
        greeter.accept(kate);

        Map<Integer, String> numberWord = Collections.singletonMap(1, "one");
        numberWord.forEach((key, val) -> print(key + ": " + val));
    }

    @Test
    public void optionals() {

        Optional<String> optional = Optional.of("bam");

        assertTrue(optional.isPresent());
        assertEquals("bam", optional.get());

        assertEquals("bam", optional.orElse("fallback"));

        optional.ifPresent((s) -> assertEquals("bam", s));
    }

    @Test
    public void streams() {

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