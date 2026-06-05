Membros : 
Gilberto Cezar Silveira Júnior - 2200  
Thiago Vilela Gomes Valim - 2211
Luís Eduardo Alves Marques - 868

Uso de Inteligência Artificial no Desenvolvimento
- Persistência com SQL
Iniciamos implementando manualmente as operações de INSERT e UPDATE para a entidade Filme. Em seguida, utilizamos o Claude para replicar esse padrão nas demais classes do projeto, gerando as operações de banco de dados de forma consistente para todas as entidades.
- Qualidade de Código
O Amazon Q Developer foi usado para:

Melhorar a indentação e legibilidade geral do código
Refinar a interface do usuário
Remover trechos desnecessários acumulados durante o desenvolvimento

- Busca Polimórfica por Vetor
A IA orientou a criação de um vetor de Conta capaz de armazenar tanto objetos do tipo Cliente quanto Vendedor, aproveitando herança para unificar a lógica de busca entre as duas entidades.
- Tratamento de Erros SQL
A IA auxiliou em dois pontos específicos:

Identificar e tratar erros de duplicidade no banco de dados (ex: chave primária repetida)
Sugerir os pontos ideais para inserção de blocos try-catch, tornando o sistema mais robusto
