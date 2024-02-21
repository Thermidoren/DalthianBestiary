package fr.densetsuuu.dalthianbestiary.entities;

import fr.densetsuuu.dalthianbestiary.entities.ai.EntityAISaccageurAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntitySaccageur extends EntityMob implements IAnimatable, IAnimationTickable {

    private int attackTimer = 0;
    private boolean isHowlingInProgress = false;

    private final AnimationFactory factory = new AnimationFactory(this);
    public EntitySaccageur(World worldIn) {
        super(worldIn);
        this.setSize(2F, 3F);
    }


    //#region Predicates
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<EntitySaccageur> entitySaccageurAnimationEvent) {
        if (this.isSwingInProgress && entitySaccageurAnimationEvent.getController().getAnimationState().equals(AnimationState.Stopped)) {
            entitySaccageurAnimationEvent.getController().markNeedsReload();
            entitySaccageurAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            this.isSwingInProgress = false;
        }
        return PlayState.CONTINUE;
    }

    private PlayState howlingPredicate(AnimationEvent<EntitySaccageur> entitySaccageurAnimationEvent) {
        if (this.isHowlingInProgress && entitySaccageurAnimationEvent.getController().getAnimationState().equals(AnimationState.Stopped)) {
            entitySaccageurAnimationEvent.getController().markNeedsReload();
            entitySaccageurAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.hurle", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            this.isHowlingInProgress = false;
        }
        return PlayState.CONTINUE;
    }
    //#endregion

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
        data.addAnimationController(new AnimationController<>(this, "howlingController", 0, this::howlingPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAISaccageurAttack(this, 1.0D, false));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(75.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60.0D);
    }

    // Apply knockback 1 to the target
    @Override
    public boolean attackEntityAsMob(net.minecraft.entity.Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof net.minecraft.entity.EntityLivingBase) {
                ((net.minecraft.entity.EntityLivingBase) entityIn).knockBack(this, 1.0F, this.posX - entityIn.posX, this.posZ - entityIn.posZ);
            }
            return true;
        }
        return false;
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
