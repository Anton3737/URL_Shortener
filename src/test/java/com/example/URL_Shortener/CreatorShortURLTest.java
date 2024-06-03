package com.example.URL_Shortener;

import com.example.URL_Shortener.responseDTO.NewShortURL;
import com.example.URL_Shortener.service.CreatorShortURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreatorShortURLTest {

    private CreatorShortURL creatorShortURL;

    @Value("${short.url.template}")
    private String shortUrlTemplate;

    @Value("${short.url.validity.days}")
    private int validityDays;

    @BeforeEach
        // Налаштовуємо середовище перед кожним тестовим методом.
    void setUp() {
        creatorShortURL = new CreatorShortURL();
    }

    @Test
        // Тестуємо створення короткого URL.
    void testCreateShortURL() {
        // Дано
        String originURL = "https://goit.global/ua";

        // Коли
        NewShortURL newShortURL = creatorShortURL.createShortURL(originURL);

        // Тоді
        assertNotNull(newShortURL); // Перевіряємо, що створений короткий URL не є null
        assertEquals(originURL, newShortURL.getOriginURL()); // Перевіряємо, що початковий URL співпадає
        assertNotNull(newShortURL.getShortURL()); // Перевіряємо, що короткий URL не є null
        assertTrue(newShortURL.getShortURL().matches(shortUrlTemplate.replace("{shortURL}", "[A-Za-z0-9]{6,8}"))); // Перевіряємо, що короткий URL відповідає шаблону
        assertEquals(LocalDate.now(), newShortURL.getCreatingDate()); // Перевіряємо, що дата створення - сьогодні
        assertEquals(LocalDate.now().plusDays(validityDays), newShortURL.getFinishingDate()); // Перевіряємо, що дата закінчення відповідає конфігурації
        assertEquals(0L, newShortURL.getCountUse()); // Перевіряємо, що кількість використань спочатку дорівнює 0
    }

    @Test
        // Тестуємо генерацію короткого URL.
    void testGenerateShortURL() {
        // Повторюємо генерацію короткого URL багато разів для впевненості
        for (int i = 0; i < 1000; i++) {
            // Коли
            String shortURL = creatorShortURL.generateShortURL();

            // Тоді
            assertNotNull(shortURL); // Перевіряємо, що згенерований короткий URL не є null
            assertTrue(shortURL.length() >= 6 && shortURL.length() <= 8); // Перевіряємо, що довжина короткого URL знаходиться від 6 до 8 символів
            assertTrue(shortURL.matches("[A-Za-z0-9]+")); // Перевіряємо, що короткий URL містить лише буквено-цифрові символи
        }
    }
}