package com.mygdx.game.Level1;

import static com.mygdx.game.Level1.World.WORLD_HEIGHT;
import static com.mygdx.game.Level1.World.WORLD_WIDTH;

public class Level1 extends Level {
    @Override
    public void generateLevel() {
        Assets.dispose();
        Assets.load();
        float y = Platform.PLATFORM_HEIGHT / 2;
        float maxJumpHeight = Bob.BOB_JUMP_VELOCITY * Bob.BOB_JUMP_VELOCITY / (2 * -gravity.y);
        float castleHeight = WORLD_HEIGHT - 5; // Зміна висоти для генерації замку
        while (y < castleHeight) {
            int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
            float x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            Platform platform = new Platform(type, x, y);
            platforms.add(platform);

            if (rand.nextFloat() > 0.9f && type != Platform.PLATFORM_TYPE_MOVING) {
                Spring spring = new Spring(platform.position.x, platform.position.y + Platform.PLATFORM_HEIGHT / 2
                        + Spring.SPRING_HEIGHT / 2);
                springs.add(spring);
            }

            if (y > WORLD_HEIGHT / 5 && rand.nextFloat() > 0.6f) {
                Squirrel squirrel = new Squirrel(platform.position.x + rand.nextFloat(), platform.position.y
                        + Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() * 2);
                squirrels.add(squirrel);
            }


            if (rand.nextFloat() > 0.6f) {
                Coin coin = new Coin(platform.position.x + rand.nextFloat(), platform.position.y + Coin.COIN_HEIGHT
                        + rand.nextFloat() * 3);
                coins.add(coin);
            }
            if (rand.nextFloat() > 0.9f) {
                float boosterX = platform.position.x + rand.nextFloat();
                float boosterY = platform.position.y + Booster.BOOSTER_HEIGHT + rand.nextFloat() * 3;
                int boosterType = rand.nextFloat() > 0.5f ? Booster.BOOSTER_TYPE_SPEED : Booster.BOOSTER_TYPE_PROTECTION;

                Booster newBooster = new Booster(boosterX, boosterY, boosterType);
                if (!isOverlappingForBooster(newBooster)) {
                    boosters.add(newBooster);
                }
            }


            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight / 3);
        }

        castle = new Castle(WORLD_WIDTH / 2, y);
    }

    private boolean isOverlappingForBooster(Booster newBooster) {
        for (Platform platform : platforms) {
            if (newBooster.bounds.overlaps(platform.bounds)) {
                return true;
            }
        }
        for (Coin coin : coins) {
            if (newBooster.bounds.overlaps(coin.bounds)) {
                return true;
            }
        }
        for (Spring spring : springs) {
            if (newBooster.bounds.overlaps(spring.bounds)) {
                return true;
            }
        }
        for (Squirrel squirrel : squirrels) {
            if (newBooster.bounds.overlaps(squirrel.bounds)) {
                return true;
            }
        }
        for (Obstacle obstacle : obstacles) {
            if (newBooster.bounds.overlaps(obstacle.bounds)) {
                return true;
            }
        }
        for (Booster booster : boosters) {
            if (newBooster.bounds.overlaps(booster.bounds)) {
                return true;
            }
        }
        return false;
    }
}
