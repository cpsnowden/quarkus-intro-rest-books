# rest-book

This project was created to get a feeling of Quarkus, focussed on developer experience and basic features.

### What is Quarkus

[Quarkus](http://quarkus.io/) is an open-source 'Kubernetes Native Java stack' tailored for the OpenJDK and 
GraalVM created in 2018 with sponsorship from Redhat. 

It tries to optimize for *fast startup* and *low memory footprint* which is useful for 
applications with:

1. large number of instances -  the framework footprint and cost scales accordingly 
2. requirements for responsive scaling - the startup time impacts the ability to rapidly add more instances to serve requests

Whilst literature suggests that the JVM based Quarkus stack gives benefits to both startup and memory footprints, to 
see the true benefit requires using GraalVM integration by building a native binary. GraalVM itself reduces startup and 
footprint costs for a JVM application via:
- Image generation uses static analysis to find code reachable and stripping the rest
- Ahead of Time (AOT) complicaiton results in binary with program machine code rather than relying on JVM JIT.

Quarkus itself has a small core framework with everything else being an 'extension' e.g. for Vert.X, Spring, Hibernate, Camel, K8

#### JaxRS

**JaxRS** is a specification for exposing and consuming REST web services, and Quarkus uses **RESTEasy** as it's implementation.

Other JaxRS implementations: Jersey, CXF

#### CDI

**CDI** is a specification for context and dependency injection including management of bean lifecycles, call interception and relies
on a set of annotations: `@Inject`, `@Qualifier`, `@ApplicationScoped`, `@RequestScoped`, `@Observes`. Quarkus's CDI implementation is ARC.

Other CDI implementations: HK2, Weld.

### Setup

To setup a new quarkus application install:

1. JDK
2. GraalVM
3. MVN/Gradle
4. Quarkus Command Line Tool (Optional)

```bash
#Create a bootstrapped application
quarkus create app org.cps.quarkus.starting:rest-book --extension='resteasy-jsonb'

#Run the Application in DEV mode
mvn quarkus:dev
```

### Hot Reload and Watch Testing

Quarkus has a **great** feature to shorten the development cycle by enabling local changes to be hot re-loaded
into a locally running dev instance (this is enabled when you run `mvn quarkus:dev`. Tests can also be automatically re-run when files are changed, think 
`jest --watch` for those UI developers!

### Testing

Quarkus supports both JVM tests and Native Mode Tests, both involved brining up a local server and running
integration tests against it. Native mode tests are important when verifying that the native binary meets
requirements.

### Configuration and Profiles

All Quarkus configuration is handled via a application.properties file (other file formats e.g. yml are allowed).
Similar to Spring-Book, configuration can be injected into the application via the `@ConfigProperty` annotation. More 
advanced control over sourcing/parsing config can be performed via `ConfigSource` and `Converter` respectively.

Similar to Spring, Quarkus supports **profiles**, enabling behaviour/config to be segregated by realm. By
default, Quarkus ships with `%dev`, `%test` and `%prod` profiles which are selected automatically e.g:

1. `mvn quarkus:dev` - when running dev local server, `%dev` profile is selected
2. `mvn test` - running test target selects `%test` profile
3. `java -jar ...` - running the packaged jar selected the `%prod` profile

Alternatively the profile can be selected using `-Dquarkus.profile` property.

### Packaging Application

#### JVM

Quarkus comes with build plugins for different executable jar types:
1. Jar (default) - bundles code, quarkus runtime and config + an index for optimized classpath scanning
    ```
    quarkus-app
      |- app/ : Contains application code
      |- lib/
      |  |- boot/ : Contains quarkus boostrap
      |  |- main/ : Contains dependencies
      |- quarkus/: Contains generated bytecode and an index file to speed up classpath scanning
      |- quarkus-run.jar : Small jar that is the entry point

2. Legacy Jar - same as `Jar` but without the index
3. Uber/Fat Jar

The type can be controlled via `mvn package -Dquarkus.package.type=jar`

#### Native

Quarkus can use GraalVM and create a binary rather than byte code using native-image and hence 
create an executable that doesn't depend on an external java jvm

- Use `mvn package -Pnative` to create a native binary which can be executed via `./target/rest-book-1.0.0-SNAPSHOT-runner`
- Given GraalVM is transforming the code, Quarkus allows you to verify the binary by running native test against them via `mvn verify -Pnative`
- Note that the binary created is specific to your development OS!

### Creating a Container

Quarkus has support for docker, in-fact the boostrap will automatically generate Dockerfile(s) for 
the different JVM/Native build types. Docker image generation can be performed using Docker or JIB 
(useful for builds where docker cannot be run on CI servers.)

1. Add the docker image extension (this just modifies the pom.xml)
    ```bash
    quarkus extension add 'container-image-docker'
    ```
2. Build an image (adds a JVM tag)
    ```bash
    mvn package -Dquarkus.container-image.build=true -Dquarkus.container-image.tag=jvm
    ```
3. Run the image (-i interative, --rm remove image on exit, -p expose port)
   ```bash
   docker run -i --rm -p 8080:8080 <docker_uname>/rest-book:jvm
   ```
   
You can also create a container containing the native binary by changing step #2. This actually
uses a docker container to run the native image build in order to generate a linux compatible binary (see `quarkus.native.container-build`)

2. Build an image (adds a JVM tag)
    ```bash
    mvn package -Dquarkus.container-image.build=true -Dquarkus.native.container-build=true -Dquarkus.container-image.tag=native --Dquarkus.container-image.tag=native
    ```

# Boiler Plate from Quarkus Archetype

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/rest-book-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- RESTEasy Classic JSON-B ([guide](https://quarkus.io/guides/rest-json)): JSON-B serialization support for RESTEasy Classic

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
