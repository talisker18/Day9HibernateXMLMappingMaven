package app.models;

public class Category {
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	private int id;
	private String name;
	
	public Category(String name) {
		super();
		this.name = name;
	}
	
	public Category() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
