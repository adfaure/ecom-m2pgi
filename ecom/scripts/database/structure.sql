DROP TABLE "photo";
DROP TABLE "seller";
DROP TABLE "member";

CREATE TABLE "member" (
	 memberID BIGSERIAL,
	 login varchar(15) UNIQUE NOT NULL,
	 password varchar(50) NOT NULL, 
	 firstName varchar(50),
	 lastName varchar(50),
	 accountType char NOT NULL,
	 email varchar(25),
	 PRIMARY KEY(memberID)
);

CREATE TABLE "seller" (
	memberID bigint REFERENCES "member"(memberID),
	RIB varchar(50) NOT NULL,
	PRIMARY KEY(memberID)
);

CREATE TABLE "photo" (
	 photoID BIGSERIAL,
	 seller_id bigint REFERENCES "seller" (memberID),
	 description varchar(250),
	 name varchar(50),
	 location varchar(50) NOT NULL,
	 price NUMERIC(2) NOT NULL,
	 PRIMARY KEY(photoID)
);
