package com.o7q.kineticdamage;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KineticDamage implements ModInitializer {
	public static final String MOD_ID = "kineticdamage";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
		{
			int damageHorizontalMultiplier = 10;
			int damageVerticalMultiplier = 100;

			int damageVerticalMax = 100;
			int damageHorizontalMax = -1;

			int knockbackXMultiplier = 10;
			int knockbackYMultiplier = 1000;
			int knockbackZMultiplier = 10;

			boolean debugLog = true;



			if (!world.isClient())
			{
				Vec3d playerVelocity = player.getVelocity();
				float playerVerticalSpeed = (float)(Math.abs(playerVelocity.y) - 0.04704000278472904);
				float playerHorizontalSpeed = Math.abs(player.getMovementSpeed()) * (float)(Math.abs(playerVelocity.x) + Math.abs(playerVelocity.z));

				player.sendMessage(Text.literal(Math.abs(playerVelocity.y) + " " + Math.abs(playerVelocity.x)));

				float damageVertical = playerVerticalSpeed * damageVerticalMultiplier;
				float damageHorizontal = playerHorizontalSpeed * damageHorizontalMultiplier;

				if (damageVertical > damageVerticalMax)
					damageVertical = 50.0f;

				if (damageHorizontalMax != -1 && damageHorizontal > damageHorizontalMax)
					damageHorizontal = damageHorizontalMax;

				float damageAmount = damageVertical + damageHorizontal;

				float knockbackX = (float)playerVelocity.x * knockbackXMultiplier;
				float knockbackY = (float)playerVelocity.y * knockbackYMultiplier;
				float knockbackZ = (float)playerVelocity.z * knockbackZMultiplier;

				DamageSource entityDamageSource = world.getDamageSources().playerAttack(player);
				entity.damage(entityDamageSource, damageAmount);
				entity.setVelocity(knockbackX, knockbackY, knockbackZ);

				if (debugLog)
					player.sendMessage(Text.literal(
							  "Vertical: " + damageVertical + "\n" +
									"Horizontal: " + damageHorizontal + "\n" +
									"Damage: " + damageAmount + "\n"));
			}

			return ActionResult.PASS;
		});
	}
}