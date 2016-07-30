#!/bin/bash
#Recaches specified pages
properties_file="src/main/resources/application.properties"
websoc_url=""
websoc_search=""

loadProperties(){
	websoc_url="$(cat $properties_file | grep app.websoc.url | cut -d "=" -f2)"
	websoc_search="$(cat $properties_file | grep app.websoc.search | cut -d "=" -f2)"
	echo "WebSoc Url: $websoc_url"
	echo "Search file path: $websoc_search"
}


refreshCache(){
	echo "Storing $1 ---> $2"
	curl -s $1 > $2
}

loadProperties
refreshCache $websoc_url $websoc_search
