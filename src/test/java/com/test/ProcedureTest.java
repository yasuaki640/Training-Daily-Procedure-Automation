package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProcedureTest {
    private WebDriver webDriver;

    @Before
    public void createDriver() {
        System.setProperty("webdriver.chrome.driver", "selenium/chromedriver/83/chromedriver");
        webDriver = new ChromeDriver();
    }

    @After
    public void quitDriver() {
        webDriver.close();
    }

    @Test
    public void normalRegisterCond() {
        //指定したURLに遷移する
        webDriver.get("https://learning-portal.kronos.jp/auth/login");

        // 最大5秒間、ページが完全に読み込まれるまで待つ
        webDriver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);

        // 指定されたname属性を持つフォームにユーザー情報を入力
        WebElement nameInput = webDriver.findElement(By.name("email"));
        nameInput.sendKeys("");

        WebElement passwordInput = webDriver.findElement(By.name("password"));
        passwordInput.sendKeys("");

        WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
        loginButton.click();

        List<WebElement> PhysicalCondRadios = webDriver.findElements(By.cssSelector("input[type='radio'][value='1']"));
        for (int i = 0; i < 6; i++) {
            PhysicalCondRadios.get(i).click();
        }

        WebElement motivationRadio = webDriver.findElement(By.cssSelector("input[type='radio'][value='4']"));
        motivationRadio.click();

        WebElement registerButton = webDriver.findElement(By.cssSelector("input[type='submit'][value='登録']"));
        registerButton.click();

        //正規表現によるパターンマッチングの準備
        String AppearedLabel = webDriver.findElement(By.className("alert-success")).getText();
        Pattern expectedPattern = Pattern.compile("の出席登録が完了しました。$");

        // 出現したラベルを用いて検証
        assertThat(expectedPattern.matcher(AppearedLabel).find(), is(true));

    }

}
