CREATE TABLE categories(
	category_id INT PRIMARY KEY auto_increment NOT NULL,
	category_name VARCHAR(50) NOT NULL,
	active_flg INT NOT NULL DEFAULT '1'
);

INSERT INTO categories(category_name, active_flg) VALUES ('水産・畜産・農産加工品', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('生鮮・チルド・冷凍食品', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('乳油製品・調味料・調味食品', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('麺類', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('スープ類', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('菓子類', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('嗜好飲料', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('飲料', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('酒類', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('氷・アイスクリーム類', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('デザート類', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('健康サポート', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('化粧品', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('トイレタリー', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('文具・仏具・雑貨', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('たばこ', 1);
INSERT INTO categories(category_name, active_flg) VALUES ('水産', 0);
INSERT INTO categories(category_name, active_flg) VALUES ('畜産', 0);
INSERT INTO categories(category_name, active_flg) VALUES ('農産加工品', 0);
INSERT INTO categories(category_name, active_flg) VALUES ('生鮮', 0);
INSERT INTO categories(category_name, active_flg) VALUES ('チルド', 0);
INSERT INTO categories(category_name, active_flg) VALUES ('冷凍食品', 0);
