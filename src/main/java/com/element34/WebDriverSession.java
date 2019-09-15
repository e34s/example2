package com.element34;

import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverSession {

   static final ThreadLocal<RemoteWebDriver> sessions = new ThreadLocal<RemoteWebDriver>();


   public static RemoteWebDriver driver(){
      return sessions.get();
   }
}
