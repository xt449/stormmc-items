import com.github.xt449.stormmc.item.CustomItem;
import com.github.xt449.stormmc.item.CustomItemManager;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class Example {

	public static void main(String[] args) {
		new Example().test();
	}

	final CustomItemManager itemManager = new CustomItemManager();

	void test() {
		CustomItem ropeItem = itemManager.registerItem("rope", "Rope");
		System.out.println(ropeItem.createItemStack().getMeta().getDamage());
	}
}
