package stockanalyzer.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader {

    @Override
    public int process(List<String> tickers) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<String>> futureList = new ArrayList<>();
        int counter = 0;

        for(String ticker : tickers) {
            futureList.add(executorService.submit(()->saveJson2File(ticker)));
        }

        for(Future<String> element : futureList) {
            try {
                String execute = element.get();
                if(execute == null) {
                    counter++;
                }

            } catch (ExecutionException | InterruptedException e) {

                //throw new RuntimeException(e);
                System.out.println("Several exceptions have occurred. Please contact your administrator.");

            }
        }

        return counter;
    }
}