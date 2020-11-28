package edu.uw.meteorRush.impl;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;

public class PlayerShip extends Entity implements DamagableEntity {

	private static final int PLAYER_WIDTH = 100;
	private static final int PLAYER_HEIGHT = 100;
	private static final double LASER_COOLDOWN = 0.22;
	private static final double SPEED = 600;
	public static final Image PLAYER_1 = ResourceLoader.loadImage("res/Player1.png").getScaledInstance(PLAYER_WIDTH,
			PLAYER_HEIGHT, 0);
	public static final Image PLAYER_2 = ResourceLoader.loadImage("res/Player2.png").getScaledInstance(PLAYER_WIDTH,
			PLAYER_HEIGHT, 0);
	public static final Image PLAYER_LASER = ResourceLoader.loadImage("res/PlayerLaser.png").getScaledInstance(50, 10,
			0);

	private Image sprite;
	private double nextFireTime;

	public PlayerShip(Vector2 position) {
		super(position, new Vector2(PLAYER_WIDTH, PLAYER_HEIGHT));
		sprite = Assets.PLAYER_1;
		TimerTask animationChange = new TimerTask() {
			@Override
			public void run() {
				if (sprite == Assets.PLAYER_1) {
					sprite = Assets.PLAYER_2;
				} else {
					sprite = Assets.PLAYER_1;
				}
			}
		};
		new Timer().schedule(animationChange, 0, 100);
	}

	@Override
	public void tick() {
		InputManager input = Game.getInstance().getInputManager();
		Vector2 move = new Vector2(input.getHorizontalAxis(), input.getVerticalAxis())
				.multiply(Game.getInstance().getDeltaTime() * -SPEED);
		Vector2 position = getPosition();

		position.add(move);
		position.setX(clamp(position.getX(), 25, 1600));
		position.setY(clamp(position.getY(), 25, 700));
		setPosition(position);
		if (input.spaceDown()) {
			double time = Game.getInstance().getTime();
			if (time > nextFireTime) {
				nextFireTime = time + LASER_COOLDOWN;
				fireLaser();
			}
		}
	}

	private double clamp(double n, double min, double max) {
		if (n < min) {
			return min;
		} else if (n > max) {
			return max;
		} else {
			return n;
		}
	}

	private void fireLaser() {
		Vector2 position = getPosition();
		Laser laser = new Laser(position);
		Game.getInstance().getOpenScene().addEntity(laser);
		ResourceLoader.loadAudioClip("res/laser.wav").start();
	}

	@Override
	public void render(Graphics g) {
		Vector2 position = getPosition();
		g.drawImage(sprite, (int) (position.getX() - PLAYER_WIDTH / 2.0), (int) (position.getY() - PLAYER_HEIGHT / 2.0),
				null);
	}

	@Override
	public void onCollisionEnter(Entity other) {
		
	}

	@Override
	public void onCollisionExit(Entity other) {

	}

	private static class Laser extends Projectile {

		private static final double DAMAGE_AMOUNT = 1;
		private static final double SPEED = 1500;
		private static final Vector2 SIZE = new Vector2(50, 50);

		Laser(Vector2 position) {
			super(position, SIZE, new Vector2(SPEED, 0));
		}

		@Override
		public void render(Graphics g) {
			Vector2 position = getPosition();
			g.drawImage(Assets.PLAYER_LASER, (int) position.getX(), (int) position.getY(), null);
		}

		@Override
		public void onCollisionEnter(Entity other) {
			if (!(other instanceof PlayerShip) && other instanceof DamagableEntity) {
				((DamagableEntity) other).damage(DAMAGE_AMOUNT);
				Game.getInstance().getOpenScene().removeEntity(this);
			} else if (other instanceof Projectile) {
				Game.getInstance().getOpenScene().removeEntity(this);
			}
		}

		@Override
		public void onCollisionExit(Entity other) {

		}

	}

	@Override
	public void damage(double amount) {
		Explosion explosion = new Explosion(getPosition(), new Vector2(100, 100), 0.1);
		Game.getInstance().getOpenScene().addEntity(explosion);
	}

}
