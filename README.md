# Симулятор проекта с kafka

## Описание
- rate-producer публикует цену на USD в топик prices.
- сервисы deposit и loan вычитывают сообщения и используют цены в своей логике
- опубликовать новую цену: `GET localhost:8081/rate/98.01`
- получить инфу по депозиту: `GET localhost:8083/deposit`
- получить инфу по кредиту: `GET localhost:8082/loan`

## Запуск
1. Поднять kafka `docker-compose up -d`
2. Запуск rate-producer: метод `main()` в классе `MainProducer`
3. Запуск loan service: метод `main()` в классе `MainLoan`
4. Запуск deposit service: метод `main()` в классе `MainDeposit`
5. Все api-тесты в модуле `tests`

## Настройка
В файле `src/main/resources/application.properties` есть три настройки:
- `price.auto-publisher.enabled=true` – включить автоматическую публикацию цен (`boolean`)
- `price.auto-publisher.min_price=90.01` – минимальная цена USD (`double`)
- `price.auto-publisher.max_price=100.11` – максимальная цена USD (`double`)