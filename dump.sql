SET DB_CLOSE_DELAY -1;         
;              
CREATE USER IF NOT EXISTS SA SALT '8cb4cd314bf4f964' HASH 'b012e518b5104abc5d4be6cbdf58e95401364a0e74a134e96307f960d52d797a' ADMIN;            
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_553435C3_1796_4F39_8E6B_A2B670A9743C START WITH 4 BELONGS_TO_TABLE;     
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_876BF05E_0F90_4228_990F_A81BE68F0155 START WITH 1 BELONGS_TO_TABLE;     
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_9C0ACE45_DEC2_44E4_9E9A_9AEF974B30DB START WITH 7 BELONGS_TO_TABLE;     
CREATE MEMORY TABLE PUBLIC.CATEGORY(
    ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_9C0ACE45_DEC2_44E4_9E9A_9AEF974B30DB) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_9C0ACE45_DEC2_44E4_9E9A_9AEF974B30DB,
    CODE VARCHAR(255),
    DIRECTORY VARCHAR(255),
    DISPLAY_NAME VARCHAR(255),
    EXPLANATION VARCHAR(255),
    PARENT_ID INTEGER
);               
ALTER TABLE PUBLIC.CATEGORY ADD CONSTRAINT PUBLIC.CONSTRAINT_3 PRIMARY KEY(ID);
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.CATEGORY; 
INSERT INTO PUBLIC.CATEGORY(ID, CODE, DIRECTORY, DISPLAY_NAME, EXPLANATION, PARENT_ID) VALUES
(1, 'HYPO', NULL, 'Hypotheek documenten', NULL, NULL),
(2, 'ZORG', NULL, 'Zorg', NULL, NULL),
(3, 'KOKEN', NULL, 'Recepten & Kookkunst', NULL, NULL),
(4, 'BANK', NULL, 'Bankzaken', NULL, NULL),
(5, 'MARCELBANK', NULL, 'Prive bank Marcel', NULL, 4);         
CREATE MEMORY TABLE PUBLIC.KEYWORD(
    ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_553435C3_1796_4F39_8E6B_A2B670A9743C) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_553435C3_1796_4F39_8E6B_A2B670A9743C,
    VALUE VARCHAR(255),
    CATEGORY_ID INTEGER
);      
ALTER TABLE PUBLIC.KEYWORD ADD CONSTRAINT PUBLIC.CONSTRAINT_F PRIMARY KEY(ID); 
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.KEYWORD;  
INSERT INTO PUBLIC.KEYWORD(ID, VALUE, CATEGORY_ID) VALUES
(1, 'ABN', 5),
(2, 'ABNAMRO', 5),
(3, 'private', 5); 
CREATE MEMORY TABLE PUBLIC.USER(
    ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_876BF05E_0F90_4228_990F_A81BE68F0155) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_876BF05E_0F90_4228_990F_A81BE68F0155,
    EMAIL VARCHAR(255),
    NAME VARCHAR(255),
    PASSWORD VARCHAR(255)
);
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.CONSTRAINT_2 PRIMARY KEY(ID);    
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.USER;     
ALTER TABLE PUBLIC.CATEGORY ADD CONSTRAINT PUBLIC.UK_ACATPLU22Q5D1ANDQL2JBVJY7 UNIQUE(CODE);   
ALTER TABLE PUBLIC.CATEGORY ADD CONSTRAINT PUBLIC.FK_81THRBNB8C08GUA7TVQJ7XDQK FOREIGN KEY(PARENT_ID) REFERENCES PUBLIC.CATEGORY(ID) NOCHECK;  
ALTER TABLE PUBLIC.KEYWORD ADD CONSTRAINT PUBLIC.FK_L7CRVIQUKE2HH591BSD40WDQV FOREIGN KEY(CATEGORY_ID) REFERENCES PUBLIC.CATEGORY(ID) NOCHECK; 
