package me.melontini.recipe_book_delight.util;

import me.melontini.dark_matter.api.base.util.mixin.ExtendedPlugin;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RecipeBookDelightPlugin extends ExtendedPlugin {
    private static final Logger LOGGER = LogManager.getLogger("RBD Mixin Plugin");

    @Override
    public void onLoad(String mixinPackage) {
        if (!FabricLoader.getInstance().isModLoaded("farmersdelight"))
            throw new RuntimeException("RecipeBookDelight requires Farmers Delight!");
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if (targetClassName.contains("CookingPotScreenHandler") && mixinClassName.contains("AsmTargets")) {
            MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
            ClassNode mapperName;
            try {
                mapperName = MixinService.getService().getBytecodeProvider().getClassNode("me.melontini.recipe_book_delight.util.AbstractRecipeScreenHandlerMapper");
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            for (MethodNode method : mapperName.methods) {
                if (!"<init>".equals(method.name) && !"<clinit>".equals(method.name)) {
                    for (LocalVariableNode localVariable : method.localVariables) {
                        if ("Lme/melontini/recipe_book_delight/util/AbstractRecipeScreenHandlerMapper;".equals(localVariable.desc) && "this".equals(localVariable.name)) {
                            localVariable.desc = "Lcom/nhoryzon/mc/farmersdelight/entity/block/screen/CookingPotScreenHandler;";
                        }
                    }
                    targetClass.methods.add(method);
                }
            }

            transform(targetClassName, targetClass, resolver);
        }
    }

    public void transform(String targetClassName, ClassNode targetClass, MappingResolver resolver) {
        String name = Arrays.stream(targetClassName.split("\\.")).toList().get(targetClassName.split("\\.").length - 1);
        LOGGER.warn("transforming " + name + " to support recipe book");
        try {
            String AbstractRecipeScreenHandler = resolver.mapClassName("intermediary", "net.minecraft.class_1729");
            String Inventory = resolver.mapClassName("intermediary", "net.minecraft.class_1263");
            String ScreenHandlerType = resolver.mapClassName("intermediary", "net.minecraft.class_3917");

            targetClass.signature = "L" + AbstractRecipeScreenHandler.replace(".", "/") + "<L" + Inventory.replace(".", "/") + ";>;";
            targetClass.superName = AbstractRecipeScreenHandler.replace(".", "/");

            List<MethodNode> method1 = targetClass.methods.stream().filter(method -> Objects.equals(method.name, "<init>")).toList();
            if (method1.isEmpty()) {
                LOGGER.error("#########################################################");
                LOGGER.error("couldn't find <init>, " + name + " transformation failed 😢");
                LOGGER.error("#########################################################");
            }

            MethodInsnNode node = null;
            for (int i = 0; i < method1.size(); i++) {
                MethodNode methodNode = method1.get(i);
                List<AbstractInsnNode> list = Arrays.stream(methodNode.instructions.toArray()).filter(abstractInsnNode -> {
                    if (abstractInsnNode instanceof MethodInsnNode methodInsnNode1) {
                        return Objects.equals(methodInsnNode1.name, "<init>") && Objects.equals(methodInsnNode1.desc, "(L" + ScreenHandlerType.replace(".", "/") + ";I)V");
                    }
                    return false;
                }).toList();
                if (list.isEmpty()) continue;
                node = (MethodInsnNode) list.get(0);
                if (node != null) break;
                if (method1.indexOf(methodNode) == method1.size() - 1) {
                    LOGGER.error("#########################################################");
                    LOGGER.error("couldn't find INVOKESPECIAL, " + name + " transformation failed 😢");
                    LOGGER.error("#########################################################");
                }
            }

            MethodInsnNode methodInsnNode = new MethodInsnNode(Opcodes.INVOKESPECIAL, AbstractRecipeScreenHandler.replace(".", "/"), "<init>", "(L" + ScreenHandlerType.replace(".", "/") + ";I)V");
            method1.get(0).instructions.set(node, methodInsnNode);
        } catch (Exception e) {
            LOGGER.error("#########################################################");
            LOGGER.error(name + " transformation failed 😢", e);
            LOGGER.error("#########################################################");
        }
    }
}
