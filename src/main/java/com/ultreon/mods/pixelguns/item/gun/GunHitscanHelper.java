package com.ultreon.mods.pixelguns.item.gun;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GunHitscanHelper {

    private static long time = 0;
    public static HitResult getCollision(LivingEntity shooter, double maxDistance) {

        EntityHitResult entityHitResult = GunHitscanHelper.getEntityCollision(shooter, maxDistance);
        if (entityHitResult != null) return entityHitResult;


        if (System.currentTimeMillis() - time > 200) {
            time = System.currentTimeMillis();
            return GunHitscanHelper.getBlockCollision(shooter, maxDistance > 32 ? 32 : maxDistance);
        }
        return null;
    }

    public static BlockHitResult getBlockCollision(LivingEntity shooter, double maxDistance) {

        Vec3d origin = shooter.getEyePos();
        Vec3d direction = shooter.getRotationVector().normalize();
        Vec3d destination = origin.add(direction.multiply(maxDistance));

        return shooter.getWorld().raycast(new RaycastContext(origin, destination, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, shooter));
    }
    public static EntityHitResult getEntityCollision(LivingEntity shooter, double maxDistance) {
        Vec3d origin = shooter.getEyePos();
        Vec3d direction = shooter.getRotationVector().normalize();
        Vec3d destination = origin.add(direction.multiply(maxDistance));

        List<Pair<Double, LivingEntity>> result = new ArrayList<>();
        // Get all LivingEntities in the box bound by the origin and the destination
        for (final LivingEntity entity : shooter.getWorld().getEntitiesByClass(LivingEntity.class, shooter.getBoundingBox().stretch(direction.multiply(maxDistance)), (e) -> true)) {
            // Casts a ray from origin to destination to see if any entitiy hitboxes intersect it
            Optional<Vec3d> entityCollisionLocation = entity.getBoundingBox().raycast(origin, destination);
            if (entityCollisionLocation.isPresent()) {
                // Entity found, add it to the result arraylist to be sorted
                double distance = origin.squaredDistanceTo(entityCollisionLocation.get());
                result.add(new Pair<>(distance, entity));
            }
        }

        // Sort the entities from closest to furthest
        result.sort(Comparator.comparingDouble(Pair::getLeft));

        // If the shooter's line of sight intersects the closest entity (and not a block), return the result
        // Otherwise get the next closest entity and repeat
        for (Pair<Double, LivingEntity> pair : result) {
            if (shooter.canSee(pair.getRight())) return new EntityHitResult(pair.getRight());
        }

        // If we ran out of entities to check, return null
        return null;
    }
}
