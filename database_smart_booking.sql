CREATE DATABASE smart_booking;

USE smart_booking;


CREATE TABLE behaviours(
    id_behaviour INT(11) PRIMARY KEY AUTO_INCREMENT, 
    title varchar(40) NOT NULL
);

INSERT INTO behaviours
VALUES (1,'Apenas responder'), (2,'Mostrar quartos disponíveis'),
(3,'Agendar uma reserva'), (4,'Cancelar uma reserva'), 
(5,'Mostrar reserva agendada do cliente');

CREATE TABLE questions(
    id_question INT(11) PRIMARY KEY AUTO_INCREMENT,
    question_text varchar(255) NOT NULL,
    id_behaviour INT(11) NOT NULL,
    FOREIGN KEY (id_behaviour) REFERENCES behaviours(id_behaviour),
    strong_word char(1) NOT NULL DEFAULT '0'
);

INSERT INTO questions (question_text, id_behaviour, strong_word)
VALUES ('bom dia', 1, '0'), ('Boa tarde', 1, '0'), ('boa noite', 1, '0'), 
('olá', 1, '0'), ('oi', 1, '0'), ('tudo bem', 1, '0'), ('como vai', 1, '0'), ('com você', 1, '0'), 

('mostrar quartos',2, '0'), ('mostre quartos', 2, '0'), ('ver quartos', 2, '0'), 
('quais quartos', 2, '0'), ('quartos disponíveis', 2, '1'), ('exibir quartos', 2, '0'),
('exiba quartos', 2, '0'), ('apresente quartos', 2, '0'), ('apresentar quartos', 2, '0'),

('agendar', 3, '0'), ('reservar', 3, '0'), ('alugar', 3, '0'), ('locar', 3, '0'), 
('fazer reserva', 3, '0'), ('fazer agendamento', 3, '0'),  ('fazer aluguel', 3, '0'),  ('fazer locação', 3, '0'),
('agendar reserva', 3, '0'), ('efetuar reserva', 3, '0'), ('efetuar agendamento', 3, '0'), ('efetuar aluguel', 3, '0'), 
('efetuar locação', 3, '0'), 
('realizar reserva', 3, '0'), ('realizar agendamento', 3, '0'), ('realizar aluguel', 3, '0'), ('realizar locação', 3, '0'), 
('executar reserva', 3, '0'), ('executar agendamento', 3, '0'), ('executar aluguel', 3, '0'), ('executar locação', 3, '0'),

('cancelar', 4, '1'), ('cancelamento', 4, '1'),

('mostrar reserva', 5, '0'), ('mostrar reservas', 5, '0'), 
('mostre reserva',5, '0'), ('mostre reservas',5, '0'),
('ver reserva', 5, '0'), ('ver reservas', 5, '0'), 
('apresente reserva',5, '0'), ('apresente reservas',5, '0'),
('apresentar reserva',5, '0'), ('apresentar reservas',5, '0'),
('exibir reserva', 5, '0'), ('exibir reservas', 5, '0'), 
('exiba reserva',5, '0'), ('exiba reservas',5, '0'),

('mostrar agendamento', 5, '0'), ('mostrar agendamentos', 5, '0'),
('mostre agendamento',5, '0'), ('mostre agendamentos',5, '0'),
('ver agendamento', 5, '0'), ('ver agendamentos', 5, '0'), 
('apresente agendamento',5, '0'), ('apresente agendamentos',5, '0'),
('apresentar agendamento',5, '0'), ('apresentar agendamentos',5, '0'),
('exibir agendamento', 5, '0'), ('exibir agendamentos', 5, '0'), 
('exiba agendamento',5, '0'), ('exiba agendamentos',5, '0'),

('mostrar aluguel', 5, '0'), ('mostrar aluguéis', 5, '0'),
('mostre aluguel',5, '0'), ('mostre aluguéis',5, '0'),
('ver aluguel', 5, '0'), ('ver aluguéis', 5, '0'), 
('apresente aluguel',5, '0'), ('apresente aluguéis',5, '0'),
('apresentar aluguel',5, '0'), ('apresentar aluguéis',5, '0'),
('exibir aluguel', 5, '0'), ('exibir aluguéis', 5, '0'), 
('exiba aluguel',5, '0'), ('exiba aluguéis',5, '0'),

('mostrar locação', 5, '0'), ('mostrar locações', 5, '0'),
('mostre locação',5, '0'), ('mostre locações',5, '0'),
('ver locação', 5, '0'), ('ver locações', 5, '0'), 
('apresente locação',5, '0'), ('apresente locações',5, '0'),
('apresentar locação',5, '0'), ('apresentar locações',5, '0'),
('exibir locação', 5, '0'), ('exibir locações', 5, '0'), 
('exiba locação',5, '0'), ('exiba locações',5, '0');

CREATE TABLE answers(
    id_answer INT(11) PRIMARY KEY AUTO_INCREMENT,
    answer_text varchar(255) NOT NULL
);

INSERT INTO answers (answer_text)
VALUES ('Bom dia!'), ('Boa tarde!'), ('Boa noite!'),
('Olá, como vai?'), ('Comigo está tudo ótimo!'),
('A seguir, está uma lista dos nossos quartos disponíveis!'),
('Ok, farei o agendamento para você!'), 
('Ok, vou conferir se você tem algum quarto alugado para poder efetuar o cancelamento'),
('Ok, vou conferir se você tem agendamentos ativos');


CREATE TABLE questions_answers(
    id_question_answer INT(11) PRIMARY KEY AUTO_INCREMENT,
    id_question INT(11) NOT NULL,
    FOREIGN KEY (id_question) REFERENCES questions(id_question),
    id_answer INT(11) NOT NULL,
    FOREIGN KEY (id_answer) REFERENCES answers(id_answer)
);

INSERT INTO questions_answers (id_question, id_answer)
VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 4), (6, 5),
(7, 5), (8, 5),

(9, 6), (10, 6), (11, 6), (12, 6), (13, 6), (14, 6), (15, 6),
(16, 6), (17, 6),

(18, 7), (19, 7), (20, 7), (21, 7), (22, 7), (23, 7), (24, 7), (25, 7), (26, 7),
(27, 7), (28, 7), (29, 7), (30, 7), (31, 7), (32, 7), (33, 7), (34, 7), (35, 7),
(36, 7), (37, 7), (38, 7),

(39, 8), (40, 8),

(41, 9), (42, 9), (43, 9), (44, 9), (45, 9), (46, 9), (47, 9),
(48, 9), (49, 9), (50, 9), (51, 9), (52, 9), (53, 9), (54, 9), (55, 9),
(56, 9), (57, 9), (58, 9), (59, 9), (60, 9), (61, 9), (62, 9), (63, 9),
(64, 9), (65, 9), (66, 9), (67, 9), (68, 9), (69, 9), (70, 9), (71, 9),
(72, 9), (73, 9), (74, 9), (75, 9), (76, 9), (77, 9), (78, 9), (79, 9),
(80, 9), (81, 9), (82, 9), (83, 9), (84, 9), (85, 9), (86, 9), (87, 9),
(88, 9), (89, 9), (90, 9), (91, 9), (92, 9), (93, 9), (94, 9), (95, 9), 
(96, 9);

CREATE TABLE room_type(
    id_room_type INT(11) PRIMARY KEY AUTO_INCREMENT,
    title varchar(40) NOT NULL,
    description varchar(255),
    price_per_day decimal(15,2) NOT NULL,
    active CHAR(1) NOT NULL DEFAULT '1'
);

INSERT INTO room_type (title, description, price_per_day)
VALUES ('Suíte comum', 'A suíte comum é aconchegante, limpa e tem ar-condicionado!', 40.00),
('Suíte de luxo', 'A suíte de luxo tem banheira de hidromassagem e televisão!', 100.00),
('Suíte presidencial', 'A suíte presidencial tem jacuzzi, playstation 5 e massagem grátis!', 200.00);

CREATE TABLE rooms(
    id_room INT(11) PRIMARY KEY AUTO_INCREMENT,
    room_number varchar(25),
    id_room_type INT(11) NOT NULL,
    FOREIGN KEY (id_room_type) REFERENCES room_type(id_room_type)
);

INSERT INTO rooms (room_number, id_room_type)
VALUES ('1-A', 1), ('1-B', 1), ('1-C', 1), ('1-D', 1), ('1-E', 1),
('2-A', 2), ('2-B', 2), ('2-C', 2), ('2-D', 2), ('2-E', 2),
('3-A', 3), ('3-B', 3), ('3-C', 3), ('3-D', 3), ('3-E', 3);

CREATE TABLE booking(
    id_booking INT(11) PRIMARY KEY AUTO_INCREMENT,
    id_room INT(11) NOT NULL,
    FOREIGN KEY (id_room) REFERENCES rooms(id_room),
    cpf_customer CHAR(11) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    full_price decimal(15,2) NOT NULL,
    booking_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cancellation_date TIMESTAMP NULL DEFAULT NULL,
    status ENUM('Agendada', 'Cancelada', 'Finalizada') NOT NULL DEFAULT 'Agendada'
);

/* PROCEDURES */

DELIMITER $$

	CREATE PROCEDURE book_room(IN id_room_type_paramater INT(11), IN cpf_customer_parameter CHAR(11), 
    IN start_date_parameter DATE, IN end_date_parameter DATE)
	BEGIN
        IF id_room_type_paramater = 0 OR cpf_customer_parameter = '' OR start_date_parameter = null 
        OR end_date_parameter = null OR start_date_parameter <= current_date()
        OR end_date_parameter <= current_date() OR start_date_parameter = end_date_parameter THEN
			SELECT 'Dados inválidos';
		ELSE 
			IF NOT EXISTS(SELECT cpf_customer FROM booking WHERE 
            cpf_customer LIKE cpf_customer_parameter AND status LIKE 'Agendada') THEN
            
				SET @id_room = 0;
            
				SELECT rooms.id_room 
                INTO @id_room FROM rooms
                INNER JOIN room_type ON rooms.id_room_type = room_type.id_room_type
				LEFT JOIN booking on rooms.id_room = booking.id_room AND
				booking.status = 'Agendada'
				WHERE booking.id_booking IS NULL AND room_type.id_room_type = id_room_type_paramater
                LIMIT 0,1;
                
                IF @id_room <> 0 THEN
            
					SELECT (end_date_parameter - start_date_parameter) * room_type.price_per_day
					AS full_price_calculated 
					INTO @full_price_period
					FROM room_type
					WHERE room_type.id_room_type = id_room_type_paramater;
				
					INSERT INTO booking (id_room, cpf_customer, start_date, end_date, full_price)
					VALUES (@id_room, cpf_customer_parameter, start_date_parameter,
					end_date_parameter, @full_price_period);
					
					SELECT CONCAT('Agendamento efetuado com sucesso! Custo total da estadia: R$ ',  @full_price_period);
				ELSE
					SELECT 'Não foram encontrados quartos disponíveis para esta categoria!';
				END IF;
                
			ELSE
				SELECT 'Você já tem um agendamento ativo!';
            END IF;
		END IF;
    
	END$$

DELIMITER ;

DELIMITER $$

	CREATE PROCEDURE cancel_booking(IN cpf_customer_parameter CHAR(11))
	BEGIN
        IF cpf_customer_parameter = '' THEN
			SELECT 'Dados inválidos';
		ELSE 
			IF EXISTS(SELECT cpf_customer FROM booking WHERE 
            cpf_customer LIKE cpf_customer_parameter AND status LIKE 'Agendada') THEN
            
				SET @full_price_period = 0;
                
                UPDATE booking SET status = 'Cancelada', cancellation_date = CURRENT_TIMESTAMP
                WHERE cpf_customer LIKE cpf_customer_parameter AND status LIKE 'Agendada';
                
				SELECT CASE 
				WHEN CONVERT(booking.cancellation_date, DATE) > booking.start_date 
				THEN (CONVERT(booking.cancellation_date, DATE) - booking.start_date) * room_type.price_per_day
				ELSE '20,00 em multa de cancelamento' END as full_price_calculated
				INTO @full_price_period
				FROM booking               
                INNER JOIN rooms ON booking.id_room = rooms.id_room
                INNER JOIN room_type ON rooms.id_room_type = room_type.id_room_type
                WHERE booking.cpf_customer LIKE cpf_customer_parameter AND status LIKE 'Cancelada'
				ORDER BY booking.cancellation_date DESC LIMIT 0,1;
					
				SELECT CONCAT('Agendamento cancelado com sucesso. Custo total da estadia: R$ ',  @full_price_period);
                
			ELSE
				SELECT 'Você não tem nenhum agendamento ativo!';
            END IF;
		END IF;
    
	END$$

DELIMITER ;

/* FUNCTIONS */


DELIMITER $$

CREATE FUNCTION count_occurrences(sentence VARCHAR(255), question VARCHAR(255))
    RETURNS INT
    BEGIN
    
    DECLARE count INT DEFAULT 0;
    
	DECLARE sentence_length INT DEFAULT 0;
	DECLARE sentence_substring_length INT DEFAULT 0;
    DECLARE temporary_sentence VARCHAR(255) DEFAULT '';
    
    loop_through_sentence:
		LOOP
			SET sentence_length = CHAR_LENGTH(sentence);
			SET temporary_sentence = SUBSTRING_INDEX(sentence, ' ', 1);
            
            /*Removes the accentuation of the sentence for better results*/
            SET temporary_sentence = REPLACE(temporary_sentence,'?','');
            SET temporary_sentence = REPLACE(temporary_sentence,'!','');
            SET temporary_sentence = REPLACE(temporary_sentence,',','');
            SET temporary_sentence = REPLACE(temporary_sentence,'.','');
            SET temporary_sentence = REPLACE(temporary_sentence,':','');
            SET temporary_sentence = REPLACE(temporary_sentence,';','');
			SET temporary_sentence = REPLACE(temporary_sentence, '(','');
            SET temporary_sentence = REPLACE(temporary_sentence, ')','');
            SET temporary_sentence = REPLACE(temporary_sentence, '"','');
            SET temporary_sentence = REPLACE(temporary_sentence, '\'','');
            
            IF question LIKE concat('%',temporary_sentence,'%') THEN
				SET count = count + 1;
			END IF;
            
            SET sentence_substring_length = CHAR_LENGTH(SUBSTRING_INDEX(sentence, ' ', 1))+2;
			SET sentence = MID(sentence, sentence_substring_length, sentence_length);
			IF sentence = '' THEN
				LEAVE loop_through_sentence;
			END IF;
		END LOOP loop_through_sentence;
        
        RETURN count;

    END$$

DELIMITER ;


