package oxim.digital.reedly.ui.feed.background;

import android.app.job.JobInfo;

public interface JobSchedulerWrapper {

    int schedule(JobInfo jobInfo);

    void cancel(int jobId);
}
