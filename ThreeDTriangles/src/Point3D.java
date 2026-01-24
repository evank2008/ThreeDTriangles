
public class Point3D {
public double x;
public double y;
public double z;
public Point3D() {
	x=y=z=0;
}
public Point3D(double x, double y, double z) {
	this.x=x;
	this.y=y;
	this.z=z;
}
public Point3D(Point3D p) {
	x=p.x;
	y=p.y;
	z=p.z;
}
public double getMagnitude() {
	//with respect to (0,0,0)
	return Math.sqrt(x*x+y*y+z*z);
}
public double getDistanceFrom(Point3D p) {
	return this.subtract(p).getMagnitude();
}
public Point3D subtract(Point3D p) {
	Point3D pp = new Point3D(this);
	pp.x-=p.x;
	pp.y-=p.y;
	pp.z-=p.z;
	return pp;
}
public Point3D getUnitVector() {
	//with respect to (0,0,0)
	Point3D p = new Point3D(this);
	double m = getMagnitude();
	if (m == 0) {
        throw new ArithmeticException("Cannot normalize zero-length vector");
    }
	p.x/=m;
	p.y/=m;
	p.z/=m;
	return p;
}
public Point3D getUnitVectorFrom(Point3D p) {
	//pointing from p to this
	return this.subtract(p).getUnitVector();
}
public double dot(Point3D p) {
	return p.x*x+p.y*y+p.z*z;
}
}
