eCRF Database Definition

CREATE TABLE "user" 
	(usr_id serial, name varchar(255) NOT NULL, phone_number integer NOT NULL, email_address varchar(355) NOT NULL, 
	 PRIMARY KEY(usr_id)
	);
	
CREATE TABLE "admin" 
	(admin_id serial, 
	 PRIMARY KEY(admin_id), 
	 FOREIGN KEY(admin_id) REFERENCES "user"(usr_id)
	);
	
CREATE TABLE "doctor" 
	(doctor_id serial, 
	 PRIMARY KEY(doctor_id), 
	 FOREIGN KEY(doctor_id) REFERENCES "user"(usr_id)
	);
	
CREATE TABLE "patient" 
	(patient_id serial, 
	 PRIMARY KEY(patient_id), 
	 FOREIGN KEY(patient_id) REFERENCES "user"(usr_id)
	);
	
CREATE TABLE "eCRF_form" 
	(form_id varchar(255), model_v_num varchar(255) NOT NULL, doctor_id serial NOT NULL, patient_id serial NOT NULL,  
	 PRIMARY KEY(form_id),
	 FOREIGN KEY(model_v_num) REFERENCES "eCRF_model"(v_num),
	 FOREIGN KEY(doctor_id) REFERENCES "doctor"(doctor_id), 
	 FOREIGN_KEY(patient_id) REFERENCES "patient"(patient_id)
	);
	
CREATE TABLE "eCRF_model" 
	(v_num varchar(255), date_created date NOT NULL, admin_id serial NOT NULL, 
	 PRIMARY KEY(v_num),
	 FOREIGN KEY(admin_id) REFERENCES "admin"(admin_id)
	);
	
CREATE TABLE "access" 
	(form_id varchar(255), usr_id serial, 
	 PRIMARY KEY(form_id, usr_id),
	 FOREIGN KEY(form_id) REFERENCES "eCRF_form"(form_id),
	 FOREIGN KEY(usr_id) REFERENCES "user"(usr_id)
	);
	
CREATE TABLE "WHO_standard" 
	(standard_id varchar(50), short_descr varchar(355) NOT NULL, long_descr varchar(355) NOT NULL,  
	 PRIMARY KEY(standard_id)
	);
	
CREATE TABLE "module" 
	(module_id serial, name varchar(255) NOT NULL, general_info varchar(355) NOT NULL, standard_id varchar(50) NOT NULL,  
	 PRIMARY KEY(module_id),
	 FOREIGN KEY(standard_id) REFERENCES "WHO_standard"(standard_id)
	);
	
CREATE TABLE "section" 
	(section_id serial, name varchar(255) NOT NULL, tip svarchar(355), sequence_no integer NOT NULL, module_id serial NOT NULL,  
	 PRIMARY KEY(section_id),
	 FOREIGN KEY(module_id) REFERENCES "module"(module_id)
	);

CREATE TABLE "field" 
	(field_id serial, name varchar(255) NOT NULL, version integer NOT NULL, date_of_release date NOT NULL, section_id serial NOT NULL, tip varchar(355),  
	 PRIMARY KEY(field_id),
	 FOREIGN KEY(section_id) REFERENCES "section"(section_id)
	);
	
CREATE TABLE "checkbox_field" 
	(ch_id serial, possible_values varchar(255) NOT NULL, num_of_possible_values integer NOT NULL,  
	 PRIMARY KEY(ch_id),
	 FOREIGN KEY(ch_id) REFERENCES "field"(field_id)
	);
	
CREATE TABLE "cv_field" 
	(cv_id serial, measurement_unit varchar(255) NOT NULL, num_of_chars integer NOT NULL,  
	 PRIMARY KEY(cv_id),
	 FOREIGN KEY(cv_id) REFERENCES "field"(field_id)
	);

CREATE TABLE "freetext_field" 
	(ft_id serial, measurement_unit varchar(255),  
	 PRIMARY KEY(ft_id),
	 FOREIGN KEY(ft_id) REFERENCES "field"(field_id)
	);
	
CREATE TABLE "date_field" 
	(d_id serial,  
	 PRIMARY KEY(d_id),
	 FOREIGN KEY(d_id) REFERENCES "field"(field_id)
	);
	
CREATE TABLE "answer" 
	(form_id varchar(255), field_id serial, answer_text varchar(355) NOT NULL, date date,  
	 PRIMARY KEY(form_id, field_id, date),
	 FOREIGN KEY(form_id) REFERENCES "eCRF_form"(form_id),
	 FOREIGN KEY(field_id) REFERENCES "field"(field_id)
	);
	
CREATE TABLE "includes" 
	(model_v_num varchar(255), field_id serial, inclued boolean NOT NULL, required NOT NULL, predef_value varchar(255),  
	 PRIMARY KEY(model_v_num, field_id),
	 FOREIGN KEY(model_v_num) REFERENCES "eCRF_model"(v_num),
	 FOREIGN KEY(field_id) REFERENCES "field"(field_id)
	);

INSERT INTO "WHO_standard" VALUES
    ('01', 'short_descr', 'long_descr');

INSERT INTO "module" VALUES
    (DEFAULT, 'Module 1', 'Module 1 to be completed on the first day of admission to the health centre', '01'), 
    (DEFAULT, 'Module 2', 'Module 2 to be completed on the first day of admission to ICU or high dependency unit. Module 2 should also be completed daily for as many days as resources allow. Continue to follow-up patients who transfer between wards', '01'), 
    (DEFAULT, 'Module 3', 'Module 3 to be completed at discharge or death', '01');

INSERT INTO "section" VALUES
    (DEFAULT, 'Clinical InclusionCriteria', 'Respiratory rate >= 50', 1, 1), 
    (DEFAULT, 'Demographics', '', 2, 1), 
    (DEFAULT, 'Vital signs', 'record most abnormal value between 00:00 to 24:00', 1, 2), 
    (DEFAULT, 'Daily clinical features', 'Unk = Unknown', 2, 2),
    (DEFAULT, 'Diagnostic/Pathogen testing', '', 1, 3), 
    (DEFAULT, 'Complications: at any time during ..', '', 2, 3);

INSERT INTO "field"(field_id, name, tip, section_id, version, date_of_release) VALUES
    (DEFAULT, 'Proven or suspected infection with pathogen of Public Health Interest', '', 5, 1, '2017-03-14'),
    (DEFAULT, 'A history of self-reported feverishness or measured fever of >= 38oC', '', 5, 1, '2017-03-14'),
    (DEFAULT, 'Sex at birth', '', 6, 1, '2017-03-14'),
    (DEFAULT, 'Healthcare worker?', '', 6, 1, '2017-03-14'),
    (DEFAULT, 'Severe dehydration', '', 7, 1, '2017-03-14'),
    (DEFAULT, 'Sternal capillary refill time > 2seconds', '', 7, 1, '2017-03-14'),
    (DEFAULT, 'Cough and sputum production', '', 8, 1, '2017-03-14'),
    (DEFAULT, 'Sore throat', '', 8, 1, '2017-03-14'),
    (DEFAULT, 'Coronavirus', '', 9, 1, '2017-03-14'),
    (DEFAULT, 'HIV:', '', 9, 1, '2017-03-14'),
    (DEFAULT, 'Seizure', '', 10, 1, '2017-03-14'),
    (DEFAULT, 'Bacteraemia', '', 10, 1, '2017-03-14'), 
    (DEFAULT, 'If date of birth is unknown, record: Age', '', 6, 1, '2017-03-14'),
    (DEFAULT, 'OR', '', 6, 1, '2017-03-14'),
    (DEFAULT, 'BP', '', 7, 1, '2017-03-14'),
    (DEFAULT, '', '', 7, 1, '2017-03-14'),
    (DEFAULT, 'If positive, type', '', 9, 1, '2017-03-14'),
    (DEFAULT, 'If positive, specify', '', 9, 1, '2017-03-14'),
    (DEFAULT, 'If positive, specify virus', '', 9, 1, '2017-03-14'),
    (DEFAULT, 'Date of birth', '', 6, 1, '2017-03-14');

INSERT INTO "date_field" VALUES
    (20);

INSERT INTO "freetext_field" VALUES
    (17, 'not specified'),
    (18, 'not specified'),
    (19, 'not specified');

INSERT INTO "cv_field" VALUES
    (13, 'years', 3),
    (14, 'months', 2),
    (15, '\\(systolic)\\', 3),
    (15, '\\(diastolic)\\ mmHg', 3);

INSERT INTO "checkbox_field" VALUES
    (1, 'Yes, No', 2),
    (2, 'Yes, No', 2),
    (3, 'Male, Female, Not specified', 3),
    (4, 'Yes, No, Unknown', 3),
    (5, 'Yes, No, Unknown', 3),
    (6, 'Yes, No, Unknown', 3),
    (7, 'Yes, No, Unknown', 3),
    (8, 'Yes, No, Unknown', 3),
    (9, 'Positive, Negative, Not done', 3),
    (10, 'Positive, Negative, Not done', 3),
    (11, 'Yes, No, Unknown', 3),
    (12, 'Yes, No, Unknown', 3);


CREATE TABLE "account" 
	(accountid serial, username varchar(255), password varchar(255), createdon date, last_login date, usr_id serial, 
	 PRIMARY KEY(accountid), 
	 FOREIGN KEY(usr_id) REFERENCES "user"(usr_id)
	);
	
CREATE TABLE "role" 
	(roleid serial, rolename varchar(255) NOT NULL, 
	 PRIMARY KEY(roleid)
	);

INSERT INTO "role" VALUES
    (DEFAULT, 'admin'), 
    (DEFAULT, 'doctor'), 
    (DEFAULT, 'patient');

CREATE TABLE "account_role" 
	(accountid serial, roleid serial, grant_date date NOT NULL, 
	 PRIMARY KEY(accountid, roleid), 
	 FOREIGN KEY(accountid) REFERENCES "account"(accountid), 
	 FOREIGN KEY(roleid) REFERENCES "role"(roleid) 
	);
	
CREATE TABLE "action" 
	(actionid serial, actionname varchar(50) NOT NULL, 
	 PRIMARY KEY(actionid) 
	);

INSERT INTO "action" VALUES
    (DEFAULT, 'view'), 
    (DEFAULT, 'update');
	
CREATE TABLE "account_role_action" 
	(accountid serial, roleid serial, formid varchar(50), actionid serial,  
	 UNIQUE(accountid, roleid, formid, actionid), 
	 FOREIGN KEY(accountid) REFERENCES "account"(accountid), 
	 FOREIGN KEY(roleid) REFERENCES "role"(roleid), 
	 FOREIGN KEY(formid) REFERENCES "eCRF_form"(form_id), 
	 FOREIGN KEY(actionid) REFERENCES "action"(actionid) 
	);
	
	
CREATE VIEW FormsPerModulesPerDate(form_id, module_id, date) AS
SELECT a.form_id, m.module_id, a.date
FROM answer a, field f, section s, module m
WHERE a.field_id = f.field_id AND f.section_id = s.section_id AND s.module_id = m.module_id
GROUP BY a.form_id, m.module_id, a.date; 

