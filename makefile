build-and-push-to-ecr:
	helm lint ./helm/ --strict
	aws ecr get-login-password --region eu-central-1 --profile mp-ops | docker login --username AWS --password-stdin 543164192837.dkr.ecr.eu-central-1.amazonaws.com/mp-kafka-rest-proxy
	docker build -t mp-kafka-rest-proxy .
	docker tag mp-kafka-rest-proxy:latest 543164192837.dkr.ecr.eu-central-1.amazonaws.com/mp-kafka-rest-proxy:latest
	docker push 543164192837.dkr.ecr.eu-central-1.amazonaws.com/mp-kafka-rest-proxy:latest
	helm push ./helm/ iot-data-mp-charts --force