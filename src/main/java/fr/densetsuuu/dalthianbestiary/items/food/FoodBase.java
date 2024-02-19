package fr.densetsuuu.dalthianbestiary.items.food;

import fr.densetsuuu.dalthianbestiary.Base;
import fr.densetsuuu.dalthianbestiary.init.ModItems;
import fr.densetsuuu.dalthianbestiary.util.Resource;
import fr.densetsuuu.dalthianbestiary.util.IHasModel;
import net.minecraft.item.ItemFood;

public class FoodBase extends ItemFood implements IHasModel {

    /**
     * Creating the Base of a new Mod Food Item
     */
    public FoodBase(String name, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        /* Setting the Name of the Block / Item */
        setUnlocalizedName(name);
        /* Setting the Registry Name of the Block / Item */
        setRegistryName(Resource.MOD_ID, name);
        /* The Tab where the Item will be placed */
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
