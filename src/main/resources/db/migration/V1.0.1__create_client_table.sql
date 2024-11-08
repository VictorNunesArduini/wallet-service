CREATE SCHEMA IF NOT EXISTS "payments";

CREATE TABLE IF NOT EXISTS "payments"."user" (
    "id" IDENTITY PRIMARY KEY,
    "name" VARCHAR(150) NOT NULL UNIQUE,
    "created_at" TIMESTAMP(9) DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(9) DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_user_name on "payments"."user" ("name");


CREATE TABLE IF NOT EXISTS "payments"."wallet" (
    "id" IDENTITY PRIMARY KEY,
    "user_id" BIGINT NOT NULL,
    "balance" DECIMAL(15,2) NOT NULL,
    "name" VARCHAR(150) NOT NULL,
    "created_at" TIMESTAMP(9) DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(9) DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ("user_id") REFERENCES "user"("id"),
    CONSTRAINT IF NOT EXISTS unique_wallet_userId_name UNIQUE ("user_id", "name")
);

CREATE INDEX IF NOT EXISTS idx_wallet_name on "payments"."wallet" ("name");
CREATE INDEX IF NOT EXISTS idx_user_id on "payments"."wallet" ("user_id");

CREATE TABLE IF NOT EXISTS "payments"."transaction" (
    "id" IDENTITY PRIMARY KEY,
    "wallet_id" BIGINT NOT NULL,
    "balance" DECIMAL(15,2) NOT NULL,
    "order_type" VARCHAR(25) NOT NULL,
    "order_value" DECIMAL(15,2) NOT NULL,
    "created_at" TIMESTAMP(9) DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(9) DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY ("wallet_id") REFERENCES "wallet"("id") ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_wallet_id ON "payments"."transaction" ("wallet_id");
CREATE INDEX IF NOT EXISTS idx_created_at ON "payments"."transaction" ("created_at");
