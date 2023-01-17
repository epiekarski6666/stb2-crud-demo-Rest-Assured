package pl.akademiaqa.employees;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class ReadEmployeeTest {

    private static final String BASE_URL = "http://localhost:3000/employees";
    @Test
    void readAllEmployeesTest(){

        Response response = given()
                .when()
                .get("http://localhost:3000/employees");
        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();
        List<String> firstNames = json.getList("firstName");
        Assertions.assertTrue(firstNames.size() > 0);
    }

    @Test
    void readOneEmployeeTest(){

        Response response = given()
                .when()
                .get("http://localhost:3000/employees/1");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));
    }

    @Test
    void consolePrintOptionsTest() {

        Response response = given()
                .when()
                .get("http://localhost:3000/employees");

        System.out.println(response.asString()); //print body
        response.prettyPrint(); //print body
        response.prettyPeek(); //print entire response
    }

    @Test
    void readOneEmployeeWithPathVariableTest(){

        Response response = given()
                .pathParam("id", 1)
                .when()
                .get("http://localhost:3000/employees/{id}");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));
    }

    @Test
    void readEmployeesWithQuerryParamsTest(){
        Response response = given()
                .queryParam("firstName", "Bartek")
                .when()
                .get("http://localhost:3000/employees");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getList("firstName").get(0));
        Assertions.assertEquals("Czarny", json.getList("lastName").get(0));
        Assertions.assertEquals("bczarny", json.getList("username").get(0));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getList("email").get(0));
    }

    @Test
    void readOneEmployeeVersionTwoTest(){

        given()
                .when()
                .get(BASE_URL + "/1")
                        .then()
                                .statusCode(200)
                .body("firstName", Matchers.equalTo("Bartek"))
                .body("lastName", Matchers.equalTo("Czarny"))
                .body("username", Matchers.equalTo("bczarny"))
                .body("email", Matchers.equalTo("bczarny@testerprogramuje.pl"));
    }
}
