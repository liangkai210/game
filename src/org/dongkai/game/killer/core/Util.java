package org.dongkai.game.killer.core;

import java.util.Random;
import java.util.Set;

public class Util {

	public static boolean isEmpty(String input) {
		return (input == null || input.length() == 0 || input.trim().length() == 0);
	}

	public static int getRandom(Set<Integer> set, int bound) {
		while (true) {
			int result = new Random().nextInt(bound);
			if (!set.contains(result)) {
				set.add(result);
				return result;
			}
		}
	}
}
