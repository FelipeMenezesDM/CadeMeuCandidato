### CadeMeuCandidato
Aplicação WEB desenvolvida em Java para consumo de serviços REST/SOAP usando base de dados pública da Câmara de Deputados.

### Conteúdo
1. [Requisitos](#requisitos)
1. [Instalação](#instalação)
1. [Configuração](#configuração)
1. [Utilização](#utilização)
1. [Projeto](#projeto)

### Requisitos
- Tomcat 8
- Microsoft SQL Server

### Instalação
1. Obtenha **.war** do projeto diretamente [neste link](https://trello-attachments.s3.amazonaws.com/5a8218ca3882b63c3d466b83/5a8218ca3882b63c3d466b88/4e2b2b8aaf5730af34c45065157f074e/cademeucandidato.war).
1. Mova o arquivo para o diretório **/webapps** do Tomcat no servidor.
1. Reinicie o Tomcat para fazer o deploy.
1. Acesse o link da aplicação em um navegador e execute as etapas de configuração descritas no próximo tópico.

### Configuração
#### Conexão com a base
1. Você deve informar o nome do Servidor onde o Microsoft SQL Server está instalado, se não for informado o sistema tomará como padrão o *localhost*.
1. Informe também a porta de conexão, que por padrão é 1433.
1. Informe o nome da Base de Dados onde as tabelas utilizadas pela aplicaçação serão criadas.
1. Informe as credenciais para conexão com a base de dados escolhida e prossiga para a próxima etapa.

![Configuração](https://trello-attachments.s3.amazonaws.com/5a8218ca3882b63c3d466b83/5a8218ca3882b63c3d466b88/b0286a3875ed140707986c0b63ff2412/instalacao.png "Configuração")

#### Criação do primeiro usuário
Você deve criar o primeiro usuário do sistema informando um nome, um endereço de e-mail, um nome de usuário e uma senha, conforme o exemplo abaixo. Após isso, o sistema estará disponível para uso e para receber novos cadastros de usuários.

![Criação do primeiro usuário](https://trello-attachments.s3.amazonaws.com/5a8218ca3882b63c3d466b83/5a8218ca3882b63c3d466b88/97842be53333f12e390655a23241a7ea/usuario.png "Criação do primeiro usuário")

### Utilização
No dashboard você tem a visão de todos os Parlamentares cadastrados na base de dados pública da Câmara ordenados alfabeticamente pelo nome. Você pode consultar as informações dos candidatos e filtrá-los por nome, sigla do Partido e Unidade Federativa

![Dashboard](https://trello-attachments.s3.amazonaws.com/5a8218ca3882b63c3d466b83/5a8218ca3882b63c3d466b88/e2d85333739afd01e7a1b019a7797998/dashboard.png "Dashboard")

![Consulta](https://trello-attachments.s3.amazonaws.com/5a8218ca3882b63c3d466b83/5a8218ca3882b63c3d466b88/6142c49ed4263f709f9fee00453f39e3/consulta.png "Consulta")

![Login](https://trello-attachments.s3.amazonaws.com/5a8218ca3882b63c3d466b83/5a8218ca3882b63c3d466b88/5ce151d4c12c683d0a307e9852c22721/login.png "Login")

![Cadastro de Usuário](https://trello-attachments.s3.amazonaws.com/5a8218ca3882b63c3d466b83/5a8218ca3882b63c3d466b88/699d1ca778e4e2d07790258d0dfa42df/cadastro.png "Cadastro de Usuário")

### Projeto
O projeto está disponível neste repositório. Faça clone executando:

    $ git clone https://github.com/FelipeMenezesDM/CadeMeuCandidato.git

Veja a aplicação em execução em: https://app.felipemenezes.com.br/cademeucandidato
