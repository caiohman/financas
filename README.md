# financas

## Tema do projeto
  Sistema de controle financeiro.

## Nome do sistema
  Finanças

## Descrição
  Sistema para ajudar no controle financeiro pessoal e corporativo. As funcionalidades vão desde controle de fluxo de caixa até agendamento de compromissos.

## Ferramentas
- Github: Utilizamos o sistema de controle de versão Github para ajudar no desenvolvimento distribuído do projeto. Link do projeto: https://github.com/caiohman/financas
- Java: A linguagem de programação foi o Java para atender os requisitos do projeto, pois oferece o suporte para a programação orientada a objetos.
 - JavaFX: A plataforma para aplicações client JavaFx para auxílio no desenvolvimento da interface gráfica. A versão utilizada é a 14. Site oficial: https://openjfx.io/
 - Gradle: A ferramenta de build foi o Gradle e a versão utilizada é a 6.4. O Gradle faz o download do javafx na versão especificada e as outras dependências necessárias. Além, faz o build do projeto e o executa, se necessário. Site oficial: https://gradle.org/
 - Google Cloud Platform SQL: A ferramenta para auxílio com o banco de dados foi a Google Cloud Platform SQL que oferece uma solução em cloud o controle dos dados. Site oficial: https://cloud.google.com/
 - Adobe XD: Para o desenvolvimento do mockup do projeto foi utilizado o Adobe XD. O link para acesso: https://xd.adobe.com/view/46f34b91-8220-41bc-6cf5-fdff538396d2-30c2/

## Info:
 No arquivo para conexão coloque as informações do seu banco de dados e as suas tabelas.
 Modelo:
 Keys.java
 package caixaApp;

 public class Keys{
   private final String DATABASE_NAME = "";
   private final String INSTANCE_CONNECTION_NAME = "";
   private final String  MYSQL_USER_NAME = "";
   private final String MYSQL_USER_PASSWORD = "";
   private final String MYSQL_ADDRESS_PORT = "";


   public String getDatabaseName(){return DATABASE_NAME;}
   public String getInstanceConnName(){return INSTANCE_CONNECTION_NAME;}
   public String getMysqlUserName(){return MYSQL_USER_NAME;}
   public String getMysqlUserPassword(){return MYSQL_USER_PASSWORD;}
   public String getMysqlAddressPort(){return MYSQL_ADDRESS_PORT;}

 }
