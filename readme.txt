-- comando usado no ambiente

 -- buildar imagem de um dos projetos
	-- cartões   docker build --tag cursoms-cartoes .
	-- eureka 	docker build --tag -p 8761:8761 cursoms-eureka .
	-- avaliador credito  Dockerfile
	
-- criacao de network no docker
	 docker network create cursoms-network
	
-- inserir rabbitmq dentro da network docker
	 docker run --name curso-rabbitmq -p 5672:5672 -p 15672:15672 --network cursoms-network rabbitmq:3-12-management
-- inserir eureka dentro da network docker	 
	 docker run --name cursoms-eureka -p 8761:8761 --network cursoms-network cursoms-eureka
-- inserir avaliador credito dentro da network com variaveis de ambiente
	$ docker run --name cursoms-avaliadorcredito --network cursoms-network -e RABBITMQ_SERVER=curso-rabbitmq -e EUREKA_SERVER=cursoms-eureka -d cursoms-avaliadorcredito
-- inserir cartoes dentro da network com variaveis de ambiente
	docker run --name cursoms-cartoes --network cursoms-network -e RABBITMQ_SERVER=curso-rabbitmq -e EUREKA_SERVER=cursoms-eureka -d cursoms-cartoes
	 
-- estopar container
	 docker stop cursoms-cartoes
	docker stop cursomskeycloak
-- remover container
	 docker container rm cursoms-cartoes
	docker container rm cursomskeycloak
-- rodar ms-gateway no network
	$ docker run --name cursoms-gateway -p 8080:8080 -e EUREKA_SERVER=cursoms-eureka -e KEYCLOAK_SERVER=cursoms-keycloak -e KEYCLOAK_PORT=8080 --network cursoms-network -d cursoms-gateway
-- rodar gateway no localhost
	$ docker run --name cursoms-gateway -p 8080:8080 -e EUREKA_SERVER=cursoms-eureka -e KEYCLOAK_SERVER=cursoms-keycloak -e KEYCLOAK_PORT=8081 -d cursoms-gateway
