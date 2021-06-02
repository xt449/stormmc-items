package com.github.xt449.stormmc.item;

import net.minestom.server.item.ItemMeta;
import net.minestom.server.item.ItemMetaBuilder;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.function.Supplier;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class CustomItemMeta extends ItemMeta {

	protected CustomItemMeta(@NotNull ItemMetaBuilder metaBuilder) {
		super(metaBuilder);
	}

	public static class Builder extends ItemMetaBuilder {

		@Override
		public @NotNull ItemMeta build() {
			return new CustomItemMeta(this);
		}

		@Override
		public void read(@NotNull NBTCompound nbtCompound) {

		}

		@Override
		protected @NotNull Supplier<@NotNull ItemMetaBuilder> getSupplier() {
			return Builder::new;
		}
	}
}
