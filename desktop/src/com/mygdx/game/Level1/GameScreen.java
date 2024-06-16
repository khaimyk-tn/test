package com.mygdx.game.Level1;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Level1.World.WorldListener;

import java.util.Iterator;


public class GameScreen extends ScreenAdapter {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	private static final int GAME_WIN = 5;
	private boolean level2Completed = false;
	private boolean level3Completed = false;

	SuperJumper game;

	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	int lastScore;
	String scoreString;
	private Platform platformWithMonster;
	private Texture platformWithMonsterTexture;

	GlyphLayout glyphLayout = new GlyphLayout();

	public GameScreen(SuperJumper game, Level currentLevel) {
		this.game = game;
		dispose();
		state = GAME_READY;
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		touchPoint = new Vector3();
		worldListener = new WorldListener() {
			@Override
			public void jump() {
				Assets.playSound(Assets.jumpSound);
			}

			@Override
			public void highJump() {
				Assets.playSound(Assets.highJumpSound);
			}

			@Override
			public void hit() {
				Assets.playSound(Assets.hitSound);
			}

			@Override
			public void coin() {
				Assets.playSound(Assets.coinSound);
			}



		};
		world = new World(worldListener, currentLevel);
		renderer = new WorldRenderer(game.batcher, world, Assets.backgroundRegion);

		pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
		resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
		quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);
		lastScore = 0;
		scoreString = "SCORE: 0";
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;
		switch (state) {
			case GAME_READY:
				updateReady();
				break;
			case GAME_RUNNING:
				updateRunning(deltaTime);
				break;
			case GAME_PAUSED:
				updatePaused();
				break;
			case GAME_LEVEL_END:
				updateLevelEnd();
				break;
			case GAME_OVER:
				updateGameOver();
				break;
		}
	}

	private void updateReady() {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	private void updateRunning(float deltaTime) {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			Bullet bullet = world.bob.shoot();
			world.bullets.add(bullet);
		}
		ApplicationType appType = Gdx.app.getType();

		if (appType == ApplicationType.Android || appType == ApplicationType.iOS) {
			world.update(deltaTime, Gdx.input.getAccelerometerX());
		} else {
			float accel = 0;
			if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) accel = 5f;
			if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) accel = -5f;
			world.update(deltaTime, accel);
		}

		if (world.score != lastScore) {
			lastScore = world.score;
			scoreString = "SCORE: " + lastScore;
		}
		if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
			state = GAME_LEVEL_END;
		}

		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			if (lastScore >= Settings.highscores[4])
				scoreString = "NEW HIGHSCORE: " + lastScore;
			else
				scoreString = "SCORE: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save();
		}

		for (Platform platform : world.platforms) {
			if (platform.monster != null) {
				if (Intersector.overlaps(world.bob.bounds, platform.monster.bounds)) {
					world.bob.state = Bob.BOB_STATE_HIT;
					world.bob.stateTime = 0;
					break;
				}
			}
		}
	}

	private void updatePaused() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}

			if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}

	private void updateLevelEnd() {

			if (Gdx.input.justTouched()) {
				if (!level2Completed) {
					Assets.updateTexture();
					Level currentLevel = new Level2(); // Створіть новий рівень 2
					startNewLevel(currentLevel, Assets.backgroundLevel1Region);// Оновіть рендерер з новим фоном
					world.score = lastScore;
					state = GAME_READY;
					level2Completed = true; // Позначте, що другий рівень пройдено
				} else if (!level3Completed){
					Assets.updateTextureToLevel3();
					Level currentLevel = new Level3(); // Створіть новий рівень 3
					startNewLevel(currentLevel, Assets.backgroundLevel1Region);// Оновіть рендерер з новим фоном
					world.score = lastScore;
					state = GAME_READY;
					level3Completed = true;
				} else {
					state = GAME_WIN;
				}

		}

	}

	private void startNewLevel(Level level, TextureRegion backgroundRegion) {
		world = new World(worldListener, level);
		renderer = new WorldRenderer(game.batcher, world, backgroundRegion);

	}

	private void updateGameOver() {
		if (Gdx.input.justTouched()) {
			game.setScreen(new MainMenuScreen(game));
            Assets.music.pause();
			Assets.dispose();
			Assets.load();
		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();

		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();
		switch (state) {
			case GAME_READY:
				presentReady();
				break;
			case GAME_RUNNING:
				presentRunning();
				break;
			case GAME_PAUSED:
				presentPaused();
				break;
			case GAME_LEVEL_END:
				if (!level3Completed && !level2Completed) {
					state = GAME_WIN; // Якщо другий рівень пройдено, перехід на екран виграшу
				} else {
					presentLevelEnd(); // Інакше продовжуйте відображати екран завершення рівня
				}
				break;
			case GAME_OVER:
				presentGameOver();
				break;
			case GAME_WIN:
					presentGameWin();
				break;
		}
		game.batcher.end();
	}

	private void presentGameWin() {
		game.setScreen(new WinScreen(game));
		Assets.dispose();
		Assets.load();
	}

	private void presentReady() {
		game.batcher.draw(Assets.ready, 160 - 192 / 2, 240 - 32 / 2, 192, 32);
	}

	private void presentRunning() {
		game.batcher.draw(Assets.pause, 320 - 64, 480 - 64, 64, 64);
		Assets.font.draw(game.batcher, scoreString, 16, 480 - 20);
	}

	private void presentPaused() {
		game.batcher.draw(Assets.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
		Assets.font.draw(game.batcher, scoreString, 16, 480 - 20);
	}

	private void presentLevelEnd() {
		glyphLayout.setText(Assets.font, "go hahol ...");
		Assets.font.draw(game.batcher, glyphLayout, 160 - glyphLayout.width / 2, 480 - 40);
		glyphLayout.setText(Assets.font, "to the next level!");
		Assets.font.draw(game.batcher, glyphLayout, 160 - glyphLayout.width / 2, 40);
	}

	private void presentGameOver() {
		game.batcher.draw(Assets.gameOver, 160 - 160 / 2, 240 - 96 / 2, 160, 96);
		glyphLayout.setText(Assets.font, scoreString);
		Assets.font.draw(game.batcher, scoreString, 160 - glyphLayout.width / 2, 480 - 20);
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	@Override
	public void pause() {
		if (state == GAME_RUNNING) state = GAME_PAUSED;
	}
}
