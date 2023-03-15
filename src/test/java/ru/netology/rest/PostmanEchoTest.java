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
            .setBaseUri("http://localhost")
            .setBasePath("/api/v1")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @Test
    @DisplayName("should currency RUB")
    void shouldCurrencyRub() {
        given()
                .spec(specs)
                .when()
                .get("/demo/accounts")
                .then()
                .body("[0].currency", equalTo("RUB"));

    }

    @Test
    @DisplayName("Should currency USD")
    void shouldCurrencyUsd() {
        given()
                .spec(specs)
                .when()
                .get("/demo/accounts")
                .then()
                .body("[1].currency", equalTo("USD"));
    }

    @Test
    @DisplayName("Should currency RUB and USD")
    void shouldCurrencyRubUsd() {
        given()
                .spec(specs)
                .when()
                .get("/demo/accounts")
                .then()
                .contentType(ContentType.JSON)
                .body("", hasSize(3))
                .body("[2].currency", equalTo("RUB"))
                .body("[1].currency", equalTo("USD"))
                .body("[0].balance", greaterThanOrEqualTo(0));

    }

    @Test
    @DisplayName("Should return JSON specs")
    void shouldReturnJSONResponseSpecs() {
        // Given - When - Then
        // Предусловия
        given()
                .spec(specs)
                .when()
                .get("/demo/accounts")
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=UTF-8")
                .contentType(ContentType.JSON)
        ;
    }

    @Test
    @DisplayName("Check what first account in list have balance greater or equal zero")
    void firstAccountInListHaveBalanceGreaterOrEqualZero() {
        given().spec(specs)
                .when()
                .get("/demo/accounts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("", hasSize(3))
                .body("[0].currency", equalTo("RUB"))
                .body("[0].balance", greaterThanOrEqualTo(0))
        ;
    }

    @Test
    @DisplayName("Check all balance of accounts is greater or equal zero")
    void allBalanceGreaterOrEqualZero() {
        given()
                .spec(specs)
                .when()
                .get("/demo/accounts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("every{ it.balance >= 0 }", is(true));

    }
}