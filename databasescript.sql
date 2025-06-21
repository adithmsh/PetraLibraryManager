-- =============================
-- Library Management System SQL Schema (PostgreSQL)
-- =============================

-- Users Table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    role VARCHAR(10) CHECK (role IN ('member', 'staff', 'admin')) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Publishers Table
CREATE TABLE publishers (
    publisher_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Books Table
CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    title VARCHAR(200) NOT NULL,
    publisher_id INT REFERENCES publishers(publisher_id) ON DELETE SET NULL,
    publication_year INT,
    cover_url TEXT,
    total_copies INT NOT NULL CHECK (total_copies >= 0),
    available_copies INT NOT NULL CHECK (available_copies >= 0)
);

-- Authors Table
CREATE TABLE authors (
    author_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- BookAuthor Join Table
CREATE TABLE book_authors (
    book_id INT REFERENCES books(book_id) ON DELETE CASCADE,
    author_id INT REFERENCES authors(author_id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

-- Categories Table
CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- BookCategory Join Table
CREATE TABLE book_categories (
    book_id INT REFERENCES books(book_id) ON DELETE CASCADE,
    category_id INT REFERENCES categories(category_id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, category_id)
);

-- Loans Table
CREATE TABLE loans (
    loan_id SERIAL PRIMARY KEY,
    book_id INT REFERENCES books(book_id) ON DELETE CASCADE,
    user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    checkout_date DATE NOT NULL,
    due_date DATE NOT NULL,
    returned_date DATE
);

-- Reservations Table
CREATE TABLE reservations (
    reservation_id SERIAL PRIMARY KEY,
    book_id INT REFERENCES books(book_id) ON DELETE CASCADE,
    user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    reserved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('waiting', 'ready', 'cancelled', 'fulfilled')) NOT NULL
);

-- Fines Table
CREATE TABLE fines (
    fine_id SERIAL PRIMARY KEY,
    loan_id INT REFERENCES loans(loan_id) ON DELETE CASCADE,
    amount NUMERIC(8, 2) NOT NULL CHECK (amount >= 0),
    paid BOOLEAN DEFAULT FALSE,
    assessed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Notifications Table (Optional)
CREATE TABLE notifications (
    notification_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read BOOLEAN DEFAULT FALSE
);
