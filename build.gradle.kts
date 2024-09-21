// Susak Oleksande DevOps 1250

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  	id("org.springframework.boot") version "3.1.0"
  	id("io.spring.dependency-management") version "1.1.0"
  	kotlin("jvm") version "1.8.21"
  	kotlin("plugin.spring") version "1.8.21"
	kotlin("plugin.serialization") version "1.8.21"
}

group = "academy.softserve"
version = "0.0.1-SNAPSHOT"
java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}


// TODO: your code starts here
// my code Susak Oleksandr

tasks.register<Exec>("npmInstall") {
        workingDir = file("/home/susak/webpack/build-tools-susakom/ui") // Указываем полный путь к папке ui
        commandLine("/home/susak/.nvm/versions/node/v14.21.3/bin/npm", "install") // Команда для установки зависимостей
}


tasks.register<Exec>("compileUi") {
        dependsOn("npmInstall")                                          // Задача зависит от npmInstall, чтобы сначала установить зависимости
        workingDir = file("/home/susak/webpack/build-tools-susakom/ui") // Папка для выполнения команды 
         commandLine("/home/susak/.nvm/versions/node/v14.21.3/bin/npm", "run", "build")  // Команда для запуска сборки Webpack
}



// Копируем index.html
tasks.register<Copy>("copyIndexHtml") {
         dependsOn("compileUi") // Зависит от сборки frontend
         from("/home/susak/webpack/build-tools-susakom/ui/dist/index.html")
         into("/home/susak/webpack/build-tools-susakom/src/main/resources") // Копируем index.html в resources
}

// Копируем app.js
tasks.register<Copy>("copyAppJs") {
        dependsOn("copyIndexHtml") // Зависит от успешного копирования index.html
        from("/home/susak/webpack/build-tools-susakom/ui/dist/app.js")
        into("/home/susak/webpack/build-tools-susakom/src/main/resources") // Копируем app.js в resources
}

// Копируем папку css
tasks.register<Copy>("copyCss") {
       dependsOn("copyAppJs") // Зависит от успешного копирования app.js
       from("/home/susak/webpack/build-tools-susakom/ui/dist/css")
       into("/home/susak/webpack/build-tools-susakom/src/main/resources/css") // Копируем папку css в resources/css

}

tasks.named("processResources") { // Указываем, что processResources зависит от copyUi
        dependsOn("copyCss")
}


tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {     
       dependsOn("copyCss") // Все задачи типа BootRun зависят от задачи copyUi
}

