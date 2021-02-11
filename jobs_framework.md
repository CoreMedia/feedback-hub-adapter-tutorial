# Jobs Framework

This sections gives a brief example on how to implement a job with the Studio's jobs framework. 
Additional documentation about this can be found 
in the _Studio Developer Manual_ at https://www.coremedia.com/en/services/downloads.

## Implementing the Studio Client

To implement a job, we have to know the name, type and parameters of it. 
We use this data to trigger a generic job via the _jobService_. 
Generic jobs have the advantage that they are not 
automatically being visualized within the job window of the Studio.

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
For the sake of simplicity, our _MyJob_ does not have any constructor parameters, but
additional services, like the _SitesService_ could be passed here.

```java
public class MyJob implements Job {
  private static final Logger LOG = LoggerFactory.getLogger(MyJob.class);

  private String siteId;

  public GetBriefingsJob() {
  }

  @JsonProperty("siteId")
  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  @Nullable
  @Override
  public Object call(@NonNull JobContext jobContext) throws JobExecutionException {
    try {
      ...
    } catch (Exception e) {
      LOG.error("Failed to execute job: {}", e.getMessage());
      throw new JobExecutionException(GenericJobErrorCode.FAILED);
    }
  }

  private WordCounterSettings getSettings() {
    return feedbackSettingsProvider.getSettings(groupId, siteId);
  }
}
```

The job has the parameter _siteId_ which can be used in the implementation
of the job's _call_ method. Note that all parameters that have been passed
must have a corresponding setter method that is annotated as _JsonProperty_
within this class.

For the exception handling, a _JobExecutionException_ is thrown with a generic error code.
Please consult the Studio Developer Manual for more details about error handling
of jobs.  
