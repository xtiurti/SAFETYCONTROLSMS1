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
  ativado INTEGER,
  numeros INTEGER,
  comandos INTEGER NOT NULL 
);

CREATE TABLE Users (
  Login VARCHAR(20) NOT NULL,
  Senha VARCHAR(50) NOT NULL,
  PRIMARY KEY(Login)
);

INSERT INTO teste VALUES(0,1,0);



