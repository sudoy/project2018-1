CREATE TABLE sales(
	sale_id INT PRIMARY KEY auto_increment NOT NULL,
	sale_date DATE NOT NULL,
	account_id INT NOT NULL,
	category_id INT NOT NULL,
	trade_name VARCHAR(100) NOT NULL,
	unit_price INT NOT NULL,
	sale_number INT NOT NULL,
	note TEXT DEFAULT NULL
);

INSERT INTO sales VALUES (
	1, '2015/1/15', 1, 1, 'からあげ弁当', 450, 3, '今日からの新商品'
);
INSERT INTO sales VALUES (
	2, '2015/1/15', 1, 1, 'あんぱん', 120, 10, NULL
);
INSERT INTO sales VALUES (
	3, '2015/1/15', 1, 2, 'コカコーラ 500ml', 130, 5, NULL
);
INSERT INTO sales VALUES (
	4, '2015/1/15', 5, 7, 'Yシャツ白 M', 1200, 1, NULL
);
INSERT INTO sales VALUES (
	5, '2015/1/14', 8, 1, 'とろっと玉子のオムライスドリア', 380, 8, NULL
);
INSERT INTO sales VALUES (
	6, '2015/1/14', 8, 3, '週刊少年マガジン2015年7号', 250, 15, NULL
);
INSERT INTO sales VALUES (
	7, '2015/1/14', 9, 3, 'NHKラジオ ラジオ英会話2015年02月号', 780, 1, NULL
);
INSERT INTO sales VALUES (
	8, '2015/1/14', 5, 1, 'チーズケーキ', 220, 4, '電子レンジで温めないこと'
);
INSERT INTO sales VALUES (
	9, '2015/1/14', 5, 1, '麻婆&海老チリ炒飯', 520, 9, '団体客'
);
INSERT INTO sales VALUES (
	10, '2015/1/14', 5, 2, 'アクエリアス 2L', 260, 2, NULL
);
INSERT INTO sales VALUES (
	11, '2015/1/14', 8, 7, '靴下黒 28cm', 800, 1, '午後から雨が降ってきた'
);
INSERT INTO sales VALUES (
	12, '2015/1/13', 10, 4, 'アサヒビール', 250, 6, NULL
);
INSERT INTO sales VALUES (
	13, '2014/12/31', 7, 1, '洋風おせち二段重', 12000, 1, NULL
);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/05/01', 3, 6, 'マルボロゴールド', 470, 2, '毎週買って行くお客さん');
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/08', 4, 6, 'マルボロゴールド', 470, 2);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/05/12', 1, 2, '富士の天然水2L', 100, 12, '2ケース購入');
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/15', 5, 6, 'マルボロゴールド', 470, 2);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/21', 4, 1, '豆大福', 120, 3);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/22', 2, 6, 'マルボロゴールド', 470, 2);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/29', 5, 6, 'マルボロゴールド', 470, 2);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/05/31', 2, 7, 'ビニール傘', 500, 20, 'ゲリラ豪雨で一気に売れた');
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/06/01', 4, 5, '冷凍マンゴー', 350, 1);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/06/05', 4, 6, 'アイコスレギュラー', 470, 2, '今月からIQOSに変更');
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/06/30', 4, 7, '虫除けスプレー', 520, 1, '明日から夏向け商品を展開');


