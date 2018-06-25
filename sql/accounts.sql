CREATE TABLE accounts(
	account_id INT PRIMARY KEY auto_increment NOT NULL,
	name VARCHAR(20) NOT NULL,
	kana VARCHAR(50) NOT NULL,
	mail VARCHAR(100) NOT NULL,
	password VARCHAR(32) NOT NULL,
	authority INT NOT NULL DEFAULT '0'
);

INSERT INTO accounts
VALUES (1, 'Admin', 'yuichi.sudo@ssie.jp', MD5('0000'), 10);
INSERT INTO accounts
VALUES (2, '土樋奎太', 'tsuchitoi.keita@mail.tokyosystem.co.jp', MD5('0000'), 1);
INSERT INTO accounts
VALUES (3, '笹原颯', 'sasahara.hayate@mail.tokyosystem.co.jp', MD5('0000'), 1);
INSERT INTO accounts
VALUES (4, '中村楓佳', 'nakamura.fuka@mail.tokyosystem.co.jp', MD5('0000'), 1);
INSERT INTO accounts
VALUES (5, '阪本真由', 'sakamoto.mayu@mail.tokyosystem.co.jp', MD5('0000'), 1);
INSERT INTO accounts
VALUES (6, '叶内森', 'kanauchi.shin@mail.tokyosystem.co.jp', MD5('0000'), 1);
INSERT INTO accounts
VALUES (7, '野島紳司', 'nojima.shinji@mail.tokyosystem.co.jp', MD5('0000'), 1);
INSERT INTO accounts
VALUES (8, '木村有理人', 'kimura.arito@mail.tokyosystem.co.jp', MD5('0000'), 1);

INSERT INTO accounts (name, kana, mail, password, authority)
VALUES ('土樋奎太', 'つちといけいた', 'tsuchitoi.keita@mail.tokyosystem.co.jp', MD5('0000'), '11');
INSERT INTO accounts (name, kana, mail, password, authority)
VALUES ('笹原颯', 'ささはらはやて', 'sasahara.hayate@mail.tokyosystem.co.jp', MD5('0000'), '11');
INSERT INTO accounts (name, kana, mail, password, authority)
VALUES ('中村楓佳', 'なかむらふうか', 'nakamura.fuka@mail.tokyosystem.co.jp', MD5('0000'), '11');
INSERT INTO accounts (name, kana, mail, password, authority)
VALUES ('Admin', 'あどみん', 'yuichi.sudo@ssie.jp', MD5('0000'), '11');

