🚀 Projeto Agenda 2025
  
  Sistema para cadastrar clientes e contatos de maneira fácil e intuitiva.

📋 Pré-requisitos
- Java JDK
- IDE (Recomendavel: IntelliJ ou Eclipse)
- pgAdmin (Banco de dados PostgreSql)

🔧 Guia de instalação:
- Ao acessar o link: https://github.com/Marcoswms/sistema_agenda
- Clique em 'Code' / Download Zip e selecione o diretório para receser seu arquivo 'sistema_agenda-main.zip'
- Com o seu descompactador de arquivos, escolha um diretório para receber a o projeto descompactado
- Inicializar sua IDE de preferência e abrir o caminho relativo ao diretório onde consta o projeto extraído.

⚙️ Configurar acesso ao Banco de Dados:
- Acessar o pgAdmin e realizar as configurações iniciais de login e senha
- Após a configuração, selecionar: Servers / PostgresSQL17 (utilizado) / Database
- Botão direito em cima de 'Database' e selecione: create / database
- No campo 'Database', digite o nome de sua tabela ('sistema-agenda' é o recomendável) e clicar em 'Save'

🛠️ Retornando a sua IDE:
- Em seu projeto, acessar o arquivo 'ConfiguracaoBancoDeDados.java' localizado no diretório: src/main/java/com/projeto/sistema/
- Dentro da Classe 'ConfiguracaoBancoDeDados', acessar o método 'dataSource' alterar os seguintes campos:
- No campo onde está o # inserir nome da tabela caso não seja o nome recomendado: dataSource.setUrl("jdbc:postgresql://localhost:5432/#");
- No campo onde está o # inserir nome de usuário relacionado ao banco de dados criado:  dataSource.setUsername("#");
- No campo onde está o # inserir senha relacionada ao banco de dados criado: dataSource.setPassword("#");
- Importante: Não se esqueça de salvar suas alterações (Eclipse: Ctrl +S / IntelliJ: Salva automaticamente).

🚀 Inicializando o projeto:
- Em seu projeto, acessar o arquivo 'SistemaApplication.java' localizado no diretório: src/main/java/com/projeto/sistema/
- Selecionar com o botão direito em cima do arquivo e escolher a opção 'Run SistemaApplication..main()'
- Aguarde o projeto conectar-se ao servidor Spring até aparecer a seguinte mensagem: (process running for...)
- Em seu navegador, digitar em uma nova janela o seguinte endereço: http://localhost:8080/administrativo
- Após a conexão, será inicializado nosso Projeto Agenda 2025.
