# Charter MOTD - Rishi Sheth

## App Deployment
The app has been deployed to Amazon Elastic Beanstalk via Docker image.  You can visit it [here](http://motd.us-east-2.elasticbeanstalk.com).
  
I also wanted to play with Amazon Route 53 to use my domain for the app.  I set up an A Record in a Hosted Zone
for motd.rishi-sheth.com that you can see [here](http://motd.rishi-sheth.com).

### Getting the MOTD
You can visit the above URL links or use a program like `curl` or Postman.

### Changing the Message of the Day
Using `curl` or Postman, you can PUT your new MOTD as a raw body to the one of the above websites.  You should receive 
a "Motd updated!" message if the operation was successful.

Using H2 for persistence, the PUT operation updates the only record in the database.  You can follow up with a GET 
request in Postman or visit the site(s) in a browser to see the updated message.

### Working with Spring

I started by learning about Spring Boot and how to wire up the basic REST functions to use locally.  I got this set up
by referencing the Spring Boot Hello World app and documentation.  I added H2 as the in-memory persistent database 
because I had used it in previous projects for prototyping.  I used a repository class and the @Autowired annotation
 to map our repository and rest controller class to each other.  I refactored `Motd` to `MotdMain` for the main 
 application and used `Motd` as an entity class to map to our database.
 
Working with Spring Boot became easier due to the amount of documentation, tutorials, and general 
info available.  I was recommended a Spring Boot class on Udemy which I started and purchased a Spring related "masterclass"
to learn more of what it is capable of.

### Fixing the broken test and Adding my own

I was able to fix the initial test pretty quick by adjusting the expected text.  I added another test for the PUT operation
to update the motd, but ran into issues where the `updateMotd()` test would work but the `getMotd()` test would fail.  
I found the Spring application context persists between tests. I used the `@DirtiesContext` annotation to fix this.  
The annotation allows the test application context to be reset per test method run.

### Maven, Docker, and Plugin Issues
Initially, I was searching for a way for Maven to build my Docker image to reduce the amount of steps for deployment.  
I used Spotify's dockerfile-maven plugin to use `mvn install dockerfile:build` to build the image and deploy
it.  I was then able to run the Docker image locally with `docker run -p 8080:8080 -t physik932/motd-code-sample`.

To deploy this to Amazon, I wanted a plugin to create a ZIP file of the Dockerfile and program JAR in one step, so I 
chose to use the Maven Assembly plugin.  This required I move my Dockerfile to src/main/docker, which then broke the 
Spotify dockerfile-maven plugin.  When debugging this online, I found [Issue #89](https://github.com/spotify/dockerfile-maven/pull/89) and [Issue #117](https://github.com/spotify/dockerfile-maven/issues/117)
 related to adding a configurable location to the Spotify plugin for the Dockerfile.  In the end, I just created my own ZIP of the Dockerfile
 and JAR to upload to Amazon.
 
### Using Amazon's Elastic Beanstalk and Route 53
I was able to set up an Elastic Beanstalk instance for a web application using a single Docker image.  I found the 
documentation provided by Amazon really helpful in wiring all this together.  For my own fun, I used Route 53 to set up
a Hosted Zone and an _A Record_ to a subdomain I own, `motd.rishi-sheth.com`.  Route 53 allowed me to tie this subdomain
to my Elastic Beanstalk instance with one step.  

### Final Thoughts
Overall, I found this project to be enjoyable.  I learned a lot thanks to tutorials and documentation provided by Spring, 
Docker, and Amazon.  I sourced information from several blog posts as well on a Spring Boot + Docker + Amazon web 
application.  I believe all of these tools are extremely useful and flexible, and will continue to learn how to work 
with them.

_Note: I added the section below during development as notes to myself.  I thought it would be fine to keep them to 
show my thought process and how I got from start to finish.  Thanks for reading._

#### 2018-04-01 - Evening
I ran into a final issue when cleaning up my README and running a final build where Spotify's Dockerfile Maven plugin and Maven's Assembly Plugin
had conflicts.  I documented it in the notes above.

#### 2018-04-01 - Morning
I finally got the deployment to Amazon's Elastic Beanstalk to work with help of the Maven Assembly Plugin.  The plugin
picks up the Dockerfile and JAR file into a ZIP file when I run `mvn clean package`.  I uploaded the zip into a new
Elastic Beanstalk application configured for a single Docker container and a web server.  The Dockerfile was set up with
Java 8 and the rest of the settings were minimal.  Once set up, I was able to access it at `http://motd.us-east-2.elasticbeanstalk.com/`.  
I used Postman to test the PUT function with a new MOTD as the message body.  

#### 2018-03-31
I've loved Spring so far.  JpaRepository and Autowiring into the controller were easy to set up basic CRUD operations.  
I used an H2 database with one table and one row of the current MOTD.  In the application.properties, I set up some basic
properties for the database and console access when deployed.  Setting up tests to run like a real web server was also
great with the Mock framework built in for Spring.  Deploying the app locally, I was able to test from Postman.  At this
point, I can update the MOTD and iterative requests work as expected.

For clarity, I renamed Motd to MotdMain for the Spring application context.  I am using Motd as an entity class.  
It's initially populated by the data.sql file under src/main/resources.  I built a second test for the update function.

#### 2018-03-29
Started with fixing the test by adding the correct message to the expected body.  To teach myself Spring, I followed 
your advice and cloned the Spring Boot Hello World project. 

I've set up a basic H2 database for the persistence of the MOTD.  The plan now is to tie CRUD operations in H2 to the 
REST operations in my controller.

# Charter Enterprise MOTD Sample Project
A small project to help assess candidate experience with web services and our technology stack.

## Instructions
We have provided a web service that provides a "message of the day", similar to what you might see logging into a 
Unix system. Unfortuantely, at Charter things don't always go as planned and we need to change the message.  We need you
 to add the abilty to change the 
message.  No message history or any other advanced functionality is needed, unless there is something you'd like to show
off.  The message can be stored in the service using any mechanism you like, but aim for simplicity.  A persistent store
like MySQL or Hypersonic could be overkill for this new requirement.  Iterative 
requests for the MOTD should return the new message, if it has been changed.
Be sure to edit this README.md so we understand what you've done.

Also, a rogue developer has left the code base broken.  To get anything done, you're doing to have to fix the tests first!
And, no, -DskipTests is not a solution!

Push your answer to this Github repo as a feature branch and create a pull request so we know you're done.

### Getting Started
* To compile
```mvn clean package```

* To run
```mvn spring-boot:run```

* To see the message:
```curl localhost:8080```

### Prerequisites
* Java 1.8
* Maven
* cURL
  
### Deployment
If you whiz through this sample, try adding a deployment.   We are a Docker and AWS shop.  Getting something into an
AWS or Heroku, or whatever you're comfortable with will be an "A+"

### Project hints and questions
* Stuck getting started?
  * The official Spring Boot "hello world" example is a great starting point.
* Still need help?
  * Further hints are available, Feel free to ask questions here.  Edit this file in your branch by adding to the 
questions section, push it, and we will update the file with answers. 

