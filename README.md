# Charter MOTD - Rishi Sheth

## App Deployment
The app has been deployed to Amazon ElasticBeanstlak via Docker image.  You can visit it [here](http://motd.us-east-2.elasticbeanstalk.com).
  
I also wanted to play with Amazon Route 53 to use my domain to deploy the app.  I set up an A Record in a Hosted Zone
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
because I had used it in previous projects for prototyping.  I used a repository class and used the @Autowired annotation
 to map our repository and rest controller class to each other.  I refactored `Motd` to `MotdMain` for the main 
 application and used `Motd` as an entity class to map to our database.
 
Working with Spring Boot because pretty easy due to the overwhelming amount of documentation, tutorials, and general 
info out about it.  I was recommended a Spring Boot class on Udemy which I started and purchased a Spring related "masterclass"
to learn more of what it is capable of.

#### Fixing the broken test and Adding my own

I was able to fix the initial test pretty quick by adjusting the expected text.  I added another test for the PUT operation
to update the motd, but ran into issues where the `updateMotd()` test would work but the `getMotd()` test would fail.  
I found the Spring application context persists between tests. I used the `@DirtiesContext` annotation to fix this.

#### Maven, Docker, and Plugin Issues
Initially, I used Spotify's dockerfile-maven plugin to use `mvn install dockerfile:build` to build the image and deploy
it.  I was then able to run the docker image locally with `docker run -p 8080:8080 -t physik932/motd-code-sample`.

To deploy this to Amazon, I wanted an easy plugin to create my Dockerfile + JAR zip easily, so I chose to use the Maven 
Assembly plugin.  This required I move my Dockerfile to src/main/docker, which then broke the Spotify plguin.  When 
searching as to why, I found [Issue #89](https://github.com/spotify/dockerfile-maven/pull/89) and [Issue #117](https://github.com/spotify/dockerfile-maven/issues/117)
 related to adding a configurable location to the Spotify plugin.  In the end, I just created my own ZIP of the Dockerfile
 and JAR to upload to Amazon.
 
#### Using Amazon's Elastic Beanstalk and Route 53
I was able to easily set up an Elastic Beanstalk for a web application using a single docker image thanks to an awesome 
guide they had.  I used Route53 to set up for fun to set up a Hosted Zone and A record to `motd.rishi-sheth.com`, and
tied it to my Elastic Beanstalk instance running the docker image of my Spring boot app.

#### Final Thoughts
Overall, this was a lot easier to learn thanks to a lot of great blogs and tutorials provided by Spring, Amazon and 
others.  I have enjoyed learning more about Docker, Spring, and Amazon services for deployment.

_Note : I added the section below during development as notes to myself.  I thought it would be fine to keep them to 
show my thought process and how I got from start to finish.  Thanks for reading._
#### 2018-04-01
I finally got the deployment to Amazon's Elastic Beanstalk to work with help of the Maven Assembly Plugin.  The plugin
picks up the Dockerfile and jar file into a zip file when I run `mvn clean package`.  I uploaded the zip into an elastic
beanstalk application configured for a single docker container and a web server.  The Dockerfile was use java 8 and the
jar file.  Once set up, I was able to access it at `http://motd.us-east-2.elasticbeanstalk.com/`.  I used Postman to 
test the PUT function with a new MOTD as the message body.  

#### 2018-03-31
I've loved Spring so far.  JpaRepository, autowiring into the controller, using an H2 database with simple props set up
in the application.properties has all been *really* easy.  Setting up mock tests and running the local server with
testing help from Postman is also really easy.  At this point I can updated the MOTD and iterative requests work as
expected.

For clarity, I renamed Motd to MotdMain and am using Motd as an entity class tied to my h2 database.  Its initially
populated by a data.sql file under src/main/resources.  The test was expanded to test my PUT function on the root
web address (auth be damned).

If this was to be a real web app, I'd have auth set up for the update.  A simple login app would be easy to use
to see history of MOTDs and maybe instead of updating via PUT, you would only create with it.  A POST function could be
created to update which MOTD we want based on a id setup, and the GET could be expanded to output all MOTDs available.

#### 2018-03-29
Started with fixing the test.  As I've never worked with Spring Boot, I cloned the 
Spring Boot Hello World as an example.  

I've set up a basic H2 database using Spring.  I'm really surprised how easy it was to add the dependency, a schema, 
and data file to prototype the H2 setup in my local web app.  Most of the magic is being done by Spring and that is 
awesome.

The plan now is to tie CRUD operations to H2 with the REST operations in my controller.

If I have time, I'm planning to tie my domain to elastic bean stalk and route53 to set up a basic web app in AWS.  If I
have more time, I'll add some basic front end or error handling.

# Charter Enterprise MOTD Sample Project
A small project to help assess candidate experience with webservices and our technology stack.

## Instructions
We have provided a webservice that provides a "message of the day", similar to what you might see logging into a 
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

