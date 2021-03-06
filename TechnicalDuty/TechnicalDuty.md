# Технический долг
## Анализ проекта на наличие технического долга
Проанализировав признаки технического долга на проекте, нами было обнаружено:
1) Дублирующийся код
2) Долгоживущие ветки
3) Отсутствие автоматизации тестов
## План мероприятий по устранению технического долга
| Мероприятия | Оценка в SP | 
|:---|:---|
| [Рефакторинг дублирующегося кода](https://trello.com/c/vyRyUFRY/12-%D1%80%D0%B5%D1%84%D0%B0%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D0%BD%D0%B3-%D0%B4%D1%83%D0%B1%D0%BB%D0%B8%D1%80%D1%83%D1%8E%D1%89%D0%B5%D0%B3%D0%BE%D1%81%D1%8F-%D0%BA%D0%BE%D0%B4%D0%B0) | 2 |
| [Смержить ветки в master](https://trello.com/c/NfsfPWOk/13-%D1%81%D0%BC%D0%B5%D1%80%D0%B6%D0%B8%D1%82%D1%8C-ui-%D0%B2-master) | 2 |
| [Автоматизация тестов](https://trello.com/c/D6PHkWJ9/14-%D0%B0%D0%B2%D1%82%D0%BE%D0%BC%D0%B0%D1%82%D0%B8%D0%B7%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%BD%D1%8B%D0%B5-%D1%82%D0%B5%D1%81%D1%82%D1%8B) | 3 |
## Сравнить объём долга и недоимплементированных фич
Суммарный объём технического долга составляет 7 SP. При этом объём недоимплементированных фич состовляет 27 SP.
## Вывод
Так как за прошлый спринт мы выполнили 26 SP, то мы не успеем выполнить мероприятия по устранению технического долга и незавершённые ещё задачи основного функционала, поэтому максимальный приоритет остаётся у основных задач, а технический долг будет устраняться, если останется время.
