### CadeMeuCandidato
Aplicação WEB desenvolvida em Java para consumo de serviços REST/SOAP usando base de dados pública da Câmara de Deputados.

### Conteúdo
1. [Requisitos]()
1. [Instalação]()
1. [Configuração]()
1. [Utilização]()
1. [Projeto]()

### Requisitos
- Tomcat 8
- Microsoft SQL Server

### Instalação
1. Obtenha **.war** do projeto diretamente neste link.
1. Mova o arquivo para o diretório **/webapps** do Tomcat no servidor.
1. Reinicie o Tomcat para fazer o deploy.
1. Acesse o link da aplicação em um navegador e execute as etapas de configuração descritas no próximo tópico.

### Configuração
#### Conexão com a base
1. Você deve informar o nome do Servidor onde o Microsoft SQL Server está instalado, se não for informado o sistema tomará como padrão o *localhost*.
1. Informe também a porta de conexão, que por padrão é 1433.
1. Informe o nome da Base de Dados onde as tabelas utilizadas pela aplicaçação serão criadas.
1. Informe as credenciais para conexão com a base de dados escolhida e prossiga para a próxima etapa.

#### Criação do primeiro usuário
Você deve criar o primeiro usuário do sistema informando um nome, um endereço de e-mail, um nome de usuário e uma senha, conforme o exemplo abaixo. Após isso, o sistema estará disponível para uso e para receber novos cadastros de usuários.


### Utilização
No dashboard você tem a visão de todos os Parlamentares cadastrados na base de dados pública da Câmara ordenados alfabeticamente pelo nome. Você pode consultar as informações dos candidatos e filtrá-los por nome, sigla do Partido e Unidade Federativa.

### Projeto
O projeto está disponível neste repositório. Faça clone executando:

    $ git clone https://github.com/FelipeMenezesDM/CadeMeuCandidato.git

Veja a aplicação em execução em: https://app.felipemenezes.com.br/cademeucandidato
