package fr.densetsuuu.dalthianbestiary.geo;

import fr.densetsuuu.dalthianbestiary.entities.SaccageurEntity;
import fr.densetsuuu.dalthianbestiary.util.Resource;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SaccageurGeoModel extends AnimatedGeoModel<SaccageurEntity> {

    private static final ResourceLocation modelResource = new ResourceLocation(Resource.MOD_ID, "geo/saccageur.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(Resource.MOD_ID, "textures/model/entities/saccageur.png");
    public static final ResourceLocation animationResource = new ResourceLocation(Resource.MOD_ID, "animations/saccageur.animation.json");

    @Override
    public ResourceLocation getModelLocation(SaccageurEntity dalthianBaseEntityMob) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureLocation(SaccageurEntity dalthianBaseEntityMob) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SaccageurEntity dalthianBaseEntityMob) {
        return animationResource;
    }
}
