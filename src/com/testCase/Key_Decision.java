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
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Key_Decision {
    static WebDriver driver;
    // Read Excel File
    File src = new File("C:\\AMIGO Selenium Excel Sheet.xlsx");
    FileInputStream input = new FileInputStream(src);
    XSSFWorkbook workbook = new XSSFWorkbook(input);
    XSSFSheet sheet = workbook.getSheetAt(0);

    public Key_Decision() throws IOException {
    }

    @BeforeTest
    public void Setup(){
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
        String sObject = sheet.getRow(29).getCell(2).getStringCellValue();
        System.out.println(sObject);

        //redirect to sObject
        driver.get(sObject);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }
    @Test(priority = 1)
    public void CreateKeyDecision() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        //Click On New Button
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\"New\"]")));
        myDynamicElement.click();

        //Associated Organization
        String OrgName = sheet.getRow(11).getCell(3).getStringCellValue();
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@title=\"Search Organization\"]")));
        myDynamicElement.sendKeys(OrgName);
        Thread.sleep(1000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\""+OrgName+"\"]")));
        myDynamicElement.click();
        Thread.sleep(2000);

        //Associated portfolio
        String PortName = sheet.getRow(12).getCell(3).getStringCellValue();
        driver.findElement(By.xpath("//input[@title=\"Search Portfolios\"]")).sendKeys(PortName);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\""+PortName+"\"]")));
        myDynamicElement.click();

        //Associated Program
        String ProgName = sheet.getRow(13).getCell(3).getStringCellValue();
        driver.findElement(By.xpath("//input[@title=\"Search Programs\"]")).sendKeys(ProgName);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\""+ProgName+"\"]")));
        myDynamicElement.click();

        //Key Decision Name
        driver.findElement(By.xpath("//input[@class=\" input\"]")).sendKeys("Test Selenium Key Decision");

        //Scope
        driver.findElement(By.xpath("//div[@class=\"ql-editor ql-blank slds-rich-text-area__content slds-text-color_weak slds-grow\"]")).sendKeys("Testing");

        //Business Requirement
        driver.findElement(By.xpath("//div[@class=\"ql-editor ql-blank slds-rich-text-area__content slds-text-color_weak slds-grow\"]")).sendKeys("Testing");

        //Decision Option
        driver.findElement(By.xpath("//div[@class=\"ql-editor ql-blank slds-rich-text-area__content slds-text-color_weak slds-grow\"]")).sendKeys("Testing");

        //Accepted Decision
        driver.findElement(By.xpath("//div[@class=\"ql-editor ql-blank slds-rich-text-area__content slds-text-color_weak slds-grow\"]")).sendKeys("Testing");

        //Historical Comment
        driver.findElement(By.xpath("//div[@class=\"ql-editor ql-blank slds-rich-text-area__content slds-text-color_weak slds-grow\"]")).sendKeys("Test Historical Comment");

        //Save
        driver.findElement(By.xpath("//button[@title=\"Save\"]")).click();

        //get Toast Message
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class=\"toastMessage slds-text-heading--small forceActionsText\"]")));
        String ToastMessage = myDynamicElement.getAttribute("innerHTML");

        //Expected Toast Message Value Set
        String ExpectedValue = "Key Decision \"Test Selenium Key Decision\" was created.";

        //Check
        Assert.assertEquals(ToastMessage,ExpectedValue);

        Thread.sleep(5000);

    }
    @Test(priority = 2)
    public void EditKeyDecision() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        //Edit Button
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class=\"slds-button slds-button_icon-border-filled\"]")));
        myDynamicElement.click();

        //Edit
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@name=\"PlatinumPMO__Key_Decision__c.PlatinumPMO__Edit\"]")));
        myDynamicElement.click();

        //Edit Popup
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[text()='Edit'])[2]")));
        myDynamicElement.click();
        Thread.sleep(2000);

        //Name
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class=\" input\"]")));
        myDynamicElement.clear();
        myDynamicElement.sendKeys("Test Selenium Key Decision-Edit");

        //Historical Comment
        driver.findElement(By.xpath("//div[@class=\"ql-editor ql-blank slds-rich-text-area__content slds-text-color_weak slds-grow\"]")).sendKeys("Test Historical Comment-Edit");


        //Save
        driver.findElement(By.xpath("//button[@title=\"Save\"]")).click();

        //get Toast Message
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class=\"toastMessage slds-text-heading--small forceActionsText\"]")));
        String ToastMessage = myDynamicElement.getAttribute("innerHTML");

        //Expected Toast Message Value Set
        String ExpectedValue = "Key Decision \"Test Selenium Key Decision-Edit\" was saved.";

        //Check
        Assert.assertEquals(ToastMessage,ExpectedValue);

        Thread.sleep(5000);

        driver.navigate().refresh();

        Thread.sleep(10000);

    }

    @Test(priority = 3)
    public void AddRaci() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        //Add Raci Chart
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name=\"PlatinumPMO__Key_Decision__c.PlatinumPMO__RACI_Chart\"]")));
        myDynamicElement.click();

        //User Type
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class=\"slds-select\"]")));
        myDynamicElement.click();
        Thread.sleep(2000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//option[text()='Responsible']")));
        myDynamicElement.click();

        //User
        String userName = sheet.getRow(37).getCell(4).getStringCellValue();
        driver.findElement(By.xpath("//input[@class=\"slds-lookup__search-input slds-input leftPaddingClass input uiInput uiInputText uiInput--default uiInput--input\"]")).sendKeys(userName+"\n");
        Thread.sleep(5000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+userName+"']")));
        myDynamicElement.click();

        Thread.sleep(2000);
        //Change Description
        driver.findElement(By.xpath("//textarea[@name=\"HistoricalComment\"]")).sendKeys("Test Historical Comment");

        //Save
        driver.findElement(By.xpath("//button[text()='Save']")).click();

        Thread.sleep(4000);

        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"slds-theme--success slds-notify--toast slds-notify slds-notify--toast forceToastMessage\"]")));
        String ToastMessage = myDynamicElement.getAttribute("innerHTML");

        //checking Toast Message Value Set
        String Chechval = "The record was successfully created.";


        //Check
        Assert.assertTrue(ToastMessage.contains(Chechval));
        Thread.sleep(5000);



        //Add Raci Chart
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name=\"PlatinumPMO__Key_Decision__c.PlatinumPMO__RACI_Chart\"]")));
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
        driver.findElement(By.xpath("//input[@class=\"slds-lookup__search-input slds-input leftPaddingClass input uiInput uiInputText uiInput--default uiInput--input\"]")).sendKeys(userName+"\n");
        Thread.sleep(3000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+userName+"']")));
        myDynamicElement.click();

        //Change Description
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
        Assert.assertTrue(ToastMessage1.contains(Chechval1));
        Thread.sleep(5000);


        //Add Raci Chart
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name=\"PlatinumPMO__Key_Decision__c.PlatinumPMO__RACI_Chart\"]")));
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
        userName = sheet.getRow(37).getCell(6).getStringCellValue();
        driver.findElement(By.xpath("//input[@class=\"slds-lookup__search-input slds-input inputSize input uiInput uiInputText uiInput--default uiInput--input\"]")).sendKeys(userName+"\n");
        Thread.sleep(3000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+userName+"']")));
        myDynamicElement.click();

        //Chasnge Description
        driver.findElement(By.xpath("//textarea[@name=\"HistoricalComment\"]")).sendKeys("Test Historical Comment");

        //Save
        driver.findElement(By.xpath("//button[text()='Save']")).click();

        Thread.sleep(4000);

        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"slds-theme--success slds-notify--toast slds-notify slds-notify--toast forceToastMessage\"]")));
        String ToastMessage2 = myDynamicElement.getAttribute("innerHTML");

        //checking Toast Message Value Set
        String Chechval2 = "The record was successfully created.";


        //Check
        Assert.assertTrue(ToastMessage2.contains(Chechval2));
        Thread.sleep(5000);
    }

    @Test(priority = 4)
    public void AddProject() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        //Click Add Project
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name=\"PlatinumPMO__Key_Decision__c.PlatinumPMO__Add_Project\"]")));
        myDynamicElement.click();

        //Lookup Project
        String ProjName = sheet.getRow(14).getCell(3).getStringCellValue();
        myDynamicElement= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder=\"search..\"]")));
        myDynamicElement.sendKeys(ProjName);
        Thread.sleep(3000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+ProjName+"']")));
        myDynamicElement.click();

        //click save
        driver.findElement(By.xpath("//span[text()='Add Project']")).click();
        Thread.sleep(5000);
    }

    @Test(priority = 5)
    public void AddDeliverable() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        //Add Deliverable
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@name=\"PlatinumPMO__Key_Decision__c.PlatinumPMO__Add_Deliverable\"]")));
        myDynamicElement.click();

        String DelijName = sheet.getRow(15).getCell(3).getStringCellValue();
        myDynamicElement= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder=\"search..\"]")));
        myDynamicElement.sendKeys(DelijName);
        Thread.sleep(3000);
        myDynamicElement=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+DelijName+"']")));
        myDynamicElement.click();

        driver.findElement(By.xpath("//span[text()='Add Deliverable']")).click();

        Thread.sleep(5000);

    }


    @AfterTest
    public void close(){
        //closing the chrome
        driver.quit();
    }
}
