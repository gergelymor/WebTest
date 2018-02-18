package com.bacskai.tests;

import com.bacskai.testcases.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;

public class QuickSearchComponentTest {
	
	private WebDriver driver;
	
	@Before
	public void setUp(){
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		ChromeOptions options = new ChromeOptions(); 
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
	}
	
	
	@Test
	public void testKoop(){
		TestKoop koopPage = new TestKoop(driver);
		koopPage.verifyElementsInitialPresence();
		koopPage.testEmptySearch();
		koopPage.testLastValidSearch("Amsterdam", "+ 1 km", "€ 100.000", "€ 300.000");
		koopPage.testPlaceDropdownAutoSuggestionOperation();
		koopPage.testMinPriceIsBiggerThanMaxPrice("€ 300.000", "€ 100.000");
		String[] radiusElements = {"+ 0 km", "+ 1 km", "+ 2 km", "+ 5 km", "+ 10 km", "+ 15 km"};
		koopPage.testRadiusElements(radiusElements);
	}
	
	@Test
	public void testHuur(){
		TestHuur huurPage = new TestHuur(driver);
		huurPage.verifyElementsInitialPresence();
		huurPage.testEmptySearch();
		huurPage.testLastValidSearch("Amsterdam", "+ 1 km", "€ 800", "€ 1.500");
		huurPage.testPlaceDropdownAutoSuggestionOperation();
		huurPage.testMinPriceIsBiggerThanMaxPrice("€ 1.500", "€ 900");
		String[] radiusElements = {"+ 0 km", "+ 1 km", "+ 2 km", "+ 5 km", "+ 10 km", "+ 15 km"};
		huurPage.testRadiusElements(radiusElements);
	}
	
	@Test
	public void testNieuwbouw(){
		TestNieuwbouw nieuwBouwPage = new TestNieuwbouw(driver);
		nieuwBouwPage.verifyElementsInitialPresence();
		nieuwBouwPage.testPlaceDropdownAutoSuggestionOperation();
		nieuwBouwPage.testEmptySearch();
		nieuwBouwPage.testLastValidSearch("Amsterdam", "+ 1 km");
	}
	
	@Test
	public void testRecreatie(){
		TestRecreatie recreatiePage = new TestRecreatie(driver);
		recreatiePage.verifyElementsInitialPresence();
		recreatiePage.testPlaceDropdownAutoSuggestionOperation();
		recreatiePage.testEmptySearch();
		recreatiePage.testLastValidSearch("Amsterdam", "+ 1 km");
	}
	
	@Test
	public void testEuropa(){
		TestEuropa europaPage = new TestEuropa(driver);
		europaPage.verifyElementsInitialPresence();
		europaPage.testEmptySearch();
		europaPage.testLastValidSearch();
	}
	
	@After
	public void tearDown(){
		driver.close();
	}
}
