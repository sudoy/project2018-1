
売上データ（5月～6月）
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/05/01', 3, 6, 'マルボロゴールド', 470, 2, '毎週買って行くお客さん');
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/08', 1, 6, 'マルボロゴールド', 470, 2);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/05/12', 1, 2, '富士の天然水2L', 100, 12, '2ケース購入');
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/15', 5, 6, 'マルボロゴールド', 470, 2);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/21', 1, 1, '豆大福', 120, 3);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/22', 2, 6, 'マルボロゴールド', 470, 2);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/05/29', 5, 6, 'マルボロゴールド', 470, 2);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/05/31', 2, 7, 'ビニール傘', 500, 20, 'ゲリラ豪雨で一気に売れた');
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number)
VALUES ('2018/06/01', 4, 5, '冷凍マンゴー', 350, 1);
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/06/05', 3, 6, 'アイコスレギュラー', 470, 2, '今月からIQOSに変更');
INSERT INTO sales (sale_date, account_id, category_id, trade_name, unit_price, sale_number,note)
VALUES ('2018/06/30', 4, 7, '虫除けスプレー', 520, 1, '明日から夏向け商品を展開');


商品カテゴリー（売上フラグの確認用）
INSERT INTO categories(category_name, active_flg) VALUES ('嗜好品', 0);
INSERT INTO categories(category_name, active_flg) VALUES ('新聞', 0);