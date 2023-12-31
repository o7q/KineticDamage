package com.o7q.kineticdamage;

import com.o7q.kineticdamage.config.ModConfigs;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.o7q.kineticdamage.config.ModConfigs.*;

public class KineticDamage implements ModInitializer {
	public static final String MOD_ID = "kineticdamage";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		ModConfigs.registerConfigs();



		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
		{
			if (!world.isClient())
			{
				Vec3d playerVelocity = player.getVelocity();
				float playerVerticalSpeed = (float)(Math.abs(playerVelocity.y) - 0.04704000278472904);
				float playerHorizontalSpeed = Math.abs(player.getMovementSpeed()) * (float)(Math.abs(playerVelocity.x) + Math.abs(playerVelocity.z));

				float damageVertical = playerVerticalSpeed * DAMAGE_MULTIPLIER_VERTICAL;
				float damageHorizontal = playerHorizontalSpeed * DAMAGE_MULTIPLIER_HORIZONTAL;

				if (damageVertical > DAMAGE_MAX_VERTICAL)
					damageVertical = DAMAGE_MAX_VERTICAL;

				if (DAMAGE_MAX_HORIZONTAL != -1 && damageHorizontal > DAMAGE_MAX_HORIZONTAL)
					damageHorizontal = DAMAGE_MAX_HORIZONTAL;

				float damageAmount = damageVertical + damageHorizontal;

				float knockbackX = (float)playerVelocity.x * KNOCKBACK_MULTIPLIER_X;
				float knockbackY = (float)playerVelocity.y * KNOCKBACK_MULTIPLIER_Y;
				float knockbackZ = (float)playerVelocity.z * KNOCKBACK_MULTIPLIER_Z;

				DamageSource entityDamageSource = world.getDamageSources().playerAttack(player);
				entity.damage(entityDamageSource, damageAmount);
				entity.setVelocity(knockbackX, knockbackY, knockbackZ);

				if (DEBUG_LOG)
					player.sendMessage(Text.literal(
							"Vertical: " + damageVertical + "\n" +
									"Horizontal: " + damageHorizontal + "\n" +
									"Damage: " + damageAmount + "\n"));
			}

			return ActionResult.PASS;
		});
	}
}