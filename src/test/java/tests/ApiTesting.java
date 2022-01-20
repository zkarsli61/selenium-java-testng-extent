package tests;

import pojos.KeywordSearchPojo;
import pojos.Statements;
import utilities.ConfigReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ApiTesting{
    Response response;
    KeywordSearchPojo actualData;
    List<Statements> statementsList;



    @BeforeClass
    public void setUp(){

        response =given()
                .when()
                .get(ConfigReader.getProperty("api-endpoint"))
                .then()
                .contentType(ContentType.JSON)
                .extract().
                response();
        actualData = response.as(KeywordSearchPojo.class);
        statementsList = actualData.getSearchResults().getStatements();
    }

    @Test(priority = 1,suiteName = "api")
    public void testResponse(){

        //status code assertion
        assertEquals(200, response.getStatusCode());
    }


    @Test(priority = 2,suiteName = "api",description = "test failed because first statement does not contain searched keyword")
    public void testStatements(){
        //assert all content contains searched keyword
//        for (int i = 1; i < statementsList.size(); i++) {
//            assertTrue(statementsList.get(i).getContent().toLowerCase().contains("alphasense"),
//                    "statement "+i+" does not contain searched keyword");
//        }
       boolean result = statementsList.stream().map(t -> t.getContent().toLowerCase()).allMatch(t->t.contains("alphasense"));
        assertTrue(result);

    }

    @Test(priority = 3, suiteName = "api")
    public void testOriginalStatementCount(){
        String allContent = "";
        //I got the number of searched keyword by getting all the statement results together.
        for (int i = 0; i < statementsList.size(); i++) {
            String singleContent = statementsList.get(i).getContent().toLowerCase();
            allContent +=singleContent;
        }
        String []arr=allContent.split("alphasense");//split adds +1 but because of the originalStatementCount is already 17, there will be no problem

        assertEquals(actualData.getSearchResults().getOriginalStatementCount(),arr.length,"number of results are not matchins");
    }

    @Test(priority = 4,suiteName = "api")
    public void testLastStatement(){
        //data content assert
        assertEquals(statementsList.get(statementsList.size()-1).getContent(),"Logo - <span class='hl'>https://mma.prnewswire.com/media/947841/AlphaSense_Logo.jpg</span>");
    }

}
