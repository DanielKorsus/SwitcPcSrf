package pp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NewListImpl<T> implements NewList<T> {

	/*
	 * Statt *synchronized* als Schlüsselwort an den Methoden wird hier eine
	 * private Instanzvariable zum Synchronisieren verwendet, damit niemand von
	 * außen an derselben Variable einen Lock setzen kann, um Verklemmungen zu
	 * vermeiden.
	 * 
	 */
	private final Object intrinsicLock = new Object();

	private class ListElement<U> {
		private U element;
		private ListElement<U> prev;
		private final ListElement<U> next;
		public ReadWriteLock lock = new ReentrantReadWriteLock();
		public Lock rLock = lock.readLock();
		public Lock wLock = lock.writeLock();

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

		int j = 0;
		ListElement<T> ptr = this.first;
		ptr.rLock.lock();

		try {

			while (j++ < i) {

				if (ptr.next != null) {
					ptr.next.rLock.lock();

					ptr = ptr.next;
					ptr.prev.rLock.unlock();
				}

			}

			return inspect(ptr.element);

		} catch (Exception e) {

			throw new RuntimeException(e);

		} finally {
			ptr.rLock.unlock();
		}
	}

	@Override
	public void add(final T e) {

		final ListElement<T> insert = new ListElement<>(e, null, this.first);

		insert.wLock.lock();

		try {

			if (this.first != null) {
				this.first.wLock.lock();
				this.first.prev = insert;
				this.first.wLock.unlock();
			}

			this.first = insert;

		} finally {

			insert.wLock.unlock();

		}

	}

	@Override
	public void mod(final int i, final T e) {

		int j = 0;
		ListElement<T> ptr = this.first;
		ptr.rLock.lock();

		try {

			while (j++ < i) {
				if (ptr.next != null) {
					ptr.next.rLock.lock();
					ptr = ptr.next;
					ptr.prev.rLock.unlock();
				}
			}

			ptr.element = e;

		} finally {
			ptr.rLock.unlock();
		}

	}

}
