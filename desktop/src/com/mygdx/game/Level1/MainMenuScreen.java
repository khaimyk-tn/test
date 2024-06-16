package com.mygdx.game.Level1;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends ScreenAdapter {
	SuperJumper game;
	OrthographicCamera guiCam;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
	Vector3 touchPoint;


	public MainMenuScreen(SuperJumper game) {
		this.game = game;

		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		soundBounds = new Rectangle(0, 0, 64, 64);
		playBounds = new Rectangle(160 - 150, 240 + 18, 300, 36);
		highscoresBounds = new Rectangle(160 - 150, 240 - 18, 300, 36);
		helpBounds = new Rectangle(160 - 155, 240 - 18 - 36, 300, 36);
		touchPoint = new Vector3();
		if (Settings.soundEnabled) {
			Assets.music.setLooping(true);
			Assets.music.play();
		}
	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new LevelSelectionScreen(game));
				return;
			}
			if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HighscoresScreen(game));
				return;
			}
			if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HelpScreen(game));
				return;
			}
			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
			}
		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.logo, 0, 480 - 10 - 142, 348, 128);
		game.batcher.draw(Assets.mainMenu, 40, 240 - 180 / 2, 300, 200);
		game.batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	@Override
	public void pause() {
		Settings.save();
	}
}
