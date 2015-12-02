CREATE TABLE Agenda (
  Numero VARCHAR(20) NOT NULL ,
  Nome VARCHAR(30) NOT NULL,
  Senha VARCHAR(10) NOT NULL,
  PRIMARY KEY(Numero)
);

CREATE TABLE Log_1 (
  Data_2 DATE NOT NULL ,
  Users_Login VARCHAR(20) NOT NULL,
  Comando VARCHAR(50) NULL,
  PRIMARY KEY(Data_2)
);

CREATE TABLE teste (
  comandos INTEGER NOT NULL 
);

CREATE TABLE Users (
  Login VARCHAR(20) NOT NULL,
  Senha VARCHAR(50) NOT NULL,
  PRIMARY KEY(Login)
);

CREATE TABLE CellPhone (
  imei VARCHAR(30) NOT NULL,
  cadastros INTEGER NOT NULL,
  senha VARCHAR(30) NOT NULL,
  PRIMARY KEY(imei)
);

INSERT INTO teste VALUES(0);



