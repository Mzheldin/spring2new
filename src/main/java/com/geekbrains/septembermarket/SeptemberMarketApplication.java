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
    // Способ оплаты
    // Дата доставки примерная
    // В куки добавить историю просмотра
    // Анализ покупок
    // Подтверждение регистрации по email

    // Домашнее задание:
    // - Добавить категории товаров и соответствующий фильтр для поиска
    // - Попробовать добавить историю просмотра пользователем товаров
    // - Добавить страницу с историей заказов

    // Основы проекта:
    // Мы считаем что работаем только с учетными записями полкупателей

    public static void main(String[] args) {
        SpringApplication.run(SeptemberMarketApplication.class, args);
    }
}
