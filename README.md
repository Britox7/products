<h2>Sobre o projeto:</h2>
<p>
  Este projeto consiste na criação de um sistema Back-end crud com java, springboot e mysql.
  Dentro do projeto iremos ver diversos conceitos como: Controllers, Interfaces, ORM (Mapeamento Objeto Relacional) e etc...
  
  Estrutura do projeto:
  
  ![image](https://github.com/Britox7/products/assets/101662003/7b486062-90f9-4b44-938a-026bcd8907a8)


</p>


<p>
  Como vimos na imagem acima nosso projeto tem como estrutura os pacotes: "Controllers" Pacote onde estão localizados todos os endpoints da API / "Entities"
  Pacote onde se encontra todas as entidades presentes / "Repositories" Pacote onde se encontra todas as interfaces do projeto.
  Temos também o arquivo "pom.xml" Arquivo de configuração do projeto, arquivo que serve para instalar todas as dependêcias do seu projeto. 
</p>
<h4>CONTROLLERS:</h4>
<div class="snippet-clipboard-content notranslate position-relative overflow-auto"><pre class="notranslate">
  <code>
@RestController
@RequestMapping(value = "/Produtos")
public class ProdutosController {

    @Autowired
    private ProdutosRepository repository;

    @GetMapping
    public List<Produtos> findAll(){
        List<Produtos> result = repository.findAll();
        return result;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        try {
            if (repository.existsById(id)){
                Produtos result = repository.findById(id).get();
                return ResponseEntity.ok(result);
            } else {
                String mensagem = "Produto não encontrado, forneça um ID válido.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno ao excluir o usuário: " + e.getMessage());
        }
    }

    @PostMapping
    public Produtos insert(@RequestBody Produtos produtos){
        Produtos result = repository.save(produtos);
        return result;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                String message = "Produto deletado com sucesso";
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto inexistente, forneça um id válido: ");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno ao excluir o produto: " + e.getMessage());
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateProduto(@RequestBody Produtos data) {
        try {
            Optional<Produtos> optionalProduto = repository.findById(data.getId());
            if (optionalProduto.isPresent()) {
                Produtos produto = optionalProduto.get();
                produto.setName(data.getName());
                produto.setPreco(data.getPreco());
                repository.save(produto);
                return ResponseEntity.ok(produto);
            } else {
                throw new EntityNotFoundException("Produto não encontrado para o ID: " + data.getId());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno ao atualizar o produto: " + e.getMessage());
        }
    }
  </code>
</div>
<p>
   
Controllers são responsáveis por lidar com as solicitações HTTP recebidas por uma aplicação web e retornar as
respostas ao usuário.  Dentro das Controllers teremos nossos endpoints da API: Criar, Deletar, Atualizar e Ler  
</p>

<h4>ENTITIES</h4>
<div class="snippet-clipboard-content notranslate position-relative overflow-auto"><pre class="notranslate">
  <code>
    @Entity
    @Table(name = "tb_comprador")
    public class Comprador {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long id;
      private String name;
      private int dinheiroDisp;

      @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, targetEntity = Produtos.class)
      @JoinColumn(name = "product_id", insertable = true, updatable = true, nullable = false)
      @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
      private Produtos produtos;
    
      public Comprador(){

      }
      Getters, Setters e Mêtodos....}
  </code>
</div> 
  <p>
    Nas entidades é onde iremos fazer nosso Mapeamento Objeto Relacional (ORM).
    O ORM será resposável por criar as tabelas no banco de dados. Tais tabelas são criadas por meio das Annotations. 
  </p>
  <h4>REPOSITORIES:</h4>
  <div class="snippet-clipboard-content notranslate position-relative overflow-auto"><pre class="notranslate">
     <code>
       public interface ProdutosRepository extends JpaRepository<Produtos, Long>
     </code>
  </div>

  <p>
    Os repositórios são responsáveis por manipular a persistência de dados.
    Interfaces de repositório definem contratos para operações de acesso aos dados, como salvar, buscar,
    atualizar e excluir registros no banco de dados. 
  </p>

  <h4>POM.XML:</h4>
  <p>
    Arquivo de configuração do projeto, nele serão definidos a versão do Java, nome do projeto, descrição e instalação de dependências e plugins.
    Caso o seu projeto precise de uma extensão ou plugin específico é nesse arquivo que você deve colocar.
  </p>
  
  </br>

  <h4>APPLICATION.PROPPERTIES:</h4>
  <p>
    O arquivo "Application.propperties" serve para configurar propriedades específicas da aplicação como: configurações do banco de dados,
    configurações do servidor web, configurações de logging e etc... 
    Exemplo abaixo.
  </p>

<div class="snippet-clipboard-content notranslate position-relative overflow-auto"><pre class="notranslate">
  <code>
spring.application.name=products

### usuário e senha de conexão com o banco de dados
spring.datasource.username=root
spring.datasource.password=brito171202

### url de conexão do banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/dbSpringProject

### apontamos para o JPA e Hibernate qual é o Dialeto do banco de dados
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

### deixamos o hibernate responsável por ler nossas entidades e criar as tabelas do nosso banco de dados automaticamente
spring.jpa.hibernate.ddl-auto=create

### configuração do Hibernate para reconhecer o nome de tabelas em caixa alta
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

### configurações de log
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
  </code>
</div>

<p>
  O funcionamento do código será postado no Linkedin: 
</p>


  
