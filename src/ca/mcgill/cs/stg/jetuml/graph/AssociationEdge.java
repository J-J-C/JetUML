/*******************************************************************************
 * JetUML - A desktop application for fast UML diagramming.
 *
 * Copyright (C) 2015 by the contributors of the JetUML project.
 *
 * See: https://github.com/prmr/JetUML
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

/**
 * @author Martin P. Robillard
 */

package ca.mcgill.cs.stg.jetuml.graph;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import ca.mcgill.cs.stg.jetuml.framework.ArrowHead;
import ca.mcgill.cs.stg.jetuml.framework.BentStyle;

/**
 *  An edge that that represents a UML association, with optional 
 *  labels and directionality.
 */
public class AssociationEdge extends ClassRelationshipEdge2
{
	public enum Directionality {None, Start, End, Both}
	
	private Directionality aDirectionality = Directionality.None;
	
	/**
	 * Creates an association edge with no labels
	 * and no directionality
	 */
	public AssociationEdge()
	{}
	
	public void setDirectionality( Directionality pDirectionality )
	{
		aDirectionality = pDirectionality;
	}
	
	public Directionality getDirectionality()
	{
		return aDirectionality;
	}
	
	@Override
	protected ArrowHead obtainStartArrowHead()
	{
		if( aDirectionality == Directionality.Both || aDirectionality == Directionality.Start )
		{
			return ArrowHead.V;
		}
		else
		{
			return ArrowHead.NONE;
		}
	}
	
	@Override
	protected ArrowHead obtainEndArrowHead()
	{
		if( aDirectionality == Directionality.Both || aDirectionality == Directionality.End )
		{
			return ArrowHead.V;
		}
		else
		{
			return ArrowHead.NONE;
		}
	}
	
	@Override
	public ArrayList<Point2D> getPoints()
	{
		return BentStyle.HVH.getPath(getStart().getBounds(), getEnd().getBounds());
   }
}
