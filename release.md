# Release Process

To release the library, follow these steps:

1. Push all changes to the `dev` branch.
2. Wait until the CI action has successfully finished.
3. Create a new branch from `dev` and name it `release/x.x.x`.
4. Change the version in the following files from `HEAD-SNAPSHOT` to `x.x.x`:
   - pom.xml
   - Nalu.java
   - NaluTest.java
   - Templates (if applicable)
5. Commit and push the changes with the comment: `prepare release x.x.x`.
6. Create a pull request to merge the `release/x.x.x` branch into `main` and complete the merge.
7. Release the new version (e.g., via Nexus).
8. Create a new branch from `main` and name it `snapshot`.
9. Change the version in the same files (pom.xml, Nalu.java, NaluTest.java, Templates) from `x.x.x` back to `HEAD-SNAPSHOT`.
10. Commit and push the changes with the comment: `start next iteration`.
11. Merge the `snapshot` branch into `dev`.
12. Delete the `snapshot` branch.