/*
 * GAMA - V1.4 http://gama-platform.googlecode.com
 * 
 * (c) 2007-2011 UMI 209 UMMISCO IRD/UPMC & Partners (see below)
 * 
 * Developers :
 * 
 * - Alexis Drogoul, UMI 209 UMMISCO, IRD/UPMC (Kernel, Metamodel, GAML), 2007-2012
 * - Vo Duc An, UMI 209 UMMISCO, IRD/UPMC (SWT, multi-level architecture), 2008-2012
 * - Patrick Taillandier, UMR 6228 IDEES, CNRS/Univ. Rouen (Batch, GeoTools & JTS), 2009-2012
 * - Beno�t Gaudou, UMR 5505 IRIT, CNRS/Univ. Toulouse 1 (Documentation, Tests), 2010-2012
 * - Phan Huy Cuong, DREAM team, Univ. Can Tho (XText-based GAML), 2012
 * - Pierrick Koch, UMI 209 UMMISCO, IRD/UPMC (XText-based GAML), 2010-2011
 * - Romain Lavaud, UMI 209 UMMISCO, IRD/UPMC (RCP environment), 2010
 * - Francois Sempe, UMI 209 UMMISCO, IRD/UPMC (EMF model, Batch), 2007-2009
 * - Edouard Amouroux, UMI 209 UMMISCO, IRD/UPMC (C++ initial porting), 2007-2008
 * - Chu Thanh Quang, UMI 209 UMMISCO, IRD/UPMC (OpenMap integration), 2007-2008
 */
package msi.gama.metamodel.shape;

import msi.gama.common.util.GeometryUtils;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.precompiler.GamlAnnotations.getter;
import msi.gama.runtime.IScope;
import msi.gama.util.GamaMap;
import msi.gaml.operators.Maths;
import msi.gaml.types.GamaGeometryType;
import com.vividsolutions.jts.geom.*;

/**
 * AgentLocation.
 * 
 * @author drogoul 11 oct. 07
 */

public class GamaPoint extends Coordinate implements ILocation {

	public GamaPoint(final double xx, final double yy) {
		x = xx;
		y = yy;
		z = 0.0d;
	}

	public GamaPoint(final double xx, final double yy, final double zz) {
		x = xx;
		y = yy;
		z = zz;
	}

	public GamaPoint(final Coordinate coord) {
		x = coord.x;
		y = coord.y;
		if ( !Double.isNaN(coord.z) ) {
			z = coord.z;
		} else {
			z = 0.0d;
		}
	}

	public GamaPoint(final ILocation point) {
		x = point.getX();
		y = point.getY();
		final double zz = point.getZ();
		if ( !Double.isNaN(zz) ) {
			z = zz;
		} else {
			z = 0.0d;
		}

	}

	@Override
	@getter("x")
	public double getX() {
		return x;
	}

	@Override
	@getter("y")
	public double getY() {
		return y;
	}

	@Override
	@getter("z")
	public double getZ() {
		return z;
	}

	@Override
	public void setLocation(final ILocation al) {
		setLocation(al.getX(), al.getY(), al.getZ());
	}

	@Override
	public boolean isPoint() {
		return true;
	}

	@Override
	public boolean isLine() {
		return false;
	}

	
	@Override
	public void setLocation(final double xx, final double yy) {
		x = xx;
		y = yy;
		z = 0.0d;
	}

	@Override
	public void setLocation(final double xx, final double yy, final double zz) {
		x = xx;
		y = yy;
		if ( !Double.isNaN(zz) ) {
			z = zz;
		} else {
			z = 0.0d;
		}
	}

	@Override
	public void setX(final double xx) {
		x = xx;
	}

	@Override
	public void setY(final double yy) {
		y = yy;
	}

	@Override
	public void setZ(final double zz) {
		if ( !Double.isNaN(zz) ) {
			z = zz;
		} else {
			z = 0.0d;
		}
	}

	@Override
	public String toString() {
		return "location[" + x + ";" + y + ";" + z + "]";
	}

	@Override
	public String toGaml() {
		return "{" + x + "," + y + "," + z + "}";
	}

	@Override
	public GamaPoint getLocation() {
		return this;
	}

	@Override
	public String stringValue(final IScope scope) {
		return "{" + x + "," + y + "," + z + "}";
	}

	// @Override
	// public IType type() {
	// return Types.get(IType.POINT);
	// }

	@Override
	public void add(final ILocation loc) {
		x = x + loc.getX();
		y = y + loc.getY();

		final double zz = loc.getZ();
		if ( !Double.isNaN(zz) ) {
			z = z + zz;
		}
	}

	@Override
	public Coordinate toCoordinate() {
		return new Coordinate(x, y, z);
	}

	@Override
	public GamaPoint copy(final IScope scope) {
		return new GamaPoint(x, y, z);
	}

	@Override
	public IShape getGeometry() {
		return GamaGeometryType.createPoint(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see msi.gama.interfaces.IGeometry#setGeometry(msi.gama.util.GamaGeometry)
	 */
	@Override
	public void setGeometry(final IShape g) {
		setLocation(g.getLocation());
	}

	/**
	 * @see msi.gama.interfaces.IGeometry#getInnerGeometry()
	 */
	@Override
	public Geometry getInnerGeometry() {
		return GeometryUtils.factory.createPoint(this);
		// return getGeometry().getInnerGeometry();
	}

	/**
	 * @see msi.gama.interfaces.IGeometry#getEnvelope()
	 */
	@Override
	public Envelope getEnvelope() {
		return new Envelope(this);
	}

	@Override
	public boolean equals(final Object o) {
		if ( o instanceof GamaPoint ) { return equals3D((GamaPoint) o); }
		return false;
	}

	/**
	 * @see msi.gama.interfaces.IGeometry#covers(msi.gama.interfaces.IGeometry)
	 */
	@Override
	public boolean covers(final IShape g) {
		if ( g.isPoint() ) { return g.getLocation().equals(this); }
		return false;
	}

	/**
	 * @see msi.gama.interfaces.IGeometry#euclidianDistanceTo(msi.gama.interfaces.IGeometry)
	 */
	@Override
	public double euclidianDistanceTo(final IShape g) {
		if ( g.isPoint() ) { return euclidianDistanceTo(g.getLocation()); }
		return g.euclidianDistanceTo(this);
	}

	@Override
	public double euclidianDistanceTo(final ILocation p) {
		// FIXME: Need to check the cost of checking if z and p.getZ() are equal to Zero so that we can use
		// this.distance(p.toCoordinate());
		return Maths.hypot(x, p.getX(), y, p.getY(), z, p.getZ());
	}

	/**
	 * @see msi.gama.interfaces.IGeometry#intersects(msi.gama.interfaces.IGeometry)
	 */
	@Override
	public boolean intersects(final IShape g) {
		if ( g.isPoint() ) { return g.getLocation().equals(this); }
		return g.intersects(this);
	}

	@Override
	public boolean crosses(final IShape g) {
		if ( g.isPoint() ) { return false; }
		return g.crosses(this);
	}

	/**
	 * @see msi.gama.interfaces.IGeometry#getAgent()
	 */
	@Override
	public IAgent getAgent() {
		return null;
	}

	/**
	 * @see msi.gama.interfaces.IGeometry#setAgent(msi.gama.interfaces.IAgent)
	 */
	@Override
	public void setAgent(final IAgent agent) {}

	/**
	 * @see msi.gama.interfaces.IGeometry#getPerimeter()
	 */
	@Override
	public double getPerimeter() {
		return 0d;
	}

	/**
	 * @see msi.gama.common.interfaces.IGeometry#setInnerGeometry(com.vividsolutions.jts.geom.Geometry)
	 */
	@Override
	public void setInnerGeometry(final Geometry point) {
		final Coordinate p = point.getCoordinate();
		setLocation(p.x, p.y, p.z);
	}

	/**
	 * @see msi.gama.common.interfaces.IGeometry#dispose()
	 */
	@Override
	public void dispose() {}

	@Override
	public GamaMap getAttributes() {
		return null;
	}

	@Override
	public GamaMap getOrCreateAttributes() {
		return null;
	}

	@Override
	public Object getAttribute(final Object key) {
		return null;
	}

	@Override
	public void setAttribute(final Object key, final Object value) {}

	@Override
	public boolean hasAttribute(final Object key) {
		return false;
	}

}
