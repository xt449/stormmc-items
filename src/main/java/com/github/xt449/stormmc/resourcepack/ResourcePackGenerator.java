package com.github.xt449.stormmc.resourcepack;

import com.github.xt449.stormmc.item.CustomItem;
import com.github.xt449.stormmc.item.CustomItemManager;
import com.google.common.collect.ListMultimap;
import com.google.gson.*;
import net.minestom.server.item.Material;

import java.io.*;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 *
 * Requires the'generated' directory output of`java -cp server.jar net.minecraft.data.Main --all` to the working directory
 */
public class ResourcePackGenerator {

	private static final File vanillaAssets = new File("generated/assets/minecraft/");
//	private static final File vanillaBlockStates = new File(vanillaAssets, "blockstates/");
	private static final File vanillaItemModels = new File(vanillaAssets, "models/item");

	private static final File customAssets = new File("pack/assets/minecraft/");
//	private static final File customBlockStates = new File(customAssets, "blockstates/");
	private static final File customItemModels = new File(customAssets, "models/item");

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final ListMultimap<Material, CustomItem> customItems;

	public ResourcePackGenerator(CustomItemManager itemManager) {
		this.customItems = itemManager.getAllCustomItems();
	}

	public void generateItems() throws IOException {
		deleteAll(customItemModels);
		new File(customItemModels, "custom").mkdirs();

		for(Material material : customItems.keySet()) {
			final File file = new File(customItemModels, material.key().value() + ".json");
			try(FileWriter writer = new FileWriter(file, false)) {
				try {
					gson.toJson(createBaseItemModel(material), writer);
				} catch(FileNotFoundException exc) {
					exc.printStackTrace();
					System.err.println("Missing vanilla model file \"" + material.key().value() + ".json\"! Skipping material...");
				}
			}
		}
	}

	private JsonObject createBaseItemModel(Material material) throws IOException {
		final JsonObject root = JsonParser.parseReader(new FileReader(new File(vanillaItemModels, material.key().value() + ".json"))).getAsJsonObject();
		final JsonArray overrides = new JsonArray(2);
		root.add("overrides", overrides);
		overrides.add(createOverrideDefault(material));

		for(CustomItem customItem : customItems.get(material)) {
			overrides.add(createOverride(customItem));

			final File file = new File(customItemModels, "custom/" + material.key().value() + customItem.id + ".json");
			try(FileWriter writer = new FileWriter(file, false)) {
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

	private void deleteAll(File file) {
		if(file.isDirectory()) {
			final File[] files = file.listFiles();
			for(int i = 0; i < files.length; i++) {
				deleteAll(files[i]);
			}
		}

		file.delete();
	}
}
