/*
 * Copyright (c) 2010, Bart Kiers
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.openimaj.math.geometry.shape.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.openimaj.math.geometry.point.Point2d;
import org.openimaj.math.geometry.shape.Polygon;

/**
 * Graham Scan convex hull algorithm, based on the implementation by <a
 * href="https://github.com/bkiers/GrahamScan">Bart Kiers</a>.
 * 
 * @author Jonathon Hare (jsh2@ecs.soton.ac.uk)
 * @author Bart Kiers
 */
public final class GrahamScan {
	/**
	 * An enum denoting a directional-turn between 3 Point2ds (vectors).
	 */
	protected static enum Turn {
		CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR
	}

	/**
	 * Returns true iff all Point2ds in <code>Point2ds</code> are collinear.
	 * 
	 * @param Point2ds
	 *            the list of Point2ds.
	 * @return true iff all Point2ds in <code>Point2ds</code> are collinear.
	 */
	protected static boolean areAllCollinear(List<Point2d> Point2ds) {

		if (Point2ds.size() < 2) {
			return true;
		}

		final Point2d a = Point2ds.get(0);
		final Point2d b = Point2ds.get(1);

		for (int i = 2; i < Point2ds.size(); i++) {

			final Point2d c = Point2ds.get(i);

			if (getTurn(a, b, c) != Turn.COLLINEAR) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the convex hull of the Point2ds created from the list
	 * <code>Point2ds</code>. Note that the first and last Point2d in the
	 * returned <code>List&lt;java.awt.Point2d&gt;</code> are the same Point2d.
	 * 
	 * @param Point2ds
	 *            the list of Point2ds.
	 * @return the convex hull of the Point2ds created from the list
	 *         <code>Point2ds</code>.
	 */
	public static Polygon getConvexHull(List<Point2d> Point2ds) {

		final List<Point2d> sorted = new ArrayList<Point2d>(getSortedPoint2dSet(Point2ds));

		if (sorted.size() < 3) {
			return new Polygon(Point2ds);
		}

		if (areAllCollinear(sorted)) {
			return new Polygon(Point2ds);
		}

		final Stack<Point2d> stack = new Stack<Point2d>();
		stack.push(sorted.get(0));
		stack.push(sorted.get(1));

		for (int i = 2; i < sorted.size(); i++) {

			final Point2d head = sorted.get(i);
			final Point2d middle = stack.pop();
			final Point2d tail = stack.peek();

			final Turn turn = getTurn(tail, middle, head);

			switch (turn) {
			case COUNTER_CLOCKWISE:
				stack.push(middle);
				stack.push(head);
				break;
			case CLOCKWISE:
				i--;
				break;
			case COLLINEAR:
				stack.push(head);
				break;
			}
		}

		// close the hull
		stack.push(sorted.get(0));

		return new Polygon(stack);
	}

	/**
	 * Returns the Point2ds with the lowest y coordinate. In case more than 1
	 * such Point2d exists, the one with the lowest x coordinate is returned.
	 * 
	 * @param Point2ds
	 *            the list of Point2ds to return the lowest Point2d from.
	 * @return the Point2ds with the lowest y coordinate. In case more than 1
	 *         such Point2d exists, the one with the lowest x coordinate is
	 *         returned.
	 */
	protected static Point2d getLowestPoint2d(List<Point2d> Point2ds) {

		Point2d lowest = Point2ds.get(0);

		for (int i = 1; i < Point2ds.size(); i++) {

			final Point2d temp = Point2ds.get(i);

			if (temp.getY() < lowest.getY() || (temp.getY() == lowest.getY() && temp.getX() < lowest.getX())) {
				lowest = temp;
			}
		}

		return lowest;
	}

	/**
	 * Returns a sorted set of Point2ds from the list <code>Point2ds</code>. The
	 * set of Point2ds are sorted in increasing order of the angle they and the
	 * lowest Point2d <tt>P</tt> make with the x-axis. If two (or more) Point2ds
	 * form the same angle towards <tt>P</tt>, the one closest to <tt>P</tt>
	 * comes first.
	 * 
	 * @param Point2ds
	 *            the list of Point2ds to sort.
	 * @return a sorted set of Point2ds from the list <code>Point2ds</code>.
	 * @see GrahamScan#getLowestPoint2d(java.util.List)
	 */
	protected static Set<Point2d> getSortedPoint2dSet(List<Point2d> Point2ds) {

		final Point2d lowest = getLowestPoint2d(Point2ds);

		final TreeSet<Point2d> set = new TreeSet<Point2d>(new Comparator<Point2d>() {
			@Override
			public int compare(Point2d a, Point2d b) {

				if (a == b || a.equals(b)) {
					return 0;
				}

				final double thetaA = Math.atan2((long) a.getY() - lowest.getY(), (long) a.getX() - lowest.getX());
				final double thetaB = Math.atan2((long) b.getY() - lowest.getY(), (long) b.getX() - lowest.getX());

				if (thetaA < thetaB) {
					return -1;
				}
				else if (thetaA > thetaB) {
					return 1;
				}
				else {
					// collinear with the 'lowest' Point2d, let the Point2d
					// closest
					// to it come first

					// use longs to guard against int-over/underflow
					final double distanceA = Math.sqrt((((long) lowest.getX() - a.getX()) * ((long) lowest.getX() - a
							.getX())) +
							(((long) lowest.getY() - a.getY()) * ((long) lowest.getY() - a.getY())));
					final double distanceB = Math.sqrt((((long) lowest.getX() - b.getX()) * ((long) lowest.getX() - b
							.getX())) +
							(((long) lowest.getY() - b.getY()) * ((long) lowest.getY() - b.getY())));

					if (distanceA < distanceB) {
						return -1;
					}
					else {
						return 1;
					}
				}
			}
		});

		set.addAll(Point2ds);

		return set;
	}

	/**
	 * Returns the GrahamScan#Turn formed by traversing through the ordered
	 * Point2ds <code>a</code>, <code>b</code> and <code>c</code>. More
	 * specifically, the cross product <tt>C</tt> between the 3 Point2ds
	 * (vectors) is calculated:
	 * 
	 * <tt>(b.getX()-a.getX() * c.getY()-a.getY()) - (b.getY()-a.getY() * c.getX()-a.getX())</tt>
	 * 
	 * and if <tt>C</tt> is less than 0, the turn is CLOCKWISE, if <tt>C</tt> is
	 * more than 0, the turn is COUNTER_CLOCKWISE, else the three Point2ds are
	 * COLLINEAR.
	 * 
	 * @param a
	 *            the starting Point2d.
	 * @param b
	 *            the second Point2d.
	 * @param c
	 *            the end Point2d.
	 * @return the GrahamScan#Turn formed by traversing through the ordered
	 *         Point2ds <code>a</code>, <code>b</code> and <code>c</code>.
	 */
	protected static Turn getTurn(Point2d a, Point2d b, Point2d c) {

		// use longs to guard against int-over/underflow
		final double crossProduct = ((b.getX() - a.getX()) * (c.getY() - a.getY())) -
				((b.getY() - a.getY()) * (c.getX() - a.getX()));

		if (crossProduct > 0) {
			return Turn.COUNTER_CLOCKWISE;
		}
		else if (crossProduct < 0) {
			return Turn.CLOCKWISE;
		}
		else {
			return Turn.COLLINEAR;
		}
	}
}
