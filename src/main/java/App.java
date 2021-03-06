import thread.WeatherParser;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<String> cities = new ArrayList<String>(Arrays.asList("Dnepr, Ukraine", "Kharkov, Ukraine", "Kiev, Ukraine",
                "Donetsk, Ukraine", "Lvov, Ukraine", "Odessa, Ukraine", "Poltava, Ukraine", "Uzhgorod, Ukraine",
                "Lugansk, Ukraine", "Zaporozhye, Ukraine"));

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Map<String, String>>> listWeather = new ArrayList<Future<Map<String, String>>>();


        //MANY THREADS
        long start = System.currentTimeMillis();
        for (String city : cities) {
            Callable callable = new WeatherParser(city);
            Future<Map<String, String>> future = executorService.submit(callable);
            listWeather.add(future);
        }
        executorService.shutdown();

        for (Future<Map<String, String>> weather : listWeather) {
            System.out.println(weather.get());
        }
        long end = System.currentTimeMillis();
        System.out.println("Много потоков: Time: " + (end-start) + " ms");

        //ONE THREAD
        start = System.currentTimeMillis();
        for (String city : cities) {
            Callable callable = new WeatherParser(city);
            System.out.println(callable.call());
        }
        end = System.currentTimeMillis();
        System.out.println("Один поток: Time: " + (end-start) + " ms");
    }



}
