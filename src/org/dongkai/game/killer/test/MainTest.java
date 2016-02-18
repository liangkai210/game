package org.dongkai.game.killer.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dongkai.game.killer.core.Controller;
import org.dongkai.game.killer.core.Player;
import org.dongkai.game.killer.core.Util;

public class MainTest {

	Controller controller = Controller.getController();
	List<String> names = new ArrayList<>();

	public MainTest() {
		addNames();
	}

	public void addNames() {
		names.add("Dong");
		names.add("Kai");
		names.add("Xu");
		names.add("Kun");
		names.add("Snow");
		names.add("Xiong");
		names.add("2");
	}

	public void addUser() {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < names.size(); i++) {
			int index = Util.getRandom(set, names.size());
			controller.addPlayer(names.get(index));
		}
	}

	public void display() {
		for (Player player : Controller.getController().getPlayers().values()) {
			System.out.println(player.toString());
		}
	}

	public void testVote(MainTest test, String name) {
		for (Player player : Controller.getController().getPlayers().values()) {
			player.voteToDeath(Controller.getController().getPlayers().get(name));
		}
		test.controller.killPerson();
	}

	public static void main(String[] args) {
		MainTest test = new MainTest();
		test.addUser();
		test.display();
		System.out.println();
		test.controller.assignStatus();
		test.display();
		System.out.println();
		test.testVote(test, "Xu");
		test.display();
		System.out.println();
		test.testVote(test, "Kun");
		test.display();
		System.out.println();
		test.testVote(test, "2");
		test.display();
		System.out.println();
	}

}
