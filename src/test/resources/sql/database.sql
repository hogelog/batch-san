DROP TABLE IF EXISTS `job_recipe`;
DROP TABLE IF EXISTS `job_recipe_log`;

CREATE TABLE IF NOT EXISTS `job_recipe` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `recipe` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `job_recipe_log` (
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT ,
    `job` VARCHAR(100) NOT NULL,
    `status` INT NOT NULL DEFAULT 0,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);
