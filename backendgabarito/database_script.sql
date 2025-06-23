-- Script SQL para criação do banco de dados
CREATE DATABASE IF NOT EXISTS gabarito_app;
USE gabarito_app;

-- Tabela de usuários
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    perfil ENUM('ADMINISTRADOR', 'PROFESSOR', 'ALUNO') NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de turmas
CREATE TABLE turmas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de disciplinas
CREATE TABLE disciplinas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de relacionamento aluno-turma
CREATE TABLE aluno_turma (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aluno_id BIGINT NOT NULL,
    turma_id BIGINT NOT NULL,
    data_matricula TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (aluno_id) REFERENCES usuarios(id),
    FOREIGN KEY (turma_id) REFERENCES turmas(id),
    UNIQUE KEY unique_aluno_turma (aluno_id, turma_id)
);

-- Tabela de provas
CREATE TABLE provas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    disciplina_id BIGINT NOT NULL,
    turma_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    data_prova DATE NOT NULL,
    total_questoes INTEGER NOT NULL,
    gabarito_oficial TEXT NOT NULL, -- JSON com as respostas corretas
    status ENUM('ATIVA', 'FINALIZADA') DEFAULT 'ATIVA',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (disciplina_id) REFERENCES disciplinas(id),
    FOREIGN KEY (turma_id) REFERENCES turmas(id),
    FOREIGN KEY (professor_id) REFERENCES usuarios(id)
);

-- Tabela de respostas dos alunos
CREATE TABLE respostas_aluno (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prova_id BIGINT NOT NULL,
    aluno_id BIGINT NOT NULL,
    respostas TEXT NOT NULL, -- JSON com as respostas do aluno
    nota DECIMAL(5,2),
    total_acertos INTEGER,
    data_submissao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (prova_id) REFERENCES provas(id),
    FOREIGN KEY (aluno_id) REFERENCES usuarios(id),
    UNIQUE KEY unique_prova_aluno (prova_id, aluno_id)
);

-- Inserção de dados de exemplo
INSERT INTO usuarios (nome, email, senha, perfil) VALUES
('Administrador', 'admin@unialfa.edu.br', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 'ADMINISTRADOR'),
('Prof. João Silva', 'joao.silva@unialfa.edu.br', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 'PROFESSOR'),
('Maria Santos', 'maria.santos@estudante.unialfa.edu.br', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 'ALUNO'),
('Pedro Costa', 'pedro.costa@estudante.unialfa.edu.br', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 'ALUNO'),
('Ana Oliveira', 'ana.oliveira@estudante.unialfa.edu.br', '$2a$10$N9qo8uLOickgx2ZMRZoMye', 'ALUNO');

INSERT INTO disciplinas (nome, codigo) VALUES
('Frameworks de Desenvolvimento Web Java', 'FDWJ001'),
('Programação para Dispositivos Móveis', 'PDM001'),
('Banco de Dados', 'BD001'),
('Estrutura de Dados', 'ED001');

INSERT INTO turmas (nome, descricao) VALUES
('5º Período - Sistemas para Internet', 'Turma do 5º período do curso de Sistemas para Internet'),
('4º Período - Sistemas para Internet', 'Turma do 4º período do curso de Sistemas para Internet');

INSERT INTO aluno_turma (aluno_id, turma_id) VALUES
(3, 1), -- Maria na turma 1
(4, 1), -- Pedro na turma 1  
(5, 1); -- Ana na turma 1

INSERT INTO provas (titulo, disciplina_id, turma_id, professor_id, data_prova, total_questoes, gabarito_oficial) VALUES
('Prova 1 - Spring Boot', 1, 1, 2, '2025-06-25', 10, '["A","B","C","D","A","B","C","D","A","B"]'),
('Prova 1 - Flutter Básico', 2, 1, 2, '2025-06-26', 5, '["A","C","B","D","A"]');

-- Senha padrão para todos os usuários: 123456
