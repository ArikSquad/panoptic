package eu.mikart.panoptic.listener;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.config.event.*;
import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.Condition;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;

import java.util.List;

public class EventfulManager {
    private final PanopticPlugin configProvider;

    public EventfulManager(PanopticPlugin configProvider) {
        this.configProvider = configProvider;
    }

    public void initialize() {
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new FishCatchListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new FishingListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new EntityKillListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new FurnaceCookListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new CraftListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new MoveListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new PlayerBedListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new ItemConsumeListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new ItemPickupListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new EntityInteractListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new ItemEnchantListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new ItemRepairListener(), configProvider);
        Bukkit.getPluginManager().registerEvents(new ItemDropListener(), configProvider);
    }

    private EventSetting.EventData getBlockBreakInner() {
        BlockBreakSetting setting = configProvider.getBlockBreakSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getBlockPlaceInner() {
        BlockPlaceSetting setting = configProvider.getBlockPlaceSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerTeleportInner() {
        PlayerTeleportSetting setting = configProvider.getPlayerTeleportSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerJoinInner() {
        PlayerJoinSetting setting = configProvider.getPlayerJoinSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerLeaveInner() {
        PlayerLeaveSetting setting = configProvider.getPlayerLeaveSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getFishCatchInner() {
        FishCatchSetting setting = configProvider.getFishCatchSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getFishingInner() {
        FishingSetting setting = configProvider.getFishingSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getEntityKillInner() {
        EntityKillSetting setting = configProvider.getEntityKillSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getFurnaceCookInner() {
        FurnaceCookSetting setting = configProvider.getFurnaceCookSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getCraftInner() {
        CraftSetting setting = configProvider.getCraftSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getMoveInner() {
        MoveSetting setting = configProvider.getMoveSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerDeathInner() {
        PlayerDeathSetting setting = configProvider.getPlayerDeathSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerDamageInner() {
        PlayerDamageSetting setting = configProvider.getPlayerDamageSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerBedInner() {
        PlayerSleepSetting setting = configProvider.getPlayerSleepSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemConsumeInner() {
        ItemConsumeSetting setting = configProvider.getItemConsumeSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemPickupInner() {
        ItemPickupSetting setting = configProvider.getItemPickupSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getEntityInteractInner() {
        EntityInteractSetting setting = configProvider.getEntityInteractSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemEnchantInner() {
        ItemEnchantSetting setting = configProvider.getItemEnchantSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemRepairInner() {
        ItemRepairSetting setting = configProvider.getItemRepairSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemDropInner() {
        ItemDropSetting setting = configProvider.getItemDropSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    public void handleEvent(Event event, List<Condition> conditions, List<Action> actions) {
        boolean allConditionsMet = conditions.stream().allMatch(cond -> cond.evaluate(event));
        if (allConditionsMet) {
            actions.forEach(action -> action.execute(event));
        }
    }

    private class BlockBreakListener implements Listener {
        @EventHandler
        public void onBlockBreak(BlockBreakEvent event) {
            EventSetting.EventData eventData = getBlockBreakInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class BlockPlaceListener implements Listener {
        @EventHandler
        public void onBlockPlace(BlockPlaceEvent event) {
            EventSetting.EventData eventData = getBlockPlaceInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class PlayerTeleportListener implements Listener {
        @EventHandler
        public void onPlayerTeleport(PlayerTeleportEvent event) {
            EventSetting.EventData eventData = getPlayerTeleportInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class PlayerJoinListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            EventSetting.EventData eventData = getPlayerJoinInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class PlayerLeaveListener implements Listener {
        @EventHandler
        public void onPlayerLeave(PlayerQuitEvent event) {
            EventSetting.EventData eventData = getPlayerLeaveInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class FishCatchListener implements Listener {
        @EventHandler
        public void onFishCatch(PlayerFishEvent event) {
            EventSetting.EventData eventData = getFishCatchInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class FishingListener implements Listener {
        @EventHandler
        public void onFishing(PlayerFishEvent event) {
            EventSetting.EventData eventData = getFishingInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class EntityKillListener implements Listener {
        @EventHandler
        public void onMobKill(EntityDeathEvent event) {
            EventSetting.EventData eventData = getEntityKillInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class FurnaceCookListener implements Listener {
        @EventHandler
        public void onFurnaceCook(FurnaceExtractEvent event) {
            EventSetting.EventData eventData = getFurnaceCookInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class CraftListener implements Listener {
        @EventHandler
        public void onCraft(CraftItemEvent event) {
            EventSetting.EventData eventData = getCraftInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class MoveListener implements Listener {
        @EventHandler
        public void onMove(PlayerMoveEvent event) {
            EventSetting.EventData eventData = getMoveInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class PlayerDeathListener implements Listener {
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            EventSetting.EventData eventData = getPlayerDeathInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class PlayerDamageListener implements Listener {
        @EventHandler
        public void onPlayerDamage(EntityDamageByEntityEvent event) {
            EventSetting.EventData eventData = getPlayerDamageInner();
            if (eventData != null && event.getEntity() instanceof Player) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class PlayerBedListener implements Listener {
        @EventHandler
        public void onPlayerBedEnter(PlayerBedEnterEvent event) {
            EventSetting.EventData eventData = getPlayerBedInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }

        @EventHandler
        public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
            EventSetting.EventData eventData = getPlayerBedInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class ItemConsumeListener implements Listener {
        @EventHandler
        public void onItemConsume(PlayerItemConsumeEvent event) {
            EventSetting.EventData eventData = getItemConsumeInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class ItemPickupListener implements Listener {
        @EventHandler
        public void onItemPickup(PlayerAttemptPickupItemEvent event) {
            EventSetting.EventData eventData = getItemPickupInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class EntityInteractListener implements Listener {
        @EventHandler
        public void onEntityInteract(PlayerInteractEntityEvent event) {
            EventSetting.EventData eventData = getEntityInteractInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class ItemEnchantListener implements Listener {
        @EventHandler
        public void onItemEnchant(EnchantItemEvent event) {
            EventSetting.EventData eventData = getItemEnchantInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class ItemRepairListener implements Listener {
        @EventHandler
        public void onItemRepair(PrepareAnvilEvent event) {
            EventSetting.EventData eventData = getItemRepairInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }

    private class ItemDropListener implements Listener {
        @EventHandler
        public void onItemDrop(PlayerDropItemEvent event) {
            EventSetting.EventData eventData = getItemDropInner();
            if (eventData != null) {
                handleEvent(event, eventData.resolveConditions(), eventData.resolveActions());
            }
        }
    }
}

