<h1 align="center">🎓 Sistema de Correção de Gabaritos – UniALFA</h1> <h3 align="center">🔚 Entrega Final – Hackathon 2025</h3>
📦 O que foi entregue
Sistema completo e funcional, com integração total entre backend, web e app mobile, utilizando banco de dados relacional MySQL.

Inclui:

✅ Backend com Spring Boot + MySQL

✅ Aplicativo Flutter para correção de gabaritos

✅ Interface Web para gestão de provas e usuários

✅ API REST conectando mobile e web

✅ Três tipos de usuário: Administrador, Professor e Aluno

✅ Banco de dados persistente e relacional

💼 Principais Funcionalidades
✅ Correção automática de gabaritos

✅ Cálculo instantâneo das notas

✅ Relatórios estatísticos em tempo real

✅ Gestão completa de usuários, turmas e provas

✅ App intuitivo para o aluno

✅ Web responsiva e de fácil navegação

🚀 Como Executar o Sistema
1. 🗄 Configurar MySQL (5 min)
bash
Copiar
Editar
# Linux
sudo apt install mysql-server

# macOS
brew install mysql
Criar banco de dados:

sql
Copiar
Editar
mysql -u root -p
CREATE DATABASE gabarito_db;
exit;
2. 🔧 Executar Backend (Spring Boot)
bash
Copiar
Editar
cd sistema-gabarito-unialfa
mvn clean spring-boot:run
✅ Acesse: http://localhost:8080

3. 📱 Executar App Flutter
bash
Copiar
Editar
cd gabarito_app
flutter pub get
flutter run
✅ App rodando no emulador ou dispositivo físico

🔐 Como Fazer Login
🌐 Web:
Usuário	Senha	Perfil	Acesso
admin	admin123	Administrador	Gestão completa
professor1	prof123	Professor	Provas e resultados
aluno1	aluno123	Aluno	Visualização de notas

📱 App Flutter:
Usa os mesmos dados acima

Login automático após o primeiro acesso

Funciona offline após login inicial

🎯 Como Demonstrar o Sistema
📱 App Flutter:
Login: admin / admin123

Selecionar aluno: Maria Santos

Escolher prova: Prova 1 – Hackathon

Marcar respostas: A,B,C,D,A,B,C,D,A,B

Resultado: Nota 8.0 – Aprovado! ⭐

🌐 Sistema Web:
Login: admin / admin123

Acessar o Dashboard

Criar ou visualizar provas

Ver correções em tempo real

Gerenciar turmas e usuários

📊 Dados Pré-Carregados
👥 Usuários

1 Administrador

1 Professor

3 Alunos

📚 Disciplinas

Sistemas para Internet

Frameworks Java

Programação Mobile

🏫 Turmas

5º Período – Sistemas para Internet

5º Período – Frameworks Java

📝 Provas

Prova 1 – Hackathon (10 questões)
Gabarito: A,B,C,D,A,B,C,D,A,B

💡 Benefícios Principais
⚡ Agilidade
Correção até 95% mais rápida

Resposta imediata ao aluno

Zero erros humanos

📈 Gestão
Relatórios automáticos de aprovação

Estatísticas dinâmicas

Histórico completo dos resultados

🔒 Segurança
Controle de acesso por perfil

Banco de dados persistente no MySQL

API segura entre mobile e backend

🏆 Arquitetura Final
text
Copiar
Editar
📱 Flutter App  →  🌐 Spring Boot API  →  🗄 MySQL
 (Mobile)          (Backend + Web)         (Banco de dados)

👥 Equipe: 
Gabriel Paiva de Almeida - 13631
Leonardo Chaves - 10471
Matheus Urbano Mackert - 13629
Bianca Gabriela da Silva - 14149
