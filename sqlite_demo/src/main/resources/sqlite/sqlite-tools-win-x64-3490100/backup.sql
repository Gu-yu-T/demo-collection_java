PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "User"
(
	id INT not null /*autoincrement needs PK*/,
	name varchar(255) not null,
	birthday date
);
INSERT INTO User VALUES(1,'Guyu',1742258939095);
INSERT INTO User VALUES(2,'Guyu2',1742172539095);
CREATE UNIQUE INDEX User_id_uindex
	on User (id);
COMMIT;
