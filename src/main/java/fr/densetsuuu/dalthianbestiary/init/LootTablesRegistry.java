package fr.densetsuuu.dalthianbestiary.init;

import fr.densetsuuu.dalthianbestiary.util.Resource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTablesRegistry {

    public static final ResourceLocation SACCAGEUR = LootTableList.register(new ResourceLocation(Resource.MOD_ID, "saccageur"));
}
