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
	private final ListMultimap<Material, CustomItem> customItems;

	public ResourcePackGenerator(CustomItemManager itemManager) {
		this.customItems = itemManager.getAllCustomItems();
	}

	public void generate() throws IOException {
		itemModels.mkdirs();
		customModels.mkdirs();

		for(Material material : customItems.keySet()) {
			final File file = new File(itemModels, material.key().value() + ".json");
			file.delete();
			try(FileWriter writer = new FileWriter(file, true)) {
				try {
					gson.toJson(createBaseModel(material), writer);
				} catch(FileNotFoundException exc) {
					System.err.println("Missing vanilla model file \"" + material.key().value() + ".json\"! Skipping material...");
				}
			}
		}
	}

	private JsonObject createBaseModel(Material material) throws IOException {
		final JsonObject root = JsonParser.parseReader(new FileReader(new File(vanillaItemModels, material.key().value() + ".json"))).getAsJsonObject();
		final JsonArray overrides = new JsonArray(2);
		root.add("overrides", overrides);
		overrides.add(createOverrideDefault(material));

		for(CustomItem customItem : customItems.get(material)) {
			overrides.add(createOverride(customItem));

			final File file = new File(customModels, material.key().value() + customItem.id + ".json");
			file.delete();
			try(FileWriter writer = new FileWriter(file, true)) {
				gson.toJson(createCustomModel(customItem), writer);
			}
		}

		return root;
	}

	private JsonElement createOverrideDefault(Material material) {
		return JsonParser.parseString("{\"predicate\":{\"custom_model_data\":0},\"model\":\"item/" + material.key().value() + "\"}");
	}

	private JsonElement createOverride(CustomItem item) {
		return JsonParser.parseString("{\"predicate\":{\"custom_model_data\":" + item.id + "},\"model\":\"item/" + item.material.key().value() + item.id + "\"}");
	}

	private JsonElement createCustomModel(CustomItem item) {
		return JsonParser.parseString("{\"parent\":\"minecraft:item/generated\",\"textures\":{\"layer0\":\"minecarft:item/" + item.name.toLowerCase().replaceAll(" ", "_") + "\"}}");
	}
}
