package eu.mikart.panoptic.listener;

import java.util.List;
import java.util.UUID;

import eu.mikart.panoptic.util.CooldownManager;
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
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.config.ConditionEvaluationMode;
import eu.mikart.panoptic.config.EventSetting;
import eu.mikart.panoptic.config.event.BlockBreakSetting;
import eu.mikart.panoptic.config.event.BlockPlaceSetting;
import eu.mikart.panoptic.config.event.CraftSetting;
import eu.mikart.panoptic.config.event.EntityInteractSetting;
import eu.mikart.panoptic.config.event.EntityKillSetting;
import eu.mikart.panoptic.config.event.FishCatchSetting;
import eu.mikart.panoptic.config.event.FishingSetting;
import eu.mikart.panoptic.config.event.FurnaceCookSetting;
import eu.mikart.panoptic.config.event.ItemConsumeSetting;
import eu.mikart.panoptic.config.event.ItemDropSetting;
import eu.mikart.panoptic.config.event.ItemEnchantSetting;
import eu.mikart.panoptic.config.event.ItemPickupSetting;
import eu.mikart.panoptic.config.event.ItemRepairSetting;
import eu.mikart.panoptic.config.event.MoveSetting;
import eu.mikart.panoptic.config.event.PlayerDamageSetting;
import eu.mikart.panoptic.config.event.PlayerDeathSetting;
import eu.mikart.panoptic.config.event.PlayerJoinSetting;
import eu.mikart.panoptic.config.event.PlayerLeaveSetting;
import eu.mikart.panoptic.config.event.PlayerSleepSetting;
import eu.mikart.panoptic.config.event.PlayerTeleportSetting;
import eu.mikart.panoptic.event.action.Action;
import eu.mikart.panoptic.event.condition.Condition;

public class EventfulManager {
    private final PanopticPlugin plugin;
    private final CooldownManager cooldownManager = new CooldownManager();

    public EventfulManager(PanopticPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
		// for now this is fine I guess
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

        plugin.debug("Listeners have been registered.");
    }

    public void unregisterAll() {
        HandlerList.unregisterAll(plugin);
    }

    public void handleEvent(Event event, List<Condition> conditions, List<Action> actions, ConditionEvaluationMode evaluationMode, EventSetting.EventData eventData, String eventType, int eventIndex) {
        Player player = getPlayerFromEvent(event);
        if (player == null) {
            return;
        }

        if (cooldownManager.isOnCooldown(event, eventData, eventType, eventIndex, player)) {
            return;
        }

        boolean conditionsMet = evaluateConditions(conditions, event, evaluationMode);
        if (conditionsMet) {
            cooldownManager.setCooldowns(event, eventData, eventType, eventIndex, player);
            actions.forEach(action -> action.execute(event));
        }
    }

    private boolean evaluateConditions(List<Condition> conditions, Event event, ConditionEvaluationMode evaluationMode) {
        if (conditions == null || conditions.isEmpty()) {
            return true;
        }

        if (evaluationMode == null) {
            return conditions.stream().allMatch(cond -> cond.evaluate(event));
        }

        return switch (evaluationMode) {
            case REQUIRE_ALL -> conditions.stream().allMatch(cond -> cond.evaluate(event));
            case REQUIRE_ANY -> conditions.stream().anyMatch(cond -> cond.evaluate(event));
            case REQUIRE_SINGLE -> conditions.stream().mapToLong(cond -> cond.evaluate(event) ? 1 : 0).sum() == 1;
            case IGNORE_CONDITIONS -> true;
        };
    }

    public long getRemainingCooldown(String eventType, int eventIndex, UUID playerId) {
        EventSetting.EventData eventData = getEventData(eventType, eventIndex);
        if (eventData == null) {
            return 0;
        }

        return cooldownManager.getRemainingCooldown(eventType, eventIndex, playerId, eventData);
    }

    // this is different from BukkitEventParser#getPlayerFromEvent, why? well nobody knows.
    private Player getPlayerFromEvent(Event event) {
        if (event instanceof PlayerEvent playerEvent) {
            return playerEvent.getPlayer();
        } else if (event instanceof BlockBreakEvent blockEvent) {
            return blockEvent.getPlayer();
        } else if (event instanceof BlockPlaceEvent blockEvent) {
            return blockEvent.getPlayer();
        } else if (event instanceof EntityDamageByEntityEvent damageEvent && damageEvent.getEntity() instanceof Player) {
            return (Player) damageEvent.getEntity();
        } else if (event instanceof EntityDeathEvent deathEvent && deathEvent.getEntity().getKiller() != null) {
            return deathEvent.getEntity().getKiller();
        } else if (event instanceof FurnaceExtractEvent furnaceEvent) {
            return furnaceEvent.getPlayer();
        } else if (event instanceof CraftItemEvent craftEvent && craftEvent.getWhoClicked() instanceof Player) {
            return (Player) craftEvent.getWhoClicked();
        } else if (event instanceof EnchantItemEvent enchantEvent) {
            return enchantEvent.getEnchanter();
        } else if (event instanceof PrepareAnvilEvent anvilEvent && anvilEvent.getViewers().getFirst() instanceof Player) {
            return (Player) anvilEvent.getViewers().getFirst();
        }
        return null;
    }

    private EventSetting.EventData getEventData(String eventType, int eventIndex) {
        try {
            return switch (eventType.toLowerCase()) {
                case "block_break" -> plugin.getBlockBreakSetting().getEvents().get(eventIndex);
                case "block_place" -> plugin.getBlockPlaceSetting().getEvents().get(eventIndex);
                case "player_teleport" -> plugin.getPlayerTeleportSetting().getEvents().get(eventIndex);
                case "player_join" -> plugin.getPlayerJoinSetting().getEvents().get(eventIndex);
                case "player_leave" -> plugin.getPlayerLeaveSetting().getEvents().get(eventIndex);
                case "fish_catch" -> plugin.getFishCatchSetting().getEvents().get(eventIndex);
                case "fishing" -> plugin.getFishingSetting().getEvents().get(eventIndex);
                case "entity_kill" -> plugin.getEntityKillSetting().getEvents().get(eventIndex);
                case "furnace_cook" -> plugin.getFurnaceCookSetting().getEvents().get(eventIndex);
                case "craft" -> plugin.getCraftSetting().getEvents().get(eventIndex);
                case "move" -> plugin.getMoveSetting().getEvents().get(eventIndex);
                case "player_death" -> plugin.getPlayerDeathSetting().getEvents().get(eventIndex);
                case "player_damage" -> plugin.getPlayerDamageSetting().getEvents().get(eventIndex);
                case "player_sleep" -> plugin.getPlayerSleepSetting().getEvents().get(eventIndex);
                case "item_consume" -> plugin.getItemConsumeSetting().getEvents().get(eventIndex);
                case "item_pickup" -> plugin.getItemPickupSetting().getEvents().get(eventIndex);
                case "entity_interact" -> plugin.getEntityInteractSetting().getEvents().get(eventIndex);
                case "item_enchant" -> plugin.getItemEnchantSetting().getEvents().get(eventIndex);
                case "item_repair" -> plugin.getItemRepairSetting().getEvents().get(eventIndex);
                case "item_drop" -> plugin.getItemDropSetting().getEvents().get(eventIndex);
                default -> null;
            };
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private class BlockBreakListener implements Listener {
        @EventHandler
        public void onBlockBreak(BlockBreakEvent event) {
            BlockBreakSetting setting = plugin.getBlockBreakSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "block_break", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class BlockPlaceListener implements Listener {
        @EventHandler
        public void onBlockPlace(BlockPlaceEvent event) {
            BlockPlaceSetting setting = plugin.getBlockPlaceSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "block_place", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class PlayerTeleportListener implements Listener {
        @EventHandler
        public void onPlayerTeleport(PlayerTeleportEvent event) {
            PlayerTeleportSetting setting = plugin.getPlayerTeleportSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "player_teleport", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class PlayerJoinListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            PlayerJoinSetting setting = plugin.getPlayerJoinSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "player_join", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class PlayerLeaveListener implements Listener {
        @EventHandler
        public void onPlayerLeave(PlayerQuitEvent event) {
            PlayerLeaveSetting setting = plugin.getPlayerLeaveSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "player_leave", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class FishCatchListener implements Listener {
        @EventHandler
        public void onFishCatch(PlayerFishEvent event) {
            FishCatchSetting setting = plugin.getFishCatchSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "fish_catch", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class FishingListener implements Listener {
        @EventHandler
        public void onFishing(PlayerFishEvent event) {
            FishingSetting setting = plugin.getFishingSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "fishing", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class EntityKillListener implements Listener {
        @EventHandler
        public void onMobKill(EntityDeathEvent event) {
            EntityKillSetting setting = plugin.getEntityKillSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "entity_kill", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class FurnaceCookListener implements Listener {
        @EventHandler
        public void onFurnaceCook(FurnaceExtractEvent event) {
            FurnaceCookSetting setting = plugin.getFurnaceCookSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "furnace_cook", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class CraftListener implements Listener {
        @EventHandler
        public void onCraft(CraftItemEvent event) {
            CraftSetting setting = plugin.getCraftSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "craft", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class MoveListener implements Listener {
        @EventHandler
        public void onMove(PlayerMoveEvent event) {
            MoveSetting setting = plugin.getMoveSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "move", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class PlayerDeathListener implements Listener {
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            PlayerDeathSetting setting = plugin.getPlayerDeathSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "player_death", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class PlayerDamageListener implements Listener {
        @EventHandler
        public void onPlayerDamage(EntityDamageByEntityEvent event) {
            if (event.getEntity() instanceof Player) {
                PlayerDamageSetting setting = plugin.getPlayerDamageSetting();
                if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                    for (EventSetting.EventData eventData : setting.getEvents()) {
                        handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "player_damage", setting.getEvents().indexOf(eventData));
                    }
                }
            }
        }
    }

    private class PlayerBedListener implements Listener {
        @EventHandler
        public void onPlayerBedEnter(PlayerBedEnterEvent event) {
            PlayerSleepSetting setting = plugin.getPlayerSleepSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "player_sleep", setting.getEvents().indexOf(eventData));
                }
            }
        }

        @EventHandler
        public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
            PlayerSleepSetting setting = plugin.getPlayerSleepSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "player_sleep", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class ItemConsumeListener implements Listener {
        @EventHandler
        public void onItemConsume(PlayerItemConsumeEvent event) {
            ItemConsumeSetting setting = plugin.getItemConsumeSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "item_consume", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class ItemPickupListener implements Listener {
        @EventHandler
        public void onItemPickup(PlayerAttemptPickupItemEvent event) {
            ItemPickupSetting setting = plugin.getItemPickupSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "item_pickup", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class EntityInteractListener implements Listener {
        @EventHandler
        public void onEntityInteract(PlayerInteractEntityEvent event) {
            EntityInteractSetting setting = plugin.getEntityInteractSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "entity_interact", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class ItemEnchantListener implements Listener {
        @EventHandler
        public void onItemEnchant(EnchantItemEvent event) {
            ItemEnchantSetting setting = plugin.getItemEnchantSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "item_enchant", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class ItemRepairListener implements Listener {
        @EventHandler
        public void onItemRepair(PrepareAnvilEvent event) {
            ItemRepairSetting setting = plugin.getItemRepairSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "item_repair", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }

    private class ItemDropListener implements Listener {
        @EventHandler
        public void onItemDrop(PlayerDropItemEvent event) {
            ItemDropSetting setting = plugin.getItemDropSetting();
            if (setting.getEvents() != null && !setting.getEvents().isEmpty()) {
                for (EventSetting.EventData eventData : setting.getEvents()) {
                    handleEvent(event, eventData.resolveConditions(), eventData.resolveActions(), eventData.getConditionEvaluationMode(), eventData, "item_drop", setting.getEvents().indexOf(eventData));
                }
            }
        }
    }
}

