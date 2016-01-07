# wallmartproject
Projeto 'prova' do wallmart

Motivações:
Desenvolver um projeto que permita o cadastro de novos malhas, e consequentemente 
a consulta por melhor rota.  


Sobre o sistema Desenvolvido

O sistema foi desenvolvido utilizando o servidor Glassfish 4.1.
IDE Utilizada: Oracle Enterprise Pack for Eclipse (12.2.1.1)


Tecnologias utilizadas: 
- JPA, para a persistência dos dados.
- Sessions Beans Stateless, desempenhando o papel da fachada, Suporte Transação
- JAX-WS, para a publicação do serviço via webservice.
- Hipster, especifica para resolução do problema de menor caminho com peso, nos grafos resultantes das rotas.
- JUNIT para os testes unitarios



Sobre a Solução para o problema.

A parte técnica do problema "Expor um webservice para cadastros" "expor um webservice para consulta do menor caminho" , é realtivamente simples.
Porém a parte da lógica é complexa, pois se trata de um problema de grafos com peso.
Após análise identifiquei desenvolver a solução para este problema seria custoso em termos de tempo, sendo assim iniciei 
uma pesquisa onde achei uma api, capaz de resolver o problema dos Grafos, o 'Hipster', tornando  assim a solução bem mais simples de se prover.


Documentos adicionais:
WallMartProject -resumo, Nesse documento descrevi sobre as configurações necessárias para o projeto.


cristianclever@gmail.com