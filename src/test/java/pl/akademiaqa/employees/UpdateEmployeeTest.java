package pl.akademiaqa.employees;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

class UpdateEmployeeTest {

    private static Faker faker;
    private String randomEmail;
    private String randomFirstName;
    private String randomLastName;

    @BeforeAll
    static void beforeAll(){
        faker = new Faker();
    }

    @BeforeEach
    void beforeEach(){
        randomEmail = faker.internet().emailAddress();
        randomFirstName = faker.name().firstName();
        randomLastName = faker.name().lastName();
    }
    @AfterAll
    @Test
    static void afterAll(){

        JSONObject employee = new JSONObject();
        employee.put("firstName", "Bartek");
        employee.put("lastName", "Czarny");
        employee.put("email", "bczarny@testerprogramuje.pl");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .when()
                .patch("http://localhost:3000/employees/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));
    }

    @Test
    void updateEmployeeTest(){

        JSONObject company = new JSONObject();
        company.put("companyName", "Akademia QA");
        company.put("taxNumber", "531-1593-430");
        company.put("companyPhone", "731-111-111");

        JSONObject address = new JSONObject();
        address.put("street", "Ul. Sezamkowa");
        address.put("suite", "8");
        address.put("city", "Wrocław");
        address.put("zipcode", "12-123");

        JSONObject employee = new JSONObject();
        employee.put("firstName", "Bartek");
        employee.put("lastName", "Czarny");
        employee.put("username", "bczarny");
        employee.put("email", "bczarny@testerprogramuje.pl");
        employee.put("phone", "731-111-111");
        employee.put("website", "testerprogramuje.pl");
        employee.put("role", "qa");
        employee.put("type", "b2b");
        employee.put("address", address);
        employee.put("company", company);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .when()
                .put("http://localhost:3000/employees/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
    }

    @Test
    void partialUpdateEmployeeTest(){

        JSONObject employee = new JSONObject();
        employee.put("email", "bczarny@akademiaqa.pl");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .when()
                .patch("http://localhost:3000/employees/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("bczarny@akademiaqa.pl", json.getString("email"));
    }

    @Test
    void updateEmployeeUsingFakerTest(){

        JSONObject company = new JSONObject();
        company.put("companyName", "Akademia QA");
        company.put("taxNumber", "531-1593-430");
        company.put("companyPhone", "731-111-111");

        JSONObject address = new JSONObject();
        address.put("street", "Ul. Sezamkowa");
        address.put("suite", "8");
        address.put("city", "Wrocław");
        address.put("zipcode", "12-123");

        JSONObject employee = new JSONObject();
        employee.put("firstName", randomFirstName);
        employee.put("lastName", randomLastName);
        employee.put("username", "bczarny");
        employee.put("email", randomEmail);
        employee.put("phone", "731-111-111");
        employee.put("website", "testerprogramuje.pl");
        employee.put("role", "qa");
        employee.put("type", "b2b");
        employee.put("address", address);
        employee.put("company", company);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .when()
                .put("http://localhost:3000/employees/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomFirstName, json.getString("firstName"));
        Assertions.assertEquals(randomLastName, json.getString("lastName"));
        Assertions.assertEquals(randomEmail, json.getString("email"));
    }

    @Test
    void partialUpdateEmployeeUsingFakerTest(){

        JSONObject employee = new JSONObject();
        employee.put("email", randomEmail);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(employee.toString())
                .when()
                .patch("http://localhost:3000/employees/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomEmail, json.getString("email"));
    }
}
