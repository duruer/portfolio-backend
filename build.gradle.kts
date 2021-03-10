plugins {
    java
    kotlin("jvm") version "1.4.31"
    kotlin("kapt") version "1.4.31"
    id("io.vertx.vertx-plugin") version "1.2.0"
}

group = "com.ahmetduruer.portfolio"
version = "1.0"

val vertxVersionVariable = "4.0.2"

repositories {
    jcenter()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/iovertx-3720/")
    maven("https://jitpack.io")
}

vertx {
    mainVerticle = "com.ahmetduruer.portfolio.Main"
    vertxVersion = vertxVersionVariable
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.vertx:vertx-web:$vertxVersionVariable")

    // vertx dependency
    implementation("org.slf4j:slf4j-simple:1.7.30")

    // dagger 2x
    implementation("com.google.dagger:dagger:2.33")
    kapt("com.google.dagger:dagger-compiler:2.33")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    register("copyJar") {
        doLast {
            copy {
                from(shadowJar.get().archiveFile.get().asFile.absolutePath)
                into("./")
            }
        }

        dependsOn(shadowJar)
    }

    vertxDebug {
        environment("EnvironmentType", "DEVELOPMENT")
    }

    vertxRun {
        environment("EnvironmentType", "DEVELOPMENT")
    }

    build {
        dependsOn("copyJar")
    }

    register("stage") {
    }
}