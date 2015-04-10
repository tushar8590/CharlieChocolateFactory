package com.java.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AmazonDataCollector {

	 List<String> itemsList = new ArrayList<String>();
	 List<String> itemResults = new ArrayList<String>();
	 static Iterator<String> itr;
	public AmazonDataCollector() {

		this.populateList();
	}

	public static void main(String ar[]) {
		AmazonDataCollector obj = new AmazonDataCollector();
		obj.processList();
	}

	public void processList() {
		List<String> items = getItemsList();

		Iterator<String> itr = items.iterator();

		while (itr.hasNext()) {
		try {
			int i = 0;

			
				i++;
				WebDriver driver = new FirefoxDriver();
				driver.get("http://www.amazon.in");
				driver.findElement(By.id("twotabsearchtextbox")).sendKeys(
						itr.next());
				driver.findElement(By.className("nav-submit-input")).click();
				// driver.manage().window().maximize();

				// String result =
				// driver.findElement(By.id("s-result-count")).getText().split(" ")[2];
				WebElement title = driver
						.findElement(By
								.xpath("//*[@id='result_0']/div/div/div/div[2]/div[1]/a/h2"));
				String result = title.getText();
				System.out.println(result);

				WebElement price = driver
						.findElement(By
								.xpath("//*[@id='result_0']/div/div/div/div[2]/div[2]/div[1]/div[1]/a/span"));
				System.out.println(price.getText());

				WebElement url = driver
						.findElement(By
								.xpath("//*[@id='result_0']/div/div/div/div[1]/div/div/a"));
				System.out.println(url.getAttribute("href").toString());
				String searchResult = result + " " + price.getText() + " "
						+ url.getAttribute("href").toString();
				itemResults.add(searchResult);

				driver.close();

				driver.quit();
				
				if (i == 20) {
					Thread.sleep(2000);
				}
			}
		 catch (Exception e) {
			 itr.remove();
			 continue;
		 }
		}
		saveResults(itemResults);

	}

	public List getItemsList() {

		return itemsList;

	}

	private boolean populateList() {

		// read the list from a text file
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(
							"C:\\Users\\jn1831\\workspace\\SeliniumDemo\\src\\com\\java\\test\\items.txt"));
			String sCurrentLine = "";
			while ((sCurrentLine = br.readLine()) != null) {
				itemsList.add(sCurrentLine);
			}
			br.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private void saveResults(List<String> results) {

		try {
			Iterator<String> itr = results.iterator();
			BufferedWriter bw = new BufferedWriter(
					new FileWriter(
							"C:\\Users\\jn1831\\workspace\\SeliniumDemo\\src\\com\\java\\test\\amazon_item_results.txt"));
			while (itr.hasNext()) {

				bw.write(itr.next());
				bw.write("\\n");
			}
			bw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
