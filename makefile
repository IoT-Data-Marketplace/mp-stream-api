build-and-push-to-ecr:
	helm lint ./helm/ --strict
	aws ecr get-login-password --region eu-central-1 --profile mp-ops | docker login --username AWS --password-stdin 543164192837.dkr.ecr.eu-central-1.amazonaws.com/mp-stream-api
	docker build -t mp-stream-api .
	docker tag mp-stream-api:latest 543164192837.dkr.ecr.eu-central-1.amazonaws.com/mp-stream-api:latest
	docker push 543164192837.dkr.ecr.eu-central-1.amazonaws.com/mp-stream-api:latest
	helm push ./helm/ iot-data-mp-charts --force