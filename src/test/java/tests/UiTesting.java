package tests;

import pages.AlphaSenseDocumentPage;
import utilities.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class UiTesting {
    AlphaSenseDocumentPage alphaSenseDocumentPage;

    @BeforeClass
    @Parameters({"url"})
    public void setUp(String url) {
        alphaSenseDocumentPage = new AlphaSenseDocumentPage();
        Driver.getDriver().get(url);
        assertEquals(Driver.getDriver().getTitle(), "AlphaSense");
    }

    @Test(priority = 1)
    public void searchkeywordTest() {

        ReusableMethods.waitForClickablility(alphaSenseDocumentPage.codeMirrorLine, 5);
        //in order to send text, we need to click code mirror element
        alphaSenseDocumentPage.codeMirrorLine.click();
        JSUtils.highlightElement(alphaSenseDocumentPage.codeMirrorLine);

        assertEquals(alphaSenseDocumentPage.codeMirrorLine.getAttribute("class"),
                "CodeMirror cm-s-default CodeMirror-empty CodeMirror-focused");//"CodeMirror cm-s-default CodeMirror-empty","");
        alphaSenseDocumentPage.textarea.sendKeys("AlphaSense" + Keys.ENTER);
        alphaSenseDocumentPage.snippetItemList.get(0).click();
        JSUtils.highlightElement(alphaSenseDocumentPage.snippetItemList.get(0));

    }

    @Test(priority = 2)
    public void clickLastSnippetTest() {
        ReusableMethods.pageDown(5);
        WebElement lastSnippet = alphaSenseDocumentPage.snippetItemList.get(alphaSenseDocumentPage.snippetItemList.size() - 1);
        lastSnippet.click();
        JSUtils.highlightElement(lastSnippet);
    }



    @Test(priority = 3)
    public void verifyTheBackgroundColor() {

        Driver.getDriver().switchTo().frame("content-1");
        JSUtils.highlightElement(alphaSenseDocumentPage.iframeContentList.get(alphaSenseDocumentPage.iframeContentList.size() - 1));
        String lastIframeContent = alphaSenseDocumentPage.iframeContentList.get(alphaSenseDocumentPage.iframeContentList.size() - 1).getText();
        assertEquals(alphaSenseDocumentPage.logoStatement.getCssValue("background-color"), "rgba(255, 190, 0, 1)");
        assertEquals(alphaSenseDocumentPage.logoLinkBg.getCssValue("background-color"), "rgba(255, 190, 0, 1)");


    }
    @Test(priority = 4, description = "this test case will fail intentionally to get screenshot")
    public void verifyLogoWindow() {
        ReusableMethods.pageUp(4);
        alphaSenseDocumentPage.snippetItemList.get(2).click();

    }
    @AfterClass
    public void tearDown() {
        ReusableMethods.waitFor(4);
        Driver.closeDriver();
    }

}

