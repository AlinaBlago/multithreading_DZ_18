package mainClass;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {

    static String input = "";
    static Scanner scn = new Scanner(System.in);
    static String tmp;
    final static String QUIT_COMMAND = "quit";
    static boolean isStop = false;

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {

            if(isStop) return;

            try (FileWriter writer = new FileWriter("output.txt", false)) {

                tmp = scn.nextLine();

                if(tmp.equals(QUIT_COMMAND)){
                    System.out.println("End of program");
                    isStop = true;
                }
                if (tmp != input && !isStop) {
                    input = tmp;
                    System.out.println("Input = " + input);
                }
                writer.write(input);

            } catch (IOException e) {
                e.printStackTrace();
            }
        };

            ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

            while (true) {
              Thread.sleep(100);
                if (tmp != null) {
                    if (tmp.equals(QUIT_COMMAND)) {
                        executorService.shutdown();
                        scheduledFuture.cancel(true);
                        return;
                    }
                }
            }
        }
    }

