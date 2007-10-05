package org.neon_toolkit.registry.oyster2;

/**
 * $Id: Properties.java,v 1.1 2007-10-05 15:12:30 rpa Exp $
 *
 * Copyright (c) 2002 The P-Grid Team,
 *                    All Rights Reserved.
 *
 * This file is part of the P-Grid package.
 * P-Grid homepage: http://www.p-grid.org/
 *
 * The P-Grid package is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file LICENSE.
 * If not you can find the GPL at http://www.gnu.org/copyleft/gpl.html
 */


//import pgrid.locale.ResourceKeys;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
//import java.util.logging.Level;
//import java.util.zip.Deflater;

import org.neon_toolkit.registry.util.Tokenizer;

/**
 * This class represents the application properties.
 *
 * @author <a href="mailto:Rui Guo <Rui.Guo@epfl.ch>">Rui Guo</a>
 * @version 1.0.0
 */
public class Properties {

	/**
	 * Property "Locale", the used locale.
	 */
	public static final String LOCALE = "Locale";

	/**
	 * Property "LocalExpertiseRegistry", the used ExpertiseRegistry file.
	 */
	public static final String LocalRegistryFile = "LocalExpertiseRegistry";
	

	/**
	 * Property "basicExpertiseRegistry " template file.
	 */
	public static final String PeerRegistryFile = "PeerRegistryFile";

	/**
	 * Property "virtualOntology" file, the ontology file to store the virtual ontology.
	 */
	public static final String VirtualOntologyFile = "VirtualOntologyFile";
	/**
	 * Property "basicTypeOntology", the ontology file imported by the type ontology.
	 */
	public static final String BasicSchemaOntologyFile1 = "BasicSchemaOntologyFile1";
	public static final String BasicSchemaOntologyFile2 = "BasicSchemaOntologyFile2";
	public static final String BasicSchemaOntologyFile3 = "BasicSchemaOntologyFile3";
	public static final String BasicSchemaOntologyFile4 = "BasicSchemaOntologyFile4";
	/**
	 * Property "typeOntology", the ontology file used as the type ontology for the application,such as: SWRC.
	 */
	public static final String TypeOntologyFile = "DefaultTypeOntology";
	/**
	 * Property "topicOntology", the ontology file used as the topic ontology for the application,such as: ACM.
	 */
	public static final String TopicOntologyFile = "DefaultTopicOntology";
	/**
	 * Property "ResolveIPs", if to lookup the hostname of remote host.
	 */
	public static final String RESOLVE_IP ="resolveIP";
	public static final String TypeOntologyRoot ="TypeOntologyRoot";
	public static final String TopicOntologyRoot = "TopicOntologyRoot";
	public static final String Condition_title = "Condition_title";
	public static final String Condition_year = "Condition_year";
	public static final String Condition_abstract= "Condition_abstract";
	public static final String Condition_4th="Condition_4th";
	public static final String Condition_5th="Condition_5th";
	public static final String ImageLocation = "ImageLocation";
	public static final String LocalPeerName = "LocalPeerName";
	public static final String LocalPeerType = "LocalPeerType";
	public static final String BootstrapPeerName = "BootstrapPeerName";
	public static final String BootstrapPeerAdr = "BootstrapPeerAdr";
	public static final String BootstrapPeerUID = "BootstrapPeerUID";
	/**
	 * The default property values.
	 */
	private static final String[] DEFAULTS = {"#", "Oyster2 properties file",
																						"#", "",
																						"#", "automatically generated",
																						"#", "",
																						"", "",
																						/* General */ "#", "General",
																						//LOCALE, "",
																						BasicSchemaOntologyFile1,"C:/GuoRui/server1/swrc.owl", 
																						BasicSchemaOntologyFile2,"C:/GuoRui/server2/protons.owl",
																						BasicSchemaOntologyFile3,"C:/GuoRui/server2/protont.owl",
																						BasicSchemaOntologyFile4,"C:/GuoRui/kaonserver/omv.owl",
																						LocalRegistryFile, "C:/GuoRui/server/localRegistry.owl",
																						PeerRegistryFile, "C:/GuoRui/server/server/pOMV.owl",
																						VirtualOntologyFile, "C:/GuoRui/server/virtualOntology.owl",
																						TypeOntologyFile, "C:/GuoRui/server/swrc.owl",
																						TopicOntologyFile, "C:/GuoRui/server/acm.rdf",
																						RESOLVE_IP , "true",
																						TypeOntologyRoot, "#Publication",
																						TopicOntologyRoot, "ACMTopic",
																						Condition_title,"swrc:title",
																						Condition_year, "swrc:year",
																						Condition_abstract, "swrc:abstract",
																						Condition_4th, "swrc:keywords",
																						Condition_5th, "swrc:author",
																						ImageLocation, "C:/GuoRui/kaon.jpg",
																						LocalPeerName, "newPeer",
																						LocalPeerType, "S",
																						BootstrapPeerName, "muriel",
																						BootstrapPeerAdr, "localhost",
																						BootstrapPeerUID, ""
																						
																						
	};																				
																						
	 /**
	  * * The property file.
	 */
	private File mFile = null;

	/**
	 * The Oyster2 facility.
	 */
	//private Oyster2Factory mOyster2 = null;

	/**
	 * The properties.
	 */
	private Hashtable<String, String> mProperties = new Hashtable<String,String>();

	/**
	 * Constructs the application properties.
	 */
	public Properties() {
		// create defaults
		for (int i = 0; i < DEFAULTS.length; i = i + 2)
			if ((!DEFAULTS[i].equals("")) && (!DEFAULTS[i].equals("#")))
				mProperties.put(DEFAULTS[i], DEFAULTS[i + 1]);
	}

	/**
	 * Initializes the properties with default values.
	 */
	synchronized void init() {
		_init(Constants.PROPERTY_FILE);
	}

	/**
	 * Initializes the properties with the given property file and properties.
	 *
	 * @param file       the property file.
	 * @param properties further initialization properties.
	 */
	synchronized void init(String file, java.util.Properties properties) {
		_init(file);
		if (properties != null) {
			for (Enumeration enu = properties.propertyNames(); enu.hasMoreElements();) {
				String key = (String)enu.nextElement();
				if (mProperties.containsKey(key)) {
					mProperties.put(key, properties.getProperty(key));
				}
			}
		}
		store();
	}

	/**
	 * This really initializes the properties.
	 *
	 * @param file the property file.
	 */
	synchronized private void _init(String file) {
		//mOyster2 = Oyster2Factory.sharedInstance();
		mFile = new File(file);
		try {
			if (!mFile.exists()) {
				mFile.createNewFile();
				store();
			} else {
				System.out.println("file existed, loading...");
				load();
				//store();
			}
		} catch (FileNotFoundException e) {
			System.err.println(e+" _init("+file+")");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println(e+" _init("+file+")");
			System.exit(-1);
		}
	}

	/**
	 * Returns the property value as boolean.
	 *
	 * @param key the key of the property.
	 * @return the value of the property.
	 */
	boolean getBoolean(String key) {
		if (getString(key).equals("true"))
			return true;
		else
			return false;
	}

	/**
	 * Returns the default value as boolean.
	 *
	 * @param key the key of the property.
	 * @return the default value of the property.
	 */
	boolean getDefaultBoolean(String key) {
		for (int i = 0; i < DEFAULTS.length; i = i + 2) {
			if (DEFAULTS[i].equals(key)) {
				if (DEFAULTS[i + 1].equals("true"))
					return true;
				else
					return false;
			}
		}
		throw new IllegalArgumentException("'" + key + "' not found!");
	}

	/**
	 * Returns the default value as integer.
	 *
	 * @param key the key of the property.
	 * @return the default value of the property.
	 */
	int getDefaultInteger(String key) {
		for (int i = 0; i < DEFAULTS.length; i = i + 2) {
			if (DEFAULTS[i].equals(key)) {
				int val;
				val = Integer.parseInt(DEFAULTS[i + 1]);
				return val;
			}
		}
		throw new IllegalArgumentException("'" + key + "' not found!");
	}

	/**
	 * Returns the default value as string.
	 *
	 * @param key the key of the property.
	 * @return the default value of the property.
	 */
	String getDefaultString(String key) {
		for (int i = 0; i < DEFAULTS.length; i = i + 2) {
			if (DEFAULTS[i].equals(key)) {
				return DEFAULTS[i + 1];
			}
		}
		throw new IllegalArgumentException("'" + key + "' not found!");
	}

	/**
	 * Returns the property value as integer.
	 *
	 * @param key the key of the property.
	 * @return the value of the property.
	 */
	int getInteger(String key) {
		int val;
		val = Integer.parseInt(getString(key));
		return val;
	}

	/**
	 * Returns the property value as string.
	 *
	 * @param key the key of the property.
	 * @return the value of the property.
	 */
	String getString(String key) {
		return (String)mProperties.get(key);
	}

	/**
	 * Sets the property value by the delivered string.
	 *
	 * @param key   the key of the property.
	 * @param value the value of the property.
	 */
	synchronized void setString(String key, String value) {
		if (mProperties.containsKey(key)) {
			mProperties.put(key, value);
			store();
		}
	}

	/**
	 * Loads the properties from the defined file.
	 */
	synchronized private void load() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(mFile));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.trim().length() == 0)
					continue;
				if (inputLine.trim().startsWith("#"))
					continue;
				String[] tokens = Tokenizer.tokenize(inputLine, "=");
				mProperties.put(tokens[0], (tokens.length == 2 ? tokens[1] : ""));
			}
			in.close();
		} catch (IOException e) {
			//pgrid.Constants.LOGGER.log(Level.WARNING, mPGrid.getResource(ResourceKeys.PROPERTY_FILE_NO_READ_WRITE).replaceFirst("FILENAME", mFile.getName()), e);
		}
	}

	/**
	 * Stores the properties to the defined file.
	 */
	synchronized private void store() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(mFile));
			for (int i = 0; i < DEFAULTS.length; i = i + 2)
				if (DEFAULTS[i].equals(""))
					out.write(Constants.LINE_SEPERATOR);
				else if (DEFAULTS[i].equals("#"))
					out.write("# " + DEFAULTS[i + 1] + Constants.LINE_SEPERATOR);
				else
					out.write(DEFAULTS[i] + "=" + getString(DEFAULTS[i]) + Constants.LINE_SEPERATOR);
			out.flush();
			out.close();
		} catch (IOException e) {
			
		}
	}

}
