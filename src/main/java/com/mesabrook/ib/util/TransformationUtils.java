// THANK YOU TO hnOsmium0001 ON GITHUB!
// https://gist.github.com/hnOsmium0001/fdc93d21e9f9e4b806d8a5bedd249ed8#file-extendedaabb-java

package com.mesabrook.ib.util;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.Vec3i;

public class TransformationUtils {

    public static Matrix4d createIdentityMatrix4d() {
        Matrix4d identity = new Matrix4d();
        identity.setIdentity();
        return identity;
    }

    public static Matrix4d combineTransformations(Matrix4d... transformations) {
        Matrix4d result = createIdentityMatrix4d();
        for (Matrix4d transformation : transformations) {
            result.mul(transformation);
        }
        return result;
    }

    public static Matrix4d getTranslationMatrix(double dx, double dy, double dz) {
        Matrix4d translation = createIdentityMatrix4d();
        // [ 1 0 0 dx ]
        // | 0 1 0 dy |
        // | 0 0 1 dz |
        // [ 0 0 0 1  ]
        translation.setM03(dx);
        translation.setM13(dy);
        translation.setM23(dz);
        return translation;
    }

    public static Matrix4d getRotationMatrix(int x, int y, int z) {
        return combineTransformations(getRotationMatrix(Axis.X, x), getRotationMatrix(Axis.Y, y), getRotationMatrix(Axis.Z, z));
    }

    public static Matrix4d getRotationMatrix(double rx, double ry, double rz) {
        return combineTransformations(getRotationMatrix(Axis.X, rx), getRotationMatrix(Axis.Y, ry), getRotationMatrix(Axis.Z, rz));
    }

    public static Matrix4d getRotationMatrix(Axis axis, int degrees) {
        return getRotationMatrix(axis, java.lang.Math.toRadians(degrees));
    }

    public static Matrix4d getRotationMatrix(Axis axis, double radians) {
        Matrix4d rotation = new Matrix4d();

        Vec3i direction = EnumFacing.getFacingFromAxis(AxisDirection.POSITIVE, axis).getDirectionVec();
        // Rotation matrix calculation is done in Matrix4d#set(AxisAngle4d), the object is just used
        // to pass in parameters
        rotation.set(new AxisAngle4d(direction.getX(), direction.getY(), direction.getZ(), radians));
        return rotation;
    }

    public static Matrix4d getScalingMatrix(double scale) {
        return getScalingMatrix(scale, scale, scale);
    }

    public static Matrix4d getScalingMatrix(double xs, double ys, double zs) {
        Matrix4d scaling = createIdentityMatrix4d();
        // [ xs 0  0  0 ]
        // | 0  ys 0  0 |
        // | 0  0  zs 0 |
        // [ 0  0  0  1 ]
        scaling.setM00(xs);
        scaling.setM11(ys);
        scaling.setM22(zs);
        return scaling;
    }

}