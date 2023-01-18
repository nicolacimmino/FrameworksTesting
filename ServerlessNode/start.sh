#!/usr/bin/env bash


# Sometimes dynamo is left running if your script crashes.
ps -A all |  grep 'DynamoDBLocal.jar' | grep -v 'grep' | xargs | cut -d " " -f 3 | xargs kill

export DEPLOY_STAGE=local
export IS_OFFLINE=true

# DynamoDB offline wants some AWS credentials set, don't need to be valid.
export AWS_DEFAULT_REGION=localhost
export AWS_ACCESS_KEY_ID=anythingGoesAsLongAsItsSet
export AWS_SECRET_ACCESS_KEY=AnyKey

# Ensure DynamoDB local is installed, if already installed this will just exit immediately.
node_modules/serverless/bin/serverless dynamodb install

node node_modules/serverless/bin/serverless slstats --disable
node node_modules/serverless/bin/serverless offline start --host '0.0.0.0' --location . -s local

# Run an admin interface for dynamo-db if needed.
# Install first with: npm install -g dynamodb-admin
#dynamodb-admin