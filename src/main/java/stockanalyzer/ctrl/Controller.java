package stockanalyzer.ctrl;

import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Controller {
		
	public void process(String ticker) {
		System.out.println("Start process");

		//TODO implement Error handling 

		//TODO implement methods for
		//1) Daten laden
		//2) Daten Analyse
		getData(ticker);
	}
	

	public Object getData(String searchString) {

		List<String> tickerString = Arrays.asList(searchString);
		YahooFinance yahooFinance = new YahooFinance();
		YahooResponse yahooResponse = yahooFinance.getCurrentData(tickerString);
		QuoteResponse quoteResponse = yahooResponse.getQuoteResponse();
		quoteResponse.getResult().stream().forEach(quote -> System.out.println(quote.getAsk()));
		
		return null;
	}


	public void closeConnection() {
		
	}
}
