CREATE TABLE accounts(
	account_id INT PRIMARY KEY auto_increment NOT NULL,
	name VARCHAR(20) NOT NULL,
	mail VARCHAR(100) NOT NULL,
	password VARCHAR(32) NOT NULL,
	authority INT NOT NULL DEFAULT '0'
);

INSERT INTO accounts
VALUES (1, 'イチロー', 'ichiro@sak.com', MD5('0123456'), 10);
INSERT INTO accounts
VALUES (2, 'ダルビッシュ 有', 'yu.darvish@sak.com', MD5('012345678'), 11);
INSERT INTO accounts
VALUES (3, '田中 将大', 'masahiro.tanaka@sak.com', MD5('awertyuio'), 11);
INSERT INTO accounts
VALUES (4, '松阪 大輔', 'daisuke.matsuzaka@sak.com', MD5('daisuke'), 0);
INSERT INTO accounts
VALUES (5, '本田 圭介', 'keisuke.honda@sak.com', MD5('a12s34d5f6'), 10);
INSERT INTO accounts
VALUES (6, '香川 真司', 'shinji.kagawa@sak.com', MD5('nikunikusii'), 1);
INSERT INTO accounts
VALUES (7, '内田 篤人', 'atsuto.uchida@sak.com', MD5('nyannyan'), 1);
INSERT INTO accounts
VALUES (8, '錦織 圭', 'kei.nishikori@sak.com', MD5('ujiko6789'), 10);
INSERT INTO accounts
VALUES (9, '池田 勇太', 'yuta.ikeda@sak.com', MD5('1234qwert'), 10);
INSERT INTO accounts
VALUES (10, '石川 遼', 'ryo.ishikawa@sak.com', MD5('@;:oijookk'), 1);
INSERT INTO accounts
VALUES (11, '上田 桃子', 'momoko.ueda@sak.com', MD5('MAXmomoko'), 1);

