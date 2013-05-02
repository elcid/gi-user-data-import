package de.springerprofessional.gi.model;

import java.io.Serializable;

public class GIUserItem implements Serializable {

	private static final long serialVersionUID = 7397411914833953194L;
	private String item;
	private int count;
	private int id;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public GIUserItem(int count, String item) {
		super();
		this.count = count;
		this.item = item;
	}

	public GIUserItem(int count, String item, int id) {
		super();
		this.item = item;
		this.count = count;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GIUserItem other = (GIUserItem) obj;
		if (count != other.count)
			return false;
		if (id != other.id)
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + id;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "[GIUserItem] " + count + " " + item;
	}

}
