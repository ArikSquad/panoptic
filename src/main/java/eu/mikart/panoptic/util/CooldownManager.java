package eu.mikart.panoptic.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import eu.mikart.panoptic.config.EventSetting;

public class CooldownManager {
    private final Map<String, Long> globalCooldowns = new ConcurrentHashMap<>();
    private final Map<String, Map<UUID, Long>> playerCooldowns = new ConcurrentHashMap<>();

    public boolean isOnCooldown(Event event, EventSetting.EventData eventData, String eventType, int eventIndex, Player player) {
        long currentTime = System.currentTimeMillis();
        String cooldownKey = getCooldownKey(eventType, eventIndex);

        if (isGlobalCooldownActive(eventData, cooldownKey, currentTime)) {
            return true;
        }

        return isPlayerCooldownActive(eventData, cooldownKey, currentTime, player);
    }

    public void setCooldowns(Event event, EventSetting.EventData eventData, String eventType, int eventIndex, Player player) {
        long currentTime = System.currentTimeMillis();
        String cooldownKey = getCooldownKey(eventType, eventIndex);

        setGlobalCooldown(eventData, cooldownKey, currentTime);
        setPlayerCooldown(eventData, cooldownKey, currentTime, player);
    }

    public long getRemainingCooldown(String eventType, int eventIndex, UUID playerId, EventSetting.EventData eventData) {
        long currentTime = System.currentTimeMillis();
        String cooldownKey = getCooldownKey(eventType, eventIndex);

        long globalRemaining = getGlobalRemainingCooldown(eventData, cooldownKey, currentTime);
        if (globalRemaining > 0) {
            return globalRemaining;
        }

        return getPlayerRemainingCooldown(eventData, cooldownKey, currentTime, playerId);
    }

    private boolean isGlobalCooldownActive(EventSetting.EventData eventData, String cooldownKey, long currentTime) {
        if (eventData.globalCooldown() == null) {
            return false;
        }

        Long lastGlobalExecution = globalCooldowns.get(cooldownKey);
        return lastGlobalExecution != null && currentTime - lastGlobalExecution < eventData.globalCooldown();
    }

    private boolean isPlayerCooldownActive(EventSetting.EventData eventData, String cooldownKey, long currentTime, Player player) {
        if (eventData.perPlayerCooldown() == null || player == null) {
            return false;
        }

        Map<UUID, Long> playerMap = playerCooldowns.get(cooldownKey);
        if (playerMap == null) {
            return false;
        }

        Long lastPlayerExecution = playerMap.get(player.getUniqueId());
        return lastPlayerExecution != null && currentTime - lastPlayerExecution < eventData.perPlayerCooldown();
    }

    private void setGlobalCooldown(EventSetting.EventData eventData, String cooldownKey, long currentTime) {
        if (eventData.globalCooldown() != null) {
            globalCooldowns.put(cooldownKey, currentTime);
        }
    }

    private void setPlayerCooldown(EventSetting.EventData eventData, String cooldownKey, long currentTime, Player player) {
        if (eventData.perPlayerCooldown() != null && player != null) {
            Map<UUID, Long> playerMap = playerCooldowns.computeIfAbsent(cooldownKey, k -> new ConcurrentHashMap<>());
            playerMap.put(player.getUniqueId(), currentTime);
        }
    }

    private long getGlobalRemainingCooldown(EventSetting.EventData eventData, String cooldownKey, long currentTime) {
        if (eventData.globalCooldown() == null) {
            return 0;
        }

        Long lastGlobalExecution = globalCooldowns.get(cooldownKey);
        if (lastGlobalExecution == null) {
            return 0;
        }

        long remaining = eventData.globalCooldown() - (currentTime - lastGlobalExecution);
        return Math.max(0, remaining);
    }

    private long getPlayerRemainingCooldown(EventSetting.EventData eventData, String cooldownKey, long currentTime, UUID playerId) {
        if (eventData.perPlayerCooldown() == null || playerId == null) {
            return 0;
        }

        Map<UUID, Long> playerMap = playerCooldowns.get(cooldownKey);
        if (playerMap == null) {
            return 0;
        }

        Long lastPlayerExecution = playerMap.get(playerId);
        if (lastPlayerExecution == null) {
            return 0;
        }

        long remaining = eventData.perPlayerCooldown() - (currentTime - lastPlayerExecution);
        return Math.max(0, remaining);
    }

    private String getCooldownKey(String eventType, int eventIndex) {
        return eventType + "_" + eventIndex;
    }
}
