package get_requests;

import base_url.RestfulBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import utils.ObjectMapperUtils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class Get15 extends RestfulBaseUrl {

/*
        Given
	            https://restful-booker.herokuapp.com/booking/22
        When
		 		I send GET Request to the URL
		Then
		 		Status code is 200
           {
    "firstname": "Guoqiang",
    "lastname": "Morante Briones",
    "totalprice": 111,
    "depositpaid": true,
    "bookingdates": {
        "checkin": "2018-01-01",
        "checkout": "2019-01-01"
    },
    "additionalneeds": "Breakfast"
}
     */

    @Test
    public void get15() {

        //Set the Url
        spec.pathParams("first","booking","second",22);

        //Set the Expected Data
        BookingDatesPojo bookingDatesPojo = new BookingDatesPojo("2018-01-01","2019-01-01");
        BookingPojo expectedData = new BookingPojo("Guoqiang","Morante Briones",111,true,bookingDatesPojo,"Breakfast");
        System.out.println("expectedData = " + expectedData);

        //Send the request and get the response
        Response response = given().spec(spec).contentType(ContentType.JSON).when().get("/{first}/{second}");
        response.prettyPrint();


        //Do Assertion
        BookingPojo actualData = ObjectMapperUtils.convertJsonToJava(response.asString(),BookingPojo.class);

        //Soft Assertion
        //1.Adim : SoftAssert objesi oluştur
        SoftAssert softAssert= new SoftAssert();

        //2.Adım: Assertion Yap
        softAssert.assertEquals(actualData.getFirstname(),expectedData.getFirstname(),"Firsname uyusmadi...");
        softAssert.assertEquals(actualData.getLastname(),expectedData.getLastname(),"Lastname uyusmadi...");
        softAssert.assertEquals(actualData.getTotalprice(),expectedData.getTotalprice(),"Totalprice uyusmadi...");
        softAssert.assertEquals(actualData.getDepositpaid(),expectedData.getDepositpaid(),"Depositpaid uyusmadi...");
        softAssert.assertEquals(actualData.getAdditionalneeds(),expectedData.getAdditionalneeds(),"Additionalneeds uyusmadi...");

        softAssert.assertEquals(actualData.getBookingdates().getCheckin(),bookingDatesPojo.getCheckin(),"Checkin uyusmadi...");
        softAssert.assertEquals(actualData.getBookingdates().getCheckout(),bookingDatesPojo.getCheckout(),"Checkout uyusmadi...");

        //3.Adım: assertAll methodunu kullan
        softAssert.assertAll();


        //Hard Assertion
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedData.getFirstname(), actualData.getFirstname());
        assertEquals(expectedData.getLastname(), actualData.getLastname());
        assertEquals(expectedData.getTotalprice(), actualData.getTotalprice());
        assertEquals(expectedData.getDepositpaid(), actualData.getDepositpaid());
        assertEquals(expectedData.getAdditionalneeds(), actualData.getAdditionalneeds());
        assertEquals(bookingDatesPojo.getCheckin(), actualData.getBookingdates().getCheckin());
        assertEquals(expectedData.getBookingdates().getCheckout(), actualData.getBookingdates().getCheckout());






    }
}
