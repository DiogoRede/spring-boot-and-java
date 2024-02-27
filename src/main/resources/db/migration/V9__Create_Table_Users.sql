CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  user_name TEXT,
  full_name TEXT,
  password TEXT,
  account_non_expired BOOLEAN DEFAULT NULL,
  account_non_locked BOOLEAN DEFAULT NULL,
  credentials_non_expired BOOLEAN DEFAULT NULL,
  enabled BOOLEAN DEFAULT NULL,
  UNIQUE (user_name)
);