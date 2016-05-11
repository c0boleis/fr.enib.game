/**
 * Copyright (c) 2007, Gaudenz Alder
 */
package fr.enib.game.editor.graphe.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import fr.enib.game.editor.graphe.codec.CodecLien;
import fr.enib.game.editor.graphe.codec.CodecNoeud;
import fr.enib.game.editor.graphe.codec.CodecTableau;
import fr.enib.game.editor.graphe.model.mxGraphModel.mxCollapseChange;
import fr.enib.game.editor.graphe.model.mxGraphModel.mxGeometryChange;
import fr.enib.game.editor.graphe.model.mxGraphModel.mxStyleChange;
import fr.enib.game.editor.graphe.model.mxGraphModel.mxValueChange;
import fr.enib.game.editor.graphe.model.mxGraphModel.mxVisibleChange;

/**
 * Singleton class that acts as a global registry for codecs. See
 * {@link mxCodec} for an example.
 */
public class mxCodecRegistry
{

	/**
	 * Maps from constructor names to codecs.
	 */
	protected static Hashtable<String, mxObjectCodec> codecs = new Hashtable<String, mxObjectCodec>();

	/**
	 * Maps from classnames to codecnames.
	 */
	protected static Hashtable<String, String> aliases = new Hashtable<String, String>();

	/**
	 * Holds the list of known packages. Packages are used to prefix short
	 * class names (eg. mxCell) in XML markup.
	 */
	protected static List<String> packages = new ArrayList<String>();

	// Registers the known codecs and package names
	static
	{
		addPackage("fr.enib.game.editor.graphe");
		addPackage("fr.enib.game.editor.graphe.util");
		addPackage("fr.enib.game.editor.graphe.model");
		addPackage("fr.enib.game.editor.graphe.view");
		addPackage("java.lang");
		addPackage("java.util");

		register(new mxObjectCodec(new ArrayList<Object>()));
		register(new mxModelCodec());
		register(new mxCellCodec());
		register(new mxStylesheetCodec());
		
		/*
		 * codec pour les differente forme du model
		 * de donn�es
		 */
		register(new CodecNoeud());
		register(new CodecLien());
		register(new CodecTableau());
		
		register(new mxRootChangeCodec());
		register(new mxChildChangeCodec());
		register(new mxTerminalChangeCodec());
		register(new mxGenericChangeCodec(new mxValueChange(), "value"));
		register(new mxGenericChangeCodec(new mxStyleChange(), "style"));
		register(new mxGenericChangeCodec(new mxGeometryChange(), "geometry"));
		register(new mxGenericChangeCodec(new mxCollapseChange(), "collapsed"));
		register(new mxGenericChangeCodec(new mxVisibleChange(), "visible"));
	}

	/**
	 * Registers a new codec and associates the name of the template constructor
	 * in the codec with the codec object. Automatically creates an alias if the
	 * codename and the classname are not equal.
	 * @param codec 
	 * @return {@link mxObjectCodec}
	 */
	public static mxObjectCodec register(mxObjectCodec codec)
	{
		if (codec != null)
		{
			String name = codec.getName();
			codecs.put(name, codec);

			String classname = getName(codec.getTemplate());

			if (!classname.equals(name))
			{
				addAlias(classname, name);
			}
		}

		return codec;
	}

	/**
	 * Adds an alias for mapping a classname to a codecname.
	 * @param classname 
	 * @param codecname 
	 */
	public static void addAlias(String classname, String codecname)
	{
		aliases.put(classname, codecname);
	}

	/**
	 * @param name Java class name.
	 * 
	 * @return a codec that handles the given object, which can be an object
	 * instance or an XML node.
	 */
	public static mxObjectCodec getCodec(String name)
	{
		String tmp = aliases.get(name);

		if (tmp != null)
		{
			name = tmp;
		}

		mxObjectCodec codec = codecs.get(name);

		// Registers a new default codec for the given name
		// if no codec has been previously defined.
		if (codec == null)
		{
			Object instance = getInstanceForName(name);

			if (instance != null)
			{
				try
				{
					codec = new mxObjectCodec(instance);
					register(codec);
				}
				catch (Exception e)
				{
					// ignore
				}
			}
		}

		return codec;
	}

	/**
	 * Adds the given package name to the list of known package names.
	 * 
	 * @param packagename Name of the package to be added.
	 */
	public static void addPackage(String packagename)
	{
		packages.add(packagename);
	}

	/**
	 * Creates and returns a new instance for the given class name.
	 * 
	 * @param name Name of the class to be instantiated.
	 * @return Returns a new instance of the given class.
	 */
	public static Object getInstanceForName(String name)
	{
		Class<?> clazz = getClassForName(name);

		if (clazz != null)
		{
			if (clazz.isEnum())
			{
				// For an enum, use the first constant as the default instance
				return clazz.getEnumConstants()[0];
			}
			else
			{
				try
				{
					return clazz.newInstance();
				}
				catch (Exception e)
				{
					// ignore
				}
			}
		}

		return null;
	}

	/**
	 * Returns a class that corresponds to the given name.
	 * 
	 * @param name
	 * @return Returns the class for the given name.
	 */
	public static Class<?> getClassForName(String name)
	{
		try
		{
			return Class.forName(name);
		}
		catch (Exception e)
		{
			// ignore
		}

		for (int i = 0; i < packages.size(); i++)
		{
			try
			{
				String s = packages.get(i);

				return Class.forName(s + "." + name);
			}
			catch (Exception e)
			{
				// ignore
			}
		}

		return null;
	}

	/**
	 * Returns the name that identifies the codec associated
	 * with the given instance..
	 *
	 * The I/O system uses unqualified classnames, eg. for a
	 * <code>fr.enib.game.editor.graphe.model.mxCell</code> this returns
	 * <code>mxCell</code>.
	 * 
	 * @param instance Instance whose node name should be returned.
	 * @return Returns a string that identifies the codec.
	 */
	public static String getName(Object instance)
	{
		Class<? extends Object> type = instance.getClass();

		if (type.isArray() || Collection.class.isAssignableFrom(type)
				|| Map.class.isAssignableFrom(type))
		{
			return "Array";
		}
		else
		{
			if (packages.contains(type.getPackage().getName()))
			{
				return type.getSimpleName();
			}
			else
			{
				return type.getName();
			}
		}
	}

}
