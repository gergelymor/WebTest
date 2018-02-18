package com.bacskai.testcases;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestHuur extends QuickSearchCommon{
	
	private final String href = "huur";
	private final String redRGBA = "rgba(240, 60, 48, 1)";
	private By placeTextField = By.id("autocomplete-input");
	private By rangeDropDown = By.id("Straal");
	private By fromPriceValue = By.id("range-filter-selector-select-filter_huurprijsvan");
	private By toPriceValue = By.id("range-filter-selector-select-filter_huurprijstot");
	private By placeNameAutoCompleteFirstOption = By.id("autocomplete-list-option0");
	private By openDropDown = By.xpath("//*[@class='autocomplete-list is-open']");
	private By dropdownNoSuggestions = By.xpath("//*[@class='autocomplete-list-outer has-no-suggestions']");

	public TestHuur(WebDriver sharedDriver) {
		super(sharedDriver);
	}
	
	public void verifyElementsInitialPresence(){
		goToSelf(href);
		Assert.assertTrue(sharedDriver.findElements(koopTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(huurTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(nieuwBouwTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(recreatieTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(europeTabButton).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(placeTextField).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(rangeDropDown).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(fromPriceValue).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(toPriceValue).size() == 1);
		Assert.assertTrue(sharedDriver.findElements(searchButton).size() == 1);
	}
	
	public void testLastValidSearch(String place, String distance, String fromPrice, String toPrice) {
		goToSelf(href);
		WebDriverWait wait = new WebDriverWait(sharedDriver, 10);
		
		//https://github.com/SeleniumHQ/selenium/issues/4075
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		fillValueInTextField(placeTextField, place);
		selectFromDropDown(rangeDropDown, distance);
		selectFromDropDown(fromPriceValue, fromPrice);
		selectFromDropDown(toPriceValue, toPrice);
		
		
		WebElement searchBtn = sharedDriver.findElement(searchButton);
		Actions builder = new Actions(sharedDriver);
		builder.moveToElement(searchBtn).click(searchBtn);
		builder.perform();
		//sharedDriver.findElement(searchButton).click();
		
		wait.until(ExpectedConditions.urlContains(place.toLowerCase()));
		String currentUrl = sharedDriver.getCurrentUrl();
		goToSelf(href);
		
		Assert.assertTrue(sharedDriver.findElements(lastQueryLabel).size() == 1);
		String lastSearchParameters = sharedDriver.findElement(lastQuery).getText();
		Assert.assertTrue(lastSearchParameters.contains(place));
		Assert.assertTrue(lastSearchParameters.contains(distance.replaceAll("\\s", "")));
		Assert.assertTrue(lastSearchParameters.contains(fromPrice));
		Assert.assertTrue(lastSearchParameters.contains(toPrice));
		
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
		fillValueInTextField(placeTextField, "Ams");
		Assert.assertTrue(sharedDriver.findElements(placeNameAutoCompleteFirstOption).size() == 1);
	}
	
	public void testMinPriceIsBiggerThanMaxPrice(String fromPrice, String toPrice){
		goToSelf(href);
		selectFromDropDown(fromPriceValue, fromPrice);
		selectFromDropDown(toPriceValue, toPrice);
		By searchBlockBody = By.xpath("//*[@class='search-block__body']");
		sharedDriver.findElement(searchBlockBody).click();
		String colorValue = sharedDriver.findElement(toPriceValue).getCssValue("color");
		Assert.assertTrue(colorValue.equals(redRGBA));
	}
	
	public void testEmptySearch(){
		goToSelf(href);
		sharedDriver.findElement(searchButton).click();
		WebDriverWait wait = new WebDriverWait(sharedDriver, 10);
		wait.until(ExpectedConditions.urlContains("heel-nederland"));
		Assert.assertTrue(sharedDriver.getCurrentUrl().contains(href));
		Assert.assertTrue(sharedDriver.findElement(placeTextField).getAttribute("value").equals("Nederland"));
	}
	
	public void testRadiusElements(String[] elements){
		List<String> inputParameterElements = new LinkedList<String>(Arrays.asList(elements));
		goToSelf(href);
		Select radiusSelect = new Select(sharedDriver.findElement(rangeDropDown));
		List<String> radiusSelectElementValues= new LinkedList<String>();
		radiusSelect.getOptions().forEach(item -> radiusSelectElementValues.add(item.getText()));
		Assert.assertTrue(inputParameterElements.equals(radiusSelectElementValues));
	}

}
