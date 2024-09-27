-- liquibase formatted sql
-- changeset Ifeanyichukwu Otiwa:init-schema

CREATE TABLE IF NOT EXISTS test (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);