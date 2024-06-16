/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mygdx.game.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;
	public static Texture backgroundLevel1;
	public static Texture pivo;
	public static TextureRegion backgroundLevel1Region;
	public static Texture items;
	public static Texture level2items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion spring;
	public static TextureRegion castle;
	public static Animation coinAnim;
	public static Animation bobJump;
	public static Animation bobFall;
	public static TextureRegion bobHit;
	public static Animation squirrelFly;
	public static Animation squirrelFlySlovakia;
	public static Animation squirrelFlyBelgia;
	public static TextureRegion platform;
	public static TextureRegion chooseLevel;
	public static Animation brakingPlatform;
	public static TextureRegion monster;
	public static BitmapFont font;

	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	public static TextureRegion platformMoving;
	public static TextureRegion booster;
	public static TextureRegion boosterSpeed;
	public static TextureRegion playButtonPressed;
	public  static  TextureRegion level1Button;
	public  static  TextureRegion highScoresRegionStage;
	public  static  TextureRegion level2Button;
	public static Texture pivo_back;
	public static Texture level3;
	public  static  TextureRegion obstacle;
	public static TextureRegion bullet;

public static TextureRegion playButton;
	public static TextureRegion level3Button;
	public static Animation bobJumpProtected;
	public static Animation bobFallProtection;
	public static TextureRegion level1ButtonHovored;
	public static TextureRegion level2ButtonHovered;
	public static TextureRegion level3ButtonHovered;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		background = loadTexture("data/background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 640, 1024);
		backgroundLevel1 = loadTexture("data/soccer-bck.png");
		backgroundLevel1Region = new TextureRegion(backgroundLevel1, 0,0,320,480);
		items = loadTexture("data/items.png");
		level2items =loadTexture("data/game-tiles-halloween@2x.png");
		level3 = loadTexture("data/level3.png");
		monster = new TextureRegion(level2items, 1031,361,175,120);
		booster = new TextureRegion(level2items,466,609,76,75);
		boosterSpeed = new TextureRegion(level2items,398,550,54,54);
		mainMenu = new TextureRegion(level2items, 34, 754, 300, 200);
		pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
		ready = new TextureRegion(items, 320, 224, 192, 32);
		gameOver = new TextureRegion(items, 352, 256, 160, 96);
		highScoresRegion = new TextureRegion(Assets.level2items, 30, 812, 300, 57);
		logo = new TextureRegion(level2items, 317, 869, 368, 128);
		soundOff = new TextureRegion(items, 0, 0, 64, 64);
		soundOn = new TextureRegion(items, 64, 0, 64, 64);
		arrow = new TextureRegion(items, 0, 64, 64, 64);
		pause = new TextureRegion(items, 64, 64, 64, 64);
		highScoresRegionStage = new TextureRegion(level2items, 357,737,297,40);
		level1ButtonHovored = new TextureRegion(level2items,623,459,205,86);
		level2ButtonHovered = new TextureRegion(level2items, 629,580,196,98);
		level3ButtonHovered = new TextureRegion(level2items, 722,709,203,93);
		spring = new TextureRegion(items, 128, 0, 32, 32);
		castle = new TextureRegion(items, 20, 377, 112, 119);
		bobJumpProtected = new Animation(0.2f, new TextureRegion(level3, 652,123,33,45), new TextureRegion(level3, 684,123,34,44));
		bobFallProtection = new Animation(0.2f, new TextureRegion(level3,719,123,33,45), new TextureRegion(level3,753,124,35,43));
		coinAnim = new Animation(0.2f, new TextureRegion(items, 128, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32),
				new TextureRegion(items, 192, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32));
		bobJump = new Animation(0.2f, new TextureRegion(items, 0, 128, 33, 32), new TextureRegion(items, 35, 128, 33, 32));
		bobFall = new Animation(0.2f, new TextureRegion(items, 68, 128, 33, 32), new TextureRegion(items, 102, 128, 33, 32));
		bobHit = new TextureRegion(items, 135, 128, 33, 32);
		squirrelFly = new Animation(0.2f, new TextureRegion(items, 384, 367, 111, 95), new TextureRegion(items, 239, 300, 133, 95));
		squirrelFlySlovakia = new Animation(0.2f, new TextureRegion(items, 168, 411, 102, 78), new TextureRegion(items, 279, 411, 95, 79));
		squirrelFlyBelgia = new Animation(0.2f, new TextureRegion(items, 151, 309, 74, 84), new TextureRegion(items, 151, 309, 74, 84));
		chooseLevel = new TextureRegion(items, 67,257,160,36);
		platform = new TextureRegion(items, 64, 160, 64, 16);
		brakingPlatform = new Animation(0.2f, new TextureRegion(items, 64, 160, 64, 16), new TextureRegion(items, 64, 176, 64, 16),
				new TextureRegion(items, 64, 192, 64, 16), new TextureRegion(items, 64, 208, 64, 16));
		playButtonPressed = new TextureRegion(level2items, 796,965,143,67);
		playButton = new TextureRegion(level2items,95,783,120,53);
		level1Button = new TextureRegion(level2items,338,328,207,91);
		level2Button = new TextureRegion(level2items,153,438,187,80);
		level3Button = new TextureRegion(level2items,153,526,198,99);
		pivo = loadTexture("data/pivo.png");
		pivo_back = loadTexture("data/pivo_back.png");
		obstacle = new TextureRegion(level3, 354,135,74,73);
		bullet = new TextureRegion(level3,242,211,25,23);

		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);
		music = Gdx.audio.newMusic(Gdx.files.internal("data/pes.mp3"));
		music.setLooping(true);
		music.setVolume(1f);
		if (Settings.soundEnabled) music.play();
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("data/jump.wav"));
		highJumpSound = Gdx.audio.newSound(Gdx.files.internal("data/highjump.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
		coinSound = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
	}

	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(1);
	}

	public static void updateTexture() {
		backgroundLevel1 =  loadTexture("data/halloween-bck2@2x_ORIG.png");
		backgroundLevel1Region = new TextureRegion(backgroundLevel1, 0, 0, 664, 1024);
		items = loadTexture("data/game-tiles-halloween@2x.png");
		platform = new TextureRegion(items,0,106,115,39);
		brakingPlatform = new Animation(0.1f, new TextureRegion(items, 0	, 106, 115, 39), new TextureRegion(items, 0, 145, 126, 39),
				new TextureRegion(items, 0, 182, 125, 46), new TextureRegion(items, 0, 228, 124, 71),new TextureRegion(items, 0, 295, 123, 69));
		platformMoving = new TextureRegion(items, 0, 106, 115, 39);//можливо потом
		bobJump = new Animation(0.2f, new TextureRegion(items, 2, 370, 66, 65), new TextureRegion(items, 65, 372, 61, 63));
		bobFall = new Animation(0.2f, new TextureRegion(items, 0, 433, 60, 60), new TextureRegion(items, 62, 444, 60, 60));
		bobHit = new TextureRegion(items, 14, 510, 60, 60);
		castle = new TextureRegion(items, 2,580,130,145);
		squirrelFly = new Animation(0.2f, new TextureRegion(level2items, 300, 0, 152, 96), new TextureRegion(level2items, 457, 0, 156, 96), new TextureRegion(level2items, 618, 0, 157, 92),  new TextureRegion(level2items, 785, 0, 149, 93), new TextureRegion(level2items, 295, 99, 159, 89));
		squirrelFlySlovakia = new Animation(0.2f, new TextureRegion(level2items, 300, 0, 152, 96), new TextureRegion(level2items, 457, 0, 156, 96), new TextureRegion(level2items, 618, 0, 157, 92),  new TextureRegion(level2items, 785, 0, 149, 93), new TextureRegion(level2items, 295, 99, 159, 89));
		squirrelFlyBelgia = new Animation(0.2f, new TextureRegion(level2items, 300, 0, 152, 96), new TextureRegion(level2items, 457, 0, 156, 96), new TextureRegion(level2items, 618, 0, 157, 92),  new TextureRegion(level2items, 785, 0, 149, 93), new TextureRegion(level2items, 295, 99, 159, 89));
		coinAnim = new Animation(0.2f, new TextureRegion(level2items, 141, 382, 40, 46), new TextureRegion(level2items, 186, 386, 32, 37),
				new TextureRegion(level2items, 228, 393, 23, 26), new TextureRegion(level2items, 255, 386, 30, 36));
		bobJumpProtected = new Animation(0.2f, new TextureRegion(level3, 859,158,52,58), new TextureRegion(level3, 914,158,60,58));
		bobFallProtection = new Animation(0.2f, new TextureRegion(level3,855,218,43,61), new TextureRegion(level3,919,225,37,60));
	}

	public static void updateTextureToLevel3(){
		backgroundLevel1 = loadTexture("data/underwater-bck@2x.png");
		backgroundLevel1Region = new TextureRegion(backgroundLevel1, 0, 0, 640, 1024);
		items = loadTexture("data/level3.png");
		bobJump = new Animation(0.2f, new TextureRegion(items, 18, 28, 122, 99), new TextureRegion(items, 149, 30, 118, 103));
		bobFall = new Animation(0.2f, new TextureRegion(items, 277, 32, 84, 98), new TextureRegion(items, 371, 38, 83, 88));
		platform = new TextureRegion(items,11,151,81,23);
		brakingPlatform = new Animation(0.1f, new TextureRegion(items, 11, 151, 81, 23), new TextureRegion(items, 13, 182, 74, 23),
				new TextureRegion(items, 10, 211, 82, 27), new TextureRegion(items, 13, 241, 78, 23),new TextureRegion(items, 12, 265, 78, 25),new TextureRegion(items, 11, 293, 85, 25),new TextureRegion(items, 13, 319, 95, 36),new TextureRegion(items, 14, 355, 84, 43));
		bobHit = new TextureRegion(items,463,15,118,111);
		monster = new TextureRegion(items,131,140,71,91);
		coinAnim = new Animation(0.2f, new TextureRegion(items, 233, 149, 48, 38), new TextureRegion(items, 285, 149, 35, 36),
				new TextureRegion(items, 321, 148, 14, 38), new TextureRegion(items, 285, 149, 35, 36));
		obstacle = new TextureRegion(items, 354,135,74,73);
		bobJumpProtected = new Animation(0.2f, new TextureRegion(level3, 141,253,125,147), new TextureRegion(level3, 270,269,123,136));
		bobFallProtection = new Animation(0.2f, new TextureRegion(level3,402,276,84,124), new TextureRegion(level3,494,279,89,121));
		squirrelFly = new Animation(0.2f, new TextureRegion(level3, 10, 427, 158, 93), new TextureRegion(level3, 176, 431, 150, 85), new TextureRegion(level3, 332, 433, 159, 84),  new TextureRegion(level3, 496, 437, 162, 78), new TextureRegion(level3, 32, 537, 171, 81));
		castle = new TextureRegion(level3,24,670,201,143);
	}

	public static void dispose() {
		background.dispose();
		backgroundLevel1.dispose();
		items.dispose();
		level2items.dispose();
		font.dispose();
		jumpSound.dispose();
		highJumpSound.dispose();
		hitSound.dispose();
		coinSound.dispose();
		clickSound.dispose();
	}
}