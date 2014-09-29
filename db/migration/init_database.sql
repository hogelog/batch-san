DROP DATABASE `batchsan_test`;
CREATE DATABASE `batchsan_test`;
GRANT ALL PRIVILEGES ON `batchsan_test`.* TO `username`@`localhost` IDENTIFIED BY "password";


DROP DATABASE `batchsan`;
CREATE DATABASE `batchsan`;
GRANT ALL PRIVILEGES ON `batchsan`.* TO `username`@`localhost` IDENTIFIED BY "password";

FLUSH PRIVILEGES;
