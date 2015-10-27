DROP TABLE "photo";
DROP TABLE "seller";
DROP TABLE "member";

CREATE TABLE IF NOT EXISTS "member" (
	 memberID BIGSERIAL,
	 login varchar(15) UNIQUE NOT NULL,
	 password varchar(50) NOT NULL, 
	 firstName varchar(50),
	 lastName varchar(50),
	 accountType char NOT NULL,
	 email varchar(25),
	 CONSTRAINT member_firstkey PRIMARY KEY(memberID)
);

CREATE TABLE IF NOT EXISTS "seller" (
	memberID bigint REFERENCES "member"(memberID),
	RIB varchar(50) NOT NULL,
	PRIMARY KEY(memberID)
);

CREATE TABLE IF NOT EXISTS "photo" (
	 photoID BIGSERIAL,
	 seller_id bigint REFERENCES "seller" (memberID),
	 description varchar(250),
	 name varchar(50),
	 location varchar(50) NOT NULL,
	 price NUMERIC(2) NOT NULL,
	 PRIMARY KEY(photoID)
);

INSERT INTO "member" VALUES (
	0,
	'dadou',
	'dadou',
	'Adrien',
	'Faure',
	'M',	
	null
);

INSERT INTO "member" VALUES (
	3,
	'bob',
	'bob',
	'Bobbine',
	'rouleau',
	's',	
	null
);

INSERT INTO "seller" VALUES (
	0,
	'bobrib'
);

INSERT INTO "photo" VALUES (
	0,
	0,
	'Ma première photo',
	'marwen.gif',
	'/tmp/photos/marwen.gif',
	2.0
);

INSERT INTO "photo" VALUES (
	1,
	0,
	'Ma deuxième photo',
	'sword4.png',
	'/tmp/photos/sword4.png',
	2.0
);

