package com.mygdx.game.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class WinScreen extends ScreenAdapter {
	SuperJumper game;
	OrthographicCamera cam;
	TextureRegion princess;
	String[] messages = { "This pivo is for you",
			"You diserved it",
			"You are very cool"
			};
	int currentMessage = 0;

	public WinScreen(SuperJumper game) {
		this.game = game;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 320, 480);
		princess = new TextureRegion(Assets.arrow.getTexture(), 210, 122, -40, 38);
	}
	
	@Override
	public void render(float delta) {
		if(Gdx.input.justTouched()) {
			currentMessage++;
			if(currentMessage == messages.length) {
				currentMessage--;
				game.setScreen(new LevelSelectionScreen(game));
			}
		}
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		game.batcher.setProjectionMatrix(cam.combined);
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0);
		Assets.font.setColor(Color.BLACK);
		game.batcher.draw(Assets.pivo, 15, 80, 300, 300);
		Assets.font.draw(game.batcher, messages[currentMessage], 0, 400, 320, Align.center, false);
		Assets.font.setColor(Color.WHITE);
		game.batcher.end();
	}
}
