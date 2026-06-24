package com.nicoleroldan.court;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class CourtResourceTest {

    @Test
    void listAll_returns200() {
        given()
                .when().get("/courts")
                .then()
                .statusCode(200);
    }

    @Test
    void create_validRequest_returns201() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Cancha A\",\"sport\":\"tenis\",\"available\":true}")
                .when().post("/courts")
                .then()
                .statusCode(201)
                .body("name", is("Cancha A"))
                .body("sport", is("tenis"))
                .body("id", notNullValue());
    }

    @Test
    void create_blankName_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"\",\"sport\":\"tenis\",\"available\":true}")
                .when().post("/courts")
                .then()
                .statusCode(400);
    }

    @Test
    void create_invalidSport_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Cancha B\",\"sport\":\"rugby\",\"available\":true}")
                .when().post("/courts")
                .then()
                .statusCode(400);
    }

    @Test
    void getById_notFound_returns404() {
        given()
                .when().get("/courts/999999")
                .then()
                .statusCode(404);
    }

    @Test
    void getById_existingCourt_returns200() {
        Long id = given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Cancha Test\",\"sport\":\"padel\",\"available\":true}")
                .when().post("/courts")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        given()
                .when().get("/courts/" + id)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("sport", is("padel"));
    }
}
