package api;

import helpers.Request;
import helpers.api.ParameterCountOrder;
import helpers.api.SystemProperty;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.IOException;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static helpers.Property.getProperty;


@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(ParameterCountOrder.class)
class JUnit5Test1 {
    private static Response response;

    @BeforeAll
    static void request() throws IOException {
        response = Request.call(getProperty("baseUrlAPI"), null);
    }

    @Test
    @DisplayName("get and assert status code")
    @SystemProperty(key = "key", value = "value")
    void test1() {
        Assertions.assertNotEquals(System.getProperty("key"), "key");
        Assertions.assertEquals(System.getProperty("key"), "value");
    }

    @Test
    void test2() {
        Assertions.assertNull(System.getProperty("key"));
    }

    @Test
    @DisplayName("Check response code")
    void test3() {
        Assertions.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @DisplayName("Check schema")
    void test4() {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schema/example.json"));
    }

}