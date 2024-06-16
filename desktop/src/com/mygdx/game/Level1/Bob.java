
package com.mygdx.game.Level1;

import com.badlogic.gdx.utils.TimeUtils;

public class Bob extends DynamicGameObject {
	public static final int BOB_STATE_JUMP = 0;
	public static final int BOB_STATE_FALL = 1;
	public static final int BOB_STATE_HIT = 2;
	public static final float BOB_JUMP_VELOCITY = 11;
	public static final float BOB_MOVE_VELOCITY = 20;
	public static final float BOB_WIDTH = 0.8f;
	public static final float BOB_HEIGHT = 0.8f;
	public static final float BOOSTER_DURATION = 10.0f; // Тривалість бустера у секундах
	public static final float BOOSTED_MOVE_VELOCITY = 25;

	public int state;
	float stateTime;
	private boolean isSpeedBoosted;
	private boolean isProtectionBoosted;
	private float speedBoosterTimeLeft;
	private float protectionBoosterTimeLeft;

	public Bob(float x, float y) {
		super(x, y, BOB_WIDTH, BOB_HEIGHT);
		state = BOB_STATE_FALL;
		stateTime = 0;
		isSpeedBoosted = false;
		isProtectionBoosted = false;
	}

	public void activateSpeedBooster() {
		isSpeedBoosted = true;
		speedBoosterTimeLeft = BOOSTER_DURATION;
		velocity.x = BOB_MOVE_VELOCITY * (BOOSTED_MOVE_VELOCITY / BOB_MOVE_VELOCITY);
	}

	public void activateProtectionBooster() {
		isProtectionBoosted = true;
		protectionBoosterTimeLeft = BOOSTER_DURATION;
	}

	public void update(float deltaTime) {
		// Оновлення залишку часу бустера швидкості
		if (isSpeedBoosted) {
			speedBoosterTimeLeft -= deltaTime;
			if (speedBoosterTimeLeft <= 0) {
				isSpeedBoosted = false;
				velocity.x = BOB_MOVE_VELOCITY;
			}
		}

		// Оновлення залишку часу бустера захисту
		if (isProtectionBoosted) {
			protectionBoosterTimeLeft -= deltaTime;
			if (protectionBoosterTimeLeft <= 0) {
				isProtectionBoosted = false;
			}
		}

		// Оновлення позиції та стану гравця
		velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;

		// Перевірка стану гравця
		if (velocity.y > 0 && state != BOB_STATE_HIT) {
			if (state != BOB_STATE_JUMP) {
				state = BOB_STATE_JUMP;
				stateTime = 0;
			}
		}

		if (velocity.y < 0 && state != BOB_STATE_HIT) {
			if (state != BOB_STATE_FALL) {
				state = BOB_STATE_FALL;
				stateTime = 0;
			}
		}

		// Контроль виходу за межі горизонталі
		if (position.x < 0) position.x = World.WORLD_WIDTH;
		if (position.x > World.WORLD_WIDTH) position.x = 0;

		stateTime += deltaTime; // Оновлення часу стану гравця
	}

	public void hitPlatform() {
		velocity.y = isSpeedBoosted ? BOB_JUMP_VELOCITY * 1.5f : BOB_JUMP_VELOCITY;
		state = BOB_STATE_JUMP;
		stateTime = 0;
	}

	public void hitSpring() {
		velocity.y = isSpeedBoosted ? BOB_JUMP_VELOCITY * 2.25f : BOB_JUMP_VELOCITY * 1.5f;
		state = BOB_STATE_JUMP;
		stateTime = 0;
	}

	public void hitSquirrel() {
		if (!isProtectionBoosted) {
			state = BOB_STATE_HIT;
			stateTime = 0;
			velocity.set(0, 0);
		}
	}
	public Bullet shoot() {
		float direction = velocity.x < 0 ? -1 : 1;
		return new Bullet(position.x, position.y, direction);
	}
	public boolean isProtectionBoosted() {
		return isProtectionBoosted;
	}
}
