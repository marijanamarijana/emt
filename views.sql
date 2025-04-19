CREATE OR REPLACE MATERIALIZED VIEW public.books_by_author AS
SELECT a.id AS author_id, COUNT(a.id) AS num_books
FROM public.author a
         LEFT JOIN public.book b ON a.id = b.author_id
GROUP BY a.id;

CREATE OR REPLACE MATERIALIZED VIEW public.authors_by_country AS
SELECT c.id AS country_id, COUNT(c.id) AS num_authors
FROM public.country c
         LEFT JOIN public.author a ON a.country_id = c.id
GROUP BY c.id;


