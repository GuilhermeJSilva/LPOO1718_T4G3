package com.lift.game.desktop;

import com.lift.game.PlayInterface;

public class NullServices implements PlayInterface {
    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public void onShowLeaderboardsRequested() {

    }

    @Override
    public void updateLeaderboards(int finalScore) {

    }
}
