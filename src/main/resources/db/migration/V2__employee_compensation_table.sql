CREATE TABLE `employee_reduction`
(
    `id`                   bigint(20)   NOT NULL AUTO_INCREMENT,
    `no`                   bigint(20)   NULL,
    `name`                 varchar(255) NULL,
    `cnp`                  varchar(14)  NULL,
    `no_and_date_cim`      varchar(255) NULL,
    `no_hours_cim`         varchar(255) NULL,
    `no_and_date_decision` varchar(255) NULL,
    `no_days_reduction`    varchar(255) NULL,
    `no_hours_reduction`   varchar(255) NULL,
    `no_hours_provided`    varchar(255) NULL,
    `salary_cim`           varchar(255) NULL,
    `salary_cim_reduction` varchar(255) NULL,
    `required_amount`      varchar(255) NULL,
    `created_at`           datetime DEFAULT NULL,
    `created_by`           varchar(255) NULL,
    `file_name`            varchar(255) NULL,
    `file_content`         longtext     NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;