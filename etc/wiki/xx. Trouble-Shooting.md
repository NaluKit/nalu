



#TBD








# Trouble-Shooting
In some cases, you might run into troubles using Nalu. This side will provide solutions to common problems. In case you will run into problems and you do not find a solution, feel free to use the [Nalu Gitter room](https://gitter.im/Nalukit42/Lobby).

## Compilation errors after updating Nalu
### Problem
After updating Nalu to a new version, compilation fails.
### Solutions
In this case please try the following steps:
* run `mvn clean install`: After updating Nalu the old compiled sources have to be newly created.
* check the factory path that you also use the new processor version.
