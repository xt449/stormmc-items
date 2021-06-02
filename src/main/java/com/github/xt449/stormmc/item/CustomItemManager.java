package com.github.xt449.stormmc.item;

import net.kyori.adventure.key.Key;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class CustomItemManager {

	private final ConcurrentHashMap<Key, CustomItem> items = new ConcurrentHashMap<>();

	private final AtomicInteger nextId = new AtomicInteger(1);

	public CustomItemManager() {
		//
	}

	private CustomItem registerItem(@NotNull @Pattern("[a-z0-9_]+") String namespace, @NotNull @Pattern("[a-z0-9_]+") String id, @NotNull String name) throws IllegalArgumentException {
		final CustomItem item = new CustomItem(nextId.getAndIncrement(), namespace, id, Objects.requireNonNull(name));
		if(items.containsKey(item.key)) {
			throw new IllegalArgumentException("Duplicate custom item key. \"" + item.key.asString() + "\n already exists!");
		}
		items.put(item.key, item);
		return item;
	}

	public CustomItem registerItem(@NotNull @Pattern("[a-z0-9_]+") String id, @NotNull String name) throws IllegalArgumentException {
		return registerItem("custom", id, name);
	}

	public @Nullable CustomItem getByKey(Key key) {
		return items.get(key);
	}
}
