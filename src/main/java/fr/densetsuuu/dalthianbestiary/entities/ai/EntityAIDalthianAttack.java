package fr.densetsuuu.dalthianbestiary.entities.ai;

import fr.densetsuuu.dalthianbestiary.entities.EntitySaccageur;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class EntityAIDalthianAttack extends EntityAIAttackMelee {
    private final int ATTACK_INTERVAL;
    private final double ATTACK_REACH;
    private final int ATTACK_ANIMATION_TICKS = 5;
    private boolean isAttacking = false;
    private int attackAnimationDelay = ATTACK_ANIMATION_TICKS;

    public EntityAIDalthianAttack(EntityCreature creature, double speedIn, boolean useLongMemory, double attackReach, int attackCooldown) {
        super(creature, speedIn, useLongMemory);
        this.ATTACK_INTERVAL = attackCooldown + ATTACK_ANIMATION_TICKS;
        this.ATTACK_REACH = attackReach;
    }

    @Override
    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
        return this.ATTACK_REACH;
    }

    @Override
    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
        double reach = this.getAttackReachSqr(p_190102_1_);
        if (p_190102_2_ <= reach && this.attackAnimationDelay <= 0) {
            resetAttack();
            this.attacker.attackEntityAsMob(p_190102_1_);
            return;
        }
        if (p_190102_2_ <= reach && this.attackTick <= 0) {
            this.attacker.swingArm(this.attacker.getActiveHand());
            this.isAttacking = true;
        }
    }

    private void resetAttack() {
        this.attackTick = this.ATTACK_INTERVAL;
        this.isAttacking = false;
        this.attackAnimationDelay = this.ATTACK_ANIMATION_TICKS;
    }

    @Override
    public void updateTask() {
        if (this.isAttacking) {
            this.attackAnimationDelay = Math.max(-2, this.attackAnimationDelay - 1);
            if (this.attackAnimationDelay == -2) {
                resetAttack();
            }
        }
        super.updateTask();
    }
}
