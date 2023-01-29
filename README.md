# Escopo e objetivo

_API Autorizador VR_.

Ou seja, é uma API rest que disponibiliza alguns recursos para cadastro de cartões e autorização de transações.

**Adicional**
- Execução de stress-test utilizando K6 

## Iniciando...

- `git clone https://github.com/junioroliveira1662/ms-mini-autorizador.git`
- `cd ms-mini-autorizador`

Agora você poderá executar os vários comandos abaixo.

## Pré-requisitos

- Instalar o K6 para executar o stress-test da aplicação. `https://k6.io/docs/get-started/installation/`

- `mvn --version`<br>
  você deverá ver a indicação da versão do Maven instalada e
  a versão do JDK, dentre outras. Observe que o JDK é obrigatório, assim como
  a definição das variáveis de ambiente **JAVA_HOME** e **M2_HOME**.

| Programas | Versão |
|-----------|:------:|
| Java      |   11   |
| Maven     | 3.8.6  | 

## Limpar, compilar, executar testes de unidade

- `mvn clean`<br>
  remove diretório _target_

- `mvn compile`<br>
  compila o projeto, deposita resultados no diretório _target_

- `mvn test`<br>
  executa todos os testes do projeto. Para executar apenas parte dos testes, por exemplo,
  aqueles contidos em uma dada classe execute `mvn -Dtest=NomeDaClasseTest test`. Observe
  que o sufixo do nome da classe de teste é `Test` (padrão recomendado). Para executar um
  único teste `mvn -Dtest=NomeDaClasseTest#nomeDoMetodo test`.

## Empacotando o projeto

- `mvn package`<br>
  gera arquivo _exemplo.jar_ no diretório _target_. Observe que
  o arquivo gerado não é executável. Um arquivo jar é um arquivo no formato
  zip. Você pode verificar o conteúde deste arquivo ao executar o comando `jar vft exemplo.jar`.

- `mvn package -P executavel-dir`<br>
  gera _exemplo-dir.jar_, executável, mas dependente do diretório _jars_,
  também criado no diretório _target_. Para executar basta o comando
  `java -jar target/exemplo-dir.jar`. Observe que se o diretório _jars_ for
  removido, então este comando falha. Por último, o diretório _jars_ deve
  ser depositado no mesmo diretório do arquivo _exemplo.jar_.

- `mvn package -P executavel-unico`<br>
  gera jar executável correspondente ao aplicativo a ser executado via linha de comandos,
  em um único arquivo, _target/exemplo-unico.jar_,
  suficiente para ser transferido e executado. Para executá-lo basta o
  comando `java -jar target/exemplo-unico.jar`.

- `mvn package -P api`<br>
  gera jar executável juntamente com todas as dependências reunidas em um único arquivo,
  _target/api.jar_. Este arquivo jar pode ser transferido para outro diretório
  ou máquina e ser executado pelo comando `java -jar target/api.jar`. A execução e exemplos
  de chamadas são fornecidos na seção seguinte.

## Executando a aplicação e a RESTFul API

- `mvn exec:java -Dexec.mainClass="nome.completo.Classe" -Dexec.args="arg1 arg2"`<br>
  executa o método _main_ da classe indicada na configuração do _plugin_ pertinente
  no arquivo pom.xml. Depende de `mvn compile`.

- `java -jar target/exemplo-unico.jar`<br>
  executa o aplicativo por meio do arquivo jar criado pelo comando `mvn package -P executavel-unico`, conforme comentado anteriormente. Observe que o comando
  anterior e o corrente produzem o mesmo efeito, contudo, o arquivo jar
  permite que seja enviado para um outro diretório ou outro computador,
  onde pode ser executado, enquanto o comando anterior (acima) exige,
  inclusive, a disponibilidade do Maven (o que pode ser útil em tempo de
  desenvolvimento).

- `java -jar target/api.jar` ou ainda `mvn spring-boot:run`<br>
  coloca em execução a API gerada por `mvn package -P api` na porta padrão (8080). Para fazer uso de porta
  diferente use `java -jar -Dserver.port=9876 target/api.jar`, por exemplo. Requisições podem ser
  submetidas conforme abaixo:
    - Abra o endereço _http://localhost:8080_ no seu navegador para visualizar a documentação da API

## Executando o stress-test com K6

- Executar o comando para iniciar a api `mvn spring-boot:run`
- Acessar a pasta dos scripts do K6, através do caminho `/src/test/stress-test`
- Dentro da pastas dos arquivos do K6, abra o terminal e digite o seguinte comando `k6 run k6-script-consulta-saldo.js`, para realizar o stress-test no método de consultar saldo.
- Dentro da pastas dos arquivos do K6, abra o terminal e digite o seguinte comando `k6 run k6-script-cadastrar-cartao.js`, para realizar o stress-test no método de cadastrar cartão.
- - Dentro da pastas dos arquivos do K6, abra o terminal e digite o seguinte comando `k6 run k6-script-realizar-transacao.js`, para realizar o stress-test no método de realizar transação.