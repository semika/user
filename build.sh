#!/bin/bash
ACCOUNT=762002331286
REGION=ap-southeast-1
SECRET_NAME=${REGION}-ecr-registry
EMAIL=sawen.sasviru@gmail.com
BUILD_NUMBER=1.0
MODULE=user

source ~/.bash_profile
mvn clean install

#Build the image
docker build -t ${MODULE}:${BUILD_NUMBER} .

#Tag the image
docker tag ${MODULE}:${BUILD_NUMBER} ${ACCOUNT}.dkr.ecr.${REGION}.amazonaws.com/${MODULE}:${BUILD_NUMBER}

#Login to AWS ECR
aws ecr get-login-password --region ${REGION} | docker login --username AWS --password-stdin ${ACCOUNT}.dkr.ecr.${REGION}.amazonaws.com

#Push the image
docker push ${ACCOUNT}.dkr.ecr.${REGION}.amazonaws.com/${MODULE}:${BUILD_NUMBER}

