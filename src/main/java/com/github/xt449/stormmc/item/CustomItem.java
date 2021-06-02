package com.github.xt449.stormmc.item;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class CustomItem implements Keyed {

	private final int id;
	public final String name;
	protected final Key key;

	protected CustomItem(int id, @NotNull @Pattern("[a-z0-9_]+") String namespace, @NotNull @Pattern("[a-z0-9_]+") String value, @NotNull String name) {
		this.id = id;
		this.name = name;
		this.key = Key.key(namespace, value);
	}

	@Override
	public @NonNull Key key() {
		return key;
	}

	private static final Material CUSTOM_ITEM_MATERIAL = Material.NETHERITE_HOE;

	public ItemStack createItemStack() {
		return ItemStack.builder(CUSTOM_ITEM_MATERIAL).meta(new CustomItemMeta.Builder().damage(id).build()).build();
	}
}
