FROM java:8
WORKDIR /
ADD target/AutoComplete-AutoComplete.jar Autocomplete.jar
ADD config.yml config.yml
EXPOSE 8080
CMD java -jar Autocomplete.jar server config.yml
