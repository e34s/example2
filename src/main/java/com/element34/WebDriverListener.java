package com.element34;

import static com.element34.WebDriverSession.sessions;
import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class WebDriverListener implements IInvokedMethodListener2 {

  private static final String BASE = "http://localhost";
  private static final int PORT = 4444;
  private static final String HUB = BASE + ":" + PORT + "/wd/hub";


  static {
    RestAssured.baseURI = BASE;
    RestAssured.port = PORT;
    RestAssured.basePath = "/e34/api";
  }

  public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    MutableCapabilities options = new ChromeOptions();

    options.setCapability("e34:l_testName", method.getTestMethod().getMethodName());
    options.setCapability("e34:video", true);
    options.setCapability("e34:token", "e58b8440-78e1-47");
    try {
      RemoteWebDriver driver = new RemoteWebDriver(new URL(HUB), options);
      sessions.set(driver);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    RemoteWebDriver driver = sessions.get();
    String sessionid = null;
    if (driver != null) {
      sessionid = driver.getSessionId().toString();
      driver.quit();
    }

    if (sessionid != null) {
      given()
          .queryParam("sessionId", sessionid)
          .queryParam("passed", testResult.getStatus() == ITestResult.SUCCESS)
          .post("/test-data");

    }
  }

  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

  }

  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

  }
}
