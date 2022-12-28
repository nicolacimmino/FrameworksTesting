In this folder are NOT the actual config files, but templates for the expected configurations.

You will need to create a separate git repo (local or on GitHub (private!)), add there the files and change the values
to actually match your configuration.

You will need to make sure that `application.properties` then points to the correct folder where the config repo is
located:

````
spring.cloud.config.server.git.uri= file:///somewhere/config-repo
````

NOTE: You need 3 forward slashes in Windows `///`.

