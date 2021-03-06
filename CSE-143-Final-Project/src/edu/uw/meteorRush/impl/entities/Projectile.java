package edu.uw.meteorRush.impl.entities;

import edu.uw.meteorRush.gameEngine.Entity;
import edu.uw.meteorRush.gameEngine.Game;
import edu.uw.meteorRush.gameEngine.Vector2;

public abstract class Projectile extends Entity {

	private Vector2 velocity;

	public Projectile(Vector2 position, Vector2 size, Vector2 velocity) {
		super(position, size);
		this.velocity = velocity.clone();
	}

	@Override
	public void tick() {
		Vector2 position = getPosition();
		position.add(velocity.clone().multiply(Game.getInstance().getDeltaTime()));
		setPosition(position);
	}

}
