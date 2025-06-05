    -- Cria o schema para o espacos-service se ele não existir
    CREATE SCHEMA IF NOT EXISTS espacos_schema;

    -- Remove a tabela espacosfisicos dentro do schema espacos_schema, se ela já existir
    DROP TABLE IF EXISTS espacos_schema.espacosfisicos;

    -- Tabela de Espaços Físicos dentro do schema espacos_schema
    CREATE TABLE IF NOT EXISTS espacos_schema.espacosfisicos (
        id BIGSERIAL PRIMARY KEY, -- ID auto-incremental
        sigla VARCHAR(20) UNIQUE NOT NULL,
        nome VARCHAR(100) NOT NULL,
        descricao TEXT,
        tipo VARCHAR(100) CHECK (tipo IN ('SALA', 'AUDITORIO', 'LABORATORIO')) NOT NULL,
        capacidade INT NOT NULL CHECK (capacidade > 0),
        status VARCHAR(50) CHECK (status IN ('ATIVO', 'EM_MANUTENCAO', 'INATIVO')) NOT NULL DEFAULT 'ATIVO'
    );

    -- Inserindo dados de exemplo para espacos_schema.espacosfisicos
    INSERT INTO espacos_schema.espacosfisicos (sigla, nome, descricao, tipo, capacidade, status) VALUES
        ('LAMI1', 'Laboratório de Informática 1', 'Laboratório com 30 computadores Dell, projetor e lousa branca.', 'LABORATORIO', 30, 'ATIVO'),
        ('LAMI2', 'Laboratório de Informática 2', 'Laboratório com 25 computadores Positivo, projetor.', 'LABORATORIO', 25, 'ATIVO'),
        ('LAMI3', 'Laboratório de Informática 3', 'Laboratório com 40 computadores Lenovo, ideal para aulas com maior número de alunos.', 'LABORATORIO', 40, 'EM_MANUTENCAO'),
        ('AUD1', 'Auditório Principal', 'Auditório com capacidade para 200 pessoas, sistema de som e climatização.', 'AUDITORIO', 200, 'ATIVO'),
        ('AUD2', 'Mini Auditório Bloco C', 'Auditório menor, capacidade para 80 pessoas.', 'AUDITORIO', 80, 'INATIVO'),
        ('SALA_B411', 'Sala B411', 'Sala de aula padrão no Bloco B, 4º andar, com lousa e projetor.', 'SALA', 50, 'ATIVO'),
        ('SALA_C102', 'Sala C102', 'Sala de aula ampla no Bloco C, 1º andar.', 'SALA', 60, 'ATIVO');

    -- Verificar dados inseridos (opcional)
    SELECT * FROM espacos_schema.espacosfisicos;