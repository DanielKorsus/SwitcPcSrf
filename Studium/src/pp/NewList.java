package pp;

public interface NewList<T> {
	public T get(int i);

	public void add(T e);

	public void mod(int i, T e);

	public default T inspect(T e) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException ex) {
			// ... nothing ...
		}
		return e;
	}
}
