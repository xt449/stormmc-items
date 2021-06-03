package com.github.xt449.stormmc.resourcepack;

import com.github.xt449.stormmc.item.CustomItem;
import com.github.xt449.stormmc.item.CustomItemManager;
import com.google.common.collect.ListMultimap;
import com.google.gson.*;
import net.minestom.server.item.Material;

import java.io.*;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class ResourcePackGenerator {

	private static final File minecraftAssets = new File("./pack/assets/minecraft");
	private static final File itemModels = new File(minecraftAssets, "/models/item");
	private static final File customModels = new File(minecraftAssets, "/models/custom");

	private static final File vanillaItemModels = new File("./vanilla/assets/minecraft/models/item");

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final CustomItemManager itemManager;
	private final ListMultimap<Material, CustomItem> customItems;

	public ResourcePackGenerator(CustomItemManager itemManager) {
		this.itemManager = itemManager;
		this.customItems = itemManager.getAllCustomItems();
	}

	public void generate() throws IOException {
		itemModels.mkdirs();
		customModels.mkdirs();

		for(Material material : customItems.keySet()) {
			final File file = new File(itemModels, material.key().value() + ".json");
			file.delete();
			final FileWriter writer = new FileWriter(file, true);
			try {
				gson.toJson(createModel(material), writer);
			} catch(FileNotFoundException exc) {
				System.err.println("Missing vanilla model file \"" + material.key().value() + ".json\"! Skipping material...");
			}
			writer.close();
		}
	}

	private JsonObject createModel(Material material) throws IOException {
		final JsonObject root = JsonParser.parseReader(new FileReader(new File(vanillaItemModels, material.key().value() + ".json"))).getAsJsonObject();
		final JsonArray overrides = new JsonArray(2);
		root.add("overrides", overrides);
		overrides.add(createDefault(material));

		for(CustomItem customItem : customItems.get(material)) {
			overrides.add(createOverride(customItem));

			final File file = new File(customModels, material.key().value() + customItem.id + ".json");
			file.delete();
			final FileWriter writer = new FileWriter(file, true);
			gson.toJson(createOverrideModel(customItem), writer);
			writer.close();
		}

		return root;
	}

	private static final JsonObject DEFAULT_PREDICATE = new JsonObject();

	static {
		DEFAULT_PREDICATE.add("custom_model_data", new JsonPrimitive(0));
	}

	private JsonObject createDefault(Material material) {
		final JsonObject override = new JsonObject();
		override.add("predicate", DEFAULT_PREDICATE);
		override.add("model", new JsonPrimitive("item/" + material.key().value()));
		return override;
	}

	private JsonObject createOverride(CustomItem item) {
		final JsonObject override = new JsonObject();
		final JsonObject predicate = new JsonObject();
		override.add("predicate", predicate);
		predicate.add("custom_model_data", new JsonPrimitive(item.id));
		override.add("model", new JsonPrimitive("custom/" + item.material.key().value() + item.id));
		return override;
	}

	private JsonObject createOverrideModel(CustomItem item) {
		final JsonObject root = new JsonObject();
		final JsonObject textures = new JsonObject();
		root.add("parent", new JsonPrimitive("minecraft:item/generated"));
		root.add("textures", textures);
		textures.add("layer0", new JsonPrimitive("minecarft:item/" + item.name.toLowerCase().replaceAll(" ", "_")));
		return root;
	}
}
