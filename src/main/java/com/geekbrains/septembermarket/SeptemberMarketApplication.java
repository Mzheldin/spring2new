package com.geekbrains.septembermarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.geekbrains.septembermarket"})
public class SeptemberMarketApplication {
    // Идеи:
    // Добавить H2 Database
    // Шифрование данных пользователей в таблице
    // Комментарии к заказу
    // Формат доставки (самовывоз-магазин, курьер)
    // Промокод к заказу
    // Дата доставки примерная
    // Анализ покупок
    // Подтверждение регистрации по email

    // Домашнее задание:
    // - Добавить этап перехода от формирования заказа к его оплате
    // - Создать учетную запись разработчика на PayPal, протестировать
    // работу PayPalController'а
    // - Улучшить логику PayPalController'a

    // Добавить до следующего занятия:
    // - Добавить страницу с историей заказов
    // - Попробовать добавить историю просмотра пользователем товаров
    // - Подумать куда лучше добавить WebSocket
    // - * Глобальный вопрос: организация категорий

    public static void main(String[] args) {
        SpringApplication.run(SeptemberMarketApplication.class, args);
    }
}
