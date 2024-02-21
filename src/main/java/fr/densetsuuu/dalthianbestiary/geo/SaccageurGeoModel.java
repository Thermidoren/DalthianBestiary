package fr.densetsuuu.dalthianbestiary.geo;

import fr.densetsuuu.dalthianbestiary.entities.EntitySaccageur;
import fr.densetsuuu.dalthianbestiary.util.Resource;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SaccageurGeoModel extends AnimatedGeoModel<EntitySaccageur> {

    private static final ResourceLocation modelResource = new ResourceLocation(Resource.MOD_ID, "geo/saccageur.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(Resource.MOD_ID, "textures/model/entities/saccageur.png");
    public static final ResourceLocation animationResource = new ResourceLocation(Resource.MOD_ID, "animations/saccageur.animation.json");

    @Override
    public ResourceLocation getModelLocation(EntitySaccageur dalthianBaseEntityMob) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySaccageur dalthianBaseEntityMob) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySaccageur dalthianBaseEntityMob) {
        return animationResource;
    }
}
