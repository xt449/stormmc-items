package com.github.xt449.stormmc.resourcepack;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class Model {

	public String parent;
	public Display display;
	public LinkedHashMap<String,String> textures;
	public List<Element> elements;

	public static class Display {
		public Position thirdperson_righthand;
		public Position thirdperson_lefthand;
		public Position firstperson_righthand;
		public Position firstperson_lefthand;
		public Position gui;
		public Position head;
		public Position ground;
		public Position fixed;

		public static class Position {
			public int[] rotation;
			public int[] translation;
			public int[] scale;
		}
	}

	public static class Element {
		public int[] from;
		public int[] to;
		public Rotation rotation;
		public boolean shade;
		public Faces faces;

		public static class Rotation {
			public int[] origin;
			public String axis;
			public float angle;
			public boolean rescale;
		}

		public static class Faces {
			public Face north;
			public Face south;
			public Face east;
			public Face west;
			public Face up;
			public Face down;

			public static class Face {
				public int[] uv;
				public String texture;
				public String cullface;
				public int rotation;
				public int tintindex;
			}
		}
	}
}
