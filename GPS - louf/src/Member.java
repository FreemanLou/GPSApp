/**
 * Member element that is in a way
 */

/**
 * @author freeman
 *
 */
public class Member {
    private String type;
    private String ref;
    private String role;
    
    public Member(String type, String ref, String role) {
	super();
	this.type = type;
	this.ref = ref;
	this.role = role;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the ref
     */
    public String getRef() {
        return ref;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((ref == null) ? 0 : ref.hashCode());
	result = prime * result + ((role == null) ? 0 : role.hashCode());
	result = prime * result + ((type == null) ? 0 : type.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Member other = (Member) obj;
	if (ref == null) {
	    if (other.ref != null)
		return false;
	} else if (!ref.equals(other.ref))
	    return false;
	if (role == null) {
	    if (other.role != null)
		return false;
	} else if (!role.equals(other.role))
	    return false;
	if (type == null) {
	    if (other.type != null)
		return false;
	} else if (!type.equals(other.type))
	    return false;
	return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Member [type=");
	builder.append(type);
	builder.append(", ref=");
	builder.append(ref);
	builder.append(", role=");
	builder.append(role);
	builder.append("]");
	builder.append("\n");
	return builder.toString();
    }
}
