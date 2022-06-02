DROP TABLE IF EXISTS subscriptions;
CREATE TABLE IF NOT EXISTS subscriptions (
	id BINARY(36) PRIMARY KEY,
	email VARCHAR(512) NOT NULL,
	status VARCHAR(256) NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL,
	version BIGINT NOT NULL
);
