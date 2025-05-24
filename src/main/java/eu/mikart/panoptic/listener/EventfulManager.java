package eu.mikart.panoptic.listener;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.config.EventSetting;
import eu.mikart.panoptic.config.event.*;
import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.Condition;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
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
    private final PanopticPlugin plugin;

    public EventfulManager(PanopticPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        if (plugin.getBlockBreakSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), plugin);
        if (plugin.getBlockPlaceSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), plugin);
        if (plugin.getPlayerTeleportSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), plugin);
        if (plugin.getPlayerJoinSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        if (plugin.getPlayerLeaveSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), plugin);
        if (plugin.getFishCatchSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new FishCatchListener(), plugin);
        if (plugin.getFishingSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new FishingListener(), plugin);
        if (plugin.getEntityKillSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new EntityKillListener(), plugin);
        if (plugin.getFurnaceCookSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new FurnaceCookListener(), plugin);
        if (plugin.getCraftSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new CraftListener(), plugin);
        if (plugin.getMoveSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new MoveListener(), plugin);
        if (plugin.getPlayerDeathSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), plugin);
        if (plugin.getPlayerDamageSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(), plugin);
        if (plugin.getPlayerSleepSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new PlayerBedListener(), plugin);
        if (plugin.getItemConsumeSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new ItemConsumeListener(), plugin);
        if (plugin.getItemPickupSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new ItemPickupListener(), plugin);
        if (plugin.getEntityInteractSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new EntityInteractListener(), plugin);
        if (plugin.getItemEnchantSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new ItemEnchantListener(), plugin);
        if (plugin.getItemRepairSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new ItemRepairListener(), plugin);
        if (plugin.getItemDropSetting().isListen())
            Bukkit.getPluginManager().registerEvents(new ItemDropListener(), plugin);

        plugin.getLogger().info("EventfulManager initialized and listeners registered.");
    }

    public void unregisterAll() {
        HandlerList.unregisterAll(plugin);
    }

    private EventSetting.EventData getBlockBreakInner() {
        BlockBreakSetting setting = plugin.getBlockBreakSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getBlockPlaceInner() {
        BlockPlaceSetting setting = plugin.getBlockPlaceSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerTeleportInner() {
        PlayerTeleportSetting setting = plugin.getPlayerTeleportSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerJoinInner() {
        PlayerJoinSetting setting = plugin.getPlayerJoinSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerLeaveInner() {
        PlayerLeaveSetting setting = plugin.getPlayerLeaveSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getFishCatchInner() {
        FishCatchSetting setting = plugin.getFishCatchSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getFishingInner() {
        FishingSetting setting = plugin.getFishingSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getEntityKillInner() {
        EntityKillSetting setting = plugin.getEntityKillSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getFurnaceCookInner() {
        FurnaceCookSetting setting = plugin.getFurnaceCookSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getCraftInner() {
        CraftSetting setting = plugin.getCraftSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getMoveInner() {
        MoveSetting setting = plugin.getMoveSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerDeathInner() {
        PlayerDeathSetting setting = plugin.getPlayerDeathSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerDamageInner() {
        PlayerDamageSetting setting = plugin.getPlayerDamageSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getPlayerBedInner() {
        PlayerSleepSetting setting = plugin.getPlayerSleepSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemConsumeInner() {
        ItemConsumeSetting setting = plugin.getItemConsumeSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemPickupInner() {
        ItemPickupSetting setting = plugin.getItemPickupSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getEntityInteractInner() {
        EntityInteractSetting setting = plugin.getEntityInteractSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemEnchantInner() {
        ItemEnchantSetting setting = plugin.getItemEnchantSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemRepairInner() {
        ItemRepairSetting setting = plugin.getItemRepairSetting();
        if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
            return setting.getEvents().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getItemDropInner() {
        ItemDropSetting setting = plugin.getItemDropSetting();
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

