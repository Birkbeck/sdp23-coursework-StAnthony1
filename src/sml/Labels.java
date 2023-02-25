package sml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: write a JavaDoc for the class

/**
 *
 * @author ...
 */
public final class Labels{
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 */
	public void addLabel(String label, int address) throws IOException {
		Objects.requireNonNull(label);
		if (labels.containsKey(label)){
			Throwable msg = new Throwable("duplicate label in instruction set");
			throw new IOException(msg);
		}
		labels.put(label, address);
	}

	/**
	 * Returns the address associated with the label.
	 *
	 * @param label the label
	 * @return the address the label refers to
	 */
	public int getAddress(String label) throws NullPointerException{
		// TODO: Where can NullPointerException be thrown here?
		//       Null pointer could be thrown to JnzInstruction.execute(),
		//		the third arg passed is a String which is not a Key in the instance of
		//		Machine's Labels Field.
		return labels.get(label);
	}

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}

	/**
	 * Tests for equal type and equal membership of labels map
	 * @param o any Object
	 * @return Boolean
	 */
	@Override
	public boolean equals(Object o){
		if (o instanceof Labels other){
			return this.labels.equals(other.labels);
		}
		return false;
	}

	@Override
	public int hashCode(){
		return labels.hashCode();
	}

	/**
	 * Gives String representation of this instance
	 *
	 * @return the string representation of the labels map
	 */
	@Override
	public String toString() {
		return labels.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> e.getKey() + " -> " + e.getValue())
				.collect(Collectors.joining(",", "[", "]")) ;
	}

}
