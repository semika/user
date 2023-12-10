#!/bin/bash
TOKEN=$(aws ecr get-authorization-token --output text --query 'authorizationData[].authorizationToken')
