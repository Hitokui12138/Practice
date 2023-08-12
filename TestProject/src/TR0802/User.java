package TR0802;

public class User {
	String name;
	int age;
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof User) {
			User u2 = (User)obj;
			return this.name.equals(u2.name) && this.age == u2.age;
		}
		return false;
	}
	
}
