package com.mygdx.game.Level1;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle {
    public static final float OBSTACLE_WIDTH = 1;
    public static final float OBSTACLE_HEIGHT = 1;

    public final Vector2 position;
    public final Rectangle bounds;

    public Obstacle(float x, float y) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x - OBSTACLE_WIDTH / 2, y - OBSTACLE_HEIGHT / 2, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
    }

    public void update(float deltaTime) {
        // Логіка оновлення перешкоди, якщо є
    }
}

