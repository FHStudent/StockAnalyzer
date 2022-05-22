package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import stockanalyzer.ctrl.Controller;
import stockanalyzer.downloader.Downloader;
import stockanalyzer.downloader.ParallelDownloader;
import stockanalyzer.downloader.SequentialDownloader;
import yahooApi.YahooFinance;
import yahooApi.beans.YahooResponse;

public class UserInterface 
{

	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		ctrl.process("ABC;AAPL;SPY;AMZN");
	}

	public void getDataFromCtrl2(){
		ctrl.process("IIA.VI;POST.VI;LNZ.VI;UQA.VI");
	}

	public void getDataFromCtrl3(){
		ctrl.process("EBS.VI;DOC.VI;SBO.VI;RBI.VI");
	}
	public void getDataFromCtrl4(){
		getDataForCustomInput();
	}
	
	public void getDataForCustomInput() {
		System.out.println("\nPlease enter assets' short name and press enter. Max. 5 assets!");

		Scanner sc = new Scanner(System.in);
		List<String> holder = new ArrayList<>();

		for(int i = 0; i <= 5; i++) {
			holder.add(sc.next());
		}
		/*while(sc.next() != "q") {
			holder.add(sc.next());
		}*/

		ParallelDownloader downloader = new ParallelDownloader();
		downloader.process(holder);
	}

	public void downloadData() {

		/*
		* Erstellt mit freundlicher Unterstützung von Mark Perov
		*/
		long startingTime;
		long endingTime;
		long timeToRunSequential;
		long timeToRunParallel;

		SequentialDownloader sequentialDownloader = new SequentialDownloader();
		ParallelDownloader parallelDownloader = new ParallelDownloader();

		List<String> tickerList = Arrays.asList("OMV.VI","EBS.VI","DOC.VI","SBO.VI","RBI.VI",
				"VIG.VI","TKA.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI","WIE.VI","CAI.VI",
				"BG.VI","POST.VI","LNZ.VI","UQA.VI","SPI.VI","ATS.VI","IIA.VI");

		try {

			startingTime = System.currentTimeMillis();
			ctrl.downloadAllTickers(tickerList, sequentialDownloader);

			endingTime = System.currentTimeMillis();
			timeToRunSequential = endingTime - startingTime;

			startingTime = System.currentTimeMillis();
			ctrl.downloadAllTickers(tickerList, parallelDownloader);

			endingTime = System.currentTimeMillis();
			timeToRunParallel = endingTime - startingTime;

			System.out.println("The parallel downloader was " + (timeToRunSequential - timeToRunParallel) + " milliseconds faster than the sequential downloader");

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(System.lineSeparator() + "An error occurred during the downloading process");
		}
	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Choice 1", this::getDataFromCtrl1);
		menu.insert("b", "Choice 2", this::getDataFromCtrl2);
		menu.insert("c", "Choice 3", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Input:",this::getDataForCustomInput);
		menu.insert("z", "Choice User Input:",this::getDataFromCtrl4);
		menu.insert("l", "Download tickers", this::downloadData);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		ctrl.closeConnection();
		System.out.println("Program finished");
	}


	protected String readLine() 
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
