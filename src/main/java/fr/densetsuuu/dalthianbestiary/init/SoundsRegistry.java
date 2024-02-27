package fr.densetsuuu.dalthianbestiary.init;

import fr.densetsuuu.dalthianbestiary.util.Resource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundsRegistry {
    public static final SoundEvent SACCAGEUR_HOWLING = new SoundEvent(new ResourceLocation(Resource.MOD_ID, "saccageur_howling"))
            .setRegistryName(new ResourceLocation(Resource.MOD_ID, "saccageur.howling"));

    public static final SoundEvent SACCAGEUR_AMBIENT = new SoundEvent(new ResourceLocation(Resource.MOD_ID, "saccageur_ambient"))
            .setRegistryName(new ResourceLocation(Resource.MOD_ID, "saccageur.ambient"));

    public static final SoundEvent SACCAGEUR_ATTACK = new SoundEvent(new ResourceLocation(Resource.MOD_ID, "saccageur_attack"))
            .setRegistryName(new ResourceLocation(Resource.MOD_ID, "saccageur.attack"));
}
