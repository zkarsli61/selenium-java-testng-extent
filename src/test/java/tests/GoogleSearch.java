package tests;

import utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GoogleSearch {

    @BeforeMethod
    @Parameters("url")
    public void setUp(String url){
//        extentTest = extentReports.createTest("googlesearch","search in google");

        Driver.getDriver().get(url);
//        extentTest.info("main page is loaded");
        Driver.getDriver().findElement(By.name("q")).sendKeys("alphasense");

        Assert.assertTrue(Driver.getDriver().getTitle().contains("Google"));
//        extentTest.pass("asserted true");
    }


    @Test
    public void googlesearch01(){
        Driver.getDriver().findElement(By.name("q")).sendKeys("alphasense" + Keys.ENTER);
//        extentTest.info("keyword is searched");
        Assert.assertTrue(Driver.getDriver().getTitle().contains("alphasense"));
//        extentTest.pass("keywor searched assertion is successful");

    }
}
