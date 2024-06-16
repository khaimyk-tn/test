
package com.mygdx.game.Level1;

import java.util.Random;

public class Squirrel extends DynamicGameObject {
	public static final float SQUIRREL_WIDTH = 1;
	public static final float SQUIRREL_HEIGHT = 0.6f;
	public static final float SQUIRREL_VELOCITY = 3f;

	private static final Animation[] SKINS = {Assets.squirrelFly, Assets.squirrelFlySlovakia, Assets.squirrelFlyBelgia};
	private static final Random rand = new Random();

	float stateTime = 0;
	public Animation skin;

	public Squirrel(float x, float y) {
		super(x, y, SQUIRREL_WIDTH, SQUIRREL_HEIGHT);
		velocity.set(SQUIRREL_VELOCITY, 0);
		skin = SKINS[rand.nextInt(SKINS.length)];
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - SQUIRREL_WIDTH / 2;
		bounds.y = position.y - SQUIRREL_HEIGHT / 2;

		if (position.x < SQUIRREL_WIDTH / 2) {
			position.x = SQUIRREL_WIDTH / 2;
			velocity.x = SQUIRREL_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - SQUIRREL_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - SQUIRREL_WIDTH / 2;
			velocity.x = -SQUIRREL_VELOCITY;
		}
		stateTime += deltaTime;
	}
}
