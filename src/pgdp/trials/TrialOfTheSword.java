package pgdp.trials;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrialOfTheSword {

	protected TrialOfTheSword() {
		throw new UnsupportedOperationException();
	}

	public static class FlatArray<T> {

		private final int[] dims;
		private final T[] values;

		/**
		 * Generate a flattened higher dimensional array.
		 * 
		 * @param clazz type of values stored in the array.
		 * @param dims  length of the individual dimensions.
		 * @throws IllegalArgumentException if any of the dimensions has negative
		 *                                  length.
		 */
		public FlatArray(Class<T> clazz, int... dims) throws IllegalArgumentException {
			int length = 1;
			for (int i : dims) {
				if (i < 0) {
					throw new IllegalArgumentException("Array Length may not be negative");
				}
				length *= i;
			}
			this.dims = dims;
			this.values = generateGenericArray(clazz, length);
		}

		/**
		 * Generates an generic array of type T with the given length.
		 * 
		 * @param <T>
		 * @param clazz  type of values stored in the array.
		 * @param length array size.
		 * @return reference to the generated generic array
		 */
		@SuppressWarnings("unchecked")
		private static <T> T[] generateGenericArray(Class<T> clazz, int length) {
			final T[] arr = (T[]) Array.newInstance(clazz, length);
			return arr;
		}

		/**
		 * Checks if the given indices are valid.
		 * 
		 * @param idxs indices to be checked.
		 * @throws IllegalArgumentException       if number of arguments doesn't match
		 *                                        the dimensions of the array.
		 * @throws ArrayIndexOutOfBoundsException if any index is not within the
		 *                                        boundaries of the corresponding
		 *                                        dimension.
		 */
		private void checkValidIdxs(int... idxs) {
			if (idxs.length != dims.length) {
				throw new IllegalArgumentException("Invalid number of arguments");
			}
			for (int i = 0; i < dims.length; i++) {
				if (idxs[i] < 0 || dims[i] <= idxs[i]) {
					throw new ArrayIndexOutOfBoundsException("Index " + idxs[i] + " for length " + dims[i]);
				}
			}
		}

		/**
		 * Retrieves value stored at the specified position.
		 * 
		 * @param idxs specifies the position of the value in the array. Order of
		 *             indices equivalent to normal array.
		 * @return value value stored at the specified position.
		 * @throws IllegalArgumentException       if number of arguments doesn't match
		 *                                        the dimensions of the array.
		 * @throws ArrayIndexOutOfBoundsException if any index is not within the
		 *                                        boundaries of the corresponding
		 *                                        dimension.
		 */
		public T get(int... idxs) {
			checkValidIdxs(idxs);
			return values[computeIndex(idxs)];
		}

		/**
		 * Stores a value at the specified position.
		 * 
		 * @param value value to be set.
		 * @param idxs  specifies the position of the value in the array. Order of
		 *              indices equivalent to normal array.
		 * @throws ArrayIndexOutOfBoundsException if any index is not within the
		 *                                        boundaries of the corresponding
		 *                                        dimension.
		 */
		public void set(T value, int... idxs) {
			checkValidIdxs(idxs);
			values[computeIndex(idxs)] = value;
		}

		/**
		 * Computes the index within the flattened array of the specified position.
		 * 
		 * @param idxs specifies the position of the value in the array. Order of
		 *             indices equivalent to normal array.
		 * @return index within the flattened array.
		 */
		public int computeIndex(int... idxs) {
			checkValidIdxs(idxs);
			// TODO
			if (dims.length == 1) {
				int[] out = idxs;
				return out[0];
			}
			int[] indexes = idxs;

			//get number from last two dimensions
			int lastTwoNum = indexes[indexes.length - 2] * dims[indexes.length - 1] + indexes[indexes.length - 1] + 1;

			//it's the #lastTwoNum Element of the next indexes dimension
			for (int i = indexes.length - 3; i >= 0; i--) {
				lastTwoNum = lastTwoNum * (indexes[i] + 1);
			}

			lastTwoNum--;

			return lastTwoNum;
		}

		/**
		 * Returns flattened array containing the values stored in this
		 * {@code FlatArray}.
		 * 
		 * @return copy of the {@code values} array.
		 */
		public T[] toArray() {
			return Arrays.copyOf(values, values.length);
		}

		@Override
		public String toString() {
			return "{ dims: " + Arrays.toString(dims) + ";\nvalues: " + Arrays.toString(values) + " }";
		}
	}

	// The following methods might be useful for the mighty lords of the public
	// GitHub repo.

	/**
	 * returns the flattened array of an array with arbitrary dimensions.
	 * 
	 * @param arr higher dimensional array (e.g. new Integer[2][3][4]).
	 * @return flattened array.
	 */
	public static Object[] flatten(Object[] arr) {
		ArrayList<Object> acc = new ArrayList<>();
		return flatten(arr, acc).toArray();
	}

	private static List<Object> flatten(Object[] arr, List<Object> acc) {
		if (arr == null) {
			return null;
		}
		for (var e : arr) {
			if (e == null) {
				acc.add(null);
			} else {
				Class<?> clazz = e.getClass();
				if (clazz.isArray()) {
					flatten((Object[]) e, acc);
				} else {
					acc.add(e);
				}
			}
		}
		return acc;
	}

	/*public static void main(String[] args) {
		FlatArray fa = new FlatArray(TrialOfTheSword.class, new int[]{4, 3, 2});
		int n = fa.computeIndex(2, 1, 1);
		System.out.println(n);
	}*/
}
