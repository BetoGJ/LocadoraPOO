DROP DATABASE IF EXISTS locadora;
CREATE DATABASE locadora;
USE locadora;

CREATE TABLE Locadora (
    CNPJ VARCHAR(18) PRIMARY KEY,
    Nome VARCHAR(45) NOT NULL,
    Cidade VARCHAR(45) NOT NULL
);

CREATE TABLE Cliente (
    CPF VARCHAR(14) PRIMARY KEY,
    Nome VARCHAR(45) NOT NULL,
    Data_de_nascimento DATETIME NOT NULL,
    Senha VARCHAR(255) NOT NULL
);

CREATE TABLE Filme (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Titulo VARCHAR(45) NOT NULL,
    Ano INT(4),
    Diretor VARCHAR(45),
    Genero VARCHAR(45),
    Classificacao VARCHAR(15),
    Quantidade INT NOT NULL,
    Disponivel TINYINT NOT NULL,
    Locadora_CNPJ VARCHAR(18) NOT NULL,

    FOREIGN KEY (Locadora_CNPJ) REFERENCES Locadora(CNPJ)
);

CREATE TABLE Vendedor (
    CPF VARCHAR(14) PRIMARY KEY,
    Nome VARCHAR(45) NOT NULL,
    Salario FLOAT NOT NULL,
    Data_de_nascimento DATETIME NOT NULL,
    Senha VARCHAR(255) NOT NULL,
    AdminStatus BOOLEAN NOT NULL,
    Locadora_CNPJ VARCHAR(18) NOT NULL,


    FOREIGN KEY (Locadora_CNPJ) REFERENCES Locadora(CNPJ)
);

CREATE TABLE Emprestimo (
    Id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    Data DATETIME NOT NULL,
    Devolvido TINYINT,
    Devolucao DATETIME NOT NULL,
    Vendedor_CPF VARCHAR(14) NOT NULL,
    Cliente_CPF VARCHAR(14) NOT NULL,
    Locadora_CNPJ VARCHAR(18) NOT NULL,
    Filme_Id INT,

    FOREIGN KEY (Vendedor_CPF) REFERENCES Vendedor(CPF),
    FOREIGN KEY (Cliente_CPF) REFERENCES Cliente(CPF),
    FOREIGN KEY (Locadora_CNPJ) REFERENCES Locadora(CNPJ),
    FOREIGN KEY (Filme_Id) REFERENCES Filme(Id)
);

CREATE TABLE Multa (
    Id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    Valor FLOAT NOT NULL,
    Data DATETIME NOT NULL,
    DataPagamento DATETIME,
    Locadora_CNPJ VARCHAR(18) NOT NULL,
    Emprestimo_Id INT NOT NULL,

    FOREIGN KEY (Locadora_CNPJ) REFERENCES Locadora(CNPJ),
    FOREIGN KEY (Emprestimo_Id) REFERENCES Emprestimo(Id)
);
/*
CREATE ROLE 'Cargo_Vendedor';
GRANT SELECT, INSERT ON locadora.Loja.Filme TO 'Cargo_Vendedor';

CREATE ROLE 'Cargo_Gerente';
GRANT ALL ON locadora.Loja.Cliente TO 'Cargo_Gerente';
GRANT ALL ON locadora.Loja.Vendedor TO 'Cargo_Gerente';

CREATE USER 'gerente'@'%' IDENTIFIED BY '1234';
CREATE USER 'vendedor'@'%' IDENTIFIED BY '1234';

GRANT 'Cargo_Gerente' TO 'gerente'@'%';
GRANT 'Cargo_Vendedor' TO 'vendedor'@'%';
*/
CREATE TABLE Cliente_da_Locadora (
    Locadora_CNPJ VARCHAR(18) NOT NULL,
    Cliente_CPF VARCHAR(20) NOT NULL,

    PRIMARY KEY (Locadora_CNPJ, Cliente_CPF),
    FOREIGN KEY (Locadora_CNPJ) REFERENCES Locadora(CNPJ),
    FOREIGN KEY (Cliente_CPF) REFERENCES Cliente(CPF)
);

INSERT INTO Locadora (CNPJ, Nome, Cidade) VALUES
    ('12.345.678/0001-95', 'Inafilmes', 'Santa Rita do Sapucaí'),
    ('47.892.113/0001-06', 'Mega Filmes HD', 'Conceição dos Ouros'),
    ('28.561.904/0001-71', 'Netflix 2', 'Pouso Alegre'),
    ('63.770.245/0001-18', 'Torrente', 'Santa Rita do Sapucaí'),
    ('91.438.526/0001-42', 'Inafilmes', 'Tupaciguara');

INSERT INTO Filme (Titulo, Ano, Diretor, Genero, Classificacao, Quantidade, Disponivel, Locadora_CNPJ) VALUES
    ('O Bicho Vai Pegar', 2006, 'Roger Allers', 'Comedia', "Livre", 10, 10, '12.345.678/0001-95'),
    ('O Bicho Vai Pegar 2', 2008, 'Todd Wilderman', 'Comedia', "18", 12, 9, '12.345.678/0001-95'),
    ('O Bicho Vai Pegar 3', 2010, 'Cody Cameron', 'Comedia', "14", 6, 3, '12.345.678/0001-95'),
    ('Kill Bill - Volume 1', 2003, 'Quentin Tarantino', 'Ação', "16", 10, 4, '63.770.245/0001-18'),
    ('Em Ritmo de Fuga', 2017, 'Edgar Wright', 'Ação',  "Livre", 10, 10, '91.438.526/0001-42');

INSERT INTO Cliente (CPF, Nome, Data_de_nascimento, Senha) VALUES
    ('12345678910', 'Luis Eduardo', '1995-03-15', '109322837'),
    ('98765432100', 'Eric', '2001-07-21', '109322837'),
    ('74185296311', 'Igor Grecco', '1988-12-01', '109322837'),
    ('85296374122', 'Daenerys Targaryen', '1999-05-10', '109322837'),
    ('15935745633', 'Guerzoni', '1992-09-30', '109322837');

INSERT INTO Vendedor (CPF, Nome, Salario, Data_de_nascimento, Senha, Locadora_CNPJ, AdminStatus) VALUES
    ('11122233344', 'João Pedro', 2500.00, '1990-02-15', '109322837', '12.345.678/0001-95', false),
    ('55566677788', 'Ana Clara', 3200.50, '1987-08-20', '109322837', '47.892.113/0001-06', false),
    ('99988877766', 'Felipe Rocha', 2800.75, '1995-11-05', '109322837', '28.561.904/0001-71', false),
    ('44455566677', 'Camila Martins', 3100.00, '1993-04-18', '109322837', '63.770.245/0001-18', false ),
    ('22233344455', 'Bruno Silva', 2700.25, '1998-01-12', '109322837', '91.438.526/0001-42', false);

INSERT INTO Emprestimo
(Data, Devolvido, Devolucao, Vendedor_CPF, Cliente_CPF, Locadora_CNPJ, Filme_Id) VALUES
    ('2025-05-01 14:30:00', 1, '2025-05-08 16:00:00', '11122233344', '12345678910', '12.345.678/0001-95', 1),
    ('2025-05-02 10:00:00', 0, '2025-05-09 10:00:00', '55566677788', '98765432100', '47.892.113/0001-06', 2),
    ('2025-05-03 18:20:00', 1, '2025-05-10 13:10:00', '99988877766', '74185296311', '28.561.904/0001-71', 3),
    ('2025-05-04 09:15:00', 0, '2025-05-11 09:15:00', '44455566677', '85296374122', '63.770.245/0001-18', 4),
    ('2025-05-05 20:45:00', 1, '2025-05-12 11:30:00', '22233344455', '15935745633', '91.438.526/0001-42', 5);

INSERT INTO Multa (Valor, Data, Locadora_CNPJ, Emprestimo_Id) VALUES
    (15.50, '2025-05-06 12:00:00', '12.345.678/0001-95', 1),
    (8.00,  '2025-05-07 15:30:00', '47.892.113/0001-06', 2),
    (20.00, '2025-05-08 10:45:00', '28.561.904/0001-71', 3),
    (12.75, '2025-05-09 18:00:00', '63.770.245/0001-18', 4),
    (5.25,  '2025-05-10 09:20:00', '91.438.526/0001-42', 5);

INSERT INTO Cliente_da_Locadora (Locadora_CNPJ, Cliente_CPF) VALUES
    ('12.345.678/0001-95', '12345678910'),
    ('47.892.113/0001-06', '98765432100'),
    ('28.561.904/0001-71', '74185296311'),
    ('63.770.245/0001-18', '85296374122'),
    ('91.438.526/0001-42', '15935745633'),
    ('12.345.678/0001-95', '74185296311');
    


