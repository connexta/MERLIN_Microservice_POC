# Purpose 

The Merlin SoS message transformation service receives data from the Kafka `input` topics, determines the 
content-type of the message, transforms its contents to `JSON` and then routes both the `XML` and `JSON` data to the 
appropriate topics. This service also ensures that the required topics are in-place at start-up.

# Project Structure

This service consists of 2 containers. 

* `sos-transformer-initializer` simply creates the topics that `sos-transformer-service` depends on and then exits.
* `sos-transformer-service` is a long-running process that completes the actual transformations and routes messages 
  between topics.
* `kubernetes` - `Kubernetes` artifacts

Both of these containers are `Spring Boot` applications which are deployed in `Kubernetes`. The containers are built 
using the `jib` plugin for `Maven`.

## Configuration

The application configuration is handled through a `Kubernetes` `ConfigMap` which is automatically read in by `Spring
Boot` at deployment time. The following properties are supported:

* [required]`mil.dia.merlin.sos.kafka.bootstrap-server`: the `DNS` name of the `Kafka` bootstrap server to connect 
  to.
* [default 1] `mil.dia.merlin.sos.kafka.partition-count`: the number of partitions for each created topic. 
* [default 1] `mil.dia.merlin.sos.kafka.replica-count`: the number of partition replicas to create across the 
  cluster. This number can't exceed the number of nodes in the cluster. 
  
## Dependencies

The project uses these modules:

* `spring-boot-starter-actuator` - This module allows us to monitor the service health and metrics through `HTTP`.
* `spring-kafka` - a Spring library for connecting to `Kafka` and using it in a Spring-like fashion.

# Build

To build both services from the parent directory:
```shell
mvn clean install
```

This will create 2 `Docker` container images on the local machine. The container names are:

* registry.localdev.me/sos-transformer-initializer
* registry.localdev.me/sos-transformer-service

# Deployment
## Local Development
For local development and testing, the process for making the built images available to the `kubernetes` cluster is 
dependent upon the `Kubernetes` 

Note: If you're running on Docker Desktop then this step is not required.

### On `k3s` *with* a local Docker registry desployed in the cluster:
```shell
$ docker push registry.localdev.me/sos-transformer-initializer:latest
$ docker push registry.localdev.me/sos-transformer-service:latest
```

### On `k3s` *without* a Docker registry deployed in the cluster:
```shell
$ docker save --output target/sos-transformer-initializer-latest.tar registry.localdev.me/sos-transformer-initializer:latest
$ docker save --output target/sos-transformer-service-latest.tar registry.localdev.me/sos-transformer-service:latest
```
```shell
$ sudo k3s ctr images import target/sos-transformer-initializer-latest.tar
$ sudo k3s ctr images import target/sos-transformer-service-latest.tar
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



