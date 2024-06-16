package com.mygdx.game.Level1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;

public class World {
	public static final Vector2 gravity = new Vector2(0, -12);
	public List<Platform> platforms;
	public List<Monster> monsters;



	public interface WorldListener {
		public void jump();
		public void highJump();
		public void hit();
		public void coin();

	}

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 20;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;

	public final Bob bob;
	public final List<Spring> springs;
	public final List<Squirrel> squirrels;
	public final List<Coin> coins;
	public final List<Obstacle> obstacles; // Додайте список перешкод
	public final List<Booster> boosters;
	public final List<Bullet> bullets;


	public Castle castle;
	public final WorldListener listener;
	public final Random rand;

	public float heightSoFar;
	public int score;
	public int state;

	private boolean isPlayerBoosted;


	public World(WorldListener listener, Level level) {
		this.bob = new Bob(5, 1);
		this.listener = listener;
		this.rand = new Random();
		monsters = new ArrayList<>();
		level.generateLevel();
		this.platforms = level.getPlatforms();
		this.springs = level.getSprings();
		this.squirrels = level.getSquirrels();
		this.boosters = level.getBoosters();
		this.coins = level.getCoins();
		this.castle = level.getCastle();
		this.obstacles = level.getObstacles(); // Отримайте перешкоди з рівня
		this.bullets = new ArrayList<>();
		this.heightSoFar = 0;
		this.score = 0;

		this.state = WORLD_STATE_RUNNING;
	}

	public void update(float deltaTime, float accelX) {
		updateBob(deltaTime, accelX);
		updatePlatforms(deltaTime);
		updateSquirrels(deltaTime);
		updateCoins(deltaTime);
		updateBoosters(deltaTime); // Оновлення бустерів
		checkMonsterCollisions();
		updateBullets(deltaTime);
		checkBulletCollisions();
		updateObstacles(deltaTime); // Оновлення перешкод

		if (bob.state != Bob.BOB_STATE_HIT) checkCollisions();
		checkGameOver();

		for (Platform platform : platforms) {
			if (platform.monster != null) {
				if (isPlayerBoosted) {
					for (Monster monster : monsters) {
						if (bob.bounds.overlaps(monster.bounds)) {
							// Захист від монстрів при активному бустері
							isPlayerBoosted = false;
							platform.monster = null; // Видалення монстра
							break;
						}
					}
				} else {
					for (Monster monster : monsters) {
						if (bob.bounds.overlaps(monster.bounds)) {
							// Гравець помирає або інша логіка
							bob.state = Bob.BOB_STATE_HIT;
							bob.stateTime = 0;
							break;
						}
					}
				}
			}
		}
	}
	private void updateBullets(float deltaTime) {
		Iterator<Bullet> iter = bullets.iterator();
		while (iter.hasNext()) {
			Bullet bullet = iter.next();
			bullet.update(deltaTime);
			if (bullet.position.x < 0 || bullet.position.x > WORLD_WIDTH) {
				iter.remove();
			}
		}
	}

	private void checkBulletCollisions() {
		List<Bullet> bulletsToRemove = new ArrayList<>();

		for (Bullet bullet : bullets) {
			for (Platform platform : platforms) {
				if (platform.monster != null && bullet.bounds.overlaps(platform.monster.bounds)) {
					platform.monster = null; // Видаляємо монстра з платформи
					bulletsToRemove.add(bullet); // Позначаємо постріл для видалення
					break; // Переходимо до наступного пострілу
				}
			}
		}

		// Видаляємо всі позначені постріли
		bullets.removeAll(bulletsToRemove);
	}





	private void updateObstacles(float deltaTime) {
		for (Obstacle obstacle : obstacles) {
			obstacle.update(deltaTime);
		}
	}
	private void checkMonsterCollisions() {
		Iterator<Monster> iterator = monsters.iterator();
		while (iterator.hasNext()) {
			Monster monster = iterator.next();
			if (bob.bounds.overlaps(monster.bounds)) {
				if (isPlayerBoosted) {
					bob.state = Bob.BOB_STATE_JUMP;
					iterator.remove(); // Видаляємо монстра, якщо гравець був у бустері
				} else {
					iterator.remove(); // Видаляємо монстра, якщо гравець не був у бустері
					bob.state = Bob.BOB_STATE_HIT;
					bob.stateTime = 0;
					break;
				}
			}
		}
	}


	private void updateBoosters(float deltaTime) {
		int len = boosters.size();
		for (int i = 0; i < len; i++) {
			Booster booster = boosters.get(i);
			booster.update(deltaTime);
		}
	}


	private void updateBob(float deltaTime, float accelX) {
		if (bob.state != Bob.BOB_STATE_HIT && bob.position.y <= 0.5f) bob.hitPlatform();
		if (bob.state != Bob.BOB_STATE_HIT) bob.velocity.x = -accelX / 10 * Bob.BOB_MOVE_VELOCITY;
		bob.update(deltaTime);
		heightSoFar = Math.max(bob.position.y, heightSoFar);
	}

	private void updatePlatforms(float deltaTime) {
		int len = platforms.size();
		for (int i = 0; i < len; i++) {
			Platform platform = platforms.get(i);
			platform.update(deltaTime);
			if (platform.state == Platform.PLATFORM_STATE_PULVERIZING && platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
				platforms.remove(platform);
				len = platforms.size();
			}
		}
	}

	private void updateSquirrels(float deltaTime) {
		int len = squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = squirrels.get(i);
			squirrel.update(deltaTime);
		}
	}

	private void updateCoins(float deltaTime) {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
	}


	private void checkCollisions() {
		checkSquirrelCollisions();
		checkItemCollisions();
		checkCastleCollisions();
		checkPlatformCollisions();
		checkBoosterCollisions(); // Перевірка зіткнень з бустерами
		checkObstacleCollisions(); // Перевірка зіткнень з перешкодами
	}

	private void checkObstacleCollisions() {
		for (Obstacle obstacle : obstacles) {
			if (bob.bounds.overlaps(obstacle.bounds)) {
				bob.state = Bob.BOB_STATE_HIT;
				bob.stateTime = 0;
				break;
			}
		}
	}
	private void checkBoosterCollisions() {
		for (Booster booster : boosters) {
			if (bob.bounds.overlaps(booster.bounds)) {
				if (booster.getType() == Booster.BOOSTER_TYPE_SPEED) {
					bob.activateSpeedBooster();
				} else if (booster.getType() == Booster.BOOSTER_TYPE_PROTECTION) {
					bob.activateProtectionBooster();
				}
				boosters.remove(booster);
				break;
			}
		}
	}
	private void checkPlatformCollisions() {
		if (bob.velocity.y > 0) return;

		int len = platforms.size();
		for (int i = 0; i < len; i++) {
			Platform platform = platforms.get(i);
			if (bob.position.y > platform.position.y) {
				if (bob.bounds.overlaps(platform.bounds)) {
					bob.hitPlatform();
					listener.jump();
					if (rand.nextFloat() > 0.5f) {
						platform.pulverize();
					}
					break;
				}
			}
		}
	}

	private void checkSquirrelCollisions() {
		int len = squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = squirrels.get(i);
			if (squirrel.bounds.overlaps(bob.bounds)) {
				bob.hitSquirrel();
				listener.hit();
			}
		}
	}

	private void checkItemCollisions() {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			if (bob.bounds.overlaps(coin.bounds)) {
				coins.remove(coin);
				len = coins.size();
				listener.coin();
				score += Coin.COIN_SCORE;
			}
		}


		len = springs.size();
		for (int i = 0; i < len; i++) {
			Spring spring = springs.get(i);
			if (bob.position.y > spring.position.y) {
				if (bob.bounds.overlaps(spring.bounds)) {
					bob.hitSpring();
					listener.highJump();
				}
			}
		}
	}


	private void checkCastleCollisions() {
		if (castle.bounds.overlaps(bob.bounds)) {
			state = WORLD_STATE_NEXT_LEVEL;
		}
	}

	private void checkGameOver() {
		if (heightSoFar - 7.5f > bob.position.y) {
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
