package com.mygdx.game.Level1;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    public static final float BULLET_WIDTH = 0.2f;
    public static final float BULLET_HEIGHT = 0.2f;
    public static final float BULLET_VELOCITY = 10f;
    Vector2 position;
    Vector2 velocity;
    Rectangle bounds;

    public Bullet(float x, float y, float direction) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(direction * BULLET_VELOCITY, 0);
        this.bounds = new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT);
    }

    public void update(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x;
        bounds.y = position.y;
    }
}
