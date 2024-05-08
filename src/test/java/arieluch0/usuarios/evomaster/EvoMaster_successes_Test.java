package arieluch0.usuarios.evomaster;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.ValidatableResponse;
import org.evomaster.client.java.controller.SutHandler;
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * This file was automatically generated by EvoMaster on 2024-05-08T13:44:16.138756842-03:00[America/Argentina/Buenos_Aires]
 * <br>
 * The generated test suite contains 5 tests
 * <br>
 * Covered targets: 37
 * <br>
 * Used time: 0h 1m 19s
 * <br>
 * Needed budget for current results: 79%
 * <br>
 * This file contains test cases that represent successful calls.
 */
public class EvoMaster_successes_Test {


    private static final SutHandler controller = new arieluch0.usuarios.evomaster.ControladorEvoMaster();
    private static String baseUrlOfSut;
    /**
     * [ems] - expectations master switch - is the variable that activates/deactivates expectations individual test cases
     * by default, expectations are turned off. The variable needs to be set to [true] to enable expectations
     */
    private static final boolean ems = false;
    /**
     * sco - supported code oracle - checking that the response status code is among those supported according to the schema
     */
    private static final boolean sco = false;


    @BeforeAll
    public static void initClass() {
        controller.setupForGeneratedTest();
        baseUrlOfSut = controller.startSut();
        controller.registerOrExecuteInitSqlCommandsIfNeeded();
        assertNotNull(baseUrlOfSut);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.urlEncodingEnabled = false;
        RestAssured.config = RestAssured.config()
                .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE))
                .redirect(redirectConfig().followRedirects(false));
    }


    @AfterAll
    public static void tearDown() {
        controller.stopSut();
    }


    @BeforeEach
    public void initTest() {
        controller.resetDatabase(List.of());
        controller.resetStateOfSUT();
    }


    @Test
    @Timeout(60)
    public void test_0() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();

        ValidatableResponse res_0 = given().accept("*/*")
                .get(baseUrlOfSut + "/v3/api-docs")
                .then()
                .statusCode(200);

        expectationHandler.expect(ems)
                /*
                 Note: No supported codes appear to be defined. https://swagger.io/docs/specification/describing-responses/.
                 This is somewhat unexpected, so the code below is likely to lead to a failed expectation
                */
                .that(sco, List.of().contains(res_0.extract().statusCode()));
    }


    @Test
    @Timeout(60)
    public void test_1() throws Exception {

        given().accept("*/*")
                .header("x-EMextraHeader123", "42")
                .get(baseUrlOfSut + "/usuarios")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("size()", equalTo(0));

    }


    @Test
    @Timeout(60)
    public void test_2() throws Exception {

        given().accept("*/*")
                .header("x-EMextraHeader123", "_EM_1_XYZ_")
                .get(baseUrlOfSut + "/usuarios/763?EMextraParam123=_EM_0_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .body(isEmptyOrNullString());

    }


    @Test
    @Timeout(60)
    public void test_3() throws Exception {

        given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .delete(baseUrlOfSut + "/usuarios/817")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("text/plain")
                .body(containsString("ERROR: No existe ningún usuario con el ID 817."));

    }


    @Test
    @Timeout(60)
    public void test_4() throws Exception {

        given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .contentType("application/json")
                .body(" { " +
                        " \"id\": 105 " +
                        " } ")
                .put(baseUrlOfSut + "/usuarios/105")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("text/plain")
                .body(containsString("ERROR: No existe ningún usuario con el ID 105."));

    }


}