--======================================================
-- SCHEMA E TABELA PARA RESERVAS-SERVICE
--======================================================

-- Cria o schema para o reservas-service, se não existir
CREATE SCHEMA IF NOT EXISTS reservas_schema;

-- Remove a tabela reservas dentro do schema reservas_schema, se ela já existir
-- Usar CASCADE é útil durante o desenvolvimento para remover dependências se você recriar a tabela.
DROP TABLE IF EXISTS reservas_schema.reservas CASCADE;

-- Tabela de Reservas dentro do schema reservas_schema
CREATE TABLE IF NOT EXISTS reservas_schema.reservas (
    id BIGSERIAL PRIMARY KEY,         -- ID auto-incremental da reserva
    data DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    professor_id INT NOT NULL,        -- ID do professor (referência conceitual ao usuarios_schema.professores.id)
    espaco_id BIGINT NOT NULL,         -- ID do espaço físico (referência conceitual ao espacos_schema.espacosfisicos.id)
    observacao TEXT,                  -- Observações adicionais sobre a reserva
    status VARCHAR(50) CHECK (status IN (
        'SOLICITADA',                 -- Reserva feita pelo professor, aguardando alguma confirmação se necessário
        'CONFIRMADA',                 -- Reserva está confirmada e ativa
        'CANCELADA_PROFESSOR',        -- Cancelada pelo professor (se permitido)
        'CANCELADA_ADMIN',            -- Cancelada pelo administrador
        'REALIZADA',                  -- Professor confirmou o uso do espaço
        'NAO_COMPARECEU',             -- Reserva confirmada, mas professor não compareceu/não confirmou uso
        'EXPIRADA'                    -- Reserva passou do tempo e não foi utilizada/confirmada
        )) NOT NULL DEFAULT 'SOLICITADA',
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, -- Quando a reserva foi solicitada
    data_atualizacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP, -- Quando a reserva foi atualizada pela última vez
    CONSTRAINT chk_horario_reserva_reservas CHECK (hora_fim > hora_inicio) -- Garante que a hora final é após a hora inicial
);

-- Índices para otimização de consultas em reservas_schema.reservas
CREATE INDEX IF NOT EXISTS idx_reservas_espaco_data_reservas ON reservas_schema.reservas(espaco_id, data);
CREATE INDEX IF NOT EXISTS idx_reservas_professor_id_reservas ON reservas_schema.reservas(professor_id);
CREATE INDEX IF NOT EXISTS idx_reservas_status_reservas ON reservas_schema.reservas(status);

-- Dados de exemplo para reservas_schema.reservas
-- Certifique-se de que os IDs de professor_id e espaco_id correspondam
-- a IDs existentes nas tabelas usuarios_schema.professores e espacos_schema.espacosfisicos
-- se você estiver populando essas tabelas com os scripts anteriores.
--
-- Assumindo IDs gerados anteriormente:
-- Professores: Ana Silva (1), Bruno Costa (2), Clara Souza (3)
-- Espaços: LAMI1 (1), LAMI2 (2), AUD1 (4), SALA_B411 (6)
INSERT INTO reservas_schema.reservas (data, hora_inicio, hora_fim, professor_id, espaco_id, observacao, status) VALUES
    (CURRENT_DATE + INTERVAL '1 day', '08:00:00', '10:00:00', 1, 1, 'Aula de Programação Web com Ana Silva no LAMI1', 'CONFIRMADA'),
    (CURRENT_DATE + INTERVAL '1 day', '10:00:00', '12:00:00', 2, 6, 'Aula de Cálculo I com Bruno Costa na SALA_B411', 'CONFIRMADA'),
    (CURRENT_DATE + INTERVAL '2 days', '14:00:00', '17:00:00', 1, 4, 'Palestra sobre IA com Ana Silva no AUD1', 'SOLICITADA'),
    (CURRENT_DATE + INTERVAL '3 days', '09:00:00', '11:30:00', 3, 2, 'Aula de Química Orgânica com Clara Souza no LAMI2', 'CONFIRMADA'),
    (CURRENT_DATE + INTERVAL '4 days', '16:00:00', '18:00:00', 2, 1, 'Orientação de TCC - Bruno Costa no LAMI1', 'SOLICITADA');

-- Verificar dados inseridos (opcional)
SELECT * FROM reservas_schema.reservas;