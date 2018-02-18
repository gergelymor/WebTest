package com.bacskai.testcases;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestNieuwbouw extends QuickSearchCommon{
	
	private final String href = "nieuwbouw";
	private By placeTextField = By.id("autocomplete-input");
	private By rangeDropDown = By.id("Straal");
	private By placeNameAutoCompleteFirstOption = By.id("autocomplete-list-option0");
	private By openDropDown = By.xpath("//*[@class='autocomplete-list is-open']");
	private By dropdownNoSuggestions = By.xpath("//*[@class='autocomplete-list-outer has-no-suggestions']");

	public TestNieuwbouw(WebDriver sharedDriver) {
		super(sharedDriver);
	}
	
	public void verifyElementsInitialPresence(){
		goToSelf(href);
		System.out.println(sharedDriver.findElements(koopTabButton).size());
		Assert.assertTrue(sharedDriver.findElements(koopTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(huurTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(nieuwBouwTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(recreatieTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(europeTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(placeTextField).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(rangeDropDown).size() == 1);
	}
	
	public void testLastValidSearch(String place, String distance) {
		goToSelf(href);
		WebDriverWait wait = new WebDriverWait(sharedDriver, 10);
		fillValueInTextField(placeTextField, place);
		selectFromDropDown(rangeDropDown, distance);
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		sharedDriver.findElement(searchButton).click();
		
		wait.until(ExpectedConditions.urlContains(place.toLowerCase()));
		String currentUrl = sharedDriver.getCurrentUrl();
		goToSelf(href);
		
		Assert.assertTrue(sharedDriver.findElements(lastQueryLabel).size() == 1);
		String lastSearchParameters = sharedDriver.findElement(lastQuery).getText();
		Assert.assertTrue(lastSearchParameters.contains(place));
		Assert.assertTrue(lastSearchParameters.contains(distance.replaceAll("\\s", "")));
		
		sharedDriver.findElement(lastQuery).click();
		wait.until(ExpectedConditions.urlContains(place.toLowerCase()));
		Assert.assertTrue(sharedDriver.getCurrentUrl().equals(currentUrl));
	}
	
	public void testPlaceDropdownAutoSuggestionOperation(){
		goToSelf(href);
		Assert.assertTrue(sharedDriver.findElements(placeNameAutoCompleteFirstOption).size() == 0);
		fillValueInTextField(placeTextField, "blahblahblahblahblahblah");
		sharedDriver.findElement(searchButton).click();
		WebDriverWait wait = new WebDriverWait(sharedDriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(dropdownNoSuggestions));
		
		fillValueInTextField(placeTextField, "Amst");
		wait.until(ExpectedConditions.presenceOfElementLocated(openDropDown));
		Assert.assertTrue(sharedDriver.findElements(placeNameAutoCompleteFirstOption).size() == 1);
	}
	
	public void testEmptySearch(){
		goToSelf(href);
		sharedDriver.findElement(searchButton).click();
		WebDriverWait wait = new WebDriverWait(sharedDriver, 10);
		wait.until(ExpectedConditions.urlContains("heel-nederland"));
		Assert.assertTrue(sharedDriver.getCurrentUrl().contains(href));
		Assert.assertTrue(sharedDriver.findElement(placeTextField).getAttribute("value").equals("Nederland"));
	}

}
