Sistema de Correção de Gabarito - UniALFA
Sistema completo para correção automática de gabaritos desenvolvido para o hackathon da Faculdade ALFA Umuarama.
📋 Sobre o Projeto
Este projeto foi desenvolvido para automatizar o processo de correção de provas objetivas, oferecendo uma solução completa que inclui:

Backend: API REST em Spring Boot com autenticação e autorização
Frontend Web: Interface administrativa em Thymeleaf + Bootstrap
Mobile: Aplicativo Flutter para envio de respostas
Banco de Dados: MySQL com estrutura completa

🚀 Tecnologias Utilizadas
Backend (Spring Boot)

Java 21
Spring Boot 3.2.0
Spring Web
Spring Data JPA
Spring Security
MySQL Connector
Thymeleaf
Bootstrap 5

Mobile (Flutter)

Flutter 3.0+
Dart
HTTP (para consumo da API)
Shared Preferences (armazenamento local)
Camera (para futuras implementações de OCR)

Banco de Dados

MySQL 8.0+

📦 Instalação e Configuração
Pré-requisitos

Java 21 ou superior
MySQL 8.0 ou superior
Flutter 3.0 ou superior
XAMPP (ou outro servidor MySQL)

1. Configuração do Banco de Dados

Inicie o MySQL no XAMPP
Crie o banco de dados executando o script SQL fornecido:

sql-- Execute o script SQL completo fornecido no arquivo database_script.sql
2. Configuração do Backend

Clone/baixe o projeto Spring Boot
Configure o application.properties:

propertiesspring.datasource.url=jdbc:mysql://localhost:3306/gabarito_app
spring.datasource.username=root
spring.datasource.password=

Execute o projeto:

bashmvn spring-boot:run
O backend estará rodando em: http://localhost:8080
3. Configuração do Mobile

No arquivo lib/services/api_service.dart e lib/services/auth_service.dart,
ajuste a URL base se necessário:

dartstatic const String baseUrl = 'http://localhost:8080/api';
// Para emulador Android use: http://10.0.2.2:8080/api
// Para dispositivo físico use o IP da máquina

Execute o aplicativo Flutter:

bashflutter pub get
flutter run
👥 Usuários de Teste
O sistema vem com usuários pré-cadastrados para teste:
Administrador

Email: admin@unialfa.edu.br
Senha: 123456

Professor

Email: joao.silva@unialfa.edu.br
Senha: 123456

Alunos

Email: maria.santos@estudante.unialfa.edu.br
Senha: 123456
Email: pedro.costa@estudante.unialfa.edu.br
Senha: 123456

🔧 Funcionalidades Implementadas
Sistema Web (Backend + Frontend)
Administrador

✅ Dashboard com estatísticas gerais
✅ Gerenciamento de usuários (CRUD)
✅ Gerenciamento de turmas (CRUD)
✅ Gerenciamento de disciplinas (CRUD)
✅ Sistema de autenticação com perfis

Professor

✅ Dashboard com suas provas
✅ Criação de provas objetivas
✅ Definição de gabarito oficial
✅ Visualização de resultados por prova
✅ Estatísticas (média da turma, total de alunos)
✅ Correção automática das provas

Aluno

✅ Dashboard com suas notas
✅ Visualização detalhada de resultados
✅ Histórico de provas realizadas

Aplicativo Mobile (Flutter)

✅ Tela de login com autenticação
✅ Armazenamento local de credenciais
✅ Seleção de aluno para envio de respostas
✅ Seleção de prova disponível
✅ Interface para entrada manual de respostas
✅ Envio automático para a API
✅ Feedback imediato com nota calculada
🚧 Funcionalidade de câmera (estrutura criada, implementação pendente)

API REST

✅ Endpoint de autenticação (/api/login)
✅ Listagem de alunos (/api/alunos)
✅ Listagem de provas (/api/provas)
✅ Envio de respostas (/api/respostas)
✅ Correção automática com cálculo de nota
✅ Validação e tratamento de erros

📊 Estrutura do Banco de Dados
Principais Tabelas

usuarios: Armazena dados de usuários (admin, professores, alunos)
turmas: Informações das turmas
disciplinas: Cadastro de disciplinas
provas: Provas criadas pelos professores
respostas_aluno: Respostas enviadas pelos alunos
aluno_turma: Relacionamento entre alunos e turmas

🎯 Fluxo de Uso
Para Professores:

Login no sistema web
Criar nova prova definindo título, disciplina, turma e gabarito
Aguardar respostas dos alunos
Visualizar resultados e estatísticas

Para Envio de Respostas (Mobile):

Login no aplicativo
Selecionar o aluno
Escolher a prova
Inserir respostas manualmente
Enviar para correção automática
Receber feedback imediato

🔐 Segurança

Autenticação baseada em Spring Security
Autorização por perfis (ADMINISTRADOR, PROFESSOR, ALUNO)
Senhas criptografadas com BCrypt
Validação de dados nas APIs
Controle de acesso nas rotas

📱 Interface Mobile

Design moderno com Material Design
Tema personalizado nas cores da UniALFA
Interface intuitiva e responsiva
Feedback visual para todas as ações
Tratamento de erros e estados de loading

🌐 Interface Web

Layout responsivo com Bootstrap 5
Dashboard específico para cada perfil
Tabelas interativas com dados em tempo real
Formulários validados
Navegação intuitiva

🏆 Equipe de Desenvolvimento
Projeto desenvolvido para o Hackathon da Faculdade ALFA Umuarama - Curso de Sistemas para Internet, 5º Período.

Nota: Este é um MVP (Produto Mínimo Viável) desenvolvido para demonstrar as funcionalidades principais do sistema. Todas as funcionalidades essenciais estão implementadas e funcionais.
