import com.github.xt449.stormmc.item.CustomItemManager;
import com.github.xt449.stormmc.item.ResourcePackGenerator;
import net.minestom.server.item.Material;

import java.io.IOException;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 *
 * Extract the vanilla client assets folder to ./vanilla/assets
 * before using to get accurate base item models
 */
public class Example {

	public static void main(String[] args) {
		new Example().test();
	}

	final CustomItemManager itemManager = new CustomItemManager();
	final ResourcePackGenerator packGenerator = new ResourcePackGenerator(itemManager);

	void test() {
		itemManager.registerItem(Material.LEAD, "Rope");
		itemManager.registerItem(Material.NETHERITE_HOE, "Test 1");
		itemManager.registerItem(Material.NETHERITE_HOE, "Test Item 3");
		itemManager.registerItem(Material.NETHERITE_HOE, "Cancel");

		try {
			packGenerator.generate();
		} catch(IOException exc) {
			exc.printStackTrace();
		}
	}
}
