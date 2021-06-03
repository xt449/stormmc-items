package com.github.xt449.stormmc.item;

import com.google.common.collect.LinkedListMultimap;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class CustomItemManager {

	final LinkedListMultimap<Material, CustomItem> customItems = LinkedListMultimap.create();

	public CustomItemManager() {
		//
	}

	public CustomItem registerItem(@NotNull Material material, @NotNull String name) {
		final List<CustomItem> items = customItems.get(material);
		final CustomItem item = new CustomItem(items.size() + 1, Objects.requireNonNull(material), Objects.requireNonNull(name));
		items.add(item);
		return item;
	}
}
