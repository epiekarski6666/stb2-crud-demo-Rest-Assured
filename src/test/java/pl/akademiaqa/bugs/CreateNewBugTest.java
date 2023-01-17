package pl.akademiaqa.bugs;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

class CreateNewBugTest {

    private static Faker faker;
    private String randomTitle;
    private String randomDescription;
    private String randomEmployeeId;
    private String randomStatus;

    @BeforeAll
    static void beforeAll(){
        faker = new Faker();
    }

    @BeforeEach
    void beforeEach(){
        randomTitle = faker.name().title();
        randomDescription = faker.lorem().sentence();
        randomEmployeeId = faker.number().digits(3);
        randomStatus = faker.name().bloodGroup();
    }

    @AfterAll
    @Test
    static void afterAll(){

        JSONObject bug = new JSONObject();
        bug.put("title", "Incorrect response code after PATH to /bugs");
        bug.put("description", "When I send a PATH request to /bugs, instead of status code 200, I'm getting 404");
        bug.put("employeeId", "1");
        bug.put("status", "open");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .patch("http://localhost:3000/bugs/1")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Incorrect response code after PATH to /bugs", json.getString("title"));
        Assertions.assertEquals("When I send a PATH request to /bugs, instead of status code 200, I'm getting 404", json.getString("description"));
        Assertions.assertEquals("1", json.getString("employeeId"));
        Assertions.assertEquals("open", json.getString("status"));
    }
    @Test
    void createNewBugTest(){

        JSONObject bug = new JSONObject();
        bug.put("title", randomTitle);
        bug.put("description", randomDescription);
        bug.put("employeeId", randomEmployeeId);
        bug.put("status", randomStatus);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .post("http://localhost:3000/bugs")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomTitle, json.getString("title"));
        Assertions.assertEquals(randomDescription, json.getString("description"));
        Assertions.assertEquals(randomEmployeeId, json.getString("employeeId"));
        Assertions.assertEquals(randomStatus, json.getString("status"));
    }

}
