package dev.mqzn.dmar.core.board.api;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import dev.mqzn.dmar.core.events.AssembleBoardCreateEvent;
import dev.mqzn.dmar.listeners.RegisterListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Assemble {

	private final JavaPlugin plugin;

	private final AssembleAdapter adapter;
	private AssembleThread thread;
	private RegisterListener listeners;
	private final AssembleStyle assembleStyle = AssembleStyle.MODERN;

	private final Map<UUID, AssembleBoard> boards;

	public Assemble(JavaPlugin plugin, AssembleAdapter adapter) {
		if (plugin == null) {
			throw new RuntimeException("Assemble can not be instantiated without a plugin instance!");
		}

		this.plugin = plugin;
		this.adapter = adapter;
		this.boards = new ConcurrentHashMap<>();

		this.setup();
	}

	/**
	 * Setup Assemble.
	 */
	public void setup() {

		// Ensure that the thread has stopped running.
		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		// Register new boards for existing online players.
		for (Player player : Bukkit.getOnlinePlayers()) {
			// Make sure it doesn't double up.
			AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(player);

			Bukkit.getPluginManager().callEvent(createEvent);
			if (createEvent.isCancelled()) {
				return;
			}

			getBoards().putIfAbsent(player.getUniqueId(), new AssembleBoard(player, this));
		}

		// Start Thread.
		this.thread = new AssembleThread(this);
	}

	/**
	 *
	 */
	public void cleanup() {
		// Stop thread.
		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		// Unregister listeners.
		if (listeners != null) {
			HandlerList.unregisterAll(listeners);
			listeners = null;
		}

		// Destroy player scoreboards.
		for (UUID uuid : getBoards().keySet()) {
			Player player = Bukkit.getPlayer(uuid);

			if (player == null || !player.isOnline()) {
				continue;
			}

			getBoards().remove(uuid);
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}

	public Map<UUID, AssembleBoard> getBoards() {
		return boards;
	}

	public boolean isDebugMode() {
		return true;
	}

	public boolean isHook() {
		return false;
	}

	public AssembleAdapter getAdapter() {
		return adapter;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public long getTicks() {
		return 2;
	}

	public AssembleStyle getAssembleStyle() {
		return assembleStyle;
	}
}
