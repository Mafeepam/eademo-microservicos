-- Conectado ao banco de dados db_usuarios
CREATE SCHEMA IF NOT EXISTS usuarios_schema;

CREATE TABLE IF NOT EXISTS usuarios_schema.usuario (
    id SERIAL PRIMARY KEY, -- Adicionando um ID primário, comum em entidades JPA
    email VARCHAR(100) UNIQUE,
    senha VARCHAR(100),
    funcao VARCHAR(50) CHECK (funcao IN ('admin', 'professor'))
);

CREATE TABLE IF NOT EXISTS usuarios_schema.professores (
    id SERIAL PRIMARY KEY, -- Adicionando um ID primário
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    curso VARCHAR(100)
    -- Você pode considerar adicionar uma FK para usuario(id) se houver um relacionamento direto
    -- ou se a lógica de negócio do monolito já assume isso via email.
);

-- Dados iniciais para o db_usuarios
INSERT INTO usuarios_schema.usuario (email, senha, funcao) VALUES
    ('fernando.borges@pro.ucsal.br', '1234', 'professor'),
    ('admin@ucsal.br', 'admin123', 'admin');

INSERT INTO usuarios_schema.professores (nome, email, curso) VALUES
    ('Ana Silva', 'ana@pro.ucsal.br', 'Análise e Desenvolvimento de Sistemas'),
    ('Bruno Costa', 'bruno@pro.ucsal.br', 'Engenharia Civil'),
    ('Clara Souza', 'clara@pro.ucsal.br', 'Engenharia Química'),
    ('Daniel Santos', 'daniel@pro.ucsal.br', 'Engenharia Mecânica'),
    ('Fernando Borges1', 'fernando.borgess@pro.ucsal.br', 'Engenharia de Software');