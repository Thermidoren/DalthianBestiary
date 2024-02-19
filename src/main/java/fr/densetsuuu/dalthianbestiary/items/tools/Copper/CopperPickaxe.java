package fr.densetsuuu.dalthianbestiary.items.tools.Copper;

import fr.densetsuuu.dalthianbestiary.Base;
import fr.densetsuuu.dalthianbestiary.init.ModItems;
import fr.densetsuuu.dalthianbestiary.util.Resource;
import fr.densetsuuu.dalthianbestiary.util.IHasModel;
import net.minecraft.item.ItemPickaxe;

public class CopperPickaxe extends ItemPickaxe implements IHasModel {

    /**
     * Creating the Base of a new Mod Item
     */
    public CopperPickaxe(String name, ToolMaterial material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(Resource.MOD_ID, name);
        setCreativeTab(Base.MOD_TAB);

        ModItems.ITEMS.add(this);
    }

    /**
     * Registering the Model of the Mod Items
     */
    @Override
    public void registerModels() {
        Base.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
