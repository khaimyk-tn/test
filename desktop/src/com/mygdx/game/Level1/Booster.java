package com.mygdx.game.Level1;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class Booster extends DynamicGameObject {
    public static final int BOOSTER_TYPE_SPEED = 0;
    public static final int BOOSTER_TYPE_PROTECTION = 1;
    public static final float BOOSTER_WIDTH = 0.8f;
    public static final float BOOSTER_HEIGHT = 0.8f;

    int type;

    public Booster(float x, float y, int type) {
        super(x, y, BOOSTER_WIDTH, BOOSTER_HEIGHT);
        this.type = type;
    }

    public TextureRegion getTexture() {
        switch (type) {
            case BOOSTER_TYPE_SPEED:
                return Assets.boosterSpeed;
            case BOOSTER_TYPE_PROTECTION:
                return Assets.booster;
            default:
                throw new IllegalArgumentException("Unknown booster type");
        }
    }

    public int getType() {
        return type;
    }
}
