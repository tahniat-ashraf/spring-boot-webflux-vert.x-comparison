#!/usr/bin/env sh

export BASE_DIR=$PWD
for i in $(find tableData -name "table-*.json" );
do
echo "Creating table : "$i
aws dynamodb create-table --cli-input-json file://$i --region "ap-southeast-1" --endpoint-url http://dynamodb.host:4566

done;
