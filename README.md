# Teads Scala Bootcamp

This repository contains the template for the exercises to work on during the Teads Scala training day.

## Install

Make sure you have Java JDK 1.8.
 If not, follow the instructions on the [Azul website](https://www.azul.com/downloads/zulu/). Make sure you download the proper JDK for your environment (OS + 32/64 bits).  
Install IntelliJ following the steps on the [JetBrains website](https://www.jetbrains.com/idea/download/).

You can also follow the official Scala website [download page](https://www.scala-lang.org/download/).

## Open and compile the project inside IntelliJ

After Java&IntelliJ have been installed, open IntelliJ.  
Install the Scala plugin using at the bottom of the home page `Configure` > `Plugins` and then searching for `Scala`.  
Then import the project using `Import Project` and selecting the project root directory.  
Then select `import project from external model` and choose the `sbt` build tool.  
Then select the java JDK you want to use.  
Then finish to start running the project inside IntelliJ :tada:

## Start the server

Run `sbt run` at the root of the project.  
Alternatively, via IntelliJ, use the sbt shell at the bottom left corner and enter `run`.
