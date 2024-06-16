package com.mygdx.game.Level1;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;

public abstract class Level {
    protected List<Platform> platforms;
    protected List<Spring> springs;
    protected List<Squirrel> squirrels;
    protected List<Coin> coins;
    protected List<Obstacle> obstacles; // Додайте список перешкод
    protected List<Booster> boosters;
    protected Castle castle;
    protected final Random rand;
    protected final Vector2 gravity;

    public Level() {
        this.platforms = new ArrayList<>();
        this.springs = new ArrayList<>();
        this.squirrels = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.obstacles = new ArrayList<>(); // Ініціалізуйте список перешкод
        this.boosters = new ArrayList<>();
        this.rand = new Random();
        this.gravity = new Vector2(0, -12); // Можна змінити для кожного рівня
    }

    public abstract void generateLevel();

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public List<Spring> getSprings() {
        return springs;
    }

    public List<Squirrel> getSquirrels() {
        return squirrels;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public List<Obstacle> getObstacles() { // Додайте гетер для перешкод
        return obstacles;
    }

    public Castle getCastle() {
        return castle;
    }

    public List <Booster> getBoosters (){
        return boosters;
    }
}
