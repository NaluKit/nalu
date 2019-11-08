# Trouble-Shooting
In some cases, you might run into troubles using Nalu. This side will provide solutions to common problems.

In case you will run into problems and you do not find a solution, feel free to use the [Nalu Gitter room](https://gitter.im/Nalukit42/Lobby).

## Compilation errors after updating Nalu
### Problem
After updating Nalu, compilation fails.
### Solutions
In this case please try the following steps:
* run `mvn clean install`: After updating Nalu the old compiled sources have to be newly created.
* check the factory path that you also use the new processor version.

# Eclipse

In order to work with the annotations processor on eclipse is needed to activate manually:
- Right click over project (Package explorer) :arrow_right: **Properties** :arrow_right: **Java Compiler** :arrow_right: **Annotation Processing**. Activate "Enable project specific settings" and "Enable annotation processing".
- On "Factory Path" activate "Enable project specific settings" and add all jars with the annotation processors (in case of nalu *M2_REPO/com/github/nalukit/nalu-processor/2.0.0/nalu-processor-2.0.0.jar*.
- If no source is generated you can check the log with the *Error Log view*. Normally some dependencies are missing from factory path. Add the necesary and reload the procces.
