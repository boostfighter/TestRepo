package com.sampleData;

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
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Risk_Register extends LoginClass{
    // Read Excel File
    File src = new File("C:\\AMIGO Selenium Excel Sheet.xlsx");
    FileInputStream input = new FileInputStream(src);
    XSSFWorkbook workbook = new XSSFWorkbook(input);
    XSSFSheet sheet = workbook.getSheetAt(0);

    public Risk_Register() throws IOException {
    }

    @Test(priority = 1)
    public void CreateRiskRegister() throws InterruptedException {
        //get sObject URL
        String sObject = sheet.getRow(32).getCell(2).getStringCellValue();
        System.out.println(sObject);

        //redirect to sObject
        driver.get(sObject);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        //Initiate Wait
        WebDriverWait wait = new WebDriverWait(driver, 50);

        //Click On New Button
        WebElement myDynamicElement = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\"New\"]")));
        myDynamicElement.click();
        Thread.sleep(5000);

        //Associated Organization
        String OrgName = sheet.getRow(11).getCell(3).getStringCellValue();
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@title=\"Search Organization\"]")));
        myDynamicElement.sendKeys(OrgName);
        Thread.sleep(5000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\""+OrgName+"\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Associated portfolio
        String PortName = sheet.getRow(12).getCell(3).getStringCellValue();
        driver.findElement(By.xpath("//input[@title=\"Search Portfolios\"]")).sendKeys(PortName);
        Thread.sleep(5000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\""+PortName+"\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Associated Program
        String ProgName = sheet.getRow(13).getCell(3).getStringCellValue();
        driver.findElement(By.xpath("//input[@title=\"Search Programs\"]")).sendKeys(ProgName);
        Thread.sleep(5000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@title=\""+ProgName+"\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Name
        String RRName = sheet.getRow(32).getCell(3).getStringCellValue();
        driver.findElement(By.xpath("//input[@class=\" input\"]")).sendKeys(RRName);
        Thread.sleep(1000);

        //Risk Register Owner
        String userName = sheet.getRow(37).getCell(4).getStringCellValue();
        driver.findElement(By.xpath("(//input[@title=\"Search People\"])[1]")).sendKeys(userName);
        Thread.sleep(5000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@title=\""+userName+"\"])[1]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //The Cause
        driver.findElement(By.xpath("(//textarea[@class=\" textarea\"])[1]")).sendKeys("Testing");
        Thread.sleep(1000);

        //The Risk
        driver.findElement(By.xpath("(//textarea[@class=\" textarea\"])[2]")).sendKeys("Testing");
        Thread.sleep(1000);

        //The Consequence
        driver.findElement(By.xpath("(//textarea[@class=\" textarea\"])[3]")).sendKeys("Testing");
        Thread.sleep(1000);

        //Risk Trigger Point
        driver.findElement(By.xpath("(//textarea[@class=\" textarea\"])[4]")).sendKeys("Testing");
        Thread.sleep(1000);

        //Risk Statement
        driver.findElement(By.xpath("(//textarea[@class=\" textarea\"])[5]")).sendKeys("Testing");
        Thread.sleep(1000);

        //Risk Category
        driver.findElement(By.xpath("(//a[@class=\"select\"])[1]")).click();
        Thread.sleep(1000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title=\"Environment: Economic\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Risk Classification
        driver.findElement(By.xpath("(//a[@class=\"select\"])[2]")).click();
        Thread.sleep(1000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title=\"Program Risk-  A risk that has potential impact to the overall program\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Risk Evolution Owner
        driver.findElement(By.xpath("(//input[@title=\"Search People\"])[2]")).sendKeys(userName);
        Thread.sleep(5000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@title=\""+userName+"\"])[2]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Probability Of Occurence
        driver.findElement(By.xpath("//input[@class=\"input uiInputSmartNumber\"]")).sendKeys("Testing");
        Thread.sleep(1000);

        //Some Impace Values
        driver.findElement(By.xpath("(//a[@class=\"select\"])[3]")).click();
        Thread.sleep(1000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title=\"1: No major impact to overall scope\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Quality Impact Values
        driver.findElement(By.xpath("(//a[@class=\"select\"])[4]")).click();
        Thread.sleep(1000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title=\"3: Some impact to specific areas of quality\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Schedule Impact Values
        driver.findElement(By.xpath("(//a[@class=\"select\"])[5]")).click();
        Thread.sleep(1000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title=\"3: 3 - 10 days\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Cost Impact Values
        driver.findElement(By.xpath("(//a[@class=\"select\"])[6]")).click();
        Thread.sleep(1000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title=\"1: Under $100k\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Risk Planning Owner
        driver.findElement(By.xpath("(//input[@title=\"Search People\"])[3]")).sendKeys(userName);
        Thread.sleep(5000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@title=\""+userName+"\"])[3]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //risk Response Strategy
        driver.findElement(By.xpath("(//a[@class=\"select\"])[7]")).click();
        Thread.sleep(1000);
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title=\"Avoid It:  Eliminate the threat of the risk by eliminating the cause\"]")));
        myDynamicElement.click();
        Thread.sleep(1000);

        //Risk Response Strstergy
        driver.findElement(By.xpath("//div[@class=\"ql-editor ql-blank slds-rich-text-area__content slds-text-color_weak slds-grow\"]")).sendKeys("Testing");
        Thread.sleep(1000);

        //Contingency Plan Details
        driver.findElement(By.xpath("//div[@class=\"ql-editor ql-blank slds-rich-text-area__content slds-text-color_weak slds-grow\"]")).sendKeys("Testing");
        Thread.sleep(1000);

        //Historical Comment
        driver.findElement(By.xpath("//div[@class=\"ql-editor ql-blank slds-rich-text-area__content slds-text-color_weak slds-grow\"]")).sendKeys("Testing");
        Thread.sleep(1000);

        //Save
        driver.findElement(By.xpath("//button[@title=\"Save\"]")).click();
        Thread.sleep(1000);

        //get Toast Message
        myDynamicElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class=\"toastMessage slds-text-heading--small forceActionsText\"]")));
        String ToastMessage = myDynamicElement.getAttribute("innerHTML");

        //Expected Toast Message Value Set
        String ExpectedValue = "Risk Register \""+RRName+"\" was created.";

        //Check
        Assert.assertEquals(ToastMessage, ExpectedValue);

        Thread.sleep(5000);
    }

    @AfterMethod
    public void afterMethod(ITestResult result)
    {
        if(result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP)
        {
            System.out.println("result Fail--"+result.getStatus());
            driver.quit();
        }
    }
}
