package com.o7q.kineticdamage.network.events.Attack;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import org.lwjgl.glfw.GLFW;

public class AttackDirectHandler
{
    public static final String KEY_CATEGORY_TEST = "key.category.kineticdamage.test";
    public static final String TEST_KEY = "key.kineticdamage.test_key";

    public static KeyBinding testKey;

    public static void registerAttackDirectHandler()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client ->{
            if (testKey.wasPressed())
            {
                PlayerEntity player = client.player;
                if (client.crosshairTarget instanceof EntityHitResult entityCrosshairTarget)
                {
                    player.sendMessage(Text.literal("HELLO " + entityCrosshairTarget.getEntity().getName() + "!"));
                }
            }
        });
    }

    public static void register()
    {
        testKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                TEST_KEY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY_TEST
        ));
        registerAttackDirectHandler();
    }
}