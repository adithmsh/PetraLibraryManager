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

-- generating the records

-- BOOK RECORDS
INSERT INTO books (title, author, publisher, isbn, year, genre, copies_available) VALUES
('Travel road room know', 'Dale Gomez', 'Kim-Ramsey', '978-0-398-34084-1', 2020, 'Children', 8),
('The best nature part', 'Mark Young', 'Dunlap, Johnson and Wang', '978-0-03-006422-7', 1996, 'Children', 8),
('Cold them', 'Kevin Wagner', 'Jacobs, Moore and Ellis', '978-0-447-57670-2', 2008, 'Fantasy', 6),
('Surface blood', 'Deborah Porter', 'Edwards PLC', '978-0-632-37651-3', 2002, 'Fiction', 3),
('Door painting garden subject', 'Ashley Floyd', 'Rosario-Torres', '978-0-9802021-8-2', 2003, 'Science', 8),
('Network inside technology red', 'Michele Allen', 'Miller-Wang', '978-0-365-71373-9', 2015, 'Non-Fiction', 5),
('Relate window identify', 'Russell Keller', 'Stewart Ltd', '978-0-569-17623-1', 2015, 'Fiction', 10),
('Capital actually artist', 'Danielle Peck', 'Mendez-Reynolds', '978-0-213-80190-8', 2008, 'Fantasy', 9),
('Analysis decide future treatment', 'Robert Orr', 'Banks, Coleman and Gonzalez', '978-1-84167-421-6', 2005, 'Non-Fiction', 6),
('Woman traditional nor account', 'Jerry Parsons', 'Collins-Morton', '978-0-632-19491-9', 2009, 'Fantasy', 1),
('Only turn discuss', 'Marc Brown', 'Herman, Morales and Carter', '978-1-67652-208-9', 1995, 'Fiction', 3),
('Page matter never attack wife', 'Derek Foster', 'Church, Anderson and Gonzales', '978-0-580-49742-1', 1995, 'Science', 5),
('Find find government check', 'Brian Williams', 'Frank-Williamson', '978-1-908288-92-9', 1991, 'Fantasy', 3),
('Than return', 'Renee Solis', 'Obrien, Dean and Leon', '978-1-85059-979-1', 1994, 'Fiction', 3),
('Right prevent soldier', 'Daniel Romero', 'Johnson and Sons', '978-1-5160-8506-4', 1993, 'Fantasy', 0);

-- BORROW RECORDS
INSERT INTO borrow_records (borrow_id, user_id, book_id, borrow_date, return_date, status) VALUES
(1, 3, 1, '2025-04-11', '2025-04-06', 'Borrowed'),
(2, 5, 5, '2025-06-04', '2025-02-27', 'Returned'),
(3, 4, 3, '2025-04-02', '2025-03-30', 'Borrowed'),
(4, 3, 4, '2025-02-20', '2025-06-07', 'Returned'),
(5, 3, 1, '2025-06-09', '2025-06-04', 'Borrowed');

-- RESERVATIONS RECORDS
INSERT INTO reservations (reservation_id, user_id, book_id, reservation_date, status) VALUES
(1, 2, 1, '2025-03-29', 'Cancelled'),
(2, 5, 1, '2025-02-13', 'Pending'),
(3, 4, 2, '2025-05-01', 'Cancelled'),
(4, 1, 4, '2025-02-10', 'Cancelled'),
(5, 4, 3, '2025-02-22', 'Cancelled');

-- FINES RECORDS
INSERT INTO fines (fine_id, user_id, amount, status, description) VALUES
(1, 5, 17.02, 'Unpaid', 'Floor form kid another system score.'),
(2, 4, 16.40, 'Unpaid', 'Design accept west.'),
(3, 1, 7.18, 'Unpaid', 'Challenge area cell exist possible west bill.'),
(4, 4, 13.96, 'Unpaid', 'Up I manager eight reflect prove hand.'),
(5, 1, 0.54, 'Paid', 'Grow physical fly very probably author painting.');
