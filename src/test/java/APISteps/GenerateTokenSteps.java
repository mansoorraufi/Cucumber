package APISteps;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.APIConstants;

import static io.restassured.RestAssured.given;

public class GenerateTokenSteps {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";

    //to use my token in the project I am using static variable
    public static String token;

    @Given("a JWT is generated")
    public void a_jwt_is_generated() {
        RequestSpecification request = given().header(APIConstants.Header_Key_Content_Type,APIConstants.Header_Value_Content_Type).body("{\n" +
                "  \n" +
                "  \"email\": \"mansoor@test.com\",\n" +
                "  \"password\": \"Test@12\"\n" +
                "}");
        Response response=request.when().post(APIConstants.GENERATE_TOKEN_URI);
       // printing response body (this option is only for API insteat of system.out.print)
       // response.prettyPrint();

        // printing only the value with Bearer key (token type) which is important
        token =  "Bearer " + response.jsonPath().getString("token");
        System.out.println(token);

    }

}
