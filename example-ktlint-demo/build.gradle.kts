plugins {
    id("com.softeq.gradle.itest") version "1.0.4"
}

dependencies {
    // ktlintRuleset(project(":customruleset"))
    implementation("org.springframework.boot:spring-boot-starter-web:${property("springWebVersion")}")

    testImplementation("org.assertj:assertj-core:${property("assertJVersion")}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${property("springWebVersion")}")
    testImplementation("org.testcontainers:junit-jupiter:${property("testcontainersVersion")}")
}

configurations {
    itestImplementation.get().extendsFrom(testImplementation.get())
}

idea {
    module {
        inheritOutputDirs = false

        testSourceDirs.plusAssign(sourceSets["itest"].allSource.srcDirs)
        testResourceDirs.plusAssign(project.sourceSets["itest"].resources.srcDirs)
    }
}

fun setupEnv(task: Test) {
    val env = System.getenv("ENV") ?: "test"
    task.environment("SMOKE_TEST_ENV", env)
}

tasks {
    withType<Test> {
        useJUnitPlatform {
            excludeTags("smoke")
        }
    }

    koverHtmlReport {
        includes = listOf("org.example.*")
        excludes = listOf("*Test*", "*Application*")
    }

    check.get().dependsOn(koverHtmlReport)

    koverVerify {
        includes = listOf("org.example.*")
        excludes = listOf("*Test*", "*Application*")

        // The plugin currently only supports line counter values.
        // For future improvements, see https://github.com/Kotlin/kotlinx-kover/issues/128

        rule {
            name = "Minimal line coverage rate in percent"
            bound {
                minValue = 100
            }
        }
    }
}
