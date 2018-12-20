package pp;

public class Main {

    public static void main(final String[] args) throws InterruptedException  {
        final NewList<Integer> list = new NewListImpl<>();
        final int iterate = 5000;
        final Thread thread0 = new Thread(() -> {
        	int sum = 0;
            for (int i = 0; i < 1250; i++) {
                sum += list.get(i);
            }
            System.out.println(Thread.currentThread().getName() + ": " + sum);
        });

        final Thread thread1 = new Thread(() -> {
           	int sum = 0;
            for (int i = 1250; i < 2500; i++) {
                sum += list.get(i);
            }
            System.out.println(Thread.currentThread().getName() + ": " + sum);
        });

        final Thread thread2 = new Thread(() -> {
           	int sum = 0;
            for (int i = 2500; i < 3750; i++) {
                sum += list.get(i);
            }
            System.out.println(Thread.currentThread().getName() + ": " + sum);
        });

        final Thread thread3 = new Thread(() -> {
           	int sum = 0;
            for (int i = 3750; i < 5000; i++) {
                sum += list.get(i);
            }
            System.out.println(Thread.currentThread().getName() + ": " + sum);
        });

        for (int i = 0; i < iterate; i++) {
            list.add(i);
        }
        final long start = System.currentTimeMillis();
        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread0.join();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.printf("%d ms", System.currentTimeMillis() - start);
    }

}
