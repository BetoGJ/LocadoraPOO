DROP DATABASE IF EXISTS locadora;
CREATE DATABASE locadora;
USE locadora;

CREATE TABLE Locadora (
    CNPJ VARCHAR(18) PRIMARY KEY,
    Nome VARCHAR(45) NOT NULL,
    Cidade VARCHAR(45) NOT NULL
);

CREATE TABLE Cliente (
    CPF VARCHAR(13) PRIMARY KEY,
    Nome VARCHAR(45) NOT NULL,
    Data_de_nascimento DATETIME NOT NULL,
    Cidade VARCHAR(45) NOT NULL
);

CREATE TABLE Filme (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Titulo VARCHAR(45) NOT NULL,
    Ano INT(4) ,
    Diretor VARCHAR(45),
    Genero VARCHAR(45),
    Classificacao INT(2),
    Quantidade INT NOT NULL,
    Disponivel TINYINT NOT NULL,
    Locadora_CNPJ VARCHAR(18) NOT NULL,

	FOREIGN KEY (Locadora_CNPJ) REFERENCES Locadora(CNPJ)
);

CREATE TABLE Vendedor (
    CPF VARCHAR(13) PRIMARY KEY,
    Nome VARCHAR(45) NOT NULL,
    Salario FLOAT NOT NULL,
    Data_de_nascimento DATETIME NOT NULL,
	Locadora_CNPJ VARCHAR(18) NOT NULL,

	FOREIGN KEY (Locadora_CNPJ) REFERENCES Locadora(CNPJ)
);

CREATE TABLE Emprestimo (
    Id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    Data DATETIME NOT NULL,
    Devolvido TINYINT,
    Devolucao DATETIME NOT NULL,
    Vendedor_CPF VARCHAR(20) NOT NULL,
    Cliente_CPF VARCHAR(20) NOT NULL,
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
    Locadora_CNPJ VARCHAR(18) NOT NULL,
    Emprestimo_Id INT NOT NULL,
    
	FOREIGN KEY (Locadora_CNPJ)REFERENCES Locadora(CNPJ),
	FOREIGN KEY (Emprestimo_Id)	REFERENCES Emprestimo(Id)
);

CREATE ROLE 'Cargo_Vendedor';
GRANT SELECT, INSERT ON locadora.Loja.Filme TO 'Cargo_Vendedor';

CREATE ROLE 'Cargo_Gerente';
GRANT ALL ON locadora.Loja.Cliente TO 'Cargo_Gerente';
GRANT ALL ON locadora.Loja.Vendedor TO 'Cargo_Gerente';

CREATE USER 'gerente'@'%' IDENTIFIED BY '1234';
CREATE USER 'vendedor'@'%' IDENTIFIED BY '1234';

GRANT 'Cargo_Gerente' TO 'gerente'@'%'; 
GRANT 'Cargo_Vendedor' TO 'vendedor'@'%'; 

CREATE TABLE Cliente_da_Locadora (
    Locadora_CNPJ VARCHAR(18) NOT NULL,
    Cliente_CPF VARCHAR(20) NOT NULL,

    PRIMARY KEY (Locadora_CNPJ, Cliente_CPF),
	FOREIGN KEY (Locadora_CNPJ) REFERENCES Locadora(CNPJ),

	FOREIGN KEY (Cliente_CPF) REFERENCES Cliente(CPF)
);

INSERT INTO Locadora (CPF, Nome, Cidade) VALUES
    ('12.345.678/0001-95', 'Localdora','Cachoeira de Minas'),
    ('47.892.113/0001-06', 'Mega Filmes HD', 'Conceição dos Ouros'),
    ('28.561.904/0001-71', 'Netflix 2', 'Pouso Alegre'),
    ('63.770.245/0001-18', 'Torrente', 'Santa Rita do Sapucaí'),
    ('91.438.526/0001-42', 'Inafilmes', 'Tupaciguara');
    
INSERT INTO Filme (Titulo, Ano, Diretor, Genero, Classificacao, Quantidade, Disponivel, Locadora_CNPJ) VALUES
    ('O Bicho Vai Pegar',2006, 'Roger Allers', 3, 10, 10, '12.345.678/0001-95'),
    ('O Bicho Vai Pegar 2',2008, 'Todd Wilderman', 3, 12, 9, '12.345.678/0001-95'),
    ('O Bicho Vai Pegar 3',2010, 'Cody Cameron', 3, 6, 3, '12.345.678/0001-95'),
    ('Kill Bill - Volume 1',2003,'Quentin Tarantino', 18, 10, 4, '63.770.245/0001-18'),
    ('Em Ritmo de Fuga',2017,'Edgar Wright',16, 10, 10, '91.438.526/0001-42');
    
INSERT INTO Cliente (CPF, Nome, Data_de_nascimento, Cidade) VALUES
    ('123.456.789-10', 'Luis Eduardo', '1995-03-15', 'Tricordiano'),
    ('987.654.321-00', 'Eric', '2001-07-21', 'Itajubá'),
    ('741.852.963-11', 'Igor Grecco', '1988-12-01', 'Marte'),
    ('852.963.741-22', 'Daenerys Targaryen', '1999-05-10', 'Porto Real'),
    ('159.357.456-33', 'Guerzoni', '1992-09-30', 'Cachoeira de Minas');

INSERT INTO Vendedor (CPF, Nome, Salario, Data_de_nascimento, Locadora_CNPJ) VALUES
    ('111.222.333-44', 'João Pedro', 2500.00, '1990-02-15', '12.345.678/0001-95'),
    ('555.666.777-88', 'Ana Clara', 3200.50, '1987-08-20', '47.892.113/0001-06'),
    ('999.888.777-66', 'Felipe Rocha', 2800.75, '1995-11-05', '28.561.904/0001-71'),
    ('444.555.666-77', 'Camila Martins', 3100.00, '1993-04-18', '63.770.245/0001-18'),
    ('222.333.444-55', 'Bruno Silva', 2700.25, '1998-01-12', '91.438.526/0001-42');

INSERT INTO Emprestimo 
(Data, Devolvido, Devolucao, Vendedor_CPF, Cliente_CPF, Locadora_CNPJ, Filme_Id) VALUES
    ('2025-05-01 14:30:00', 1, '2025-05-05 16:00:00',
     '111.222.333-44', '123.456.789-10', '12.345.678/0001-95', 1),
    ('2025-05-02 10:00:00', 0, NULL,
     '555.666.777-88', '987.654.321-00', '47.892.113/0001-06', 2),
    ('2025-05-03 18:20:00', 1, '2025-05-08 13:10:00',
     '999.888.777-66', '741.852.963-11', '28.561.904/0001-71', 3),
    ('2025-05-04 09:15:00', 0, NULL,
     '444.555.666-77', '852.963.741-22', '63.770.245/0001-18', 4),
    ('2025-05-05 20:45:00', 1, '2025-05-10 11:30:00',
     '222.333.444-55', '159.357.456-33', '91.438.526/0001-42', 5);

INSERT INTO Multa (Valor, Data, Locadora_CNPJ, Emprestimo_Id) VALUES
    (15.50, '2025-05-06 12:00:00', '12.345.678/0001-95', 1),
    (8.00,  '2025-05-07 15:30:00', '47.892.113/0001-06', 2),
    (20.00, '2025-05-08 10:45:00', '28.561.904/0001-71', 3),
    (12.75, '2025-05-09 18:00:00', '63.770.245/0001-18', 4),
    (5.25,  '2025-05-10 09:20:00', '91.438.526/0001-42', 5);

INSERT INTO Cliente_da_Locadora (Locadora_CNPJ, Cliente_CPF) VALUES
    ('12.345.678/0001-95', '123.456.789-10'),
    ('47.892.113/0001-06', '987.654.321-00'),
    ('28.561.904/0001-71', '741.852.963-11'),
    ('63.770.245/0001-18', '852.963.741-22'),
    ('91.438.526/0001-42', '159.357.456-33');
