package APIPackage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

// to change the order of execution
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExamples {

    //one thing to remember
    //base URI - base URL
    //end then using when keyword, we will send the end point


    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";

    // we need to perform CURD operation
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NzQwODAyODQsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY3NDEyMzQ4NCwidXNlcklkIjoiNDc1NiJ9.YBrGjEIhFrhBul8KSHMOpFoEAgot2jlofha6LyJdTVo";
  static   String employee_id;

    @Test
    public void bGetOneEmployee(){
        //prepare the request
        //to prepare the request, we use request specification
        RequestSpecification request = given().header("Authorization", token)
                .header("Content-Type", "application/json")
                .queryParam("employee_id",employee_id);

        //to hit the end point/ to make the request which will return response
        Response response = request.when().get("/getOneEmployee.php");

        // System.out.println(response.asString());
        response.prettyPrint();
        //verifying the status code
        response.then().assertThat().statusCode(200);

        //using jsonpath method, we are extracting the value from the response body
        String firstName = response.jsonPath().getString("employee.emp_firstname");
        System.out.println(firstName);
        //first way of assertion
        Assert.assertEquals(firstName, "Mansoor");

        //second way of assertion to verify the value in response body using hamcrest matchers
        response.then().assertThat().body("employee.emp_firstname", equalTo("Mansoor"));


    }
@Test
    public void aCreateEmployee(){
        RequestSpecification request = given().header("Authorization", token)
                .header("Content-Type", "application/json").body("{\n" +
                        "  \"emp_firstname\": \"Mansoor\",\n" +
                        "  \"emp_lastname\": \"Raufi\",\n" +
                        "  \"emp_middle_name\": \"MR\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2000-01-02\",\n" +
                        "  \"emp_status\": \"Permenent\",\n" +
                        "  \"emp_job_title\": \"QA Engineer\"\n" +
                        "}");
        Response response = request.when().post("/createEmployee.php");
        response.prettyPrint();

        employee_id = response.jsonPath().getString("Employee.employee_id");
        System.out.println(employee_id);


    }
@Test
    public void cUpdateEmployee(){
        RequestSpecification request = given().header("Authorization", token)
                .header("Content-Type", "application/json").
                body("{\n" +
                        "  \"employee_id\": \""+employee_id+"\",\n" +
                        "  \"emp_firstname\": \"Ahmad\",\n" +
                        "  \"emp_lastname\": \"Mohammad\",\n" +
                        "  \"emp_middle_name\": \"Ms\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2003-06-05\",\n" +
                        "  \"emp_status\": \"Active\",\n" +
                        "  \"emp_job_title\": \"QA Engineer\"\n" +
                        "}");
     Response response= request.when().put("/updateEmployee.php");
     response.prettyPrint();
     //verification
     response.then().assertThat().statusCode(200);
     response.then().assertThat().body("Message", equalTo("Employee record Updated"));

    }
@Test
    public void dGetUpdatedEmployee(){
        RequestSpecification request = given().header("Authorization", token)
                .header("Content-Type", "application/json")
                .queryParam("employee_id", employee_id);

        //to hit the end point/ to make the request which will return response
        Response response = request.when().get("/getOneEmployee.php");

        // System.out.println(response.asString());
        response.prettyPrint();
        //verifying the status code
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("employee.emp_job_title", equalTo("QA Engineer"));
    }


}
