CREATE TABLE `user_activity`
(
  `id`             bigint(20) NOT NULL AUTO_INCREMENT,
  `accept`         varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `context_path`   varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at`     datetime                                DEFAULT NULL,
  `lang`           varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `method`         varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remote_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remote_port`    int(11)    NOT NULL,
  `remote_user`    varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `request_body`   longtext COLLATE utf8mb4_unicode_ci,
  `request_params` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `servlet_path`   varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_agent`     varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username`       varchar(50) COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

create table `users`
(
  `id`         bigint(20) NOT NULL AUTO_INCREMENT,
  `username`   varchar(255) DEFAULT NULL,
  `password`   varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name`  varchar(255) DEFAULT NULL,
  `language`   varchar(255) DEFAULT NULL,
  `email`      varchar(255) DEFAULT NULL,
  `phone`      varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

create table `application_error`
(
  `id`             bigint auto_increment primary key,
  `created_at`     datetime                          null,
  `created_by`     varchar(50)                       null,
  `updated_at`     datetime                          null,
  `updated_by`     varchar(50)                       null,
  `email`          varchar(255)                      null,
  `email_sent`     bit default b'0'                  null,
  `fixed`          bit default b'0'                  null,
  `level`          enum ('ERROR', 'WARNING', 'INFO') null,
  `message`        varchar(500)                      null,
  `root_cause`     longtext                          null,
  `stacktrace`     longtext                          null,
  `origin`         int                               not null,
  `context_path`   varchar(255)                      null,
  `method`         varchar(255)                      null,
  `request_body`   longtext                          null,
  `request_params` varchar(255)                      null,
  `servlet_path`   varchar(255)                      null,
  `app`            int default 0                     not null
);

CREATE TABLE `user_privileges`
(
  `id_user`      bigint(20) NOT NULL,
  `id_privilege` bigint(20) NOT NULL,
  PRIMARY KEY (`id_user`, `id_privilege`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE `privileges`
(
  `id`          bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_parinte`  bigint(20)                              DEFAULT NULL,
  `valid`       bit(1)     NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `users` (`id`, `username`, `password`, `first_name`, `last_name`, `language`, `email`, `phone`)
VALUES (1, 'admin', '36a9e7f1c95b82ffb99743e0c5c4ce95d83c9a430aac59f84ef3cbfab6145068', 'Admin', 'Admin', 'en', NULL,
        NULL);

INSERT INTO `user_privileges` (`id_user`, `id_privilege`)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5);

INSERT INTO privileges (`id`, `id_parinte`, description, `valid`)
VALUES (1, 0, 'Utilizatori', 1);

INSERT INTO privileges (`id`, `id_parinte`, description, `valid`)
VALUES (2, 1, 'Vizualizare utilizatori', 1);

INSERT INTO privileges (`id`, `id_parinte`, description, `valid`)
VALUES (3, 1, 'Adaugare utilizator', 1);

INSERT INTO privileges (`id`, `id_parinte`, description, `valid`)
VALUES (4, 1, 'Editare utilizatori', 1);

INSERT INTO privileges (`id`, `id_parinte`, description, `valid`)
VALUES (5, 1, 'Stergere utilizatori', 1);