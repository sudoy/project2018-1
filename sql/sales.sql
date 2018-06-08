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


