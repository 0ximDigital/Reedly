package oxim.digital.reedly.ui.router;

public interface Router {

    void closeScreen();

    void goBack();

    void showUserSubscriptionsScreen();

    void showFeedItemsScreen(int feedId);

    void showFeedItemContentScreen(String contentUrl);

    void showAddNewFeedScreen();

}
