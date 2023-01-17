package pl.akademiaqa.bugs;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteBugTest {

    @Test
    void deleteBugTest(){

        Response response = given()
                .when()
                .delete("http://localhost:3000/bugs/6")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }
}
