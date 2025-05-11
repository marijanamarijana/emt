CREATE MATERIALIZED VIEW books_by_author AS
SELECT a.id AS author_id, COUNT(a.id) AS num_books
FROM author a
         LEFT JOIN book b ON a.id = b.author_id
GROUP BY a.id;

CREATE MATERIALIZED VIEW authors_by_country AS
SELECT c.id AS country_id, COUNT(c.id) AS num_authors
FROM country c
         LEFT JOIN author a ON a.country_id = c.id
GROUP BY c.id;

CREATE MATERIALIZED VIEW good_books AS
SELECT b.id as book_id, b.name as book_name
FROM book b
WHERE b.state = true;


CREATE MATERIALIZED VIEW most_wished_authors AS
    SELECT u.username AS username, a.id AS author_id, a.name AS name, a.surname AS surname,
       COUNT(*) AS counted_wished_author_books
FROM
    author a
    JOIN book b ON a.id = b.author_id
    JOIN wishlist_books wb ON wb.book_id = b.id
    JOIN book_users u ON wb.user_username = u.username

GROUP BY u.username, a.id, a.name, a.surname
HAVING COUNT(*) > 0
ORDER BY counted_wished_author_books DESC;
