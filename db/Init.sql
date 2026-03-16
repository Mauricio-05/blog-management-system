CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Crear tipo ENUM para roles
CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');

CREATE TABLE IF NOT EXISTS app_user (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role user_role NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL
    );


-- Crear tabla Post
CREATE TABLE IF NOT EXISTS post (
                                    id BIGSERIAL PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES app_user(id) ON DELETE CASCADE
    );

-- Crear tabla Comment
CREATE TABLE IF NOT EXISTS comment (
                                       id BIGSERIAL PRIMARY KEY,
                                       content TEXT NOT NULL,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       deleted_at TIMESTAMP NULL,
                                       post_id BIGINT NOT NULL,
                                       author_id BIGINT NOT NULL,
                                       FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES app_user(id) ON DELETE CASCADE
    );

-- Crear índices para optimizar consultas
CREATE INDEX IF NOT EXISTS idx_post_author_id ON post(author_id);
CREATE INDEX IF NOT EXISTS idx_comment_post_id ON comment(post_id);
CREATE INDEX IF NOT EXISTS idx_comment_author_id ON comment(author_id);

