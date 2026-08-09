import com.varabyte.kobweb.gradle.application.tasks.KobwebExportTask
import com.varabyte.kobweb.gradle.application.tasks.KobwebStartTask
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

plugins {
    kotlin("multiplatform") version "2.4.0"
    kotlin("plugin.compose") version "2.4.0"
    kotlin("plugin.serialization") version "2.4.0"

    id("com.varabyte.kobweb.application") version "0.25.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlin {
    /*
     * Set includeServer = false if this is a frontend-only/static site.
     * With true, Kobweb creates both:
     *   - jsMain  -> browser frontend
     *   - jvmMain -> Kobweb server/API backend
     */
    configAsKobwebApplication(includeServer = true)

    js {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
            implementation("com.varabyte.kobwebx:kobwebx-serialization-kotlinx:0.25.0")
        }

        jsMain.dependencies {
            implementation("androidx.compose.runtime:runtime:1.11.2")
            implementation("org.jetbrains.compose.html:html-core:1.11.1")
            implementation("com.varabyte.kobweb:kobweb-core:0.25.0")
            implementation("com.varabyte.kobweb:kobweb-silk:0.25.0")

            implementation("com.varabyte.kobwebx:silk-icons-lucide:0.25.0")

            implementation(npm("jquery", "4.0.0"))

            implementation(devNpm("tailwindcss", "4.3.3"))
            implementation(devNpm("@tailwindcss/postcss", "4.3.3"))
            implementation(devNpm("postcss", "8.5.23"))
            implementation(devNpm("postcss-loader", "8.1.1"))
        }

        jvmMain.dependencies {
            implementation("com.varabyte.kobweb:kobweb-api:0.25.0")
        }
    }
}

/*
 * Tailwind's PostCSS configuration must be copied into Kotlin/JS's generated
 * npm package directory, where Webpack can find it.
 *
 * Create this source file beside build.gradle.kts:
 *
 *   postcss.config.mjs
 *
 * with:
 *
 *   export default {
 *     plugins: {
 *       "@tailwindcss/postcss": {},
 *     },
 *   }
 */
val jsWorkspace = rootProject.layout.buildDirectory.dir("js")
val jsProjectDir = jsWorkspace.map { it.dir("packages/${rootProject.name}") }

val configurePostCss by tasks.registering(Copy::class) {
    val kotlinNodeJsSetup by rootProject.tasks.getting(NodeJsSetupTask::class)
    val kotlinNpmInstall by rootProject.tasks.getting(KotlinNpmInstallTask::class)

    from(layout.projectDirectory.file("postcss.config.mjs"))
    into(jsProjectDir)

    dependsOn(kotlinNodeJsSetup, kotlinNpmInstall)
}

tasks.named("jsBrowserDevelopmentWebpack") {
    dependsOn(configurePostCss)
}

tasks.named("jsBrowserProductionWebpack") {
    dependsOn(configurePostCss)
}

tasks.named<KobwebStartTask>("kobwebStart") {
    dependsOn(configurePostCss)
}

tasks.named<KobwebExportTask>("kobwebExport") {
    dependsOn(configurePostCss)
}
