package ru.netology.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class PostmanEchoTest {

    private RequestSpecification specs = new RequestSpecBuilder()
            .setBaseUri("https://postman-echo.com")
            .log(LogDetail.ALL)
            .build();

    @Test
    @DisplayName("Test postman echo")
    void testPostman() {
        given()
                .spec(specs)
                .body("Hell world")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data", equalTo("Hell world"));

    }

}