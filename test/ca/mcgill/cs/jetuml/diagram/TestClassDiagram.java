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
package ca.mcgill.cs.jetuml.diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.cs.jetuml.JavaFXLoader;
import ca.mcgill.cs.jetuml.diagram.edges.DependencyEdge;
import ca.mcgill.cs.jetuml.diagram.nodes.ClassNode;
import ca.mcgill.cs.jetuml.diagram.nodes.PackageNode;

/*
 * This class is used to test the methods of the abstract
 * class as well.
 */
public class TestClassDiagram
{
	private Diagram aDiagram;
	private DiagramAccessor aDiagramAccessor;
	private PackageNode aPackageNode1;
	private ClassNode aClassNode1;
	private ClassNode aClassNode2;
	private ClassNode aClassNode3;
	private DependencyEdge aEdge1;
	private DependencyEdge aEdge2;
	private DependencyEdge aEdge3;
	
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
		aDiagramAccessor = new DiagramAccessor(aDiagram);
		aClassNode1 = new ClassNode();
		aClassNode2 = new ClassNode();
		aClassNode3 = new ClassNode();
		aPackageNode1 = new PackageNode();
		aEdge1 = new DependencyEdge();
		aEdge2 = new DependencyEdge();
		aEdge3 = new DependencyEdge();
	}
	
	@Test
	public void testInit()
	{
		assertEquals(0, aDiagramAccessor.getEdges().size());
		assertEquals(0, aDiagramAccessor.getRootNodes().size());
	}
	
	@Test
	public void testNumberOfEdgesEmpty()
	{
		assertEquals(0, aDiagram.numberOfEdges());
	}
	
	@Test
	public void testNumberOfEdgesNotEmpty()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aEdge1.connect(aClassNode1, aClassNode1, aDiagram);
		aDiagram.addEdge(aEdge1);
		aEdge2.connect(aClassNode1, aClassNode2, aDiagram);
		aDiagram.addEdge(aEdge2);
		assertEquals(2, aDiagram.numberOfEdges());
	}
	
	@Test
	public void testIndexOf()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aEdge1.connect(aClassNode1, aClassNode1, aDiagram);
		aDiagram.addEdge(aEdge1);
		aEdge2.connect(aClassNode1, aClassNode2, aDiagram);
		aDiagram.addEdge(aEdge2);
		assertEquals(0, aDiagram.indexOf(aEdge1));
		assertEquals(1, aDiagram.indexOf(aEdge2));
	}
	
	@Test
	public void testAddEdgeIndexEmpty()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aEdge1.connect(aClassNode1, aClassNode1, aDiagram);
		aDiagram.addEdge(0, aEdge1);
		assertEquals(1, aDiagram.numberOfEdges());
		assertEquals(0, aDiagram.indexOf(aEdge1));
	}
	
	@Test
	public void testAddEdgeIndexBefore()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aEdge1.connect(aClassNode1, aClassNode1, aDiagram);
		aDiagram.addEdge(aEdge1);
		aEdge2.connect(aClassNode1, aClassNode2, aDiagram);
		aDiagram.addEdge(0, aEdge2);
		assertEquals(2, aDiagram.numberOfEdges());
		assertSame(aEdge2, aDiagramAccessor.getEdges().get(0));
		assertSame(aEdge1, aDiagramAccessor.getEdges().get(1));
	}
	
	@Test
	public void testAddEdgeIndexAfter()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aEdge1.connect(aClassNode1, aClassNode1, aDiagram);
		aDiagram.addEdge(aEdge1);
		aEdge2.connect(aClassNode1, aClassNode2, aDiagram);
		aDiagram.addEdge(1, aEdge2);
		assertEquals(2, aDiagram.numberOfEdges());
		assertSame(aEdge1, aDiagramAccessor.getEdges().get(0));
		assertSame(aEdge2, aDiagramAccessor.getEdges().get(1));
	}
	
	@Test
	public void testAddRemoveRootNode()
	{
		aDiagram.addRootNode(aClassNode1);
		assertEquals(1, aDiagramAccessor.getRootNodes().size());
		assertSame(aClassNode1, aDiagramAccessor.getRootNodes().get(0));
		aDiagram.addRootNode(aClassNode2);
		assertEquals(2, aDiagramAccessor.getRootNodes().size());
		assertSame(aClassNode2, aDiagramAccessor.getRootNodes().get(1));
		
		aDiagram.removeRootNode(aClassNode2);
		assertEquals(1, aDiagramAccessor.getRootNodes().size());
		assertSame(aClassNode1, aDiagramAccessor.getRootNodes().get(0));
		
		aDiagram.removeRootNode(aClassNode1);
		assertEquals(0, aDiagramAccessor.getRootNodes().size());
	}
	
	@Test
	public void testAddRemoveEdge()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aEdge1.connect(aClassNode1, aClassNode1, aDiagram);
		aDiagram.addEdge(aEdge1);
		assertEquals(1, aDiagramAccessor.getEdges().size());
		aEdge2.connect(aClassNode1, aClassNode2, aDiagram);
		aDiagram.addEdge(aEdge2);
		assertEquals(2, aDiagramAccessor.getEdges().size());
		assertSame(aEdge1, aDiagramAccessor.getEdges().get(0));
		assertSame(aEdge2, aDiagramAccessor.getEdges().get(1));
		aEdge3.connect(aClassNode2, aClassNode1, aDiagram);
		aDiagram.addEdge(aEdge3);
		assertEquals(3, aDiagramAccessor.getEdges().size());
		assertSame(aEdge1, aDiagramAccessor.getEdges().get(0));
		assertSame(aEdge2, aDiagramAccessor.getEdges().get(1));
		assertSame(aEdge3, aDiagramAccessor.getEdges().get(2));
		
		aDiagram.removeEdge(aEdge2);
		assertEquals(2, aDiagramAccessor.getEdges().size());
		assertSame(aEdge1, aDiagramAccessor.getEdges().get(0));
		assertSame(aEdge3, aDiagramAccessor.getEdges().get(1));
		
		aDiagram.removeEdge(aEdge1);
		assertEquals(1, aDiagramAccessor.getEdges().size());
		assertSame(aEdge3, aDiagramAccessor.getEdges().get(0));
	}
	
	@Test
	public void testAllElementsEmpty()
	{
		Iterable<DiagramElement> elements = aDiagram.allElements();
		assertFalse(elements.iterator().hasNext());
	}
	
	@Test
	public void testAllElementsNodesAndEdges()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aEdge1.connect(aClassNode1, aClassNode1, aDiagram);
		aDiagram.addEdge(aEdge1);
		List<DiagramElement> elements = new ArrayList<>();
		for( DiagramElement element : aDiagram.allElements() )
		{
			elements.add(element);
		}
		assertEquals(3, elements.size());
		assertTrue( elements.contains(aClassNode1));
		assertTrue( elements.contains(aClassNode2));
		assertTrue( elements.contains(aEdge1));
	}
	
	@Test
	public void testAllElementsNodesWithChildren()
	{
		aDiagram.addRootNode(aClassNode1);
		aPackageNode1.addChild(aClassNode2);
		aDiagram.addRootNode(aPackageNode1);
		List<DiagramElement> elements = new ArrayList<>();
		for( DiagramElement element : aDiagram.allElements() )
		{
			elements.add(element);
		}
		assertEquals(2, elements.size());
		assertTrue(elements.contains(aClassNode1));
		assertTrue(elements.contains(aPackageNode1));
	}
	
	@Test
	public void testContainsEmpty()
	{
		assertFalse(aDiagram.contains(aClassNode1));
		assertFalse(aDiagram.contains(aEdge1));
	}
	
	@Test
	public void testContainsEdge()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aEdge1.connect(aClassNode1, aClassNode1, aDiagram);
		aDiagram.addEdge(aEdge1);
		aEdge2.connect(aClassNode1, aClassNode2, aDiagram);
		aDiagram.addEdge(aEdge2);
		aEdge3.connect(aClassNode2, aClassNode1, aDiagram);
		assertTrue(aDiagram.contains(aEdge1));
		assertTrue(aDiagram.contains(aEdge2));
		assertFalse(aDiagram.contains(aEdge3));
	}
	
	@Test
	public void testContainsNodeRoot()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		assertTrue(aDiagram.contains(aClassNode1));
		assertTrue(aDiagram.contains(aClassNode2));
		assertFalse(aDiagram.contains(aClassNode3));
	}
	
	@Test
	public void testContainsNodeChild()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aDiagram.addRootNode(aPackageNode1);
		aPackageNode1.addChild(aClassNode3);
		assertTrue(aDiagram.contains(aClassNode1));
		assertTrue(aDiagram.contains(aClassNode2));
		assertTrue(aDiagram.contains(aClassNode3));
		assertTrue(aDiagram.contains(aPackageNode1));
	}
	
	@Test
	public void testContainsNodeChildChild()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aDiagram.addRootNode(aPackageNode1);
		aPackageNode1.addChild(aClassNode3);
		ClassNode child = new ClassNode();
		PackageNode packageNode = new PackageNode();
		packageNode.addChild(child);
		aPackageNode1.addChild(new PackageNode());
		aPackageNode1.addChild(packageNode);
		aPackageNode1.addChild(new ClassNode());
		aPackageNode1.addChild(new PackageNode());
		packageNode.addChild(new ClassNode());
		assertTrue(aDiagram.contains(aClassNode1));
		assertTrue(aDiagram.contains(aClassNode2));
		assertTrue(aDiagram.contains(aClassNode3));
		assertTrue(aDiagram.contains(aPackageNode1));
		assertTrue(aDiagram.contains(child));
		assertTrue(aDiagram.contains(packageNode));
	}
	
	@Test
	public void testEdgesConnectedToEmpty()
	{
		aDiagram.addRootNode(aClassNode1);
		assertTrue(aDiagramAccessor.getEdgesConnectedTo(aClassNode1).isEmpty());
	}
	
	@Test
	public void testEdgesConnectedToTwoEdges()
	{
		aDiagram.addRootNode(aClassNode1);
		aDiagram.addRootNode(aClassNode2);
		aDiagram.addRootNode(aClassNode3);
		aEdge1.connect(aClassNode2, aClassNode2, aDiagram);
		aEdge2.connect(aClassNode2, aClassNode3, aDiagram);
		aEdge3.connect(aClassNode3, aClassNode2, aDiagram);
		aDiagram.addEdge(aEdge1);
		aDiagram.addEdge(aEdge2);
		aDiagram.addEdge(aEdge3);
		assertTrue(aDiagramAccessor.getEdgesConnectedTo(aClassNode1).isEmpty());
		List<Edge> result = aDiagramAccessor.getEdgesConnectedTo(aClassNode2);
		assertEquals(3, result.size());
		result = aDiagramAccessor.getEdgesConnectedTo(aClassNode3);
		assertEquals(2, result.size());
		assertTrue(result.contains(aEdge2));
		assertTrue(result.contains(aEdge3));
	}
}
