/*******************************************************************************
 * JetUML - A desktop application for fast UML diagramming.
 *
 * Copyright (C) 2018 by the contributors of the JetUML project.
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

package ca.mcgill.cs.jetuml.diagram.builder.constraints;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.cs.jetuml.JavaFXLoader;
import ca.mcgill.cs.jetuml.diagram.ClassDiagram;
import ca.mcgill.cs.jetuml.diagram.edges.DependencyEdge;
import ca.mcgill.cs.jetuml.diagram.edges.GeneralizationEdge;
import ca.mcgill.cs.jetuml.diagram.nodes.ClassNode;
import ca.mcgill.cs.jetuml.geom.Point;

public class TestClassDiagramEdgeConstraints
{
	private ClassDiagram aDiagram;
	private ClassNode aNode1;
	private ClassNode aNode2;
	private DependencyEdge aEdge1;
	private GeneralizationEdge aGen1;
	
	/**
	 * Load JavaFX toolkit and environment.
	 */
	@BeforeClass
	@SuppressWarnings("unused")
	public static void setupClass()
	{
		JavaFXLoader loader = JavaFXLoader.instance();
	}
	
	@Before
	public void setUp()
	{
		aDiagram = new ClassDiagram();
		aNode1 = new ClassNode();
		aNode2 = new ClassNode();
		aEdge1 = new DependencyEdge();
		aGen1 = new GeneralizationEdge();
	}
	
	private void createDiagram()
	{
		aNode2.moveTo(new Point(0,100));
		aDiagram.addRootNode(aNode1);
		aDiagram.addRootNode(aNode2);
	}
	
	@Test
	public void testNoSelfGeneralizationNotAGeneralizationEdge()
	{
		createDiagram();
		assertTrue(ClassDiagramEdgeConstraints.noSelfGeneralization(aEdge1, aNode1, aNode2).satisfied());
		assertTrue(ClassDiagramEdgeConstraints.noSelfGeneralization(aEdge1, aNode1, aNode1).satisfied());
	}
	
	@Test
	public void testNoSelfGeneralizationGeneralizationEdge()
	{
		createDiagram();
		assertTrue(ClassDiagramEdgeConstraints.noSelfGeneralization(aGen1, aNode1, aNode2).satisfied());
		assertFalse(ClassDiagramEdgeConstraints.noSelfGeneralization(aGen1, aNode1, aNode1).satisfied());
	}
}
