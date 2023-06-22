package org.example;

import java.util.*;
import java.util.concurrent.*;

public class A {
    private final int THREADNUM = 1000;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(this.THREADNUM);

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public void make(){
        for (int i = 0; i < THREADNUM; i++) {
            this.threadPool.submit(() -> {
                String route = generateRoute("RLRFR", 100);
                int countR = 0;
                for (int j = 0; j < route.length(); j++) {
                    if (route.charAt(j) == 'R') {
                        countR++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(countR)) {
                        int countFr = sizeToFreq.get(countR);
                        countFr++;
                        sizeToFreq.put(countR, countFr);
                    }
                    else {
                        sizeToFreq.put(countR, 1);
                    }
                }
//                System.out.println("Количество поворотов направо: " + countR);
            });
        }
        this.threadPool.shutdown();
//        поиск самого частого значения
        int maxVal = 0;
        int maxFreq = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxVal) {
                maxVal = entry.getValue();
                maxFreq = entry.getKey();
            }
        }
        System.out.println("Самое частое количество повторений " + maxFreq + " (встретилось " + maxVal + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
