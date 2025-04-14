
import com.weishuai.common.exception.streamException.ThrowingConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Description : 处理stream中的Exception
 * https://www.baeldung.com/java-lambda-exceptions#handling-unchecked-exceptions
 *  - 检查异常是编译器强制要求捕获并处理可能发生的异常，包括IOException、SQLException以及用户自定义的Exception等；
 *  - 未检查异常是编译器不强制要求捕获并处理可能发生的异常，包括RuntimeException类异常。
 * @Author : Future Buddha
 * @Date: 2022-03-08 10:17
 */
public class ExceptionUtil {

    /**
     * 处理未经检查的异常
     * 此方法仅处理Consumer类型的功能接口的lambda 表达式。我们可以为其他函数式接口（如Function、BiFunction、BiConsumer等）编写类似的包装器方法。
     *
     * @param consumer
     * @param clazz
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E extends Exception> Consumer<T> consumerWrapper(Consumer<T> consumer, Class<E> clazz) {

        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = clazz.cast(ex);
                    System.err.println(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw ex;
                }
            }
        };
    }

    /**
     * 从 Lambda 表达式中抛出检查异常
     *
     * @param throwingConsumer
     * @param <T>
     * @return
     */
    public static <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, Exception> throwingConsumer) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * 处理 Lambda 表达式中的检查异常
     *
     * @param throwingConsumer
     * @param exceptionClass
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println("Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    //#####################################################################################################

    List<Integer> integers = Arrays.asList(3, 9, 7, 6, 10, 20);

    /**
     * 处理未经检查的异常
     * 普通处理 demo
     * 如果列表中的任何元素是0，那么我们会得到一个ArithmeticException: / by zero
     */
    public void demo00() {
        integers.forEach(i -> {
            try {
                System.out.println(50 / i);
            } catch (ArithmeticException e) {
                System.err.println("Arithmetic Exception occured : " + e.getMessage());
            }
        });
    }

    /**
     * 处理未经检查的异常 01
     */
    public void demo01() {
        integers.forEach(consumerWrapper(i -> System.out.println(50 / i), ArithmeticException.class));
    }

    /**
     * 处理未经检查的异常 02
     */
    public void demo02() {
        integers.forEach(consumerWrapper(i -> {
            System.out.println(50 / i);
        }, ArithmeticException.class));
    }

    //--------------------------------------------------------------------------------------------------------

    /**
     * 将integers写入文件
     */
    static void writeToFile(Integer integer) throws IOException {
        // logic to write to file which throws IOException
    }

    /**
     * 从 Lambda 表达式中抛出检查异常
     * 普通处理 demo
     * 在编译期间会得到未处理的IOException错误。
     */
    public void demo03() {
        integers.forEach(i -> {
            try {
                writeToFile(i);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 从 Lambda 表达式中抛出检查异常 01
     */
    public void demo04() {
        integers.forEach(throwingConsumerWrapper(i -> writeToFile(i)));
    }

    /**
     * 从 Lambda 表达式中抛出检查异常 02
     */
    public void demo05() {
        integers.forEach(throwingConsumerWrapper(ExceptionUtil::writeToFile));
    }

    //--------------------------------------------------------------------------------------------------------

    /**
     * 处理 Lambda 表达式中的检查异常 01
     */
    public void demo06() {
        integers.forEach(handlingConsumerWrapper(
                i -> writeToFile(i), IOException.class));
    }

    /**
     * 处理 Lambda 表达式中的检查异常 02
     */
    public void demo07() {
        integers.forEach(handlingConsumerWrapper(
                ExceptionUtil::writeToFile, IOException.class));
    }
}


























