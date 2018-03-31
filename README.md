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

## Rishi Sheth Development Notes

####2018-03-31
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

####2018-03-29
Started with fixing the test.  As I've never worked with Spring Boot, I cloned the 
Spring Boot Hello World as an example.  

I've set up a basic H2 database using Spring.  I'm really surprised how easy it was to add the dependency, a schema, 
and data file to prototype the H2 setup in my local web app.  Most of the magic is being done by Spring and that is 
awesome.

The plan now is to tie CRUD operations to H2 with the REST operations in my controller.

If I have time, I'm planning to tie my domain to elastic bean stalk and route53 to set up a basic web app in AWS.  If I
have more time, I'll add some basic front end or error handling.

### TODO
 1. Set up docker plugin to automatically build the app into a docker image
 2. Create a quick deployment process of the docker image to Amazon.
 3. Set up my EC2 and Route 53 to play nice on motd.rishi-sheth.com
