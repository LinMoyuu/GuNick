package cn.linmoyu.gunick.listener;

import cn.linmoyu.gunick.GuNick;
import cn.linmoyu.gunick.event.PlayerUnNickEvent;
import cn.linmoyu.gunick.utils.API;
import cn.linmoyu.gunick.utils.Messages;
import dev.iiahmed.disguise.UndisguiseResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerUnNickListener implements Listener {

    @EventHandler
    public void onUnNick(PlayerUnNickEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();

        UndisguiseResponse response = GuNick.getPlugin().getDisguiseProvider().undisguise(player);
        String version = GuNick.getPlugin().getDescription().getVersion();
        switch (response) {
            case SUCCESS:
                if (event.needRefresh()) GuNick.getPlugin().getDisguiseProvider().refreshAsPlayer(player);
                player.setDisplayName(API.getPlayerName(player));
                player.sendMessage(Messages.UNNICK_SUCCESSFUL_MESSAGE);
                break;
            case FAIL_ALREADY_UNDISGUISED:
                event.setCancelled(true);
                player.sendMessage(Messages.UNNICK_FAIL_ALREADY_MESSAGE);
                break;
            default:
                event.setCancelled(true);
                player.sendMessage(Messages.translateCC("&c在当前子服对你取消匿名时出错. 上报管理员时请提供以下错误, 并附带所在模式或大厅: " + response + ". (v" + version + ")"));
                break;
        }
    }
}
