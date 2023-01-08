package pgdp.trials;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TrialOfTheDreams {

	protected TrialOfTheDreams() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Picks the specified {@code lock} using iterative deepening search.
	 * 
	 * @param lock arbitrary {@code Function} that takes a key ({@code byte} array)
	 *             and checks if it opens the lock.
	 * @return {@code byte} array containing the combination to open the lock.
	 */
	public static byte[] lockPick(Function<byte[], Boolean> lock) {
		byte[] result = null;
		int depth = 1;
		do {
			if ((result = lockPick(lock, depth)) != null) {
				break;
			}
			depth++;
		} while (depth < Integer.MAX_VALUE);
		return result;
	}

	/**
	 * Picks the specified {@code lock} up to the specified depth using depth first
	 * search.
	 * 
	 * @param lock   arbitrary {@code Function} that takes a key ({@code byte}
	 *               array) and checks if it opens the lock.
	 * @param maxlen maximum length of the combinations to be checked.
	 * @return {@code byte} array containing the combination to open the lock or
	 *         {@code null} if no such combination exists.
	 */
	public static byte[] lockPick(Function<byte[], Boolean> lock, int maxlen) {
		// TODO
		Function<Byte[], Boolean> n_lock = bytes -> {
			byte[] tempByteArray = new byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				tempByteArray[i] = bytes[i].byteValue();
			}
			return lock.apply(tempByteArray);
		};

		List temp = lockPick(n_lock, new ArrayList<>(), maxlen);

		if (temp == null) {
			return null;
		}

		byte[] output = new byte[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			output[i] = (byte) temp.get(i);
		}

		return output;
	}

	private static List<Byte> lockPick(Function<Byte[], Boolean> lock, List<Byte> key, int maxlen) {
		// TODO
		Byte[] keyArray = key.toArray(new Byte[]{});
		//stop if key.size() equals maxlen
		if (key.size() > maxlen) {
			if (lock.apply(keyArray)) {
				return key;
			}
			return null;
		}
		//byte can have value from -128 to 127
		for (int byteValue = -128; byteValue <= 127; byteValue++) {
				key.add((byte) byteValue);
				List<Byte> possibleSolution = lockPick(lock, key, maxlen);
				if (possibleSolution != null) {
					return possibleSolution;
				}
				key.remove(key.size() - 1);
			}

		return null;
	}

}
