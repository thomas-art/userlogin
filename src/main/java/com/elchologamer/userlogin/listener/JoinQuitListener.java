package com.elchologamer.userlogin.listener;

import com.elchologamer.userlogin.ULPlayer;
import com.elchologamer.userlogin.UserLogin;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

public class JoinQuitListener implements PluginMessageListener, Listener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("userlogin:main")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String uuid = in.readUTF();
        ULPlayer.get(UUID.fromString(uuid)).onJoin(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (UserLogin.getPlugin().getConfig().getBoolean("disableVanillaJoinMessages")) {
            event.setJoinMessage(null);
        }
        ULPlayer.get(event.getPlayer()).onJoin(false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ULPlayer.get(event.getPlayer()).onQuit();
    }
}