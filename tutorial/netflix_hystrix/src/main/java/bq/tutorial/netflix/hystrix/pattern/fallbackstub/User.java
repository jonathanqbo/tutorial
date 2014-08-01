/*
Copyright (c) 2014 (Jonathan Q. Bo) 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package bq.tutorial.netflix.hystrix.pattern.fallbackstub;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at 3:48:21 PM Jul 31, 2014
 *
 */
public class User {

	private String id;
	
	private String name;
	
	private boolean hasRoleX;
	
	private boolean hasRoleY;
	
	private boolean hasRoleZ;

	public User(String id, String name, boolean hasRoleX, boolean hasRoleY,
			boolean hasRoleZ) {
		super();
		this.id = id;
		this.name = name;
		this.hasRoleX = hasRoleX;
		this.hasRoleY = hasRoleY;
		this.hasRoleZ = hasRoleZ;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHasRoleX() {
		return hasRoleX;
	}

	public void setHasRoleX(boolean hasRoleX) {
		this.hasRoleX = hasRoleX;
	}

	public boolean isHasRoleY() {
		return hasRoleY;
	}

	public void setHasRoleY(boolean hasRoleY) {
		this.hasRoleY = hasRoleY;
	}

	public boolean isHasRoleZ() {
		return hasRoleZ;
	}

	public void setHasRoleZ(boolean hasRoleZ) {
		this.hasRoleZ = hasRoleZ;
	}
	
}
