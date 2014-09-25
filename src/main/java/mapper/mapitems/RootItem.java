package mapper.mapitems;

public class RootItem<T extends MapItem> {

	private T t;

	public RootItem() {
		t = null;
	}

	public RootItem(T t) {
		this.t = t;
	}

	public T get() {
		return t;
	}

	public void set(T t) {
		this.t = t;
	}
}
