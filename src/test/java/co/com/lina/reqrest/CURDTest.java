package co.com.lina.reqrest;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SerenityRunner.class)
public class CURDTest extends TestBase {

    private String name = "morpheus";
    private String job = "leader";

    @Title("This test will create a new user")
    @Test
    public void createNewUser() {

        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setJob(job);

        SerenityRest.rest()
                .given()
                .header("Content-type", "application/json")

                .when()
                .body(userPojo)
                .post("/users")

                .then()
                .log()
                .body()
                .statusCode(201);
    }


    @Title("This test will Verify an existing user of the application")
    @Test
    public void verifyUserExist() {
        SerenityRest.rest()
                .given()
                .queryParam("page", "1")
                .when()
                .get("/users")
                .then()
                .log()
                .body()
                .body("data[1].first_name", equalTo("Janet"))
                .statusCode(200);
    }

    @Title("This test will check user update ")
    @Test
    public void updateUser() {

        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setJob("zion resident");

        SerenityRest.rest()
                .given()
                .header("Content-Type", "application/json")
                .when()
                .body(userPojo)
                .put("/2")
                .then()
                .log()
                .body()
                .log()
                .status()
                .body("name", equalTo(name))
                .body("job", equalTo("zion resident"))
                .statusCode(200);
    }

    @Title("This test will Delete a user")
    @Test
    public void deleteUser() {
        SerenityRest.rest()
                .given()
                .when()
                .delete("/users/2")
                .then()
                .log()
                .status()
                .statusCode(204);
    }

}