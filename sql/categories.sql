CREATE TABLE categories(
	category_id INT PRIMARY KEY auto_increment NOT NULL,
	category_name VARCHAR(50) NOT NULL,
	active_flg INT NOT NULL DEFAULT '1'
);

INSERT INTO categories VALUES (1, '食料品', 1);
INSERT INTO categories VALUES (2, '飲料', 1);
INSERT INTO categories VALUES (3, '本・雑誌', 1);
INSERT INTO categories VALUES (4, '酒類', 1);
INSERT INTO categories VALUES (5, '冷凍食品', 1);
INSERT INTO categories VALUES (6, 'たばこ', 1);
INSERT INTO categories VALUES (7, 'その他', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('嗜好品', 0);
INSERT INTO categories(category_name, active_flg) VALUES ('新聞', 0);
