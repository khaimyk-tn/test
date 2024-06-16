package com.mygdx.game.Level1;

public class Monster extends DynamicGameObject {
    public static final float MONSTER_WIDTH = 1.0f;
    public static final float MONSTER_HEIGHT = 1.0f;

    public Monster(float x, float y) {
        super(x, y, MONSTER_WIDTH, MONSTER_HEIGHT);
    }

    public void update(float deltaTime) {
        // Оновлення логіки монстра
        bounds.setPosition(position.x, position.y);
    }

    public boolean checkBulletCollision(Bullet bullet) {
        // Перевірка колізій з снарядом
        return bullet.bounds.overlaps(bounds);
    }
}
