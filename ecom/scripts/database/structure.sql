DROP TABLE "orders", "cart", "photo", "seller" ,"member";

CREATE TABLE "member" (
	 login varchar(15) UNIQUE NOT NULL,
	 password varchar(50) NOT NULL, 
	 firstName varchar(50),
	 lastName varchar(50),
	 accountType char NOT NULL,
	 email varchar(25),
	 PRIMARY KEY(login)
);

CREATE TABLE "seller" (
	login varchar(15) REFERENCES "member"(login),
	RIB varchar(50) NOT NULL,
	PRIMARY KEY(login)
);

CREATE TABLE "photo" (
	 photoID BIGSERIAL,
	 seller_id varchar(15) REFERENCES "seller" (login),
	 description varchar(250),
	 name varchar(50),
	 web_location varchar(250) NOT NULL,
	 file_location varchar(250) NOT NULL,
	 price NUMERIC(5,2) NOT NULL,
	 PRIMARY KEY(photoID)
);

CREATE TABLE IF NOT EXISTS "orders" (
	 orderID BIGSERIAL,
	 login varchar(15) REFERENCES "member" (login),
	 photoID bigint REFERENCES "photo" (photoID),
	 date_created timestamp default current_timestamp,
	 PRIMARY KEY(orderID)
);

CREATE TABLE IF NOT EXISTS "cart" (
	login varchar(15) REFERENCES "member" (login),
	photoID bigint REFERENCES "photo" (photoID),
	PRIMARY KEY (login , photoID )
);