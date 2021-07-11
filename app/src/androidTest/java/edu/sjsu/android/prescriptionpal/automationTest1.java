package edu.sjsu.android.prescriptionpal;

import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class automationTest1 {
    public static void main(String args[]) throws MalformedURLException, InterruptedException {

        // Test precondition
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
        dc.setCapability("platformName","android");
        dc.setCapability("appPackage","edu.sjsu.android.prescriptionpal");
        dc.setCapability("appActivity","edu.sjsu.android.prescriptionpal.StartActivity");

        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),dc);

        // Test 1: test if the user can login successfully with a valid email and password combination.
        TimeUnit.SECONDS.sleep(3);
        MobileElement el1 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/button_swithToLogin");
        el1.click();
        TimeUnit.SECONDS.sleep(3);
        MobileElement el2 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/login_email");
        el2.sendKeys("testautomation1@gmail.com");
        TimeUnit.SECONDS.sleep(3);
        MobileElement el3 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/login_pw");
        el3.sendKeys("12345678");
        TimeUnit.SECONDS.sleep(3);
        MobileElement el4 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/button_login");
        el4.click();

        // Test post condition
        TimeUnit.SECONDS.sleep(5);
        MobileElement result = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/Title");
        Assert.assertEquals(result.getText(), "Select Account Type");
        TimeUnit.SECONDS.sleep(3);
        driver.quit();
    }

}
