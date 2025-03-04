package dev.stormy.client.module.modules.movement;

import dev.stormy.client.main.Stormy;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.module.setting.impl.SliderSetting;
import dev.stormy.client.module.setting.impl.TickSetting;
import dev.stormy.client.utils.Utils;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;
import dev.stormy.client.module.Module;
import dev.stormy.client.module.setting.impl.DescriptionSetting;

@SuppressWarnings("unused")
public class Flight extends Module {
    public ComboSetting<modeee> flightMode;
    private int counter, ticks;
    private boolean started;
    public final SliderSetting airspeed;
    public final TickSetting gs, ascend, descend;

    public Flight() {
        super("Flight", ModuleCategory.Movement, 0);
        this.registerSetting(new DescriptionSetting("ZOOM"));
        this.registerSetting(airspeed = new SliderSetting("Airspeed", 1, 0, 10, 0.5));
        this.registerSetting(flightMode = new ComboSetting<>("Mode", modeee.AirWalk));
        this.registerSetting(gs = new TickSetting("GroundSpoof", false));
        this.registerSetting(ascend = new TickSetting("Ascend", false));
        this.registerSetting(descend = new TickSetting("Descend", false));
    }

    public void onDisable() {
        if (!Stormy.moduleManager.getModuleByClazz(Bhop.class).isEnabled() && mc.thePlayer != null) {
            mc.thePlayer.speedInAir = 0.02F;
        }
    }


    @SubscribeEvent
    public void airTrafficControl(TickEvent e) {
        if (!Utils.Player.isPlayerInGame()) return;
        if (gs.isToggled()) mc.thePlayer.onGround = true;
        switch (flightMode.getMode()) {
            case AirWalk: {
                mc.thePlayer.motionY = 0.0F;
                mc.thePlayer.speedInAir = (float) airspeed.getInput() * 2 / 100;
                if (ascend.isToggled() && mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.thePlayer.onGround = true;
                    mc.thePlayer.jumpTicks = 0;
                    }
                if (descend.isToggled() && mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.thePlayer.motionY = -0.5F;
                    }
                }
            }
        }
    public enum modeee {
        AirWalk
    }
}