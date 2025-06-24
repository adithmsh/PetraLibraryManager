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

-- Book Categories (linking books to categories)
INSERT INTO book_categories (book_id, category_id) VALUES
-- Fiction books
(1, 1), (1, 3),    -- 1984: Fiction, Science Fiction
(2, 1), (2, 12),   -- To Kill a Mockingbird: Fiction, Literature
(3, 1), (3, 12),   -- The Great Gatsby: Fiction, Literature
(4, 1), (4, 6),    -- Pride and Prejudice: Fiction, Romance
(5, 1), (5, 12),   -- Huckleberry Finn: Fiction, Literature
(6, 1), (6, 4), (6, 13), -- Harry Potter: Fiction, Fantasy, Young Adult
(7, 1), (7, 5),    -- The Shining: Fiction, Mystery
(8, 1), (8, 5),    -- Murder on Orient Express: Fiction, Mystery
(9, 1), (9, 3),    -- Foundation: Fiction, Science Fiction
(10, 1), (10, 3),  -- Fahrenheit 451: Fiction, Science Fiction
(11, 1), (11, 3),  -- The Handmaid's Tale: Fiction, Science Fiction
(12, 1), (12, 12), -- Beloved: Fiction, Literature
(13, 1), (13, 12), -- The Old Man and the Sea: Fiction, Literature
(14, 1), (14, 12), -- To the Lighthouse: Fiction, Literature
(15, 1), (15, 12), -- Great Expectations: Fiction, Literature
(16, 1), (16, 12), -- Hamlet: Fiction, Literature
(17, 2), (17, 7),  -- I Know Why the Caged Bird Sings: Non-Fiction, Biography
(18, 1), (18, 12), -- Of Mice and Men: Fiction, Literature
(19, 1), (19, 3),  -- Brave New World: Fiction, Science Fiction
(20, 1), (20, 3),  -- Slaughterhouse-Five: Fiction, Science Fiction
-- Technical books
(21, 2), (21, 10), (21, 11), -- Clean Code: Non-Fiction, Technology, Programming
(22, 2), (22, 10), (22, 11), -- TAOCP: Non-Fiction, Technology, Programming
(23, 2), (23, 10), (23, 11), -- Refactoring: Non-Fiction, Technology, Programming
(24, 2), (24, 10), (24, 11), -- Code Complete: Non-Fiction, Technology, Programming
(25, 2), (25, 10), (25, 11), -- Algorithms: Non-Fiction, Technology, Programming
-- Additional book categories
(26, 1), (26, 4), (26, 13), -- HP Chamber: Fiction, Fantasy, Young Adult
(27, 1), (27, 4), (27, 13), -- HP Prisoner: Fiction, Fantasy, Young Adult
(28, 1), (28, 3), (28, 13), -- Hunger Games: Fiction, Science Fiction, Young Adult
(29, 1), (29, 4),    -- LOTR Fellowship: Fiction, Fantasy
(30, 1), (30, 4),    -- LOTR Two Towers: Fiction, Fantasy
(31, 1), (31, 4),    -- LOTR Return: Fiction, Fantasy
(32, 1), (32, 5),    -- Where the Crawdads Sing: Fiction, Mystery
(33, 1), (33, 6),    -- Seven Husbands: Fiction, Romance
(34, 1), (34, 12),   -- Catcher in the Rye: Fiction, Literature
(35, 1), (35, 5),    -- Gone Girl: Fiction, Mystery
(36, 1), (36, 12),   -- Kite Runner: Fiction, Literature
(37, 1), (37, 8),    -- Book Thief: Fiction, History
(38, 1), (38, 4),    -- Alchemist: Fiction, Fantasy
(39, 1), (39, 12),   -- Life of Pi: Fiction, Literature
(40, 1), (40, 12),   -- Catch-22: Fiction, Literature
(41, 1), (41, 12),   -- One Hundred Years: Fiction, Literature
(42, 1), (42, 5),    -- Da Vinci Code: Fiction, Mystery
(43, 1), (43, 3),    -- Dune: Fiction, Science Fiction
(44, 1), (44, 4),    -- Game of Thrones: Fiction, Fantasy
(45, 1), (45, 3),    -- The Martian: Fiction, Science Fiction
(46, 1), (46, 5),    -- Girl with Dragon Tattoo: Fiction, Mystery
(47, 1), (47, 4), (47, 14), -- Narnia: Fiction, Fantasy, Children
(48, 1), (48, 8),    -- Memoirs of Geisha: Fiction, History
(49, 1), (49, 6), (49, 13), -- Twilight: Fiction, Romance, Young Adult
(50, 1), (50, 3),    -- Ready Player One: Fiction, Science Fiction
(51, 1), (51, 6), (51, 13), -- Fault in Our Stars: Fiction, Romance, Young Adult
(52, 2), (52, 9),    -- Brief History of Time: Non-Fiction, Science
(53, 1), (53, 5),    -- Girl on the Train: Fiction, Mystery
(54, 2), (54, 7),    -- Educated: Non-Fiction, Biography
(55, 2), (55, 7),    -- Becoming: Non-Fiction, Biography
(56, 1), (56, 5),    -- Silent Patient: Fiction, Mystery
(57, 1), (57, 4),    -- Circe: Fiction, Fantasy
(58, 1), (58, 6),    -- Normal People: Fiction, Romance
(59, 1), (59, 6);    -- It Ends with Us: Fiction, Romance-- Sample Data for Library Management System

-- Publishers
INSERT INTO publishers (name) VALUES
('Penguin Random House'),
('HarperCollins'),
('Simon & Schuster'),
('Macmillan'),
('Hachette Book Group'),
('Scholastic'),
('Oxford University Press'),
('Cambridge University Press'),
('MIT Press'),
('O''Reilly Media');

-- Authors
INSERT INTO authors (name) VALUES
('George Orwell'),
('Harper Lee'),
('F. Scott Fitzgerald'),
('Jane Austen'),
('Mark Twain'),
('J.K. Rowling'),
('Stephen King'),
('Agatha Christie'),
('Isaac Asimov'),
('Ray Bradbury'),
('Margaret Atwood'),
('Toni Morrison'),
('Ernest Hemingway'),
('Virginia Woolf'),
('Charles Dickens'),
('William Shakespeare'),
('Maya Angelou'),
('John Steinbeck'),
('Aldous Huxley'),
('Kurt Vonnegut'),
('Robert C. Martin'),
('Donald E. Knuth'),
('Martin Fowler'),
('Steve McConnell'),
('Thomas H. Cormen'),
-- Additional authors for new books
('Suzanne Collins'),
('J.R.R. Tolkien'),
('Delia Owens'),
('Taylor Jenkins Reid'),
('J.D. Salinger'),
('Gillian Flynn'),
('Khaled Hosseini'),
('Markus Zusak'),
('Paulo Coelho'),
('Yann Martel'),
('Joseph Heller'),
('Gabriel García Márquez'),
('Dan Brown'),
('Frank Herbert'),
('George R.R. Martin'),
('Andy Weir'),
('Stieg Larsson'),
('C.S. Lewis'),
('Arthur Golden'),
('Stephenie Meyer'),
('Ernest Cline'),
('John Green'),
('Stephen Hawking'),
('Paula Hawkins'),
('Tara Westover'),
('Michelle Obama'),
('Alex Michaelides'),
('Madeline Miller'),
('Sally Rooney'),
('Colleen Hoover');

-- Categories
INSERT INTO categories (name) VALUES
('Fiction'),
('Non-Fiction'),
('Science Fiction'),
('Fantasy'),
('Mystery'),
('Romance'),
('Biography'),
('History'),
('Science'),
('Technology'),
('Programming'),
('Literature'),
('Young Adult'),
('Children'),
('Self-Help');

-- Books (54 books total)
INSERT INTO books (isbn, title, publisher_id, publication_year, cover_url, total_copies, available_copies) VALUES
('978-0-452-28423-4', '1984', 1, 1949, 'https://example.com/covers/1984.jpg', 5, 3),
('978-0-06-112008-4', 'To Kill a Mockingbird', 2, 1960, 'https://example.com/covers/mockingbird.jpg', 4, 2),
('978-0-7432-7356-5', 'The Great Gatsby', 3, 1925, 'https://example.com/covers/gatsby.jpg', 6, 4),
('978-0-14-143951-8', 'Pride and Prejudice', 1, 1813, 'https://example.com/covers/pride.jpg', 3, 3),
('978-0-486-40649-0', 'Adventures of Huckleberry Finn', 4, 1884, 'https://example.com/covers/huckfinn.jpg', 3, 1),
('978-0-439-70818-8', 'Harry Potter and the Philosopher''s Stone', 6, 1997, 'https://example.com/covers/hp1.jpg', 8, 5),
('978-0-385-33312-0', 'The Shining', 1, 1977, 'https://example.com/covers/shining.jpg', 4, 2),
('978-0-06-207348-4', 'Murder on the Orient Express', 2, 1934, 'https://example.com/covers/orient.jpg', 3, 3),
('978-0-553-29337-0', 'Foundation', 1, 1951, 'https://example.com/covers/foundation.jpg', 5, 4),
('978-0-345-34296-8', 'Fahrenheit 451', 4, 1953, 'https://example.com/covers/fahrenheit.jpg', 4, 3),
('978-0-385-49081-8', 'The Handmaid''s Tale', 1, 1985, 'https://example.com/covers/handmaid.jpg', 6, 4),
('978-1-4000-3386-0', 'Beloved', 3, 1987, 'https://example.com/covers/beloved.jpg', 3, 2),
('978-0-684-80122-3', 'The Old Man and the Sea', 3, 1952, 'https://example.com/covers/oldman.jpg', 4, 4),
('978-0-15-690739-6', 'To the Lighthouse', 2, 1927, 'https://example.com/covers/lighthouse.jpg', 2, 1),
('978-0-14-200506-4', 'Great Expectations', 1, 1861, 'https://example.com/covers/expectations.jpg', 5, 3),
('978-0-14-017739-8', 'Hamlet', 1, 1603, 'https://example.com/covers/hamlet.jpg', 6, 5),
('978-0-345-41943-5', 'I Know Why the Caged Bird Sings', 4, 1969, 'https://example.com/covers/caged.jpg', 3, 2),
('978-0-14-018642-0', 'Of Mice and Men', 1, 1937, 'https://example.com/covers/mice.jpg', 4, 3),
('978-0-06-085052-4', 'Brave New World', 2, 1932, 'https://example.com/covers/brave.jpg', 5, 4),
('978-0-385-33384-7', 'Slaughterhouse-Five', 1, 1969, 'https://example.com/covers/slaughterhouse.jpg', 3, 2),
('978-0-13-235088-4', 'Clean Code', 10, 2008, 'https://example.com/covers/cleancode.jpg', 7, 6),
('978-0-201-89683-1', 'The Art of Computer Programming Vol 1', 8, 1968, 'https://example.com/covers/taocp1.jpg', 2, 1),
('978-0-201-48567-7', 'Refactoring', 8, 1999, 'https://example.com/covers/refactoring.jpg', 4, 3),
('978-0-7356-1967-8', 'Code Complete', 9, 2004, 'https://example.com/covers/codecomplete.jpg', 5, 4),
('978-0-262-03384-8', 'Introduction to Algorithms', 9, 2009, 'https://example.com/covers/algorithms.jpg', 6, 5),
-- Additional 29 books
('978-0-439-70819-5', 'Harry Potter and the Chamber of Secrets', 6, 1998, 'https://example.com/covers/hp2.jpg', 7, 4),
('978-0-439-70820-1', 'Harry Potter and the Prisoner of Azkaban', 6, 1999, 'https://example.com/covers/hp3.jpg', 6, 3),
('978-0-545-01022-1', 'The Hunger Games', 6, 2008, 'https://example.com/covers/hunger.jpg', 8, 6),
('978-0-7475-3269-9', 'The Lord of the Rings: Fellowship', 2, 1954, 'https://example.com/covers/lotr1.jpg', 5, 2),
('978-0-7475-3270-5', 'The Lord of the Rings: Two Towers', 2, 1954, 'https://example.com/covers/lotr2.jpg', 4, 3),
('978-0-7475-3271-2', 'The Lord of the Rings: Return of the King', 2, 1955, 'https://example.com/covers/lotr3.jpg', 4, 2),
('978-0-06-112241-5', 'Where the Crawdads Sing', 2, 2018, 'https://example.com/covers/crawdads.jpg', 9, 7),
('978-0-525-50950-1', 'The Seven Husbands of Evelyn Hugo', 3, 2017, 'https://example.com/covers/evelyn.jpg', 6, 4),
('978-0-316-76948-0', 'The Catcher in the Rye', 5, 1951, 'https://example.com/covers/catcher.jpg', 4, 2),
('978-0-06-231500-7', 'Gone Girl', 2, 2012, 'https://example.com/covers/gonegirl.jpg', 5, 3),
('978-0-385-53777-2', 'The Kite Runner', 1, 2003, 'https://example.com/covers/kite.jpg', 4, 3),
('978-0-316-01084-2', 'The Book Thief', 5, 2005, 'https://example.com/covers/bookthief.jpg', 6, 4),
('978-0-679-72326-2', 'The Alchemist', 1, 1988, 'https://example.com/covers/alchemist.jpg', 7, 5),
('978-0-7432-4722-1', 'Life of Pi', 3, 2001, 'https://example.com/covers/lifeofpi.jpg', 3, 2),
('978-0-06-440055-8', 'Catch-22', 2, 1961, 'https://example.com/covers/catch22.jpg', 4, 3),
('978-0-679-60013-1', 'One Hundred Years of Solitude', 1, 1967, 'https://example.com/covers/solitude.jpg', 3, 1),
('978-0-7432-7357-2', 'The Da Vinci Code', 3, 2003, 'https://example.com/covers/davinci.jpg', 8, 6),
('978-0-553-21311-0', 'Dune', 1, 1965, 'https://example.com/covers/dune.jpg', 5, 3),
('978-0-380-97746-8', 'A Game of Thrones', 5, 1996, 'https://example.com/covers/got1.jpg', 7, 4),
('978-0-553-38168-9', 'The Martian', 1, 2011, 'https://example.com/covers/martian.jpg', 6, 5),
('978-0-06-231509-0', 'The Girl with the Dragon Tattoo', 2, 2005, 'https://example.com/covers/dragon.jpg', 4, 2),
('978-0-14-303943-3', 'The Chronicles of Narnia: The Lion, the Witch and the Wardrobe', 1, 1950, 'https://example.com/covers/narnia1.jpg', 5, 3),
('978-0-7432-4722-8', 'Memoirs of a Geisha', 3, 1997, 'https://example.com/covers/geisha.jpg', 3, 2),
('978-0-316-76988-6', 'Twilight', 5, 2005, 'https://example.com/covers/twilight.jpg', 8, 6),
('978-0-345-80727-5', 'Ready Player One', 4, 2011, 'https://example.com/covers/readyplayer.jpg', 5, 4),
('978-0-06-440936-0', 'The Fault in Our Stars', 2, 2012, 'https://example.com/covers/fault.jpg', 7, 5),
('978-0-553-57340-1', 'A Brief History of Time', 1, 1988, 'https://example.com/covers/briefhistory.jpg', 4, 3),
('978-0-385-47571-6', 'The Girl on the Train', 1, 2015, 'https://example.com/covers/girlontrain.jpg', 6, 4),
('978-0-06-207949-3', 'Educated', 2, 2018, 'https://example.com/covers/educated.jpg', 5, 3),
('978-0-7432-7356-2', 'Becoming', 3, 2018, 'https://example.com/covers/becoming.jpg', 8, 6),
('978-0-345-53943-4', 'The Silent Patient', 4, 2019, 'https://example.com/covers/silent.jpg', 6, 4),
('978-0-525-52088-5', 'Circe', 3, 2018, 'https://example.com/covers/circe.jpg', 4, 2),
('978-0-385-54395-7', 'Normal People', 1, 2018, 'https://example.com/covers/normal.jpg', 5, 3),
('978-0-316-01077-4', 'It Ends with Us', 5, 2016, 'https://example.com/covers/itends.jpg', 7, 5);

-- Book Authors (linking books to their authors)
INSERT INTO book_authors (book_id, author_id) VALUES
(1, 1),   -- 1984 by George Orwell
(2, 2),   -- To Kill a Mockingbird by Harper Lee
(3, 3),   -- The Great Gatsby by F. Scott Fitzgerald
(4, 4),   -- Pride and Prejudice by Jane Austen
(5, 5),   -- Huckleberry Finn by Mark Twain
(6, 6),   -- Harry Potter by J.K. Rowling
(7, 7),   -- The Shining by Stephen King
(8, 8),   -- Murder on Orient Express by Agatha Christie
(9, 9),   -- Foundation by Isaac Asimov
(10, 10), -- Fahrenheit 451 by Ray Bradbury
(11, 11), -- The Handmaid's Tale by Margaret Atwood
(12, 12), -- Beloved by Toni Morrison
(13, 13), -- The Old Man and the Sea by Ernest Hemingway
(14, 14), -- To the Lighthouse by Virginia Woolf
(15, 15), -- Great Expectations by Charles Dickens
(16, 16), -- Hamlet by William Shakespeare
(17, 17), -- I Know Why the Caged Bird Sings by Maya Angelou
(18, 18), -- Of Mice and Men by John Steinbeck
(19, 19), -- Brave New World by Aldous Huxley
(20, 20), -- Slaughterhouse-Five by Kurt Vonnegut
(21, 21), -- Clean Code by Robert C. Martin
(22, 22), -- TAOCP by Donald E. Knuth
(23, 23), -- Refactoring by Martin Fowler
(24, 24), -- Code Complete by Steve McConnell
(25, 25), -- Algorithms by Thomas H. Cormen
-- Additional book-author mappings
(26, 6),  -- Harry Potter Chamber of Secrets by J.K. Rowling
(27, 6),  -- Harry Potter Prisoner of Azkaban by J.K. Rowling
(28, 26), -- The Hunger Games by Suzanne Collins
(29, 27), -- LOTR Fellowship by J.R.R. Tolkien
(30, 27), -- LOTR Two Towers by J.R.R. Tolkien
(31, 27), -- LOTR Return of the King by J.R.R. Tolkien
(32, 28), -- Where the Crawdads Sing by Delia Owens
(33, 29), -- The Seven Husbands of Evelyn Hugo by Taylor Jenkins Reid
(34, 30), -- The Catcher in the Rye by J.D. Salinger
(35, 31), -- Gone Girl by Gillian Flynn
(36, 32), -- The Kite Runner by Khaled Hosseini
(37, 33), -- The Book Thief by Markus Zusak
(38, 34), -- The Alchemist by Paulo Coelho
(39, 35), -- Life of Pi by Yann Martel
(40, 36), -- Catch-22 by Joseph Heller
(41, 37), -- One Hundred Years of Solitude by Gabriel García Márquez
(42, 38), -- The Da Vinci Code by Dan Brown
(43, 39), -- Dune by Frank Herbert
(44, 40), -- A Game of Thrones by George R.R. Martin
(45, 41), -- The Martian by Andy Weir
(46, 42), -- The Girl with the Dragon Tattoo by Stieg Larsson
(47, 43), -- The Chronicles of Narnia by C.S. Lewis
(48, 44), -- Memoirs of a Geisha by Arthur Golden
(49, 45), -- Twilight by Stephenie Meyer
(50, 46), -- Ready Player One by Ernest Cline
(51, 47), -- The Fault in Our Stars by John Green
(52, 48), -- A Brief History of Time by Stephen Hawking
(53, 49), -- The Girl on the Train by Paula Hawkins
(54, 50), -- Educated by Tara Westover
(55, 51), -- Becoming by Michelle Obama
(56, 52), -- The Silent Patient by Alex Michaelides
(57, 53), -- Circe by Madeline Miller
(58, 54), -- Normal People by Sally Rooney
(59, 55); -- It Ends with Us by Colleen Hoover

-- Book Categories (linking books to categories)
INSERT INTO book_categories (book_id, category_id) VALUES
-- Fiction books
(1, 1), (1, 3),    -- 1984: Fiction, Science Fiction
(2, 1), (2, 12),   -- To Kill a Mockingbird: Fiction, Literature
(3, 1), (3, 12),   -- The Great Gatsby: Fiction, Literature
(4, 1), (4, 6),    -- Pride and Prejudice: Fiction, Romance
(5, 1), (5, 12),   -- Huckleberry Finn: Fiction, Literature
(6, 1), (6, 4), (6, 13), -- Harry Potter: Fiction, Fantasy, Young Adult
(7, 1), (7, 5),    -- The Shining: Fiction, Mystery
(8, 1), (8, 5),    -- Murder on Orient Express: Fiction, Mystery
(9, 1), (9, 3),    -- Foundation: Fiction, Science Fiction
(10, 1), (10, 3),  -- Fahrenheit 451: Fiction, Science Fiction
(11, 1), (11, 3),  -- The Handmaid's Tale: Fiction, Science Fiction
(12, 1), (12, 12), -- Beloved: Fiction, Literature
(13, 1), (13, 12), -- The Old Man and the Sea: Fiction, Literature
(14, 1), (14, 12), -- To the Lighthouse: Fiction, Literature
(15, 1), (15, 12), -- Great Expectations: Fiction, Literature
(16, 1), (16, 12), -- Hamlet: Fiction, Literature
(17, 2), (17, 7),  -- I Know Why the Caged Bird Sings: Non-Fiction, Biography
(18, 1), (18, 12), -- Of Mice and Men: Fiction, Literature
(19, 1), (19, 3),  -- Brave New World: Fiction, Science Fiction
(20, 1), (20, 3),  -- Slaughterhouse-Five: Fiction, Science Fiction
-- Technical books
(21, 2), (21, 10), (21, 11), -- Clean Code: Non-Fiction, Technology, Programming
(22, 2), (22, 10), (22, 11), -- TAOCP: Non-Fiction, Technology, Programming
(23, 2), (23, 10), (23, 11), -- Refactoring: Non-Fiction, Technology, Programming
(24, 2), (24, 10), (24, 11), -- Code Complete: Non-Fiction, Technology, Programming
(25, 2), (25, 10), (25, 11); -- Algorithms: Non-Fiction, Technology, Programming

-- Sample Users (assuming users table exists with user_id, name, email, etc.)
-- Note: This assumes you have a users table - adjust as needed for your schema

-- Sample Loans (some active, some returned)
INSERT INTO loans (book_id, user_id, checkout_date, due_date, returned_date) VALUES
(1, 1, '2024-06-01', '2024-06-15', '2024-06-12'),  -- Returned early
(2, 2, '2024-06-05', '2024-06-19', NULL),          -- Still out
(3, 1, '2024-06-10', '2024-06-24', NULL),          -- Still out
(7, 3, '2024-05-20', '2024-06-03', '2024-06-08'),  -- Returned late
(21, 4, '2024-06-12', '2024-06-26', NULL),         -- Still out
(22, 2, '2024-06-08', '2024-06-22', NULL),         -- Still out
(5, 5, '2024-06-15', '2024-06-29', NULL),          -- Still out
(5, 5, '2024-06-16', '2024-06-30', NULL),          -- Still out (second copy)
(28, 3, '2024-06-18', '2024-07-02', NULL),         -- Hunger Games still out
(32, 1, '2024-06-20', '2024-07-04', NULL),         -- Crawdads still out
(43, 4, '2024-06-14', '2024-06-28', NULL),         -- Dune still out
(49, 2, '2024-06-17', '2024-07-01', NULL),         -- Twilight still out
(55, 5, '2024-06-19', '2024-07-03', NULL);         -- Becoming still out

-- Sample Reservations
INSERT INTO reservations (book_id, user_id, reserved_at, status) VALUES
(1, 3, '2024-06-20 10:30:00', 'waiting'),
(6, 2, '2024-06-18 14:15:00', 'ready'),
(21, 1, '2024-06-19 09:45:00', 'waiting'),
(7, 4, '2024-06-17 16:20:00', 'fulfilled'),
(29, 3, '2024-06-21 11:00:00', 'waiting'),  -- LOTR Fellowship
(44, 1, '2024-06-22 13:30:00', 'waiting'),  -- Game of Thrones
(32, 4, '2024-06-20 15:45:00', 'ready'),    -- Crawdads ready
(51, 2, '2024-06-19 10:15:00', 'waiting');  -- Fault in Our Stars

-- Sample Fines (for overdue books)
INSERT INTO fines (loan_id, amount, paid, assessed_at) VALUES
(4, 5.00, FALSE, '2024-06-04 09:00:00'),  -- Late return of The Shining
(4, 2.50, TRUE, '2024-06-06 09:00:00');   -- Additional fine, now paid

-- Sample Notifications
INSERT INTO notifications (user_id, message, type, created_at, read) VALUES
(2, 'Your book "To Kill a Mockingbird" is due tomorrow', 'due_reminder', '2024-06-18 09:00:00', FALSE),
(2, 'Your reserved book "Harry Potter" is ready for pickup', 'reservation_ready', '2024-06-18 11:30:00', TRUE),
(3, 'You have an overdue fine of $5.00', 'fine_notice', '2024-06-04 10:00:00', FALSE),
(1, 'Thank you for returning "1984" early!', 'return_confirmation', '2024-06-12 14:20:00', TRUE),
(4, 'Your book "Clean Code" is due in 3 days', 'due_reminder', '2024-06-23 09:00:00', FALSE),
(3, 'Your book "The Hunger Games" is due in 5 days', 'due_reminder', '2024-06-27 09:00:00', FALSE),
(1, 'Your reserved book "Where the Crawdads Sing" is ready for pickup', 'reservation_ready', '2024-06-20 12:00:00', FALSE),
(5, 'Your book "Becoming" is due tomorrow', 'due_reminder', '2024-07-02 09:00:00', FALSE),
(4, 'New book "The Silent Patient" has been added to the catalog', 'new_arrival', '2024-06-15 10:00:00', TRUE),
(2, 'Your book "Twilight" renewal was successful', 'renewal_confirmation', '2024-06-22 11:30:00', TRUE);