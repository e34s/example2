package com.element34;

import static com.element34.WebDriverSession.driver;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({WebDriverListener.class,ParallelMethods.class})
public class PostResultExample {



  @Test
  public void passedChrome(){
    driver().get("https://www.google.com/ncr");
    Assert.assertEquals(driver().getTitle(), "Google");
  }

  @Test
  public void failedChrome(){
    driver().get("https://www.google.com/ncr");
    Assert.assertEquals(driver().getTitle(), "Not Google");
  }
}
