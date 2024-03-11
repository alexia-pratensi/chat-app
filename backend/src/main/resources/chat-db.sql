CREATE TABLE USERS (
					`id` INT AUTO_INCREMENT PRIMARY KEY,
					`username` VARCHAR(255),
					`password` VARCHAR(255),
					`role` VARCHAR(255)
);

INSERT INTO USERS (`username`, `password`, `role`) VALUES ('Conseiller', 'password', 'AGENT');
INSERT INTO USERS (`username`, `password`, `role`) VALUES ('Rima', 'password', 'USER');
INSERT INTO USERS (`username`, `password`, `role`) VALUES ('Romain', 'password', 'USER');
INSERT INTO USERS (`username`, `password`, `role`) VALUES ('Alexia', 'password', 'USER');
INSERT INTO USERS (`username`, `password`, `role`) VALUES ('Sylvain', 'password', 'USER');


CREATE TABLE MESSAGES (
					`id` INT AUTO_INCREMENT PRIMARY KEY,
					`content` TEXT,
					`source_id` INT,
					`target_id` INT,
					`created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (`source_id`) REFERENCES `USERS`(`id`),
					FOREIGN KEY (`target_id`) REFERENCES `USERS`(`id`)
);


