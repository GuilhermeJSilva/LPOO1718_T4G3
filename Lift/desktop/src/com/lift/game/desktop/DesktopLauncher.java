package com.lift.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.lift.game.LiftGame;
import com.lift.game.playinterface.NullServices;

public class DesktopLauncher{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		int grade = 50;
		config.resizable = false;
		config.height = 16 * grade;
		config.width = 9 * grade;
		new LwjglApplication(new LiftGame(new NullServices()), config);
	}
}
