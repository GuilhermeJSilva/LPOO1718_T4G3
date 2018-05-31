package com.lift.game;

public interface PlayInterface {

    void signIn();
    void signOut();
    boolean isSignedIn();
    void onShowLeaderboardsRequested();
    void updateLeaderboards(int finalScore);
}
