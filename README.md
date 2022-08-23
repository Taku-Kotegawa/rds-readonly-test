# RDSリードレプリカ接続テスト

### 事前準備

1. テーブル作成

~~~
CREATE TABLE account (
	username varchar(255) NOT NULL,
	"version" int8 NOT NULL,
	created_by varchar(255) NOT NULL,
	created_date timestamp NOT NULL,
	last_modified_by varchar(255) NOT NULL,
	last_modified_date timestamp NOT NULL,
	allowed_ip varchar(255) NULL,
	api_key varchar(255) NULL,
	department varchar(255) NULL,
	email varchar(255) NULL,
	first_name varchar(255) NULL,
	image_uuid varchar(255) NULL,
	last_name varchar(255) NULL,
	"password" varchar(255) NULL,
	profile varchar(1000) NULL,
	status varchar(255) NULL,
	url varchar(255) NULL,
	CONSTRAINT account_idx1 UNIQUE (api_key),
	CONSTRAINT account_pkey PRIMARY KEY (username)
);
~~~

2. application.propertiesのDB接続設定

### テスト方法

http://localhost:8080/writable

-> メインDBに接続

http://localhost:8080/readonly

-> セカンダリーDBに接続

上記URLにアクセスするとそれぞれの接続先のaccountテーブルの件数を表示する。

正しくDBに接続できているかは、DBのログから確認してほしい。
