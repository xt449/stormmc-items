package com.github.xt449.stormmc.item;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.ItemStackBuilder;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class CustomItem {

	public final int id;
	public final Material material;
	public final String name;

	protected CustomItem(int id, @NotNull Material material, @NotNull String name) {
		this.id = id;
		this.material = material;
		this.name = name;
	}

	public ItemStackBuilder createBuilder() {
		return ItemStack.builder(material).meta(metaBuilder -> metaBuilder.customModelData(id).displayName(Component.text(name)));
	}

	public ItemStackBuilder createSimpleBuilder() {
		return ItemStack.builder(material).meta(metaBuilder -> metaBuilder.customModelData(id));
	}
}
