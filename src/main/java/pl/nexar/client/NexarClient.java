package pl.nexar.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.lwjgl.glfw.GLFW;

public class NexarClient implements ClientModInitializer {
    private static KeyBinding menuKey;
    private static KeyBinding autoDripToggleKey;
    private static boolean autoDripEnabled = false;
    private static int useCooldown = 0;

    @Override
    public void onInitializeClient() {
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nexar_client.open_menu", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, "category.nexar_client"));

        autoDripToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nexar_client.toggle_autodrip", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6, "category.nexar_client"));

        ClientTickEvents.END_CLIENT_TICK.register(NexarClient::tick);
    }

    private static void tick(MinecraftClient client) {
        while (menuKey.wasPressed()) {
            if (client.currentScreen == null) client.setScreen(new NexarScreen());
        }

        while (autoDripToggleKey.wasPressed()) {
            autoDripEnabled = !autoDripEnabled;
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§5[Nexar] §fAutoDrip: "
                        + (autoDripEnabled ? "§aON" : "§cOFF")), true);
            }
        }

        if (useCooldown > 0) useCooldown--;
        if (!autoDripEnabled || client.player == null || client.world == null
                || client.interactionManager == null || client.currentScreen != null) return;

        // Prototyp: zwykła interakcja z klapą, gdy trzymasz pointed dripstone.
        if (client.player.getMainHandStack().isOf(Items.POINTED_DRIPSTONE)
                && client.crosshairTarget instanceof BlockHitResult hit
                && client.world.getBlockState(hit.getBlockPos()).getBlock() instanceof TrapdoorBlock
                && useCooldown == 0) {
            client.interactionManager.interactBlock(client.player, Hand.MAIN_HAND, hit);
            useCooldown = 4;
        }
    }

    private static class NexarScreen extends Screen {
        protected NexarScreen() { super(Text.literal("Nexar Client")); }

        @Override
        public void render(net.minecraft.client.gui.DrawContext context,
                           int mouseX, int mouseY, float delta) {
            this.renderBackground(context, mouseX, mouseY, delta);
            int x = this.width / 2, y = this.height / 2;
            context.drawCenteredTextWithShadow(this.textRenderer, "Nexar Client", x, y - 34, 0xFFFFFFFF);
            context.drawCenteredTextWithShadow(this.textRenderer, "Witaj bossie!", x, y - 10, 0xFFB56CFF);
            context.drawCenteredTextWithShadow(this.textRenderer,
                    "AutoDrip: " + (autoDripEnabled ? "ON" : "OFF") + " | F6: toggle",
                    x, y + 16, 0xFFFFFFFF);
            context.drawCenteredTextWithShadow(this.textRenderer, "ESC - zamknij",
                    x, y + 38, 0xFFAAAAAA);
            super.render(context, mouseX, mouseY, delta);
        }

        @Override
        public boolean shouldPause() { return false; }
    }
}
