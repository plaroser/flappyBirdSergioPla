package com.sergio.pla.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sergio.pla.game.SergioPlaGame;
import com.sergio.pla.utils.GameCallBack;

public class DesktopLauncher implements GameCallBack {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Game Sergio";
		config.width = 272*2;
		config.height = 408*2;
		new LwjglApplication(new SergioPlaGame(new DesktopLauncher()), config);
	}

	@Override
	public void callBack() {

	}

	@Override
	public boolean isSound() {
		return false;
	}
}
