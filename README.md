##### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.MVSSTON</groupId>
    <artifactId>BeeNetty</artifactId>
    <version>v1.0</version>
</dependency>
```

##### Gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
        implementation 'com.github.MVSSTON:BeeNetty:v1.0'
}
```