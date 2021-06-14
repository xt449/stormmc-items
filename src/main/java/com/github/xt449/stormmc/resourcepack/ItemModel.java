package com.github.xt449.stormmc.resourcepack;

import java.util.List;
import java.util.Map;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class ItemModel extends Model {

	public String gui_light;
	public List<OverrideCase> overrides;

	public static class OverrideCase {
		public Map<String, Number> predicate;
		public String model;
	}
}
