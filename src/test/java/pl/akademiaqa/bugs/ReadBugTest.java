package pl.akademiaqa.bugs;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ReadBugTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";

    @Test
    void readOneBug(){

        given()
                .get(BASE_URL + "/5")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title", Matchers.equalTo("Bug title"))
                .body("description", Matchers.equalTo("Bug description"))
                .body("employeeId", Matchers.equalTo("6"))
                .body("status", Matchers.equalTo("in progress"));
    }
}
