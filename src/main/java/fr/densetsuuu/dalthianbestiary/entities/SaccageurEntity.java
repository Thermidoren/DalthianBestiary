package fr.densetsuuu.dalthianbestiary.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SaccageurEntity extends EntityCreature implements IAnimatable, IAnimationTickable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public SaccageurEntity(World worldIn) {
        super(worldIn);
        this.ignoreFrustumCheck = true;
        this.setSize(3F, 3F);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<SaccageurEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        super.initEntityAI();
    }


    @Override
    public void tick() {
        super.onUpdate();
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }
}
