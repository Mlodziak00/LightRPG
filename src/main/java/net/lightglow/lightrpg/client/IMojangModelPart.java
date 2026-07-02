package net.lightglow.lightrpg.client;

import org.joml.Vector3d;

public interface IMojangModelPart {
    default Vector3d lightrpg$getPosition() {return new Vector3d();}
    default Vector3d lightrpg$getRotation() {return new Vector3d();}
}
