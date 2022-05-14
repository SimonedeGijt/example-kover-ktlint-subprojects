import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    idea
    id("org.jetbrains.kotlinx.kover") version "0.5.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("com.github.ben-manes.versions") version "0.42.0"
}

repositories {
    mavenCentral()
}

subprojects {
    group = "org.example"
    version = "1.0-SNAPSHOT"

    apply(plugin = "idea")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }

    ktlint {
        version.set("0.44.0")
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)

            customReporters {
                register("html") {
                    fileExtension = "html"
                    dependency = "com.pinterest.ktlint:ktlint-reporter-html:0.38.1"
                }
            }
        }
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }
    }
}

kover {
    generateReportOnCheck = false // false to do not execute `koverMergedReport` task before `check` task
    // disabledProjects = setOf("customruleset") // setOf("project-name") to disable coverage for project with name `project-name`
    runAllTestsForProjectTask = true // true to run all tests in all projects if `koverHtmlReport`, `koverXmlReport`, `koverReport`, `koverVerify` or `check` tasks executed on some project
}
