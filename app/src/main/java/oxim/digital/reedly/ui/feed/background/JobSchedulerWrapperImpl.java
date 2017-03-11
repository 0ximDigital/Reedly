package oxim.digital.reedly.ui.feed.background;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;

public final class JobSchedulerWrapperImpl implements JobSchedulerWrapper {

    private final JobScheduler jobScheduler;

    public JobSchedulerWrapperImpl(final JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    @Override
    public int schedule(final JobInfo jobInfo) {
        return jobScheduler.schedule(jobInfo);
    }

    @Override
    public void cancel(final int jobId) {
        jobScheduler.cancel(jobId);
    }
}
