INSERT INTO reservation_time(start_at)
VALUES ('10:00');
INSERT INTO reservation_time(start_at)
VALUES ('11:00');
INSERT INTO reservation_time(start_at)
VALUES ('12:00');
INSERT INTO reservation_time(start_at)
VALUES ('13:00');
INSERT INTO theme(name, description, thumbnail)
VALUES ('레벨1 탈출', '우테코 레벨2를 탈출하는 내용입니다.', 'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg');
INSERT INTO theme(name, description, thumbnail)
VALUES ('레벨2 탈출', '우테코 레벨3를 탈출하는 내용입니다.', 'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg');
INSERT INTO theme(name, description, thumbnail)
VALUES ('레벨3 탈출', '우테코 레벨4를 탈출하는 내용입니다.', 'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg');
INSERT INTO theme(name, description, thumbnail)
VALUES ('레벨4 탈출', '우테코 레벨5를 탈출하는 내용입니다.', 'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg');
INSERT INTO reservation(name, date, time_id, theme_id)
VALUES ('애쉬', '2024-05-01', 3, 2);
INSERT INTO reservation(name, date, time_id, theme_id)
VALUES ('애쉬', '2024-05-01', 2, 2);
INSERT INTO reservation(name, date, time_id, theme_id)
VALUES ('애쉬', '2024-04-30', 2, 2);
INSERT INTO reservation(name, date, time_id, theme_id)
VALUES ('애쉬', '2024-04-30', 1, 1);
INSERT INTO reservation(name, date, time_id, theme_id)
VALUES ('애쉬', '2024-04-02', 3, 3);
INSERT INTO reservation(name, date, time_id, theme_id)
VALUES ('애쉬', '2024-03-02', 3, 3);
INSERT INTO reservation(name, date, time_id, theme_id)
VALUES ('애쉬', '2099-04-30', 1, 1);

