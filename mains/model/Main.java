package model;

public class Main {
	public static void main(String[] args) {
		BlockWorld bw = BlockWorld.getInstance();
		World w = bw.createWorld(20, 20, "World", "Juan");
		System.out.println(bw.showPlayerInfo(w.getPlayer()));
	}
}
