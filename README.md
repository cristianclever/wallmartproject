# wallmartproject
Projeto 'prova' do wallmart

Motiva��es:
Desenvolver um projeto que permita o cadastro de novos malhas, e consequentemente 
a consulta por melhor rota.  


Sobre o sistema Desenvolvido

O sistema foi desenvolvido utilizando o servidor Glassfish 4.1.
IDE Utilizada: Oracle Enterprise Pack for Eclipse (12.2.1.1)


Tecnologias utilizadas: 
- JPA, para a persist�ncia dos dados.
- Sessions Beans Stateless, desempenhando o papel da fachada, Suporte Transa��o
- JAX-WS, para a publica��o do servi�o via webservice.
- Hipster, especifica para resolu��o do problema de menor caminho com peso, nos grafos resultantes das rotas.
- JUNIT para os testes unitarios



Sobre a Solu��o para o problema.

A parte t�cnica do problema "Expor um webservice para cadastros" "expor um webservice para consulta do menor caminho" , � realtivamente simples.
Por�m a parte da l�gica � complexa, pois se trata de um problema de grafos com peso.
Ap�s an�lise identifiquei desenvolver a solu��o para este problema seria custoso em termos de tempo, sendo assim iniciei 
uma pesquisa onde achei uma api, capaz de resolver o problema dos Grafos, o 'Hipster', tornando  assim a solu��o bem mais simples de se prover.


Documentos adicionais:
WallMartProject -resumo, Nesse documento descrevi sobre as configura��es necess�rias para o projeto.


cristianclever@gmail.com