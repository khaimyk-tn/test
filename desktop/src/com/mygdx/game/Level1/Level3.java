package com.mygdx.game.Level1;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.game.Level1.World.WORLD_HEIGHT;
public class Level3 extends Level {
    public static Texture background;
    public static TextureRegion backgroundRegion;

    @Override
    public void generateLevel() {
        //sprites
        float y = Platform.PLATFORM_HEIGHT / 2;
        float maxJumpHeight = Bob.BOB_JUMP_VELOCITY * Bob.BOB_JUMP_VELOCITY / (2 * -gravity.y);
        float castleHeight = WORLD_HEIGHT - 250; // Зміна висоти для генерації замку
        while (y < castleHeight) {
            int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
            float x = rand.nextFloat() * (World.WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            Platform platform = new Platform(type, x, y);
            platforms.add(platform);

            if (rand.nextFloat() > 0.9f && type != Platform.PLATFORM_TYPE_MOVING) {
                Spring spring = new Spring(platform.position.x, platform.position.y + Platform.PLATFORM_HEIGHT / 2
                        + Spring.SPRING_HEIGHT / 2);
                springs.add(spring);
            }

            if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
                Squirrel squirrel = new Squirrel(platform.position.x + rand.nextFloat(), platform.position.y
                        + Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() * 2);
                squirrels.add(squirrel);
            }

            if (rand.nextFloat() > 0.6f) {
                Coin coin = new Coin(platform.position.x + rand.nextFloat(), platform.position.y + Coin.COIN_HEIGHT
                        + rand.nextFloat() * 3);
                coins.add(coin);
            }

            // Додавання перешкод з перевіркою перетинів
            if (rand.nextFloat() > 0.9f) {
                float obstacleX = platform.position.x + rand.nextFloat();
                float obstacleY = platform.position.y + Obstacle.OBSTACLE_HEIGHT + rand.nextFloat() * 3;

                Obstacle newObstacle = new Obstacle(obstacleX, obstacleY);

                if (!isOverlapping(newObstacle)) {
                    obstacles.add(newObstacle);
                }
            }

            // Додавання бустерів
            if (rand.nextFloat() > 0.9f) {
                float boosterX = platform.position.x + rand.nextFloat();
                float boosterY = platform.position.y + Booster.BOOSTER_HEIGHT + rand.nextFloat() * 3;
                int boosterType = rand.nextFloat() > 0.5f ? Booster.BOOSTER_TYPE_SPEED : Booster.BOOSTER_TYPE_PROTECTION;

                Booster newBooster = new Booster(boosterX, boosterY, boosterType);
                if (!isOverlappingForBooster(newBooster)) {
                    boosters.add(newBooster);
                }
            }

            // Додавання монстра на платформу з певною ймовірністю
            if (type != Platform.PLATFORM_TYPE_MOVING) {
                if ( y > WORLD_HEIGHT - (15*19.5) && rand.nextFloat() > 0.6f) {
                    Monster monster = new Monster(platform.position.x, platform.position.y + Platform.PLATFORM_HEIGHT);

                    if (!isOverlappingWithSprings(monster)) {
                        platform.setMonster(monster);

                        // Додавання ще однієї платформи поруч із монстром
                        float additionalPlatformX = platform.position.x + Platform.PLATFORM_WIDTH + rand.nextFloat() * 2;
                        if (additionalPlatformX > World.WORLD_WIDTH - Platform.PLATFORM_WIDTH / 2) {
                            additionalPlatformX = platform.position.x - Platform.PLATFORM_WIDTH - rand.nextFloat() * 2;
                        }

                        Platform additionalPlatform = new Platform(Platform.PLATFORM_TYPE_STATIC, additionalPlatformX, platform.position.y);
                        platforms.add(additionalPlatform);
                    }
                }
            }

            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight / 3);
        }

        castle = new Castle(World.WORLD_WIDTH / 2, y);
    }

    // Метод для перевірки перетинів перешкод
    private boolean isOverlapping(Obstacle newObstacle) {
        for (Platform platform : platforms) {
            if (newObstacle.bounds.overlaps(platform.bounds)) {
                return true;
            }
        }
        for (Coin coin : coins) {
            if (newObstacle.bounds.overlaps(coin.bounds)) {
                return true;
            }
        }
        for (Spring spring : springs) {
            if (newObstacle.bounds.overlaps(spring.bounds)) {
                return true;
            }
        }
        for (Squirrel squirrel : squirrels) {
            if (newObstacle.bounds.overlaps(squirrel.bounds)) {
                return true;
            }
        }
        for (Obstacle obstacle : obstacles) {
            if (newObstacle.bounds.overlaps(obstacle.bounds)) {
                return true;
            }
        }
        return false;
    }

    // Метод для перевірки перетинів бустерів
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

    // Метод для перевірки перетинів монстрів з пружинами
    private boolean isOverlappingWithSprings(Monster monster) {
        for (Spring spring : springs) {
            if (monster.bounds.overlaps(spring.bounds)) {
                return true;
            }
        }
        return false;
    }
}
