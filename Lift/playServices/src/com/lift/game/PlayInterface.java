public interface PlayInterface {

    void signIn();
    void signOut();
    boolean isSignedIn();
    void onShowLeaderboardsRequest();
    void updateLeaderboards(int finalScore);
}
