DROP TABLE IF EXISTS `job_recipe`;
DROP TABLE IF EXISTS `job_recipe_log`;

CREATE TABLE IF NOT EXISTS `job_recipe` (
    `id` IDENTITY PRIMARY KEY,
    `recipe` TEXT NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `job_recipe_log` (
    `id` IDENTITY PRIMARY KEY,
    `job` VARCHAR(100) NOT NULL,
    `status` INT NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
);
