Memcached lab: Vehicle Speed Control
------------------------------------
This example uses [Spring](http://www.springsource.org/) and [Jersey](http://jersey.java.net/) to create a simple Json web service.
No knowledge of Spring and Jersey is required, however basic Java knowledge is required.


Prerequisites: A [Memcached](http://memcached.org/) instance should be up and running on localhost:11211

This example is a web service containing some info about a fictional "vehicle speed control" system in Belgium.
One should assume that we are working with a very old slow database system controlled by the government. In reality the repository is mocked and slowed down by some Thread.sleep() to ease this example. I'm aware that there are several solutions to rescue this badly performing web service, but we are going to use Memcached.
The repository contains data on a weekly basis. The web service only shows statistics about the last week.

### 1: Start the application
Open up this project inside your favorite Java IDE and deploy the web application. I used [IntelliJ](http://www.jetbrains.com/idea/) and Tomcat 6. If you browse to [http://localhost:8080/](http://localhost:8080/) you should see a basic html page containing some links to the JSON web-service. Try out a few links and you immidialty see the problem, it's painfully slow! Don't (yet) click on the links to the Non-Offenders statistics.  

### 2: Non-Deterministic caching
Open the "SpeedingService" class and search for the "getTotal". You see there's basicly no logic inside, it's just a passthrough from our repository. This is an ideal place to do some caching! For this method we are going to use the Non-Deterministic caching strategy. In a nutchel: chech the cach if we have the required info, if not fetch it and store it and return the info.
Change the "getTotal" method to:

    @Override
    public Total getTotal() {
        Total total;
        try {
            total = memcachedClient.get(MEMCACHED_KEYS.TOTAL_TOTAL.toString());   // Get value out from memcached
            if (total == null) {    // If total is not in the cache, get if from the database and cache it
                total = new Total((long) slowDatabaseRepository.getAllVehicles().size());
                memcachedClient.set(MEMCACHED_KEYS.TOTAL_TOTAL.toString(), 0, total);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return total;
    }

As you can see this is far from rocked science! Go ahead and redeploy the application and navigate to [http://localhost:8080/rest/speedcontrol/total](http://localhost:8080/rest/speedcontrol/total). The first time you access the links it will be slow, but if you refresh your page you'll notice a lightning fast response! Now redeploy your app and navigate to the previous link. Guess what, still cached! Now continue and fix the TODO's 1 till 4.

### 3: Exercises on Deterministic caching
The statistics about the Non-Offenders is even slower! It's to slow to shove of the waiting duty to our first user. 
We now that our slow database gets updated Sunday night, so why don't we precache the data? This is called deterministic caching.
Go ahead and have a look at the "buildDeterministicCache" method, it has some strange annotations on it, all you need to know is that this method gets ran automatically ones a week(Sunday midnight). The logic inside should ring a bell, the only difference is we are only storing the data. If you go to the "getTotalNonOffenders" method, you'll see that there's only a link to our cache.  Rather then deploying our app and continue on the next monday, uncomment TODO 5. The "PostConstruct" annotation will run this method on startup. Now redeploy! You notice that it takes way longer to deploy, when it's done go ahead and open [http://localhost:8080/rest/speedcontrol/nonoffenders/total](http://localhost:8080/rest/speedcontrol/nonoffenders/total). Immediate response! You may continue by implementing TODO 6.

### 4: Extra
+ Try to write a method that flushes all the Non-Deterministic data from the cache and call this method inside a weekly scheduled job.
+ Try to create a webcall to execute the flush method.