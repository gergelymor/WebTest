package com.bacskai.testcases;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestEuropa extends QuickSearchCommon {
	
	private final String href = "europe";
	private By landDropDown = By.xpath("//div[@class='search-dropdown custom-select']");
	private By coutryOptions = By.xpath("//*[@role='option']");
	private By lastSearchedItemLink = By.xpath("//*[@class='search-block__last-query']/a");
	private By selectedOption = By.xpath("//*[@class='selected-option']");

	public TestEuropa(WebDriver sharedDriver) {
		super(sharedDriver);
	}
	
	public void verifyElementsInitialPresence(){
		goToSelf(href);
		Assert.assertTrue(sharedDriver.findElements(koopTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(huurTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(nieuwBouwTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(recreatieTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(europeTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(landDropDown).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(searchButton).size() == 1);
	}
	
	public void testLastValidSearch(){
		goToSelf(href);
		sharedDriver.findElement(landDropDown).click();
		
		List<WebElement> countries = sharedDriver.findElements(coutryOptions);
		countries.get(0).click();
		
		String selectedValueHref = countries.get(0).getAttribute("data-selectbox-option-display-value");//.getText();//.getCssValue("data-selectbox-option-display-value");
		sharedDriver.findElement(searchButton).click();
		WebDriverWait wait = new WebDriverWait(sharedDriver, 10);
		wait.until(ExpectedConditions.urlContains(selectedValueHref));
		String currentUrl = sharedDriver.getCurrentUrl();
		goToSelf(href);
		
		Assert.assertTrue(sharedDriver.findElements(lastQueryLabel).size() == 1);
		sharedDriver.findElement(lastSearchedItemLink).click();
		wait.until(ExpectedConditions.urlContains(selectedValueHref));
		Assert.assertTrue(sharedDriver.getCurrentUrl().equals(currentUrl));
	}
	
	public void testEmptySearch(){
		goToSelf(href);
		sharedDriver.findElement(searchButton).click();
		WebDriverWait wait = new WebDriverWait(sharedDriver, 10);
		wait.until(ExpectedConditions.urlContains("heel-europa"));
		Assert.assertTrue(sharedDriver.getCurrentUrl().contains(href));
		Assert.assertTrue(sharedDriver.findElement(selectedOption).getText().equals("Alle landen"));
	}

}
