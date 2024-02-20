package fr.densetsuuu.dalthianbestiary.init;

import fr.densetsuuu.dalthianbestiary.util.Resource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModTab extends CreativeTabs {

    /**
     * Create new Creative Tabs here!
     */

    /**
     * creating the Name of a new Creative Tab
     */
    public ModTab(String name) {
        super(Resource.MOD_ID + "." + name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem() {
        return null;
    }

}
