INSERT INTO `user_privileges` (`id_user`, `id_privilege`)
VALUES (1, 6),
       (1, 7),
       (1, 8);

INSERT INTO privileges (`id`, `id_parinte`, description, `valid`)
VALUES (6, 0, 'Reducere angajati', 1);

INSERT INTO privileges (`id`, `id_parinte`, description, `valid`)
VALUES (7, 6, 'Vizualizare Reducere angajati', 1);

INSERT INTO privileges (`id`, `id_parinte`, description, `valid`)
VALUES (8, 6, 'Adaugare Reducere angajati', 1);