package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;


import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {


    static Faker faker = new Faker(new Locale("ru"));

    static DataName dataName(String status) {

        return new DataName(faker.name().lastName(), faker.internet().password(), status);
    }

    static String noValidLog() {
        String noValidLog = faker.name().username();
        return noValidLog;
    }

    static String noValidPass() {
        String noValidPass = faker.internet().password();
        return noValidPass;
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll(DataName usr) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(usr) // передаём в теле
                // объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }
}