
package com.mygdx.game.Level1;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	private final TextureRegion backgroundRegion;
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;

	public WorldRenderer(SpriteBatch batch, World world, TextureRegion backgroundRegion) {
		this.world = world;
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		this.batch = batch;
		this.backgroundRegion = backgroundRegion;
	}

	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(backgroundRegion, 0, 0, World.WORLD_WIDTH, World.WORLD_HEIGHT);
		batch.end();

		if (world.bob.position.y > cam.position.y) cam.position.y = world.bob.position.y;
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		renderBackgroundLevel1();
		renderObjects();

		batch.begin();
		batch.end();
	}


	public void renderBackgroundLevel1() {
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.backgroundLevel1Region, cam.position.x - FRUSTUM_WIDTH / 2, cam.position.y - FRUSTUM_HEIGHT / 2, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT);
		batch.end();
	}

	public void renderObjects() {
		batch.enableBlending();
		batch.begin();
		renderBob();
		renderPlatforms();
		renderItems();
		renderSquirrels();
		renderCastle();
		renderBoosters(); // Виклик методу рендерингу бустерів
		renderBullets(); // Відображення куль

		renderObstacles(); // Виклик методу рендерингу перешкод
		batch.end();
	}

	private void renderBob() {
		TextureRegion keyFrame;
		if (world.bob.isProtectionBoosted()) {
			switch (world.bob.state) {
				case Bob.BOB_STATE_FALL:
					keyFrame = Assets.bobFallProtection.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
					break;
				case Bob.BOB_STATE_JUMP:
					keyFrame = Assets.bobJumpProtected.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
					break;
				case Bob.BOB_STATE_HIT:
				default:
					keyFrame = Assets.bobHit;
			}
		} else {
			switch (world.bob.state) {
				case Bob.BOB_STATE_FALL:
					keyFrame = Assets.bobFall.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
					break;
				case Bob.BOB_STATE_JUMP:
					keyFrame = Assets.bobJump.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
					break;
				case Bob.BOB_STATE_HIT:
				default:
					keyFrame = Assets.bobHit;
			}
		}

		float side = world.bob.velocity.x < 0 ? -1 : 1;
		if (side < 0)
			batch.draw(keyFrame, world.bob.position.x + 0.5f, world.bob.position.y - 0.5f, side * 1, 1);
		else
			batch.draw(keyFrame, world.bob.position.x - 0.5f, world.bob.position.y - 0.5f, side * 1, 1);
	}
	private void renderBullets() {
		for (Bullet bullet : world.bullets) {
			batch.draw(Assets.bullet, bullet.position.x, bullet.position.y, Bullet.BULLET_WIDTH, Bullet.BULLET_HEIGHT);
		}
	}
	private void renderPlatforms() {
		int len = world.platforms.size();
		for (int i = 0; i < len; i++) {
			Platform platform = world.platforms.get(i);
			TextureRegion keyFrame = Assets.platform;
			if (platform.state == Platform.PLATFORM_STATE_PULVERIZING) {
				keyFrame = Assets.brakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_NONLOOPING);
			}

			batch.draw(keyFrame, platform.position.x - 1, platform.position.y - 0.25f, 2, 0.5f);
			if (platform.monster != null) {
				batch.draw(Assets.monster, platform.monster.position.x - Monster.MONSTER_WIDTH / 2, platform.monster.position.y - Monster.MONSTER_HEIGHT / 2, Monster.MONSTER_WIDTH, Monster.MONSTER_HEIGHT);
			}
		}
	}
	private void renderObstacles() {
		for (Obstacle obstacle : world.obstacles) {
			batch.draw(Assets.obstacle, obstacle.position.x - 0.5f, obstacle.position.y - 0.5f, 2, 2);
		}
	}
	private void renderItems() {
		int len = world.springs.size();
		for (int i = 0; i < len; i++) {
			Spring spring = world.springs.get(i);
			batch.draw(Assets.spring, spring.position.x - 0.5f, spring.position.y - 0.5f, 1, 1);
		}

		len = world.coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = world.coins.get(i);
			TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
			batch.draw(keyFrame, coin.position.x - 0.5f, coin.position.y - 0.5f, 1, 1);
		}

	}

	private void renderSquirrels() {
		int len = world.squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = world.squirrels.get(i);
			TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(squirrel.stateTime, Animation.ANIMATION_LOOPING);
			float side = squirrel.velocity.x < 0 ? -1 : 1;
			if (side < 0)
				batch.draw(keyFrame, squirrel.position.x + 0.5f, squirrel.position.y - 0.5f, side * 2, 2);
			else
				batch.draw(keyFrame, squirrel.position.x - 0.5f, squirrel.position.y - 0.5f, side * 2, 2);
		}
	}
	private void renderBoosters() {
		for (Booster booster : world.boosters) {
			TextureRegion keyFrame = booster.getTexture(); // Використання методу getTexture() для отримання правильної текстури
			batch.draw(keyFrame, booster.position.x - 0.5f, booster.position.y - 0.5f, 1, 1);
		}
	}

	private void renderCastle() {
		Castle castle = world.castle;
		batch.draw(Assets.castle, castle.position.x - 1, castle.position.y - 1, 3, 3);
	}


}
