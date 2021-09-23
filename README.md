##### Maven

```xml
<repositories>
    <repository>
        <id>Masston Repo</id>
        <url>https://repo.mstn.me</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>me.mstn</groupId>
    <artifactId>BeeNetty</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

##### Gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://repo.mstn.me' }
    }
}
```
```groovy
dependencies {
        implementation 'me.mstn:BeeNetty:1.0-SNAPSHOT'
}
```