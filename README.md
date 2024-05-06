# Inviten
=======
# PAP2023Z-Z05

## Overview

Repozytorium projektu zespołu Z05 na przedmiot PAP2023Z.

## Contributors

Członkowie zespołu:

1. Grzegorz Statkiewicz
2. Bartosz Psik
3. Bartosz Jasiński
4. Alicja Jonczyk

Prowadzący

1. Michał Chwesiuk

## Structure

```
|- README.md
|- docs/ - dokumentacja projektu
    |- architecture.md - opis architektury aplikacji
    |- requirements.md - opis wymagań projektowych
|- src/ - kod źródłowy aplikacji
```

## Setup

Do uruchomienia projektu potrzebne są zainstalowanie poszczególne narzędzia

### IDE

W celu sprawniejszej pracy zalecane jest uzycie IDE `InteliJ` (mozna skorzystać z darmowej studenckiej licencji JetBrains) lub `Visual Studio Code`

### Api

Do uruchomienia `api` niezbędna będzie konfiguracja Java'y wraz z framework'iem Spring Boot. Stąd nalezy zasetup'ować środowisko zgodnie z opisem w sekcji [Installing Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.installing)

1. Zainstalować [Java SDK 20 lub nowszy](https://www.oracle.com/java/technologies/downloads/)
2. Zainstalować narzędzie [Maven](https://maven.apache.org/) (najprawdopodobniej jest już zainstalowany wraz z Java SDK lub IDE)
3. Uruchomić projekt znajdujący się w folderze `./src/api` za pomocą IDE lub z konsoli za pomocą komendy

   ```bash
   mvn spring-boot:run
   ```

4. Po urchomieniu aplikacji można sprawdzić działanie za pomocą przeglądarki pod adresem [/hello](http://localhost:8080/hello)

### Mobile

Do uruchimienia aplikacji mobilnej nalezy skonfigurować środowisko zgodnie z wymaganiami React Native. Jezeli konfigurujemy je pod Windows'em, w tym celu jedyną opcją jest development na Android'a. W tym celu nalezy wykonać konfigurację zgodnie z instrukcją [Setting up the development environment](https://reactnative.dev/docs/environment-setup?os=windows&platform=android)

1. Zainstalować [Android Studio](https://developer.android.com/studio/index.html) i skonfigurować środowisko zgodnie z instrukcją React Native
2. Skonfigurować odpowiedni emulator [AVD](https://developer.android.com/studio/run/managing-avds.html) lub [urządzenie fizyczne](https://reactnative.dev/docs/running-on-device)
3. Zainstalować [Node.js](https://nodejs.org/en/download/current) oraz [Yarn](https://classic.yarnpkg.com/lang/en/docs/install/)
4. Uruchomić projekt znajdujący się w folderze `./src/mobile` za pomocą Android Studio (wtedy uruchamiamy projekt w podfolderze z
   `android`) lub za pomocą komendy
   ```bash
   yarn android
   ```
5. Po uruchomieniu aplikacja powinna pojawić się na skonfigurowanym symulatorze bądź podłączonym urządzeniu (w zalezności od wybranej opcji)
