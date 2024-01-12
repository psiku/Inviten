<<<<<<< HEAD
<<<<<<< HEAD
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
<<<<<<< HEAD
<<<<<<< HEAD

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab-stud.elka.pw.edu.pl/gstatkie/pap2023z-z05/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/ee/user/project/merge_requests/merge_when_pipeline_succeeds.html)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing(SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

***

# Editing this README

When you're ready to make this README your own, just edit this file and use the handy template below (or feel free to structure it however you want - this is just a starting point!). Thank you to [makeareadme.com](https://www.makeareadme.com/) for this template.

## Suggestions for a good README
Every project is different, so consider which of these sections apply to yours. The sections used in the template are suggestions for most open source projects. Also keep in mind that while a README can be too long and detailed, too long is better than too short. If you think your README is too long, consider utilizing another form of documentation rather than cutting out information.

## Name
Choose a self-explaining name for your project.

## Description
Let people know what your project can do specifically. Provide context and add a link to any reference visitors might be unfamiliar with. A list of Features or a Background subsection can also be added here. If there are alternatives to your project, this is a good place to list differentiating factors.

## Badges
On some READMEs, you may see small images that convey metadata, such as whether or not all the tests are passing for the project. You can use Shields to add some to your README. Many services also have instructions for adding a badge.

## Visuals
Depending on what you are making, it can be a good idea to include screenshots or even a video (you'll frequently see GIFs rather than actual videos). Tools like ttygif can help, but check out Asciinema for a more sophisticated method.

## Installation
Within a particular ecosystem, there may be a common way of installing things, such as using Yarn, NuGet, or Homebrew. However, consider the possibility that whoever is reading your README is a novice and would like more guidance. Listing specific steps helps remove ambiguity and gets people to using your project as quickly as possible. If it only runs in a specific context like a particular programming language version or operating system or has dependencies that have to be installed manually, also add a Requirements subsection.

## Usage
Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.

## Support
Tell people where they can go to for help. It can be any combination of an issue tracker, a chat room, an email address, etc.

## Roadmap
If you have ideas for releases in the future, it is a good idea to list them in the README.

## Contributing
State if you are open to contributions and what your requirements are for accepting them.

For people who want to make changes to your project, it's helpful to have some documentation on how to get started. Perhaps there is a script that they should run or some environment variables that they need to set. Make these steps explicit. These instructions could also be useful to your future self.

You can also document commands to lint the code or run tests. These steps help to ensure high code quality and reduce the likelihood that the changes inadvertently break something. Having instructions for running tests is especially helpful if it requires external setup, such as starting a Selenium server for testing in a browser.

## Authors and acknowledgment
Show your appreciation to those who have contributed to the project.

## License
For open source projects, say how it is licensed.

## Project status
If you have run out of energy or time for your project, put a note at the top of the README saying that development has slowed down or stopped completely. Someone may choose to fork your project or volunteer to step in as a maintainer or owner, allowing your project to keep going. You can also make an explicit request for maintainers.
>>>>>>> 9ac0bc7 (Initial commit)
=======
>>>>>>> d1d6f11 (Setup project requirements)
=======

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
>>>>>>> f70028c (Setup hello world projects and update readme)
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
>>>>>>> 0508e92 (change invite methid)
