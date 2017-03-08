package oxim.digital.reedly.domain.interactor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import oxim.digital.reedly.domain.interactor.type.SingleUseCase;
import rx.Completable;
import rx.Single;

public final class GetUserSubscribedFeedsUseCase implements SingleUseCase<List<String>> {

    private static final long TASK_DELAY = 3000;

    @Override
    public Single<List<String>> execute() {
        return Completable.timer(TASK_DELAY, TimeUnit.MILLISECONDS)
                          .toSingle(() -> Arrays.asList("FirstFeed", "SecondFeed", "ThirdFeed"));
    }
}
