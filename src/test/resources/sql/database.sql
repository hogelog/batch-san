CREATE TABLE IF NOT EXISTS `job_recipe_log` (
    `id` IDENTITY PRIMARY KEY,
    `job` VARCHAR(100) NOT NULL,
    `status` INT NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
);
