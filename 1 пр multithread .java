import java.util.Scanner;

public class MediocreFactorialExample {

    public static void main(String[] args) {
        // просим юзера ввести цифру
        Scanner input = new Scanner(System.in);
        System.out.print("Введите число для нахождения факториала: ");

        // цифра, для которой считаем факториал
        int num = input.nextInt();

        // создаем задачу для 1 потока
        Runnable job1 = () -> {
            int res = computeFactorial(num);
            System.out.println("Поток 1: Факториал от " + num + " равен  " + res);
        };

        // создаем задачу для 2 потока
        Runnable job2 = () -> {
            int res = computeFactorial(num);
            System.out.println("Поток 2: Факториал от " + num + " равен  " + res);
        };

        // создаем потоки и передаем задачи
        Thread t1 = new Thread(job1);
        Thread t2 = new Thread(job2);

        // запускаем потоки
        t1.start();
        t2.start();

        try {
            // ждем завершения потоков
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // выводим сообщение о завершении
        System.out.println("Оба потока закончили свою работу");

        // Закрываем Scanner
        input.close();
    }

    private static int computeFactorial(int num) {
        int res = 1;
        for (int i = 1; i <= num; i++) {
            res *= i;
            System.out.println("Промежуточный результат: " + res);
            try {
                // замедляем потоки
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
