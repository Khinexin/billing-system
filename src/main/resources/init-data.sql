CREATE TABLE `billers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transaction` (
  `transition_id` int NOT NULL AUTO_INCREMENT,
  `amount` bigint DEFAULT NULL,
  `api_caller` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `reference_no` varchar(255) DEFAULT NULL,
  `billers_id` int DEFAULT NULL,
  PRIMARY KEY (`transition_id`),
  KEY `FKqign95g6j62sv6nmjqk90hf4w` (`billers_id`),
  CONSTRAINT `FKqign95g6j62sv6nmjqk90hf4w` FOREIGN KEY (`billers_id`) REFERENCES `billers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `users_user_roles` (
  `users_id` int NOT NULL,
  `user_roles` int DEFAULT NULL,
  KEY `FKor54r63fqp9kww8u2b4t1hqtl` (`users_id`),
  CONSTRAINT `FKor54r63fqp9kww8u2b4t1hqtl` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--

INSERT INTO users (name, description, dateTime) VALUES
  ('biller 1', 'biller 1 dec', '20221228214122'),
  ('biller 2', 'biller 2 dec', '20221228214130'),
  ('biller 3', 'biller 3 dec', '20221228214130'),
  ('biller 4', 'biller 4 dec', '20221228214133'),
  ('biller 5', 'biller 5 dec', '20221228214134'),
  ('biller 6', 'biller 6 dec', '20221228214134'),
  ('biller 7', 'biller 7 dec', '20221228214134');