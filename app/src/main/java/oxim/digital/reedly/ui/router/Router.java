package oxim.digital.reedly.ui.router;

public interface Router {

    void closeScreen();

    void goBack();

    void showUserSubscriptionsScreen();

    void showFavouriteFeedItemsScreen();

    void showFeedItemsScreen(int feedId, String feedTitle);

    void showFeedItemContentScreen(String contentUrl);

    void showAddNewFeedScreen();

    void hideAddNewFeedScreen();

}
