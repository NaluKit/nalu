# Steps to create a release

To release the library, the following steps needs to be done:

* push all changes to `dev` 
* wait until the action has successfully finished
* create new branch from `dev` and name it `release/x.x.x`
* change revision in POM from 'HEAD-SNAPSHOT' to 'x.x.x'
* change 'HEAD-SNAPSHOT' to 'x.x.x' (be careful: do not change the value inside the README)
* push changes using comment 'set version to x.x.x'
* merge the `release/x.x.x`-branch into main (create PR) 
* release new version (Nexus)
* create new branch from main and name it `snapshot`
* change version 'x.x.x' back to 'HEAD-SNAPSHOT' (pom, Nalu.java, NaluTest.java + Templates)
* merge the snapshot branch into dev
* delete the snapshot branch
