package eu.mikart.panoptic.listener;

import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.Condition;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

import eu.mikart.panoptic.config.ConfigProvider;
import eu.mikart.panoptic.config.event.BlockBreakSetting;

public class EventfulManager {
  private final ConfigProvider configProvider;

  public EventfulManager(ConfigProvider configProvider) {
    this.configProvider = configProvider;
  }

  public void initialize() {
    Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), Bukkit.getPluginManager().getPlugin("Panoptic"));
  }

  private BlockBreakSetting.Inner getBlockBreakInner() {
    BlockBreakSetting setting = configProvider.getBlockBreakSetting();
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
      BlockBreakSetting.Inner inner = getBlockBreakInner();
      if (inner != null) {
        handleEvent(event, inner.resolveConditions(), inner.resolveActions());
      }
    }
  }
}
