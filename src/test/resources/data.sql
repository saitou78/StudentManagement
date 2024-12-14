INSERT INTO students (name, frigana, nickname, email, address, age, gender, remark, is_deleted)
VALUES
('斎藤りこ', 'サイトウリコ', NULL, 'saitou@mail', '東京', 23, '女', NULL, 0),
('鈴木花子', 'スズキハナコ', NULL, 'hanako@mail', '東京', 35, '女', NULL, 0),
('山田勇人', 'ヤマダハヤト', NULL, 'hayato@mail', '千葉', 20, '男', NULL, 0),
('榎並勇人', 'エナミハヤト', NULL, 'enami@mail', '千葉', 20, '男', NULL, 0),
('榎並浩二', 'エナミコウジ', NULL, 'kouji@mail', '千葉', 20, '男', 'カレー好き', 0);

INSERT INTO students_courses (student_id, course_name, start_date, final_date)
VALUES
(1, 'プログラミング基礎', '2024-01-01 09:00:00', '2024-12-31 18:00:00'),
(2, 'データベース設計', '2023-04-01 09:00:00', '2024-03-31 18:00:00'),
(3, 'Javaプログラミング', '2024-01-01 10:00:00', '2025-01-01 18:00:00'),
(4, 'Webアプリケーション開発', '2024-06-01 09:00:00', '2025-06-01 18:00:00'),
(5, 'プログラミング基礎', '2024-01-01 09:00:00', '2024-12-31 18:00:00'),
(5, 'Python入門', '2023-09-01 09:00:00', '2024-09-01 18:00:00');

INSERT INTO course_applications (course_id, student_id, status)
VALUES
(1, 1, '本申し込み'),
(2, 2, '仮申し込み'),
(3, 3, '受講中'),
(4, 4, '受講終了'),
(5, 5, '本申し込み'),
(6, 5, '本申し込み');