# DreamWeaver64 - RPG powered by AI

(requires OpenAI API to run)

<img width="703" alt="image" src="https://github.com/qube1t/dreamweaver64-ai-rpg/assets/77086852/d4910eb3-456a-42e5-b354-968a456ebff4">


## To setup OpenAI's API

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `apiproxy.config`
- put inside the credentials that you received from no-reply@digitaledu.ac.nz (put the quotes "")

  ```
  email: "YOUR@EMAIL"
  apiKey: "YOUR_KEY"
  ```
  these are your credentials to invoke the OpenAI GPT APIs
  
## To run the game

`./mvnw clean javafx:run`

## To debug the game

`./mvnw clean javafx:run@debug` then in VS Code "Run & Debug", then run "Debug JavaFX"

## Contributors

[Samuel Kurian](https://www.linkedin.com/in/kurian-samuel/)

[Siyeon Kim](https://www.linkedin.com/in/siyeonkim612) 

[Yuewen Sun](https://www.linkedin.com/in/james-sun-513165241/)

