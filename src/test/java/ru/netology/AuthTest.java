package ru.netology;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class AuthTest {

    @BeforeEach
    void befor() {
        open("http://localhost:9999");
    }

    @Test
    void inputValidLogPasStatActive() {
        val user = DataGenerator.dataName("active");
        DataGenerator.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("body div#root h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void inputValidLogPasStat_Bocked() {
        val user = DataGenerator.dataName("blocked");
        DataGenerator.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[class=notification__content]").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void inputNo_Valid_LogValidPasStatActive() {
        val user = DataGenerator.dataName("active");
        DataGenerator.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(DataGenerator.noValidLog());
        $("span[data-test-id='password'] input").setValue(user.getPassword());
        $("button[data-test-id='action-login']").click();
        $("[class=notification__content]").shouldHave(text("Неверно указан логин или пароль"));
    }
    @Test
    void inputValidLogNo_Valid_PasStatActive() {
        val user = DataGenerator.dataName("active");
        DataGenerator.setUpAll(user);
        $("span[data-test-id='login'] input").setValue(user.getLogin());
        $("span[data-test-id='password'] input").setValue(DataGenerator.noValidPass());
        $("button[data-test-id='action-login']").click();
        $("[class=notification__content]").shouldHave(text("Неверно указан логин или пароль"));
    }
//    ...
//Для активации этого тестового режима при запуске SUT нужно указать флаг -P:profile=test, т.е.:
// java -jar app-ibank.jar -P:profile=test
}