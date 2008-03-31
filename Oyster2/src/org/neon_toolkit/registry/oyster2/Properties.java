package org.neon_toolkit.registry.oyster2;

/**
 * $Id: Properties.java,v 1.3 2008-03-31 14:27:50 rpa Exp $
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
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

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
	 * Property "basicTypeOntology", the ontology file imported by the type ontology.
	 */
	public static final String localRegistry = "localRegistry";
	public static final String typeOntology = "typeOntology";
	public static final String peerDescriptionOntology = "peerDescriptionOntology";
	public static final String mappingDescriptionOntology = "mappingDescriptionOntology";
	public static final String owlChangeOntology = "owlChangeOntology";
	public static final String topicOntology = "topicOntology";
	public static final String changeOntology = "changeOntology";
	public static final String owlodmOntology = "owlodmOntology";
	public static final String workflowOntology = "workflowOntology";
	
	public static final String image = "image";
	
	
	/**
	 * Property "ResolveIPs", if to lookup the hostname of remote host.
	 */
	public static final String RESOLVE_IP ="resolveIP";
	public static final String typeOntologyRoot ="typeOntologyRoot";
	public static final String topicOntologyRoot = "topicOntologyRoot";
	
	
	public static final String localPeerName = "localPeerName";
	public static final String localPeerType = "localPeerType";
	public static final String bootStrapPeerName = "bootStrapPeerName";
	public static final String bootStrapPeerIP = "bootStrapPeerIP";
	public static final String discoveryFrec = "discoveryFrec";
	
	
	public static final String searchCondition_1 = "searchCondition_1";
	public static final String searchCondition_2 = "searchCondition_2";
	public static final String searchCondition_3= "searchCondition_3";
	public static final String searchCondition_4="searchCondition_4";
	public static final String searchCondition_5="searchCondition_5";
	
	
	public static final String NUMBER_OF_COLUMNS="NUMBER_OF_COLUMNS";
	public static final String COLUMN_TYPE0 = "COLUMN_TYPE0";
	public static final String COLUMN_TYPE1 = "COLUMN_TYPE1";
	public static final String COLUMN_TYPE2= "COLUMN_TYPE2";
	public static final String COLUMN_NAME0="COLUMN_NAME0";
	public static final String COLUMN_NAME1="COLUMN_NAME1";
	public static final String COLUMN_NAME2="COLUMN_NAME2";
	public static final String COLUMN_WIDTH0="COLUMN_WIDTH0";
	public static final String COLUMN_WIDTH1="COLUMN_WIDTH1";
	public static final String COLUMN_WIDTH2="COLUMN_WIDTH2";
	
	/**
	 * The default property values.
	 *
	 *
	 */
	private static final String[] HEADER = {"#", "Oyster2 properties file",
		"#", "",
		"#", "The paths can be absolute (e.g. ",
		"#", "e\\:/this is a test/oyster.jpg), however",
		"#", "it is not allowed to use blank spaces in the path",
		"#", "of an ontology (e.g. topicOntology, typeOntology,",
		"#", "peerDescriptionOntology, mapppingDescriptionOntology), since this string",
		"#", "(the path of the ontology) should be a normal url",
		"#", "(java.net.URI). But you can use %20 instead of a blank space",
		"#", "",
		"", "",
	};
	
	/*
	private static final String[] DEFAULTS = {										localRegistry,"O2serverfiles/localRegistry.owl",
																						typeOntology,"O2serverfiles/OMV.owl",
																						peerDescriptionOntology, "O2serverfiles/pOMV.owl",
																						mappingDescriptionOntology,"O2serverfiles/mappingOMV.owl", 
																						owlChangeOntology,"O2serverfiles/OWLChanges.owl",
																						topicOntology,"O2serverfiles/dmozT.rdf",
																						changeOntology,"O2serverfiles/changes.owl",
																						owlodmOntology,"O2serverfiles/owl11v1.5.owl",
																						workflowOntology,"O2serverfiles/workflow.owl",
																						image, "O2serverfiles/o1.GIF",
																						typeOntologyRoot, "#Ontology",
																						topicOntologyRoot, "Top",
																						localPeerName, "localhost",
																						localPeerType, "S",
																						bootStrapPeerName, "oysterUPM",
																						bootStrapPeerIP, "138.100.11.159",
																						discoveryFrec, "120000",
																						searchCondition_1,"omv:name",
																						searchCondition_2, "omv:isOfType",
																						searchCondition_3, "omv:naturalLanguage",
																						searchCondition_4, "omv:hasOntologyLanguage",
																						searchCondition_5, "omv:keywords",																						
																						NUMBER_OF_COLUMNS, "3",																			
																						COLUMN_TYPE0,"http://omv.ontoware.org/2005/05/ontology#name",
																						COLUMN_TYPE1, "http://omv.ontoware.org/2005/05/ontology#description",
																						COLUMN_TYPE2, "http://omv.ontoware.org/2007/05/pomv#ontologyOMVLocation",
																						COLUMN_NAME0, "Ontology Name",
																						COLUMN_NAME1, "Description",
																						COLUMN_NAME2, "oyster:peer",
																						COLUMN_WIDTH0, "100",
																						COLUMN_WIDTH1, "163",
																						COLUMN_WIDTH2, "100",
	};
	*/																				
	
	private static final String[] DEFAULTS = {											localRegistry,"O2serverfiles/localRegistry.owl",
																						typeOntology,"http://omv.ontoware.org/2005/05/ontology",
																						peerDescriptionOntology, "http://omv.ontoware.org/2007/05/pomv",
																						mappingDescriptionOntology,"http://omv.ontoware.org/2007/05/mappingomv", 
																						owlChangeOntology,"http://omv.ontoware.org/2007/07/OWLChanges",
																						topicOntology,"http://oyster2.ontoware.org/dmozT.rdf",
																						changeOntology,"http://omv.ontoware.org/2007/10/changes",
																						owlodmOntology,"http://owlodm.ontoware.org/OWL1.1",
																						workflowOntology,"http://omv.ontoware.org/2007/07/workflow",
																						image, "http://oyster2.ontoware.org/o1.gif",
																						typeOntologyRoot, "#Ontology",
																						topicOntologyRoot, "Top",
																						localPeerName, "localhost",
																						localPeerType, "S",
																						bootStrapPeerName, "oysterUPM",
																						bootStrapPeerIP, "138.100.11.159",
																						discoveryFrec, "120000",
																						searchCondition_1,"omv:name",
																						searchCondition_2, "omv:isOfType",
																						searchCondition_3, "omv:naturalLanguage",
																						searchCondition_4, "omv:hasOntologyLanguage",
																						searchCondition_5, "omv:keywords",																						
																						NUMBER_OF_COLUMNS, "3",																			
																						COLUMN_TYPE0,"http://omv.ontoware.org/2005/05/ontology#name",
																						COLUMN_TYPE1, "http://omv.ontoware.org/2005/05/ontology#description",
																						COLUMN_TYPE2, "http://omv.ontoware.org/2007/05/pomv#ontologyOMVLocation",
																						COLUMN_NAME0, "Ontology Name",
																						COLUMN_NAME1, "Description",
																						COLUMN_NAME2, "oyster:peer",
																						COLUMN_WIDTH0, "100",
																						COLUMN_WIDTH1, "163",
																						COLUMN_WIDTH2, "100",
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
			if ((!DEFAULTS[i].equals("")) && (!DEFAULTS[i].equals("#"))){
				mProperties.put(DEFAULTS[i], DEFAULTS[i + 1]);
			}
	}

	/**
	 * Initializes the properties with default values.
	 */
	synchronized public void  init() {
		_init(Constants.PROPERTY_FILE);
	}

	/**
	 * Initializes the properties with default values.
	 */
	synchronized public void  init(String file) {
		_init(file);
	}
	
	/**
	 * Initializes the properties with the given property file and properties.
	 *
	 * @param file       the property file.
	 * @param properties further initialization properties.
	 */
	synchronized public void init(String file, java.util.Properties properties) {
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
		mFile = new File(file);
		try {
			if (!mFile.exists()) {
				System.out.println("It's your first time...");
				mFile.createNewFile();
				store();
				String host = InetAddress.getLocalHost().getHostName();
				setString(localPeerName,host);
				storeOn();
			} else {
				
				System.out.println("file existed, loading...");
				mProperties.clear();
				load();
				storeOn();
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
	public boolean getBoolean(String key) {
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
	public boolean getDefaultBoolean(String key) {
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
	public int getDefaultInteger(String key) {
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
	public String getDefaultString(String key) {
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
	public int getInteger(String key) {
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
	public String getString(String key) {
		return (String)mProperties.get(key);
	}

	/**
	 * Sets the property value by the delivered string.
	 *
	 * @param key   the key of the property.
	 * @param value the value of the property.
	 */
	synchronized public void setString(String key, String value) {
		//if (mProperties.containsKey(key)) {
		//	mProperties.put(key, value);
		//	storeOn();
		//}
		mProperties.put(key, value);
		
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
			System.out.println("Couldnt load existing file");
			//pgrid.Constants.LOGGER.log(Level.WARNING, mPGrid.getResource(ResourceKeys.PROPERTY_FILE_NO_READ_WRITE).replaceFirst("FILENAME", mFile.getName()), e);
		}
	}

	/**
	 * Stores the properties to the defined file.
	 */
	synchronized public void store() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(mFile));
			GregorianCalendar calendar = new GregorianCalendar();
			out.write("# "+ calendar.get(Calendar.DATE) + " " + (calendar.get(Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR) + " "+ calendar.get(Calendar.HOUR_OF_DAY)+ ":"+calendar.get(Calendar.MINUTE)+ Constants.LINE_SEPERATOR);
			for (int i = 0; i < HEADER.length; i = i + 2)
				if (HEADER[i].equals(""))
					out.write(Constants.LINE_SEPERATOR);
				else if (HEADER[i].equals("#"))
					out.write("# " + HEADER[i + 1] + Constants.LINE_SEPERATOR);
				else
					out.write(HEADER[i] + "=" + getString(HEADER[i]) + Constants.LINE_SEPERATOR);
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
	
	/**
	 * Stores the properties to the defined file.
	 */
	@SuppressWarnings("unchecked")
	synchronized public void storeOn() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(mFile));
			GregorianCalendar calendar = new GregorianCalendar();
			out.write("# "+ calendar.get(Calendar.DATE) + " " + (calendar.get(Calendar.MONTH) + 1) + " " + calendar.get(Calendar.YEAR) + " "+ calendar.get(Calendar.HOUR_OF_DAY)+ ":"+calendar.get(Calendar.MINUTE)+ Constants.LINE_SEPERATOR);
			for (int i = 0; i < HEADER.length; i = i + 2)
				if (HEADER[i].equals(""))
					out.write(Constants.LINE_SEPERATOR);
				else if (HEADER[i].equals("#"))
					out.write("# " + HEADER[i + 1] + Constants.LINE_SEPERATOR);
				else
					out.write(HEADER[i] + "=" + getString(HEADER[i]) + Constants.LINE_SEPERATOR);
			
			Iterator it = mProperties.entrySet().iterator();
			while(it.hasNext()){
				Entry<String,String> h = (Entry<String,String>)it.next();
				out.write(h.getKey().toString() + "=" + h.getValue().toString() + Constants.LINE_SEPERATOR);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			
		}
	}

}
