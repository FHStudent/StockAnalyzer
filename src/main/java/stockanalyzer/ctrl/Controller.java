package stockanalyzer.ctrl;

import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.Result;
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

		/*
		* Mit freundlicher Unterstützung von Curt Schleritzko und Mathias Palfinger
		* */
		List<String> tickerString = Arrays.asList(searchString.split(";"));
		YahooFinance yahooFinance = new YahooFinance();
		YahooResponse yahooResponse = yahooFinance.getCurrentData(tickerString);
		QuoteResponse quoteResponse = yahooResponse.getQuoteResponse();

		quoteResponse.getResult().stream().forEach(quote -> System.out.println(quote.getSymbol() + ": " + quote.getAsk()));
		System.out.println();

		/*
		* Mit freundlicher Unterstützung von Curt Schleritzko und Mathias Palfinger (selbst modifiziert)
		* */
		System.out.println("Highest value: " + quoteResponse.getResult().stream().mapToDouble(Result::getAsk).max().orElseThrow(null)); //Gets the highest value and prints it out
		System.out.println("Average value: " + quoteResponse.getResult().stream().mapToDouble(Result::getAsk).average().orElseThrow(null)); //Gets the average value and prints it out
		
		return null;
	}


	public void closeConnection() {
		
	}
}
