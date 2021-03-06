package com.testCase;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class System_obj {

    static WebDriver driver;
    // Read Excel File
    File src = new File("C:\\AMIGO Selenium Excel Sheet.xlsx");
    FileInputStream input = new FileInputStream(src);
    XSSFWorkbook workbook = new XSSFWorkbook(input);
    XSSFSheet sheet = workbook.getSheetAt(0);

    public System_obj() throws IOException, IOException {
    }

    @BeforeTest
    public void Setup() throws IOException {
        //get WebDriver Path
        String webDriverPath = sheet.getRow(3).getCell(2).getStringCellValue();
        System.out.println(webDriverPath);

        //get UserName & Password
        String username = sheet.getRow(1).getCell(2).getStringCellValue();
        System.out.println(username);
        String password = sheet.getRow(2).getCell(2).getStringCellValue();
        System.out.println(password);

        //Open Chrome & go to Salesforce login page
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        //all other arguments(if need then add)
        //options.addArguments("--always-authorize-plugins"); options.addArguments("--no-sandbox"); options.addArguments("--disable-dev-shm-usage"); options.addArguments("--aggressive-cache-discard"); options.addArguments("--disable-cache"); options.addArguments("--disable-application-cache"); options.addArguments("--disable-offline-load-stale-cache"); options.addArguments("--disk-cache-size=0"); options.addArguments("--headless"); options.addArguments("--disable-gpu"); options.addArguments("--dns-prefetch-disable"); options.addArguments("--no-proxy-server"); options.addArguments("--log-level=3"); options.addArguments("--silent"); options.addArguments("--disable-browser-side-navigation"); options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get("https://login.salesforce.com");

        //give UserName & Password & Click to Login
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
        driver.findElement(By.id("Login")).click();
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);

        //get sObject URL
        String sObject = sheet.getRow(33).getCell(2).getStringCellValue();
        System.out.println(sObject);

        //redirect to sObject
        driver.get(sObject);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    @Test(priority = 0)
    public void CreateSystem() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        //click New Button
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\"New\"]")));
        myDynamicElement.click();

        //Associated Organization
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@title=\"Search Organization\"]")));
        myDynamicElement.sendKeys("Extron Organization");
        Thread.sleep(2000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\"Extron Organization\"]")));
        myDynamicElement.click();


        //Associated portfolio
        driver.findElement(By.xpath("//input[@title=\"Search Portfolios\"]")).sendKeys("Extron Portfolio");
        Thread.sleep(2000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\"Extron Portfolio\"]")));
        myDynamicElement.click();

        //Associated Program
        driver.findElement(By.xpath("//input[@title=\"Search Programs\"]")).sendKeys("Extron Program");
        Thread.sleep(2000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\"Extron Program\"]")));
        myDynamicElement.click();


        //System Name
        driver.findElement(By.xpath("//input[@class=\" input\" and @maxlength=\"80\"]")).sendKeys("New System Create");


        //System Type
        driver.findElement(By.xpath("(//a[@class=\"select\" and @role=\"button\"])[1]")).sendKeys("Future System");

        //System Disposition
        driver.findElement(By.xpath("(//a[@class=\"select\"])[2]")).sendKeys("Continue after go live");

        //Description
        driver.findElement(By.xpath("(//textarea[@class=\" textarea\"])[1]")).sendKeys("New System Created");

        //Decommission?
        driver.findElement(By.xpath("//input[@type=\"checkbox\"]")).click();

        //Date
        driver.findElement(By.xpath("//a[@class=\"datePicker-openIcon display\"]")).click();
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='15']")));
        myDynamicElement.click();

        //Time
        driver.findElement(By.xpath("//a[@class=\"timePicker-openIcon display\"]")).click();
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@data-hours=\"00\"]")));
        myDynamicElement.click();


        //Historical Comment
        driver.findElement(By.xpath("(//textarea[@class=\" textarea\"])[2]")).sendKeys("test");

        //Save System
        driver.findElement(By.xpath("//button[@title=\"Save\"]")).click();
        Thread.sleep(5000);


        //get Toast Message
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class=\"toastMessage slds-text-heading--small forceActionsText\"]")));
        String ToastMessage = myDynamicElement.getAttribute("innerHTML");
        System.out.println("the toast message value--->" + ToastMessage);

        //Expected Toast Message Value Set
        String ExpectedValue = "System \"New System Create\" was created.";
        System.out.println("the ExpectedValue--->" + ExpectedValue);

        //Check
        Assert.assertEquals(ToastMessage, ExpectedValue);

        Thread.sleep(5000);

    }

    @Test(priority = 2)
    public void EditSystem() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        //Edit System
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class=\"slds-button slds-button_icon-border-filled\"]")));
        myDynamicElement.click();

        //click Edit Button
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@name=\"PlatinumPMO__System__c.PlatinumPMO__EDIT\"]")));
        myDynamicElement.click();
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[text()='Edit'])")));
        myDynamicElement.click();

        //System Name Change
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class=\" input\" and @maxlength=\"80\"]")));
        myDynamicElement.clear();
        myDynamicElement.sendKeys(" TEST New System 2");

        //System Type
        driver.findElement(By.xpath("(//a[@class=\"select\" and @role=\"button\"])[1]")).sendKeys("Legacy System");


        //Historical Comments
        driver.findElement(By.xpath("(//textarea[@class=\" textarea\"])[2]")).sendKeys(" Test New System");

        //Click Save Button
        driver.findElement(By.xpath("//button[@title=\"Save\"]")).click();
        Thread.sleep(5000);

        //get Toast Message
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class=\"toastMessage slds-text-heading--small forceActionsText\"]")));
        String ToastMessage = myDynamicElement.getAttribute("innerHTML");

        //Expected Toast Message Value Set
        String ExpectedValue = "System \"TEST New System 2\" was saved.";

        //Check
        Assert.assertEquals(ToastMessage,ExpectedValue);

        Thread.sleep(5000);
    }

    @Test(priority = 3)
    public void AddDataTable() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        //Add Data Table
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name=\"PlatinumPMO__System__c.PlatinumPMO__Add_Data_Table\"]")));
        myDynamicElement.click();

        //Data Table Name
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class=\" input\" and @maxlength=\"80\"]")));
        myDynamicElement.sendKeys("TEST NEW DATA TABLE");

        //Data Consistency Factor
        driver.findElement(By.xpath("(//a[@class=\"select\"])[1]")).sendKeys("2");

        //Data Table Description
        driver.findElement(By.xpath("(//a[@class=\"select\"])[1]")).sendKeys("Data Table Create");

        //Data Reliability Factor
        driver.findElement(By.xpath("(//a[@class=\"select\"])[2]")).sendKeys("B");

        //Business Domain Expert

        driver.findElement(By.xpath("//input[@title=\"Search People\"]")).sendKeys("Subhajit Mishra");
        driver.findElement(By.xpath("//div[@title=\"Subhajit Mishra\"]")).click();

        //Historical Comments
        driver.findElement(By.xpath("(//textarea[@class=\" textarea\"])[2]")).sendKeys(" Test New ");

        //Click Save Button
        driver.findElement(By.xpath("//button[@title=\"Save\"]")).click();
        Thread.sleep(5000);

        //Get Toast Message
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"slds-theme--success slds-notify--toast slds-notify slds-notify--toast forceToastMessage\"]")));
        String ToastMessage = myDynamicElement.getAttribute("innerHTML");

        //checking Toast Message Value Set
        String Chechval = "was created.";


        //Check
        Assert.assertTrue(ToastMessage.contains(Chechval));
        Thread.sleep(5000);

    }

    @Test(priority = 4)
    public void AddRaci() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        //Add Raci Chart
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name=\"PlatinumPMO__System__c.PlatinumPMO__RACI_Chart\"]")));
        myDynamicElement.click();

        //User Type
        myDynamicElement= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class=\"slds-select\"]")));
        myDynamicElement.click();
        Thread.sleep(2000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//option[text()='Responsible']")));
        myDynamicElement.click();

        //User
        driver.findElement(By.xpath("//input[@class=\"slds-lookup__search-input slds-input leftPaddingClass input uiInput uiInputText uiInput--default uiInput--input\"]")).sendKeys("Techcloud Developer\n");
        Thread.sleep(5000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Techcloud Developer']")));
        myDynamicElement.click();

        Thread.sleep(2000);
        //Historical Comment
        driver.findElement(By.xpath("//textarea[@name=\"HistoricalComment\"]")).sendKeys("Test Historical Comment");

        //Save
        driver.findElement(By.xpath("//button[text()='Save']")).click();

        Thread.sleep(4000);

        //Get Toast Message
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"slds-theme--success slds-notify--toast slds-notify slds-notify--toast forceToastMessage\"]")));
        String ToastMessage = myDynamicElement.getAttribute("innerHTML");

        //checking Toast Message Value Set
        String Chechval = "The record was successfully created.";


        //Check
        Assert.assertTrue(ToastMessage.contains(Chechval));
        Thread.sleep(5000);

        //Add Raci Chart
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name=\"PlatinumPMO__System__c.PlatinumPMO__RACI_Chart\"]")));
        myDynamicElement.click();

        Thread.sleep(2000);

        //User Type
        myDynamicElement= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class=\"slds-select\"]")));
        myDynamicElement.click();
        Thread.sleep(2000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//option[text()='Accountable']")));
        myDynamicElement.click();

        Thread.sleep(2000);

        //User
        driver.findElement(By.xpath("//input[@class=\"slds-lookup__search-input slds-input leftPaddingClass input uiInput uiInputText uiInput--default uiInput--input\"]")).sendKeys("Techcloud Developer\n");
        Thread.sleep(3000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Techcloud Developer']")));
        myDynamicElement.click();

        //Historical Comment
        driver.findElement(By.xpath("//textarea[@name=\"HistoricalComment\"]")).sendKeys("Test Historical Comment");

        //Save
        driver.findElement(By.xpath("//button[text()='Save']")).click();

        Thread.sleep(4000);

        //Get Toast Message
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"slds-theme--success slds-notify--toast slds-notify slds-notify--toast forceToastMessage\"]")));
        String ToastMessage1 = myDynamicElement.getAttribute("innerHTML");

        //checking Toast Message Value Set
        String Chechval1 = "The record was successfully created.";


        //Check
        Assert.assertTrue(ToastMessage.contains(Chechval));
        Thread.sleep(5000);

        //Add Raci Chart
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name=\"PlatinumPMO__System__c.PlatinumPMO__RACI_Chart\"]")));
        myDynamicElement.click();

        Thread.sleep(2000);

        //User Type
        myDynamicElement= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class=\"slds-select\"]")));
        myDynamicElement.click();
        Thread.sleep(2000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//option[text()='Consulted']")));
        myDynamicElement.click();

        Thread.sleep(2000);

        //User
        driver.findElement(By.xpath("//input[@class=\"slds-lookup__search-input slds-input inputSize input uiInput uiInputText uiInput--default uiInput--input\"]")).sendKeys("Subhajit Mishra\n");
        Thread.sleep(3000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Subhajit Mishra']")));
        myDynamicElement.click();

        //Historical Comment
        driver.findElement(By.xpath("//textarea[@name=\"HistoricalComment\"]")).sendKeys("Test Historical Comment");

        //Save
        driver.findElement(By.xpath("//button[text()='Save']")).click();

        Thread.sleep(4000);

        //Get Toast Message
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"slds-theme--success slds-notify--toast slds-notify slds-notify--toast forceToastMessage\"]")));
        String ToastMessage2 = myDynamicElement.getAttribute("innerHTML");

        //checking Toast Message Value Set
        String Chechval2 = "The record was successfully created.";


        //Check
        Assert.assertTrue(ToastMessage.contains(Chechval));
        Thread.sleep(5000);
    }

    @AfterTest
    //Closing the Chrome
    public void terminateBrowser(){
        driver.close();
    }

}