# Purpose 

The Merlin SoS message transformation service receives data from the Kafka `input` topics, determines the 
content-type of the message, transforms its contents to `JSON` and then routes both the `XML` and `JSON` data to the 
appropriate topics. This service also ensures that the required topics are in-place at start-up.

# Project Structure

* `src/main/java` - the service source code
* `src/main/test` - unit tests
* `src/main/kubernetes` - `Kubernetes` artifacts

This is a `Spring Boot` application which is deployed in `Kubernetes`. The container is built using the `jib` plugin
for `Maven`.

## Configuration

The application configuration is handled through a `Kubernetes` `ConfigMap` which is automatically read in by `Spring
Boot` at deployment time. The following properties are supported:

* [required]`mil.afdcgs.merlin.sos.kafka.bootstrap-server`: the `DNS` name of the `Kafka` bootstrap server to connect 
  to.
* [default 1] `mil.afdcgs.merlin.sos.kafka.partition-count`: the number of partitions for each created topic. 
* [default 1] `mil.afdcgs.merlin.sos.kafka.replica-count`: the number of partition replicas to create across the 
  cluster. This number can't exceed the number of nodes in the cluster. 
  
## Dependencies

The project uses these modules:

* `spring-boot-starter-actuator` - This module allows us to monitor the service health and metrics through `HTTP`.
* `spring-kafka` - a Spring library for connecting to `Kafka` and using it in a Spring-like fashion.

# Build

To build the project:
```shell
mvn clean install
```

This will create a `Docker` container image and upload it to the local `Docker` repository.

# Deployment
## Local Development
For local development and testing, the process for making the built images available to the `kubernetes` cluster is 
dependent upon the `Kubernetes` 

Note: If you're running on Docker Desktop then this step is not required.

### On `k3s` *with* a local Docker registry desployed in the cluster:
```shell
$ docker push registry.local/sos-transformation-service:latest
```

### On `k3s` *without* a Docker registry deployed in the cluster:
```shell
$ docker save --output target/sos-transformation-service-latest.tar registry.local/sos-transformation-service:latest
```
```shell
$ sudo k3s ctr images import target/sos-transformation-service-latest.tar
```

## Create Kubernetes Artifcats
```shell
$ kubectl apply -f src/main/kubernetes/sos-transformation-service.yaml
```

## Uninstall
It can be removed with:
```shell
$ kubectl delete -f src/main/kubernetes/sos-transformation-service.yaml
```



