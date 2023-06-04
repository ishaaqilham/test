import static io.restassured.RestAssured.*;

import common.Constants;
import common.ResponseCodes;
import data.CategoryDetailsData;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class CategoryDetailsTest {
    SoftAssert softAssert;
    JSONObject jsonObject;

    @Test
    public void testGetCategoryDetails() throws IOException, ParseException {
        softAssert = new SoftAssert();

        CategoryDetailsData categoryDetailsData =
                new CategoryDetailsData("src/test/resources/DetailsCatalogueFalseData.json");

        String response =
                given()
                        .baseUri(Constants.BASE_URL)
                        .basePath(String.format("/%s/Categories/6327/Details.json", Constants.API_VERSION))
                        .contentType(ContentType.JSON)
                        .queryParam("catalogue", categoryDetailsData.getCatalogue())
                        .log().all()
                .when()
                        .get()
                .then()
                        .log().all()
                        .statusCode(ResponseCodes.RESPONSE_CODE_200)
                        .extract().asString();

        jsonObject = new JSONObject(response);

        softAssert.assertEquals(jsonObject.get("Name"), "Carbon credits", "Name mismatch");
        softAssert.assertEquals(jsonObject.get("CanRelist"), true, "The value for CanRelist is not true");

        jsonObject = new JSONObject(jsonObject.getJSONArray("Promotions").get(1).toString());

        softAssert.assertEquals(jsonObject.get("Description"), "Good position in category",
                "Description for Gallery promotions is not correct");

        softAssert.assertAll();

    }

}
