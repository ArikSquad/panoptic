package eu.mikart.panoptic.listener;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.config.event.*;
import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.Condition;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

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
}
