import redteam.*;
import blueteam.*;
import battleship.core.*;
import java.util.*;

/*
 * Duo Battle: Red Team vs Blue Team
 * Win the 2v2 battle against the other team.
 */
public class Main extends Game {

	public static void main(String[] args) {
		int seed = 42;
		if (args.length >= 1) {
			seed = Integer.parseInt(args[0]);
		}
		Game mission = Main.launch(seed);
		mission.run();
	}

	public static Game launch(int seed) {
		Game mission = new Main();
		mission.setSeed(seed);
		mission.setArenaFile("./arena.txt");
		mission.setTurnFile("./turns.txt");
		mission.setLogFile("./logs.txt");
		return mission;
	}
	
	public Main() {
		Arena arena = initializeArena();
		setArena(arena);
	}
	
	@Override
	public String getObjective() {
		return "Mission Objective: Win the 2v2 battle against the other team.";
	}

	private List<Ship> redTeam = new ArrayList<Ship>();
	private List<Ship> blueTeam = new ArrayList<Ship>();
	
	@Override
	public Arena initializeArena() {
		Arena arena = new Arena(10, 10);
		int[][] spawns = {
			{0, 0},
			{0, 9},
			{9, 0},
			{9, 9}
		};
	
		// add ship and team here
		Ship scarlet = new ScarletShip();
		setShipTeam(scarlet, "Red Team");
		setShipColor(scarlet, "#ff291e");
		// spawnShip(arena, x, y, ship);
		spawnShip(arena, spawns[0][0], spawns[0][1], scarlet);
		redTeam.add(scarlet);

		Ship crimson = new CrimsonShip();
		setShipTeam(crimson, "Red Team");
		setShipColor(crimson, "#ce1059");
		spawnShip(arena, spawns[1][0], spawns[1][1], crimson);
		redTeam.add(crimson);

		Ship azure = new AzureShip();
		setShipTeam(azure, "Blue Team");
		setShipColor(azure, "#1c89ff");
		spawnShip(arena, spawns[2][0], spawns[2][1], azure);
		blueTeam.add(azure);

		Ship teal = new TealShip();
		setShipTeam(teal, "Blue Team");
		setShipColor(teal, "#149eba");
		spawnShip(arena, spawns[3][0], spawns[3][1], teal);
		blueTeam.add(teal);

		return arena;
	}

	private int countSunkShips(List<Ship> team) {
		int sunkCount = 0;
		for (Ship ship : team) {
			if (ship.isSunk()) {
				sunkCount++;
			}
		}
		return sunkCount;
	}

	private boolean isTeamSunk(List<Ship> team) {
		int sunkCount = countSunkShips(team);
		boolean allSunk = sunkCount == team.size();
		return allSunk;
	}
	
	@Override
	public boolean isCompleted() {
		boolean redSunk = isTeamSunk(redTeam);
		boolean blueSunk = isTeamSunk(blueTeam);
		return redSunk || blueSunk;
	}
	
	@Override
	public String getResults() {
		List<Ship> sunk = new ArrayList<Ship>();
		List<Ship> allShips = new ArrayList<Ship>(redTeam);
		allShips.addAll(blueTeam);
		for (Ship ship : allShips) {
			if (ship.isSunk()) {
				sunk.add(ship);
			}
		}
		// Score = number of enemy ships sunk
		int redScore = countSunkShips(blueTeam);
		int blueScore = countSunkShips(redTeam);
		String res = "";
		if (redScore == blueScore) {
			res += "Battle was a draw.";
		} else if (redScore > blueScore) {
			res += "Red Team wins.";
		} else {
			res += "Blue Team wins.";
		}
		res += String.format(" Ships Sunk: Red (%d) - Blue (%d)", redScore, blueScore);
		for (Ship ship : sunk) {
			res += "\n";
			res += "- " + ship.getName() + " sunk by " + ship.getSunkBy().getName() + ".";
		}
		boolean isDraw = redScore == blueScore;
		// if (!isDraw) {
		// 	Helper.writeFileLine("./OUTPUT", "100");
		// } else {
		// 	Helper.writeFileLine("./OUTPUT", "0");
		// }
		return res;
	}

	@Override
	public void run() {
		this.runMission(getArena());
	}
   
}