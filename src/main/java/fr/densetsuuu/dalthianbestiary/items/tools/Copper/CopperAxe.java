package fr.densetsuuu.dalthianbestiary.items.tools.Copper;

import fr.densetsuuu.dalthianbestiary.Base;
import fr.densetsuuu.dalthianbestiary.init.ModItems;
import fr.densetsuuu.dalthianbestiary.util.Resource;
import fr.densetsuuu.dalthianbestiary.util.IHasModel;
import net.minecraft.item.ItemAxe;

public class CopperAxe extends ItemAxe implements IHasModel {

    /**
     * Creating the Base of a new Mod Tool
     */
    public CopperAxe(String name, ToolMaterial material) {
        super(material, 6.0f, -3.2f);
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