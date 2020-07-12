package nl.dantevg.afkscoreboard;

import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin implements Listener {
	
	private Objective objective;
	
	@Override
	public void onEnable() {
		// Register events
		this.getServer().getPluginManager().registerEvents(this, this);
		
		// Add afk objective
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getMainScoreboard();
		this.objective = scoreboard.getObjective("afk");
		if (this.objective == null) {
			// afk objective not present yet (probably first time set up)
			this.objective = scoreboard.registerNewObjective("afk", "dummy", "Is AFK");
		}
	}
	
	@EventHandler
	public void onAfkStatusChange(AfkStatusChangeEvent event) {
		// This function gets called whenever the afk status of a player changes
		
		Player player = event.getAffected().getBase();
		Score score = objective.getScore(player.getName());
		
		// Set the player's score on the scoreboard to 1 if afk, to 0 if not
		score.setScore(event.getValue() ? 1 : 0);
	}
	
}
