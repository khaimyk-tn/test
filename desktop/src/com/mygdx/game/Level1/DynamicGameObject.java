
package com.mygdx.game.Level1;

import com.badlogic.gdx.math.Vector2;

public class DynamicGameObject extends GameObject {
	public final Vector2 velocity;
	public final Vector2 accel;

	public DynamicGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.velocity = new Vector2();
		this.accel = new Vector2();
	}

	public void update(float deltaTime) {
		// Оновлюємо швидкість з урахуванням прискорення
		velocity.add(accel.x * deltaTime, accel.y * deltaTime);

		// Оновлюємо позицію з урахуванням швидкості
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);

		// Оновлюємо межі об'єкта (для зіткнень тощо)
		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;
	}
}
