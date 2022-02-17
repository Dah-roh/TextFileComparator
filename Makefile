build:
	docker build -t text-file-comparator:latest .

ddev: build
	docker-compose -f docker-compose.yml up --build

prod: build
	docker-compose up --build

dev:
	./mvnw spring-boot:run

test:
	./mvnw test
