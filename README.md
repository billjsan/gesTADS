<h2 align="center"> GesTADS</h2>

<h5 align=center> Repositório para o projeto de gestão de almoxaridado GesTADS que será desenvolvido como trabalho final da disciplina de Padrões de Projeto do curso de Análise e Desenvolvimento de Sistemas do IFPE </h5>



* <h3>Arquitetura básica do projeto</h3>

<img src="doc/img/architecture.jpeg" alt="basic architecture" height="525">


* <h3>Diagrama de casos de uso</h3>
<img src="doc/img/usecaseDiagram.jpeg" alt="basic architecture" height="725">

<h3 align= 'center'>Banco de dados</h3>
<h5 align=center>Esse projeto utiliza um banco de dados MySQL, dessa forma é necessário criar o banco de dados e as tabelas antes de utilizar o sistema.</h5>

<h5>Passo 1: verificar se o MySQL está instalado na máquina que vai rodar o sistema gesTADS. Caso esteja instalado entre no terminal do MySQL com as credenciais root para usuário e não informar nada para a senha. Caso não tenha o MySQL instalado na máquina fazer a instalação do mesmo e refazer esse passo.</h5>
<h5>Passo 2: criar o banco de dados através do comando SQL: create database gesTADS;</h5>
<h5>Passo 3: Entrar no banco de dados criado anteriormente através do comando SQL: use gesTADS;</h5>
<h5>Passo 4: Criar as tabelas do banco de dados. São elas: employee, cargo, produto e transacao. Para criar as tabelas utilize os comandos SQL a seguir: create table employee (id int primary key, nome varchar (255), login varchar (255), senha varchar (255), cargo varchar (255), cpf varchar (100) not null unique); create table cargo (id int auto_increment primary key, nome varchar (255));
create table produto (id int primary key, nome varchar (255), serialn varchar (255), fabricante varchar (255), descricao varchar (255), fabricacao varchar (255), validade varchar (255));
</h5>
<h5>Passo 5: verificar se as tabelas foram criadas através do comando SQL: show tables;</h5>
<h5>Pronto, seu banco de dados está configurado e pronto para uso!</h5>


