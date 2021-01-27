# User Interactions

To support user interfaction which should be send to the server, 
the Studio's job framwork can be used. This sections gives a brief example
on how to implement a job. Additional documentation about this can be found
in the _Studio Developer Manual_ at https://www.coremedia.com/en/services/downloads.

## Implementing the Studio Client

To implement a job, we have to know the name of the job type and the 
parameters that should be passed. We use this to trigger a generic job
via the _jobService_. We use a generic job here since this one won't be
automatically visualized within the job window inside the Studio.

```
  //the parameters to pass to the job
  var params:Object = {
    siteId: siteId
  };

  //the job type to execute
  var JOB_TYPE:String = "myJob";
  jobService.executeJob(
          new GenericRemoteJob(JOB_TYPE, params),
          //on success
          function (details:Object):void {
            //do something afterwards
          },
          //on error
          function (error:JobExecutionError):void {
            trace('[ERROR]', "Error executing job: " + error);
          }
  );
```

## Implementing the Studio Server

To create a new job definition, we need two classes: the _JobFactory_ and the 
actual _Job_.

```java
public class MyJobFactory implements JobFactory {
  
  public AssignBriefingJobFactory() {
  }

  @Override
  public boolean accepts(@NonNull String jobType) {
    return jobType.equals("myJob");
  }

  @NonNull
  @Override
  public Job createJob() {
    return new MyJob();
  }
}
```

The _JobFactory_ simply checks if the given job type is matching and allows
to parameterize the new job instance with other Spring bean instances.
For the sake of simplicity, our _MyJob_ does not have any constructor parameters.

```java

```
