# aws

Small utility module that exports ```AmazonWebServiceClient``` services.

## exports

* One ore more ```amazon services```, like ```AmazonS3Client```, ```AmazonSimpleEmailServiceClient```, etc...

## dependency

```xml
<dependency>
  <groupId>org.jooby</groupId>
  <artifactId>jooby-aws</artifactId>
  <version>{{version}}</version>
</dependency>
```

## usage

application.conf:

```properties
aws.accessKey = AKIAIOSFODNN7EXAMPLE
aws.secretKey =  wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
```

```java
{
  use(new Aws()
    .with(creds -> AmazonS3Client.builder().withCredentials(creds).build())
    .with(creds -> AmazonSimpleEmailServiceClient.builder.withCredentials(creds).build())
  );

  get("/", req -> {
    AmazonS3 s3 = require(AmazonS3.class);
    // work with s3
  });
}
```

Keep in mind, you will need the ```s3 (ses, sqs,sns, etc..)``` jar in your classpath.

This module is small and simple. All it does is bind ```AmazonWebServiceClient``` instances in
{{guice}}. It also helps to bind utility classes like ```TransferManager```

```java
{
  use(new Aws()
    .with(creds -> AmazonS3Client.builder().withCredentials(creds).build())
    .doWith((AmazonS3Client s3) -> TransferManagerBuilder.standard().withS3Client(s3).build())
  );

  post("/", req -> {
    TransferMananger tm = require(TransferManager.class);
  });
}
```

## handling access and secret keys

Keys are defined in ```.conf``` file. It is possible to use global or per service keys.

```properties
 aws.accessKey = AKIAIOSFODNN7EXAMPLE
 aws.secretKey =  wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY

 aws.s3.accessKey = S3IOSFODNN7S3EXAMPLE
 aws.s3.secretKey = s3alrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
```

```
{
  use(new Aws()
    // use aws.s3 keys
    .with(creds -> AmazonS3Client.builder().withCredentials(creds).build())
    // use global keys
    .with(creds -> AmazonSimpleEmailServiceClient.builder.withCredentials(creds).build())
  );
```

The module also install the defaults aws credentials provider. Provider precedence is as follows:

- application *.conf file (application.conf, application.prod.conf, etc...)
- environment (EnvironmentVariableCredentialsProvider)
- jvm system properties (SystemPropertiesCredentialsProvider)
- aws configuration profile (ProfileCredentialsProvider)
- ec2 (EC2ContainerCredentialsProviderWrapper)
