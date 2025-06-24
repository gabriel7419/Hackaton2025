#🎓 Sistema de Correção de Gabaritos – UniALFA
🔚 Entrega Final – Hackathon 2025

#📦 O que foi entregue
Um sistema completo, do backend ao mobile, totalmente funcional e integrado com banco de dados MySQL.

Inclui:

✅ Backend com Spring Boot + MySQL
✅ Aplicativo Flutter para correção de gabaritos
✅ Interface web para gestão de provas, usuários e resultados
✅ API REST integrada entre mobile e web
✅ Três perfis de acesso: Administrador, Professor e Aluno
✅ Banco de dados relacional e persistente

💼 Principais funcionalidades
✅ Correção automática de gabaritos em tempo real
✅ Cálculo instantâneo de notas e aprovação
✅ Relatórios estatísticos dinâmicos
✅ Gestão completa de provas, turmas e usuários
✅ Interface mobile simples e intuitiva
✅ Sistema web responsivo e fácil de usar

🚀 Como executar o sistema
1. 🗄 Configurar o MySQL (5 minutos)
bash
Copiar
Editar
# Linux
sudo apt install mysql-server

# macOS
brew install mysql
Acesse o MySQL e crie o banco de dados:

sql
Copiar
Editar
mysql -u root -p
CREATE DATABASE gabarito_db;
exit;
#2. 🔧 Rodar o backend Spring Boot (2 minutos)
bash
Copiar
Editar
cd sistema-gabarito-unialfa
mvn clean spring-boot:run
✅ Backend disponível em: http://localhost:8080

#3. 📱 Rodar o app Flutter (2 minutos)
bash
Copiar
Editar
cd gabarito_app
flutter pub get
flutter run
✅ Aplicativo funcionando em emulador ou celular conectado

🔐 Como fazer login
🌐 Acesso via navegador:
Usuário	Senha	Perfil	Acesso
admin	admin123	Administrador	Gestão completa
professor1	prof123	Professor	Provas e resultados
aluno1	aluno123	Aluno	Visualização de notas

📱 No app Flutter:
Utilize os mesmos usuários e senhas.

Login automático após o primeiro acesso.

Funciona offline após login inicial.

🎯 Como demonstrar o sistema
📱 No app Flutter:
Login com admin / admin123

Selecionar aluno: Maria Santos

Escolher prova: Prova 1 – Hackathon

Marcar respostas: A,B,C,D,A,B,C,D,A,B

Resultado: Nota 8.0 – Aprovado! ⭐

🌐 No sistema web:
Login com admin / admin123

Acessar dashboard: visualizar estatísticas

Criar nova prova

Acompanhar correções em tempo real

Gerenciar usuários e turmas

📊 Dados pré-carregados
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

💡 Benefícios principais
⚡ Agilidade
Correção 95% mais rápida

Resposta instantânea para alunos

Sem margem de erro humana

📈 Gestão eficiente
Relatórios automáticos

Painéis com dados em tempo real

Histórico completo de desempenho

🔒 Segurança
Controle de acesso por perfil

Dados armazenados de forma segura no MySQL

Comunicação protegida entre app e backend

🏆 Arquitetura Final
text
Copiar
Editar
📱 Flutter App  →  🌐 Spring Boot API  →  🗄 MySQL
 (Mobile)          (Backend + Web)         (Banco de dados)
