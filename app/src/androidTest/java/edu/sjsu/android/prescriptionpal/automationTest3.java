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

public class automationTest3 {
    public static void main(String args[]) throws MalformedURLException, InterruptedException {

        // Test precondition
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
        dc.setCapability("platformName","android");
        dc.setCapability("appPackage","edu.sjsu.android.prescriptionpal");
        dc.setCapability("appActivity","edu.sjsu.android.prescriptionpal.StartActivity");

        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),dc);

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


        TimeUnit.SECONDS.sleep(3);
        MobileElement el5 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/Patient");
        el5.click();
        TimeUnit.SECONDS.sleep(3);
        MobileElement el6 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/age");
        el6.sendKeys("21");
        TimeUnit.SECONDS.sleep(3);
        MobileElement el7 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/contact_number");
        el7.sendKeys("3100001111");
        TimeUnit.SECONDS.sleep(3);
        MobileElement el8 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/address");
        el8.sendKeys("123 Main Street, San Jose, CA95134");
        TimeUnit.SECONDS.sleep(3);
        MobileElement el9 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/button_signup");
        el9.click();

        // Test 3: test if user can change his or her default pharmacy store
        TimeUnit.SECONDS.sleep(3);
        MobileElement el10 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/button_pharmacy");
        el10.click();
        TimeUnit.SECONDS.sleep(3);
        MobileElement el11 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/change_pharmacy_button");
        el11.click();
        TimeUnit.SECONDS.sleep(3);
        MobileElement el12 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/spinner1");
        el12.click();
        TimeUnit.SECONDS.sleep(3);
        MobileElement el13 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[4]");
        el13.click();
        TimeUnit.SECONDS.sleep(3);
        MobileElement el14 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/button_change_pharmacy_save");
        el14.click();

        // Test post condition
        TimeUnit.SECONDS.sleep(3);
        MobileElement result3 = (MobileElement) driver.findElementById("edu.sjsu.android.prescriptionpal:id/textView_pharmacy_name");
        Assert.assertEquals(result3.getText(), "Costco Pharmacy");
        TimeUnit.SECONDS.sleep(3);
        driver.quit();
    }
}
