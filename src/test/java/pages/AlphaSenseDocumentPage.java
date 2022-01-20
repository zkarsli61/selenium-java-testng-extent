package pages;

import utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AlphaSenseDocumentPage {
    public AlphaSenseDocumentPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }
    @FindBy(css = ".react-codemirror2")
    public WebElement codeMirror;

    @FindBy(xpath = "//*[contains(@class,'CodeMirror')][1]")
    public WebElement codeMirrorLine;

    @FindBy(tagName = "textarea")
    public WebElement textarea;

    @FindBy(css = ".snippetItem__content")
    public List<WebElement> snippetItemList;

    @FindBy(tagName = "p")
    public List<WebElement> iframeContentList;

    @FindBy(id = "fse6357_0")
    public WebElement logoStatement;

    @FindBy(id = "fse6357_7")
    public WebElement logoLinkBg;

    @FindBy(xpath = "//a[@href='https://c212.net/c/link/?t=0&l=en&o=3308005-1&h=1298818245&u=https%3A%2F%2Fmma.prnewswire.com%2Fmedia%2F947841%2FAlphaSense_Logo.jpg&a=https%3A%2F%2Fmma.prnewswire.com%2Fmedia%2F947841%2FAlphaSense_Logo.jpg']")
    public WebElement logoLink;

    @FindBy(partialLinkText = "www.vikingglobal.com")
    public WebElement vikingGlobalLink;




}
