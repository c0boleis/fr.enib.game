/**
 * Copyright (c) 2007, Gaudenz Alder
 */
package fr.enib.game.editor.graphe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import fr.enib.game.model.Lien;
import fr.enib.game.model.Model;
import fr.enib.game.model.enums.LienConection;
import fr.enib.game.model.exceptions.LimitLienException;
import fr.enib.game.model.interfaces.IClonableObject;
import fr.enib.game.model.interfaces.ILien;
import fr.enib.game.model.interfaces.INoeud;
import fr.enib.game.model.interfaces.IRemovable;

/**
 * Cells are the elements of the graph model. They represent the state
 * of the groups, vertices and edges in a graph.
 *
 * <h4>Edge Labels</h4>
 * 
 * Using the x- and y-coordinates of a cell's geometry it is
 * possible to position the label on edges on a specific location
 * on the actual edge shape as it appears on the screen. The
 * x-coordinate of an edge's geometry is used to describe the
 * distance from the center of the edge from -1 to 1 with 0
 * being the center of the edge and the default value. The
 * y-coordinate of an edge's geometry is used to describe
 * the absolute, orthogonal distance in pixels from that
 * point. In addition, the mxGeometry.offset is used
 * as a absolute offset vector from the resulting point.
 * 
 * The width and height of an edge geometry are ignored.
 * 
 * To add more than one edge label, add a child vertex with
 * a relative geometry. The x- and y-coordinates of that
 * geometry will have the same semantiv as the above for
 * edge labels.
 */
public class mxCell implements mxICell, Cloneable, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 910211337632342672L;

	/**
	 * Holds the Id. Default is null.
	 */
	protected String id;

	/**
	 * Holds the user object. Default is null.
	 */
	protected Object value;

	/**
	 * Holds the geometry. Default is null.
	 */
	protected mxGeometry geometry;

	/**
	 * Holds the style as a string of the form
	 * stylename[;key=value]. Default is null.
	 */
	protected String style;

	/**
	 * Specifies whether the cell is a vertex or edge and whether it is
	 * connectable, visible and collapsed. Default values are false, false,
	 * true, true and false respectively.
	 */
	protected boolean vertex = false, edge = false, connectable = true,
			visible = true, collapsed = false;

	/**
	 * Reference to the parent cell and source and target terminals for edges.
	 */
	protected mxICell parent, source, target;

	/**
	 * Holds the child cells and connected edges.
	 */
	protected List<Object> children, edges;

	/**
	 * Constructs a new cell with an empty user object.
	 */
	public mxCell()
	{
		this(null);
	}

	/**
	 * Constructs a new cell for the given user object.
	 * 
	 * @param value
	 *   Object that represents the value of the cell.
	 */
	public mxCell(Object value)
	{
		this(value, null, null);
	}

	/**
	 * Constructs a new cell for the given parameters.
	 * 
	 * @param value Object that represents the value of the cell.
	 * @param geometry Specifies the geometry of the cell.
	 * @param style Specifies the style as a formatted string.
	 */
	public mxCell(Object value, mxGeometry geometry, String style)
	{
		setValue(value);
		setGeometry(geometry);
		setStyle(style);
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getId()
	 */
	public String getId()
	{
		return id;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setId(String)
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getValue()
	 */
	public Object getValue()
	{
		return value;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setValue(Object)
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getGeometry()
	 */
	public mxGeometry getGeometry()
	{
		return geometry;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setGeometry(fr.enib.game.editor.graphe.model.mxGeometry)
	 */
	public void setGeometry(mxGeometry geometry)
	{
		this.geometry = geometry;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getStyle()
	 */
	public String getStyle()
	{
		return style;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setStyle(String)
	 */
	public void setStyle(String style)
	{
		this.style = style;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#isVertex()
	 */
	public boolean isVertex()
	{
		return vertex;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setVertex(boolean)
	 */
	public void setVertex(boolean vertex)
	{
		this.vertex = vertex;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#isEdge()
	 */
	public boolean isEdge()
	{
		return edge;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setEdge(boolean)
	 */
	public void setEdge(boolean edge)
	{
		this.edge = edge;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#isConnectable(fr.enib.game.model.enums.LienConection)
	 */
	@Override
	public boolean isConnectable(LienConection conection)
	{
		if(value instanceof INoeud){
			INoeud noeud = (INoeud) value;
			switch (conection) {
			case entrant:
				return noeud.lienEntrantSontConectables();
			case sortant:
				return noeud.lienSortantSontConectables();
			default:
				return connectable;
			}
		}
		return connectable;
	}

	/**
	 * 
	 * @param connectable
	 */
	public void setConnectable(boolean connectable)
	{
		this.connectable = connectable;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#isVisible()
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setVisible(boolean)
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#isCollapsed()
	 */
	public boolean isCollapsed()
	{
		return collapsed;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setCollapsed(boolean)
	 */
	public void setCollapsed(boolean collapsed)
	{
		this.collapsed = collapsed;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getParent()
	 */
	public mxICell getParent()
	{
		return parent;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setParent(fr.enib.game.editor.graphe.model.mxICell)
	 */
	public void setParent(mxICell parent)
	{
		this.parent = parent;
	}

	/**
	 * Returns the source terminal.
	 */
	public mxICell getSource()
	{
		return source;
	}

	/**
	 * Sets the source terminal.
	 * 
	 * @param source Cell that represents the new source terminal.
	 * @param terminal 
	 */
	private void setSource(mxICell source,boolean terminal)
	{
		this.source = source;
		if(isEdge() && terminal){
			System.out.println("\tset SOURCE");
			if(this.value==null)return;
			if(!(this.value instanceof ILien))return;
			ILien lien = (ILien) this.value;
			//TODO set noeud de depart
		}
	}

	/**
	 * Returns the target terminal.
	 */
	public mxICell getTarget()
	{
		return target;
	}

	/**
	 * Sets the target terminal.
	 * 
	 * @param target Cell that represents the new target terminal.
	 * @param terminal 
	 */
	private void setTarget(mxICell target,boolean terminal)
	{
		this.target = target;
		if(isEdge() && terminal){
			System.out.println("\tset TARGET");
			if(this.value==null)return;
			if(!(this.value instanceof ILien))return;
			ILien lien = (ILien) this.value;
			//TODO set noeud de d'arrivée
		}
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getTerminal(boolean)
	 */
	public mxICell getTerminal(boolean source)
	{
		return (source) ? getSource() : getTarget();
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#setTerminal(fr.enib.game.editor.graphe.model.mxICell, boolean)
	 */
	public mxICell setTerminal(mxICell terminal, boolean isSource)
	{
		if (isSource)
		{
			setSource(terminal,true);
		}
		else
		{
			setTarget(terminal,true);
		}

		return terminal;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getChildCount()
	 */
	public int getChildCount()
	{
		return (children != null) ? children.size() : 0;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getIndex(fr.enib.game.editor.graphe.model.mxICell)
	 */
	public int getIndex(mxICell child)
	{
		return (children != null) ? children.indexOf(child) : -1;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getChildAt(int)
	 */
	public mxICell getChildAt(int index)
	{
		return (children != null) ? (mxICell) children.get(index) : null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#insert(fr.enib.game.editor.graphe.model.mxICell)
	 */
	public mxICell insert(mxICell child)
	{
		int index = getChildCount();

		if (child.getParent() == this)
		{
			index--;
		}

		return insert(child, index);
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#insert(fr.enib.game.editor.graphe.model.mxICell, int)
	 */
	public mxICell insert(mxICell child, int index)
	{
		if (child != null)
		{
			child.removeFromParent();
			child.setParent(this);

			if (children == null)
			{
				children = new ArrayList<Object>();
				children.add(child);
			}
			else
			{
				children.add(index, child);
			}
		}

		return child;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#remove(int)
	 */
	public mxICell remove(int index)
	{
		mxICell child = null;

		if (children != null && index >= 0)
		{
			child = getChildAt(index);
			remove(child);
		}

		return child;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#remove(fr.enib.game.editor.graphe.model.mxICell)
	 */
	public mxICell remove(mxICell child)
	{
		if (child != null && children != null)
		{
			children.remove(child);
			child.setParent(null);
			if(child.getValue() instanceof IRemovable){
				((IRemovable) child.getValue()).remove();
			}
		}

		return child;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#removeFromParent()
	 */
	public void removeFromParent()
	{
		if (parent != null)
		{
			parent.remove(this);
		}
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getEdgeCount()
	 */
	public int getEdgeCount()
	{
		return (edges != null) ? edges.size() : 0;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getEdgeIndex(fr.enib.game.editor.graphe.model.mxICell)
	 */
	public int getEdgeIndex(mxICell edge)
	{
		return (edges != null) ? edges.indexOf(edge) : -1;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#getEdgeAt(int)
	 */
	public mxICell getEdgeAt(int index)
	{
		return (edges != null) ? (mxICell) edges.get(index) : null;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#insertEdge(fr.enib.game.editor.graphe.model.mxICell, boolean)
	 */
	public mxICell insertEdge(mxICell edge, boolean isOutgoing,boolean creerLien)
	{
		
		if (edge != null)
		{
			edge.removeFromTerminal(isOutgoing);
			edge.setTerminal(this, isOutgoing);

			if (edges == null || edge.getTerminal(!isOutgoing) != this
					|| !edges.contains(edge))
			{
				if (edges == null)
				{ 
					edges = new ArrayList<Object>();
				}

				edges.add(edge);
				if(isOutgoing)return edge;
				if(!(edge instanceof mxCell))return edge;
				mxCell cell = (mxCell)edge;
				if(cell.source==null)return edge;
				Object obj1 = cell.source.getValue();
				Object obj2 = this.getValue();
				if(!(obj1 instanceof INoeud))return edge;
				if(!(obj2 instanceof INoeud))return edge;
				INoeud noeud1 = (INoeud)obj1;
				INoeud noeud2 = (INoeud)obj2;
				if(!creerLien)return edge;
				try{
					ILien  lien = new Lien(noeud1,noeud2);
					((mxCell) edge).value = lien;
					if(!Model.get().ajouterModelObject(lien)){
						((mxCell) edge).value = null;
					}
				}catch(IllegalArgumentException e){
					//								this.removeEdge(edge, isOutgoing);
					//								cell.removeEdge(edge, !isOutgoing);
					//								edge.removeFromParent();
					//								e.printStackTrace();
//					((mxCell) edge).value = null;
				}catch(LimitLienException e){
					this.removeEdge(edge, isOutgoing);
					cell.removeEdge(edge, !isOutgoing);
					edge.removeFromParent();
					e.printStackTrace();
//					((mxCell) edge).value = null;
				}
			}
		}

		return edge;
	}

	/* 
	 * (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#removeEdge(fr.enib.game.editor.graphe.model.mxICell, boolean, boolean)
	 */
	@Override
	public mxICell removeEdge(mxICell edge, boolean isOutgoing)
	{
		if (edge != null)
		{
			if (edge.getTerminal(!isOutgoing) != this && edges != null)
			{
				edges.remove(edge);
			}

			edge.setTerminal(null, isOutgoing);
			if(edge.getValue() instanceof IRemovable){
				((IRemovable) edge.getValue()).remove();
			}
		}

		return edge;
	}

	/* (non-Javadoc)
	 * @see fr.enib.game.editor.graphe.model.mxICell#removeFromTerminal(boolean)
	 */
	public void removeFromTerminal(boolean isSource)
	{
		mxICell terminal = getTerminal(isSource);

		if (terminal != null)
		{
			terminal.removeEdge(this, isSource);
		}
	}

	/**
	 * Returns the specified attribute from the user object if it is an XML
	 * node.
	 * 
	 * @param name Name of the attribute whose value should be returned.
	 * @return Returns the value of the given attribute or null.
	 */
	public String getAttribute(String name)
	{
		return getAttribute(name, null);
	}

	/**
	 * Returns the specified attribute from the user object if it is an XML
	 * node.
	 * 
	 * @param name Name of the attribute whose value should be returned.
	 * @param defaultValue Default value to use if the attribute has no value.
	 * @return Returns the value of the given attribute or defaultValue.
	 */
	public String getAttribute(String name, String defaultValue)
	{
		Object userObject = getValue();
		String val = null;

		if (userObject instanceof Element)
		{
			Element element = (Element) userObject;
			val = element.getAttribute(name);
		}

		if (val == null)
		{
			val = defaultValue;
		}

		return val;
	}

	/**
	 * Sets the specified attribute on the user object if it is an XML node.
	 * 
	 * @param name Name of the attribute whose value should be set.
	 * @param value New value of the attribute.
	 */
	public void setAttribute(String name, String value)
	{
		Object userObject = getValue();

		if (userObject instanceof Element)
		{
			Element element = (Element) userObject;
			element.setAttribute(name, value);
		}
	}

	/**
	 * Returns a clone of the cell.
	 */
	public Object clone(boolean transfert) throws CloneNotSupportedException
	{
		mxCell clone = (mxCell) super.clone();

		clone.setValue(cloneValue(transfert));
		clone.setStyle(getStyle());
		clone.setCollapsed(isCollapsed());
		clone.setConnectable(isConnectable(LienConection.entrant_sortant));
		clone.setEdge(isEdge());
		clone.setVertex(isVertex());
		clone.setVisible(isVisible());
		clone.setParent(null);
		clone.setSource(null,false);
		clone.setTarget(null,false);
		clone.children = null;
		clone.edges = null;

		mxGeometry geometry = getGeometry();

		if (geometry != null)
		{
			clone.setGeometry((mxGeometry) geometry.clone());
		}

		return clone;
	}

	/**
	 * Returns a clone of the user object. This implementation clones any XML
	 * nodes or otherwise returns the same user object instance.
	 */
	protected Object cloneValue(boolean transfert)
	{
		Object value = getValue();

		if (value instanceof Node)
		{
			value = ((Node) value).cloneNode(true);
		}
		else if( value instanceof IClonableObject){
			/*
			 * si le clone a utiliser pour un transfert on ne clone
			 * pas la "value"
			 */
			if(transfert)return value;
			return ((IClonableObject)value).cloneObject();
		}

		return value;
	}

}
