<!-- GETTING STARTED -->

## Summary

This is the result out of challenge I've enrolled in, comprising of some work done with the Reqres API and Localstack.

### How to run

Before running this Test Suite, make sure that Docker is properly installed and working on your machine. 
If you wish to run this locally (via the IDE, meaning without a test container), change the last line of the deploy.sh contained in this repo:

  ```sh
  docker-compose -f "$compose_file" up -d localstack
  ```

If you're running this via test container, simply run the deploy.sh file.

### Analyzing the results

Since the tests can be run in multiple ways, here's how you're able to retrieve the test reports containing all the tests cases that were executed, with their steps properly described.

1. Tests ran via Docker containers:
   1. Go to the volume related to the test container
   2. Open the 'build' folder and look for the 'test-reports'
   3. There should be several folders related to each test set that ran (check the paths mentioned in the main runner java class)
2. Tests ran locally via gradle:
   1. Same thing as mentioned before, only this time, the reports are in the system's folder and not on the volume
   
3. Tests ran via IDEA using Cucumber plugin:
   1. Use IntelliJ's Play Button over the MAIN feature files
   2. After running the tests, each resulting test report should be under the 'target' folder
