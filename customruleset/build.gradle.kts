plugins {
    `java-library`
}

dependencies {
    implementation("com.pinterest.ktlint:ktlint-core:0.44.0")

    testImplementation("org.assertj:assertj-core:${property("assertJVersion")}")
    testImplementation("org.testcontainers:junit-jupiter:${property("testcontainersVersion")}")
}

tasks {
    check.get().dependsOn(koverHtmlReport)
}
