package glue.steps;

import java.util.ArrayList;
import java.util.Map;

import org.testng.Assert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pages.HomePage;
import pages.Search;
import pages.SearchResults;

public class MyStepdefs extends Browser {


  private static String url;
  private static String endpoint;
  private static Response response;

  @Given("^browser \"([^\"]*)\" with remote address: \"([^\"]*)\"$")
  public void browserWithRemoteAddress(String browserName, String address) throws Throwable {
    openRemoteWebDriver(browserName, address);
  }

  @Given("^browser \"([^\"]*)\"$")
  public void browser(String browserName) throws Throwable {
    openLocalWebDriver(browserName);
  }

  @And("^website loaded this address: \"([^\"]*)\"$")
  public void websiteLoadedThisAddress(String url) throws Throwable {
    driver.get(url);
  }

  @When("^I execute a search for \"([^\"]*)\"$")
  public void iExecuteASearchFor(String textForSearching) throws Throwable {
    Search searchInStore = new Search();
    searchInStore.searchInStore(textForSearching);
  }

  //    @After
  public void tearDown() {
    driver.close();
  }

  @Then("^I should expect there is a result$")
  public void iShouldExpectThereIsAResult() throws Throwable {
    SearchResults searchResults = new SearchResults();
    searchResults.verifyResults();
  }

  @Then("^I should verify all buttons$")
  public void iShouldVerifyAllButtons() throws Throwable {
    HomePage homePage = new HomePage();
    homePage.verifyAllButons();
  }


  @Given("^base url: \"([^\"]*)\"$")
  public void baseUrl(String baseUrl) throws Throwable {
    this.url = baseUrl;
  }

  @And("^endpoint: \"([^\"]*)\"$")
  public void endpoint(String endpoint) throws Throwable {
    this.endpoint = endpoint;
  }

  @When("^I made a call$")
  public void iMadeACall() throws Throwable {
    //        https://airtube.info/api/get_data_current.php?location_id=3129
    response = RestAssured.given().get(this.url + this.endpoint);
    Assert.assertEquals(response.getStatusCode(), 200);
  }


  @Then("^I should receive data$")
  public void iShouldReceiveData() throws Throwable {
    ArrayList<ArrayList> spisuk = response.jsonPath().get("sensordatavalues");
    response.prettyPrint();
        /*int i = 0;
        for (ArrayList a : spisuk) {
            Map map = (Map) a.get(i);
            String value = map.get("value").toString();
            String valueType = map.get("value_type").toString();
            System.out.println("Stoinostta na prahovite cahstici "+valueType+": " + value);
            i++;
        }*/
  }

  @Then("^I should receive data for my repos$")
  public void iShouldReceiveDataForMyRepos() throws Throwable {
    response.prettyPrint();
  }

  @When("^I list all users$")
  public void iListAllUsers() throws Throwable {
    response = RestAssured.given().get(this.url + this.endpoint);
    Assert.assertEquals(response.getStatusCode(), 200);
  }

  @Then("^I should receive: (\\d+) users$")
  public void iShouldReceiveUsers(int numOfUsers) throws Throwable {
    ArrayList<ArrayList> listOfUsers = response.jsonPath().get("data");
    for (int i = 0; i < numOfUsers; i++) {
      System.out.println("*** First name: " + ((Map) listOfUsers.get(i)).get("first_name"));
    }
  }

  @When("^Add user$")
  public void addUser() throws Throwable {
    String body = "\"name\": \"morpheus\",\n" +
        "    \"job\": \"leader\"";
    response = RestAssured.given().baseUri(this.url + this.endpoint).contentType("aplication/json")
        .when().post(body);
  }


  @Then("^User added successfully$")
  public void userAddedSuccessfully() throws Throwable {
    Assert.assertEquals(response.getStatusCode(), 201);
  }

}
