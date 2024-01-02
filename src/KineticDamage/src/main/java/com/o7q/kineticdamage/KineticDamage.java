package com.o7q.kineticdamage;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.o7q.kineticdamage.config.ConfigManager.ConfigInit;
import static com.o7q.kineticdamage.config.ConfigValues.*;

public class KineticDamage implements ModInitializer {
	public static final String MOD_ID = "kineticdamage";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private final double Y_OFFSET = -0.0784000015258789D;

	@Override
	public void onInitialize()
	{
		ConfigInit();

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
		{
			if (!world.isClient() && !player.isSpectator())
			{
				Vec3d playerVelocity = player.getVelocity();
				double playerBaseSpeed = player.getMovementSpeed();
				double playerHeadYaw = player.getYaw();
				double playerFallDistance = player.fallDistance;

				Vec3d entityVelocity = entity.getVelocity();

				float playerSpeedXZMultiplier = 1.0f;

				if (player.isSprinting())
					playerSpeedXZMultiplier *= ACTION_SPRINTING_MULTIPLIER;
				if (player.isSwimming())
					playerSpeedXZMultiplier *= ACTION_SWIMMING_MULTIPLIER;
				if (player.isSneaking())
					playerSpeedXZMultiplier *= ACTION_SNEAKING_MULTIPLIER;
				if (player.isCrawling())
					playerSpeedXZMultiplier *= ACTION_CRAWLING_MULTIPLIER;

				double playerSpeedX = (playerBaseSpeed + 1) * Math.abs((playerVelocity.x == 0 ? 1 : playerVelocity.x)) * playerSpeedXZMultiplier;
				double playerSpeedZ = (playerBaseSpeed + 1) * Math.abs((playerVelocity.z == 0 ? 1 : playerVelocity.z)) * playerSpeedXZMultiplier;
				double playerSpeedY = Math.abs(playerVelocity.y) + Y_OFFSET;
				Vec3d playerSpeed = new Vec3d(playerSpeedX, playerSpeedY, playerSpeedZ);

				double damageAmount = CalculateDamage(playerSpeed, playerFallDistance);
				Vec3d knockbackAmount = CalculateKnockback(entityVelocity, playerSpeed, playerVelocity.y, playerHeadYaw);

				DamageSource entityDamageSource = world.getDamageSources().playerAttack(player);
				entity.damage(entityDamageSource, (float)damageAmount);
				entity.setVelocity(knockbackAmount);

				if (DEBUG_CHAT_LOG)
					player.sendMessage(Text.literal(
						"\n\n" +
						"Speed:\n" +
						"  X: " + playerSpeed.x + "\n" +
						"  Y: " + playerSpeed.y + "\n" +
						"  Z: " + playerSpeed.z + "\n" +
						"  Base: " + playerBaseSpeed + "\n" +
						"Knockback:\n" +
						"  X: " + knockbackAmount.x + "\n" +
						"  Y: " + knockbackAmount.y + "\n" +
						"  Z: " + knockbackAmount.z + "\n" +
						"Damage: " + damageAmount + "\n" +
						"Yaw: " + playerHeadYaw
					));
			}
			return ActionResult.PASS;
		});
	}

	private double CalculateDamage(Vec3d playerSpeed, double playerFallDistance)
	{
		double damageXZ = (playerSpeed.x + playerSpeed.z) * DAMAGE_MULTIPLIER_HORIZONTAL;
		double damageY = (playerSpeed.y + playerFallDistance) * DAMAGE_MULTIPLIER_VERTICAL;

		if (damageXZ > DAMAGE_MAX_HORIZONTAL && DAMAGE_MAX_HORIZONTAL > 0)
			damageXZ = DAMAGE_MAX_HORIZONTAL;

		if (damageY > DAMAGE_MAX_VERTICAL && DAMAGE_MAX_VERTICAL > 0)
			damageY = DAMAGE_MAX_VERTICAL;

		return damageXZ + damageY;
	}

	private Vec3d CalculateKnockback(Vec3d entityVelocity, Vec3d playerSpeed, double playerVelocityY, double playerHeadYaw)
	{
		double verticalSignum = Math.signum(playerVelocityY + Y_OFFSET);
		verticalSignum = verticalSignum == 0 ? 1 : verticalSignum;

		double knockbackX = entityVelocity.x + Math.sin(Math.toRadians(-playerHeadYaw)) * playerSpeed.x * KNOCKBACK_MULTIPLIER_X;
		double knockbackZ = entityVelocity.z + Math.cos(Math.toRadians(playerHeadYaw)) * playerSpeed.z * KNOCKBACK_MULTIPLIER_Z;
		double knockbackY = entityVelocity.y + verticalSignum * playerSpeed.y * KNOCKBACK_MULTIPLIER_Y;

		return new Vec3d(knockbackX, knockbackY, knockbackZ);
	}
}