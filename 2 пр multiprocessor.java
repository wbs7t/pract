import java.util.Scanner;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;




public class FactorialCalculator {
    public static void main(String[] args) {
        //получение числа от пользователя
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число для вычисления факториала: ");
        long inputNumber = scanner.nextLong();

        //создание экземпляр задачи и ForkJoinPool для параллельного выполнения
        FactorialTask factorialTask = new FactorialTask(inputNumber);
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        //измерение время выполнения
        long startTime = System.currentTimeMillis();

        //запуск задачи и получение результата
        long result = forkJoinPool.invoke(factorialTask);

        //выводим результата и время выполнения
        System.out.println("Факториал числа " + inputNumber + " равен " + result);
        long endTime = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");

        //закрытие scanner
        scanner.close();
    }
}
//класс вычисления факториала
class FactorialTask extends RecursiveTask<Long> {
    private long n;

    public FactorialTask(long n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 1) {
            return 1L;
        } else {
            //разбитие на 2 подзадачи
            FactorialTask task1 = new FactorialTask(n / 2);
            task1.fork(); //запуск 1 подзадачу асинхронно

            FactorialTask task2 = new FactorialTask(n - n / 2);
            return task1.join() * task2.compute(); // Выполняем 2 подзадачу синхронно
        }
    }
}