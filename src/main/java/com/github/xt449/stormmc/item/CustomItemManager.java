package com.github.xt449.stormmc.item;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class CustomItemManager {

	final ListMultimap<Material, CustomItem> customItems = LinkedListMultimap.create();

	public CustomItemManager() {
		//
	}

	public CustomItem registerItem(@NotNull Material material, @NotNull String name) {
		final List<CustomItem> items = customItems.get(material);
		final CustomItem item = new CustomItem(items.size() + 1, Objects.requireNonNull(material), Objects.requireNonNull(name));
		items.add(item);
		return item;
	}

	public ListMultimap<Material, CustomItem> getAllCustomItems() {
		return Multimaps.unmodifiableListMultimap(customItems);
	}

	public List<CustomItem> getCustomItems(Material material) {
		return Collections.unmodifiableList(customItems.get(material));
	}
}
