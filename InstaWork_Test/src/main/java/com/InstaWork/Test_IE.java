package com.InstaWork;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Test_IE {
	private static WebElement element = null;

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		System.out.print("Enter search keyword: \n");
		String keyword = scanner.nextLine();
		System.out.print("Enter target Website: \n");
		String websiteName = scanner.nextLine();
		System.out.print("Enter Total number of pages to search: \n");
		int pageNo = scanner.nextInt();
		
		WebDriver driver  = new InternetExplorerDriver();
		System.setProperty("webdriver.ie.driver", "C:\\Users\\H314228\\Downloads\\IEDriverServer.exe");
		Boolean found = false;
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://www.google.com");

		element = driver.findElement(By.name("q"));
		element.sendKeys(keyword);
		element.sendKeys(Keys.RETURN);
		int page = 0;
		while (!found && page <= pageNo) {
			try {
				List<WebElement> list = driver.findElements(By.xpath("//cite"));
				System.out.println(list.size());
				System.out.println(list.get(0).toString());
				Thread.sleep(10000);
				page++;
				for (int i = 0; i < list.size(); i++) {
					String link = list.get(i).getText();
					System.out.println(link);
					if (link.contains(websiteName)) {
						System.out.println("Website Found at Page" + page);
						found = true;
						fnHighlightMe(driver, list.get(i));
						break;
					} else {
						System.out.println("Website Not Found in page" + page);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}

			if (!found) {
				try {
					driver.findElement(By.xpath(".//*[@id='pnnext']/span[2]")).click();
					Thread.sleep(10000);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		// driver.close();
	}

	public static void fnHighlightMe(WebDriver driver, WebElement element) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
				"color: red; border: 3px solid red;");
		Thread.sleep(1000);
	}
}
