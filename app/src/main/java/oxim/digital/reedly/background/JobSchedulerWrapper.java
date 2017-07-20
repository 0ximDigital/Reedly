package oxim.digital.reedly.background;

import android.app.job.JobInfo;

public interface JobSchedulerWrapper {

    int schedule(JobInfo jobInfo);

    void cancel(int jobId);
}
