package utilities.extentreports;
import utilities.Driver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class TestListeners implements ITestListener{

    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    // for the safe thread in parallel exec
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());

        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + " Successful</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS,m);

    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        extentTest.get().fail("<details><summary><b><font color=red>" +
                "Exception occured, click to see details:" + "</font>/</b></summary>" +
                exceptionMessage.replaceAll(",","<br>") + "</details> \n");

//        WebDriver driver =((TestClassUsingListeners)result.getInstance()).driver;
        String path;

        if (!(result.getTestClass().getName().toLowerCase().contains("api"))) {
            path = takeScreenShot(Driver.getDriver(),result.getMethod().getMethodName());
            try {
                extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font>/</b>",
                        MediaEntityBuilder.createScreenCaptureFromPath(path).build());
            }catch (IOException e){
                extentTest.get().fail("Test Failed, cannot attach screenshot");
            }
        }



        String logText = "<b>Test Method " + methodName + " Failed</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
        extentTest.get().log(Status.FAIL,m);

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + " Skipped</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        extentTest.get().log(Status.SKIP,m);

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    public String takeScreenShot(WebDriver driver, String methodName){
        String fileName = getScreenShotName(methodName);
        String directory = System.getProperty("user.dir") + "/screenshots/";
        new File(directory).mkdirs();
        String path = directory + fileName;

        try {
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
            System.out.println("******************************");
            System.out.println("Screenshot stored at: "+ path);
            System.out.println("******************************");

        }catch (Exception e){
            e.printStackTrace();
        }
        return path;
    }

    public static String getScreenShotName(String methodName){
        Date d = new Date();
        String fileName = methodName + "_" + d.toString().replace(":","_").replace(" ","_")+ ".png";
        return fileName;
    }
}