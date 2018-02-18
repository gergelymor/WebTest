package com.bacskai.testcases;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class QuickSearchCommon {
	
	protected final String FUNDA_URL = "http://www.funda.nl/";
	protected WebDriver sharedDriver;
	protected By searchButton;
	protected By koopTabButton;
	protected By huurTabButton;
	protected By nieuwBouwTabButton;
	protected By recreatieTabButton;
	protected By europeTabButton;
	protected By lastQuery;
	protected By lastQueryLabel;
	
	protected void selectFromDropDown(By element, String value){
		Boolean isElementPresent = sharedDriver.findElements(element).size() > 0;
		Assert.assertTrue(isElementPresent);
		Select select = new Select(sharedDriver.findElement(element));
		select.selectByVisibleText(value);
	}
	
	protected void fillValueInTextField(By element, String value){
		Boolean isElementPresent = sharedDriver.findElements(element).size() > 0;
		Assert.assertTrue(isElementPresent);
		sharedDriver.findElement(element).clear();
		sharedDriver.findElement(element).sendKeys(value);
	}
	
	protected void goToSelf(String href){
		sharedDriver.get(FUNDA_URL);
		WebDriverWait wait = new WebDriverWait(sharedDriver, 10);
		By quickSearchElement = By.xpath("//*[@class='search-block__navigation-item'][@href='/"+href+"/']");
		wait.until(ExpectedConditions.presenceOfElementLocated(quickSearchElement));
		sharedDriver.findElement(quickSearchElement).click();
		wait.until(ExpectedConditions.urlContains(href));
	}
	
	public QuickSearchCommon(WebDriver sharedDriver) {
		this.setSharedDriver(sharedDriver);
		this.searchButton = By.xpath("//button[@class='button-primary-alternative']");
		this.koopTabButton = By.xpath("//*[@class='search-block__navigation-item'][@href='/koop/']");
		this.huurTabButton = By.xpath("//*[@class='search-block__navigation-item'][@href='/huur/']");
		this.nieuwBouwTabButton = By.xpath("//*[@class='search-block__navigation-item'][@href='/nieuwbouw/']");
		this.recreatieTabButton = By.xpath("//*[@class='search-block__navigation-item'][@href='/recreatie/']");
		this.europeTabButton = By.xpath("//*[@class='search-block__navigation-item'][@href='/europe/']");
		this.lastQuery = By.xpath("//*[@class='search-block__last-query']/a");
		this.lastQueryLabel = By.xpath("//*[@class='search-block__last-query-label']");
	}

	public WebDriver getSharedDriver() {
		return sharedDriver;
	}

	public void setSharedDriver(WebDriver sharedDriver) {
		this.sharedDriver = sharedDriver;
	}

}
