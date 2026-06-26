ENTIDADES INICIAIS

CREATE TABLE endereco (
id SERIAL PRIMARY KEY,
logradouro VARCHAR(100),
numero VARCHAR(20),
bairro VARCHAR(100),
cidade VARCHAR(100),
uf CHAR(2),
cep VARCHAR(9)
);

CREATE TABLE tutor (
id SERIAL PRIMARY KEY,
nome VARCHAR(100),
endereco_id INTEGER REFERENCES endereco(id),
telefone VARCHAR(20)
);

CREATE TABLE animal (
id SERIAL PRIMARY KEY,
nome VARCHAR(100),
especie VARCHAR(50),
raca VARCHAR(100),
tutor_id INTEGER REFERENCES tutor(id)
);

CREATE TABLE consulta (
id SERIAL PRIMARY KEY,
data_atendimento DATE,
motivo VARCHAR(255),
valor numeric(10,2),
animal_id INTEGER REFERENCES animal(id)
);

REGRAS DE NEGÓCIO

* Um tutor pode possuir vários animais.
* Um animal pertence a um único tutor.
* Uma consulta deve estar vinculada a um animal cadastrado.
* O valor da consulta deve ser ≥ 0.
* Deve ser possível listar todas as consultas de um animal.
* Deve ser possível listar todos os animais de um tutor.
