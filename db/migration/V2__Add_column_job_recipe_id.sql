ALTER TABLE `job_recipe_log`
  ADD COLUMN `job_recipe_id` INT UNSIGNED DEFAULT NULL AFTER `job`,
  ADD INDEX `job_recipe_id` USING BTREE (`job_recipe_id`)
  ;
