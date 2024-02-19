package fr.densetsuuu.dalthianbestiary.items.food;

import fr.densetsuuu.dalthianbestiary.Base;
import fr.densetsuuu.dalthianbestiary.util.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FoodEffectBase extends ItemFood implements IHasModel {

    PotionEffect effect;

    public FoodEffectBase(String name, int amount, float saturation, boolean isWolfFood, PotionEffect effect) {
        super(amount, saturation, isWolfFood);
        setUnlocalizedName(name);
        setAlwaysEdible();

        this.effect = effect;
    }

    /**
     * Registering the Model of the Mod Items
     */
    @Override
    public void registerModels() {
        Base.proxy.registerItemRenderer(this, 0, "inventory");
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            player.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
