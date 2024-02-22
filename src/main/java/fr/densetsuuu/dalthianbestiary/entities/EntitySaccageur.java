package fr.densetsuuu.dalthianbestiary.entities;

import fr.densetsuuu.dalthianbestiary.entities.ai.EntityAIDalthianAttack;
import fr.densetsuuu.dalthianbestiary.entities.ai.saccageur.EntityAISaccageurNearestAttackablePlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
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

    private boolean isHowling = false;

    private final AnimationFactory factory = new AnimationFactory(this);
    public EntitySaccageur(World worldIn) {
        super(worldIn);
        this.setSize(2F, 3F);
    }

    public boolean isHowling() {
        return isHowling;
    }

    public void setHowling(boolean howling) {
        isHowling = howling;
    }

    //#region Geckolib
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
    //#endregion

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
        if (this.isHowling && entitySaccageurAnimationEvent.getController().getAnimationState().equals(AnimationState.Stopped)) {
            entitySaccageurAnimationEvent.getController().markNeedsReload();
            entitySaccageurAnimationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.saccageur.hurle", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            this.isHowling = false;
        }
        return PlayState.CONTINUE;
    }
    //#endregion

    //#region AI
    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIDalthianAttack(this, 1.0D, false, 20.0F, 60));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.5D, 32.0F));
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAISaccageurNearestAttackablePlayer(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));

    }
    //#endregion

    //#region Attributes & Attac
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(75.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
    }

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
    //#endregion

    @Override
    public void tick() {
        super.onUpdate();
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }


}
