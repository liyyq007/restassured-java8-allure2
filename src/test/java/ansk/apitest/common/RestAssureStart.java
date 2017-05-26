package ansk.apitest.common;


import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import javaslang.collection.HashMap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Created by ASkrypnychenko on 5/24/2017.
 */
public class RestAssureStart {

    // TODO
//    @DataProvider(name = "circuits")
//    public String[][] createCircuitData() {
//        return new String[][] {
//                { "monza", "Italy" },
//                { "spa", "Belgium" },
//                { "sepang", "Malaysia" }
//        };
    ResponseSpecification respSpec;

    @BeforeClass
    public void initPath() {

        RestAssured.baseURI = "https://api.github.com";
        respSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    @Test
    public void getHomePageStatusCheck(){
      given().
              when().
              get("/").
              then().
              assertThat().
              statusCode(200);

    }
    @Test
    public void authStatusCheck(){
        given().
                auth().
                preemptive().
                basic("scrip.a.v@gmail.com", "Adrianu2015").
                when().
                get("/user").
                then().
                statusCode(200);

    }

    @Test
    public void lightBodychecks() throws Exception{
//      given().
//              when().
//              get("/scripnichenko").
//              then().
//              assertThat().
//              statusCode(200);

//        given().
//                auth().
//                preemptive().
//                basic("scrip.a.v@gmail.com", "Adrianu2015").
//                when().
//                get("/scripnichenko").
//                then().
//                statusCode(200);


        given().
                when().
                get("/").
                then().
                assertThat().
                body("current_user_url", equalTo("https://api.github.com/user")).
                body("public_gists_url",equalTo("https://api.github.com/gists/public")).
                body( "organization_repositories_url",equalTo("https://api.github.com/orgs/{org}/repos{?type,page,per_page,sort}")).
                body( "hub_url",isOneOf("https://api.github.com/hub","https://api.github.com/gists/private"));
//                body(("https://api.github.com/user"));


    }
    @Test
    public void sizeBodyCheck(){

//        Response response =
//                given().
//
//                        when().
//                        get("/").
//                        then().
//                        spec(respSpec).
//                        extract().
//                        response();
//
//
//        response.print();
//        HashMap<String,String> result =
//                new ObjectMapper().readValue(response, HashMap.class);
//assert()
        given().
                when().
                get("/").
                then().
                assertThat().
                spec(respSpec).
                and().
                body("size()",is(31));

    }
    @Test //Time Measuring
    public void timeCheck(){
        given().
                when().
                get("/").
                then().
                time(lessThan(700L)); // Milliseconds.

    }
}
