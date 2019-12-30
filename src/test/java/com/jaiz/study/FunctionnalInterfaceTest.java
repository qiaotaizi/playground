package com.jaiz.study;

import com.jaiz.study.bean.RedisCommand;
import org.junit.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * 函数式接口测试
 */
public class FunctionnalInterfaceTest {
    /**
     *
     */
    @Test
    public void myFunctionTest() {
        MyFunction my = param -> System.out.println("content is: " + param);
        System.out.println("func name=" + my.getFunctionName());
        System.out.println("func name2=" + my.getFunctionName2());
        my.printAString("hello world");
        System.out.println(MyFunction.getClassName());
    }

    @Test
    public void functionTest() {
        Function<RedisCommand, String> redisCommandDealer =
                command -> {
                    System.out.println("函数执行中...");
                    if (command.getCommand() == null) {
                        return "bad command!";
                    }
                    switch (command.getCommand()) {
                        case "get":
                            return _getHandler(command.getArgs());
                        case "set":
                            return _setHandler(command.getArgs());
                        default:
                            return "unsupported command";
                    }
                };

        Function<RedisCommand, RedisCommand> compose = arg -> {
            System.out.println("函数开始于" + System.currentTimeMillis());
            return arg;
        };
        Function<String, String> andThen = arg -> {
            System.out.println("函数结束于" + System.currentTimeMillis());
            return arg;
        };
        Function<RedisCommand, String> redisCommandDealerPlus =
                redisCommandDealer.compose(compose).andThen(andThen);

        List<RedisCommand> commands = new ArrayList<>();
        commands.add(RedisCommand.build("get", "hello", "hi"));
        commands.add(RedisCommand.build("set", "hello"));
        commands.add(RedisCommand.build("keys", "*"));

        commands.forEach(cmd -> {
            String result = redisCommandDealerPlus.apply(cmd);
            System.out.println("命令" + cmd.toString() + "的结果：" + System.lineSeparator() + result);
        });
    }

    @Test
    public void toIntFunctionTest() {
        ToIntFunction<String> stringParseInt = s -> {
            if (s == null || s.length() == 0) {
                return 0;
            }
            char[] cs = s.toCharArray();
            int start = 0;
            boolean negativeFlag = cs[0] == '-';
            if (negativeFlag) {
                start = 1;
            }
            int result = 0;
            for (int i = start; i < cs.length; i++) {
                char c = cs[i];
                if (!_isNumber(c)) {
                    return 0;
                }
                result = result * 10 + (c - 48);
            }
            return negativeFlag ? result * -1 : result;
        };

        String[] tests = {"0", "1", "123", "-1", "-123", "1000", "1.3"};

        for (String test : tests) {
            System.out.println(test + "的结果是" + stringParseInt.applyAsInt(test));
        }

        stringParseInt.applyAsInt("-123");
    }

    @Test
    public void toDoubleBiFunctionTest() {
        ToDoubleBiFunction<Double, Double> add = (d1, d2) -> {
            if (d1 == null || d2 == null) return 0;
            return d1 + d2;
        };

        Double[] ds = {
                1.3, 2.4,
                2.5, 5.0,
                10.2, 43.1,
                -1.2, 98.6
        };

        for (int i = 0; i < ds.length; i++) {
            Double d1 = ds[i];
            Double d2 = ds[++i];
            System.out.println(d1 + "+" + d2 + "=" + add.applyAsDouble(d1, d2));
        }
    }

    private Random generator = new Random(System.currentTimeMillis() / 1000000);

    @Test
    public void supplierTest() {
        Supplier<Integer> integerSupplier = () -> generator.nextInt();

        for (int i = 0; i < 10; i++) {
            System.out.println(integerSupplier.get());
        }
    }

    @Test
    public void comsumerTest() {
        Consumer<Object> eater = obj -> {
            System.out.println("真香");
        };
        Consumer<Object> gurrr = obj -> {
            System.out.println("嗝~");
        };

        eater = eater.andThen(gurrr);

        Object[] objs = {"hello", 1, 1.3, new Date()};
        for (Object obj : objs) {
            eater.accept(obj);
        }
    }

    @Test
    public void genericLambdaTest() {
        t((Consumer<Integer>) i -> i++);
    }

    /**
     * 方法引用
     */
    @Test
    public void methodQuoteTest() {
        Consumer<Object> sout = System.out::println;
        Consumer<Object> soutf = System.out::print;
        List<Object> list = List.of("hello", " ", "world", "!");
        list.forEach(soutf);

        Function<String, Integer> integerValueOf = Integer::valueOf;
        List<String> list1 = List.of("0", "1", "2");
        List<Integer> list2 = list1.stream().map(integerValueOf).collect(Collectors.toList());
        list2.forEach(sout);

        Supplier<Date> dateSupplier = Date::new;

        sout.accept(dateSupplier.get());

    }

    /**
     * 测试局部变量类型推断
     * java11新特性
     */
    @Test
    public void localVariableTest() {
        var cmd = RedisCommand.build("set", "hello");
        Consumer<Object> printer=System.out::println;
        printer.accept(cmd);
    }

    private <T> void t(Consumer<T> param) {

    }

    @Test
    public void isNumberTest() {
        System.out.println((int) '0');
        System.out.println((int) '1');
        System.out.println((int) '2');
        System.out.println((int) '3');
        System.out.println((int) '4');
        System.out.println((int) '5');
        System.out.println((int) '6');
        System.out.println((int) '7');
        System.out.println((int) '8');
        System.out.println((int) '9');
        System.out.println((int) '.');
    }


    /**
     * 判断一个字符是否可以转换为数字
     *
     * @param c
     * @return
     */
    private boolean _isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * set命令处理器
     *
     * @param args
     * @return
     */
    private String _setHandler(String[] args) {
        return "OK";
    }

    /**
     * get命令处理器
     *
     * @param args
     * @return
     */
    private String _getHandler(String[] args) {
        if (args.length == 0) {
            return "args error";
        }
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append("value of ").append(arg).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
