package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class BaseRestTest {

    @BeforeTest
    public void beforeTest() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api";
    }

    @Test
    public void firstTest() {

        String post = new JSONObject()
                .put("author","damiano")
                .put("content","content")
                .put("createDate",new JSONObject()
                                    .put("chronology",new JSONObject()
                                                        .put("calendarType","gregorian")
                                                        .put("id","0"))
                                    .put("dayOfMonth",22)
                                    .put("dayOfWeek","MONDAY")
                                    .put("dayOfYear",22)
                                    .put("era",new JSONObject()
                                                .put("era",0))
                                    .put("leapYear", true)
                                    .put("month",0)
                                    .put("monthValue",0)
                                    .put("year",0))
                .put("id",3)
                .put("status","DRAFT")
                .put("title","Empty title").toString();

        given()
               .contentType(ContentType.JSON)
               .body(post)
           .when()
               .post("/post")
           .then()
//               .statusCode(201)
               .log().all()
           .and()
               .extract()
               .response();

        given()
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .body("id", hasItem(3))
                .body("title",hasItem("Empty title"))
                .log().all();
    }
}
