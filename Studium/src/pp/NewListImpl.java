package pp;

public class NewListImpl<T> implements NewList<T> {
	/*
	 * Statt *synchronized* als Schlüsselwort an den Methoden wird hier eine private
	 * Instanzvariable zum Synchronisieren verwendet, damit niemand von außen an
	 * derselben Variable einen Lock setzen kann, um Verklemmungen zu vermeiden.
	 * 
	 */
	private final Object intrinsicLock = new Object();

	private class ListElement<U> {
		private U element;
		private ListElement<U> prev;
		private final ListElement<U> next;

		private ListElement(final U element, final ListElement<U> prev, final ListElement<U> next) {
			this.element = element;
			this.prev = prev;
			this.next = next;
		}
	}

	private ListElement<T> first;

	public NewListImpl() {
		this.first = null;
	}

	@Override
	public T get(final int i) {
		synchronized (this.intrinsicLock) {
			int j = 0;
			ListElement<T> ptr = this.first;
			while (j++ < i) {
				ptr = ptr.next;
			}
			return inspect(ptr.element);
		}
	}

	@Override
	public void add(final T e) {
		synchronized (this.intrinsicLock) {
			final ListElement<T> insert = new ListElement<>(e, null, this.first);
			if (this.first != null) {
				this.first.prev = insert;
			}
			this.first = insert;
		}
	}

	@Override
	public void mod(final int i, final T e) {
		synchronized (this.intrinsicLock) {
			int j = 0;
			ListElement<T> ptr = this.first;
			while (j++ < i) {
				ptr = ptr.next;
			}
			ptr.element = e;
		}
	}
}
