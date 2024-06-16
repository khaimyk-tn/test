package com.mygdx.game.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LevelSelectionScreen extends ScreenAdapter {
    SuperJumper game;
    OrthographicCamera guiCam;
    Rectangle level1Bounds;
    Rectangle level2Bounds;
    Rectangle level3Bounds;
    Rectangle backBounds;
    Vector3 touchPoint;
    boolean level1Hovered;
    boolean level2Hovered;
    boolean level3Hovered;

    public LevelSelectionScreen(SuperJumper game) {
        this.game = game;

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        backBounds = new Rectangle(0, 0, 64, 64);

        // Встановимо області для кнопок вибору рівня
        level1Bounds = new Rectangle(60, 300, 207, 91);
        level2Bounds = new Rectangle(60, 240 - 18, 187, 80);
        level3Bounds = new Rectangle(160-150,240-82,300,36);

        touchPoint = new Vector3();

    }

    public void update() {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (level1Bounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.dispose();
                Assets.load();
                Assets.playSound(Assets.clickSound);
                game.setScreen(new GameScreen(game, new Level1())); // Перехід на рівень 1
                return;
            }
            if (level2Bounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                Assets.load();
                Assets.updateTexture();
                game.setScreen(new GameScreen(game, new Level2())); // Перехід на рівень 2
                return;
            }
            if (level3Bounds.contains(touchPoint.x,touchPoint.y)){
                Assets.playSound(Assets.clickSound);
                Assets.updateTextureToLevel3();
                game.setScreen(new GameScreen(game, new Level3()));
                return;
            }
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
        // Перевірка наведення на кнопку рівня 1
        guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        level1Hovered = level1Bounds.contains(touchPoint.x, touchPoint.y);
        level2Hovered = level2Bounds.contains(touchPoint.x, touchPoint.y);
        level3Hovered = level3Bounds.contains(touchPoint.x, touchPoint.y);

    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);

        game.batcher.enableBlending();
        game.batcher.begin();
        game.batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);

        // Відображення кнопок вибору рівня
        game.batcher.draw(Assets.chooseLevel,90,400,160,36);

        if (level1Hovered) {
            game.batcher.draw(Assets.level1ButtonHovored, 60, 300, 207, 91); // Збільшена кнопка при наведенні
        } else {
            game.batcher.draw(Assets.level1Button, 60, 300, 207, 91);
        }

        if (level2Hovered){
            game.batcher.draw(Assets.level2ButtonHovered, 60, level2Bounds.y, 196, 98);

        } else {
            game.batcher.draw(Assets.level2Button, 60, level2Bounds.y, 187, 80);

        }

        if (level3Hovered){
            game.batcher.draw(Assets.level3ButtonHovered, 60, level3Bounds.y-25, 198, 99);
        } else {
            game.batcher.draw(Assets.level3Button, 60, level3Bounds.y-25, 198, 99);
        }

        game.batcher.draw(Assets.arrow, 0, 0, 64, 64);

        game.batcher.end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }
}
