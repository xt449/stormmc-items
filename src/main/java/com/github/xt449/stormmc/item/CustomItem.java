package com.github.xt449.stormmc.item;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class CustomItem {

	protected final int id;
	public final Material material;
	public final String name;

	protected CustomItem(int id, @NotNull Material material, @NotNull String name) {
		this.id = id;
		this.material = material;
		this.name = name;
	}

	public ItemStack createItemStack() {
		return ItemStack.builder(material).meta(new CustomItemMeta.Builder().displayName(Component.text(name)).customModelData(id).attributes(Collections.emptyList()).build()).build();
	}
}
