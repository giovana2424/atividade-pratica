ENTIDADES INICIAIS

CREATE TABLE cliente (
id SERIAL PRIMARY KEY,
nome VARCHAR(100),
telefone VARCHAR(20)
);

CREATE TABLE veiculo (
id SERIAL PRIMARY KEY,
placa VARCHAR(10),
modelo VARCHAR(100),
ano INTEGER,
cliente_id INTEGER REFERENCES cliente(id)
);

CREATE TABLE ordem_servico (
id SERIAL PRIMARY KEY,
descricao_problema VARCHAR(255),
valor_servico DECIMAL(10,2),
status VARCHAR(20),
veiculo_id INTEGER REFERENCES veiculo(id)
);

REGRAS DE NEGÓCIO

* Não é permitido abrir uma Ordem de Serviço para um veículo não cadastrado.
* O valor do serviço não pode ser negativo.
* O status da Ordem de Serviço deve indicar se ela está ABERTA ou CONCLUIDA.
* Deve ser possível consultar todo o histórico de manutenções de um veículo.
* Deve ser possível consultar todos os veículos de um cliente.
