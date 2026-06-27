ENTIDADES INICIAIS

CREATE TABLE aluno (
id SERIAL PRIMARY KEY,
nome VARCHAR(100),
email VARCHAR(150),
telefone VARCHAR(20)
);

CREATE TABLE curso (
id SERIAL PRIMARY KEY,
nome VARCHAR(100),
descricao VARCHAR(500),
carga_horaria INTEGER,
vagas_totais INTEGER,
vagas_disponiveis INTEGER
);

CREATE TABLE matricula (
id SERIAL PRIMARY KEY,
data_matricula DATE,
valor DECIMAL(10,2),
aluno_id INTEGER REFERENCES aluno(id),
curso_id INTEGER REFERENCES curso(id)
);

REGRAS DE NEGÓCIO

* Não é permitido matricular um aluno não cadastrado.
* Não é permitido matricular em um curso não cadastrado.
* O valor pago na matrícula não pode ser negativo.
* Um aluno não pode ser matriculado duas vezes no mesmo curso.
* Não é permitido matricular alunos em cursos que atingiram o limite máximo de vagas.
* Deve ser possível consultar todos os alunos matriculados em um curso.
* Deve ser possível consultar todos os cursos em que um aluno está matriculado.
