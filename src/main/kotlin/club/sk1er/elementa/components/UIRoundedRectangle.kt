package club.sk1er.elementa.components

import club.sk1er.elementa.UIComponent
import club.sk1er.elementa.dsl.pixels
import club.sk1er.elementa.shaders.FloatUniform
import club.sk1er.elementa.shaders.Shader
import club.sk1er.elementa.shaders.Vec4Uniform
import club.sk1er.mods.core.universal.UniversalGraphicsHandler
import org.lwjgl.util.vector.Vector4f
import java.awt.Color

/**
 * Draws a rounded rectangle
 */
open class UIRoundedRectangle(radius: Float) : UIComponent() {
    init {
        setRadius(radius.pixels())
    }

    override fun draw() {
        beforeDraw()

        val radius = getRadius()

        val color = getColor()
        if (color.alpha != 0)
            drawRoundedRectangle(getLeft(), getTop(), getRight(), getBottom(), radius, color)

        super.draw()
    }

    companion object {
        private val shader = Shader("rect", "rounded_rect")
        private val shaderRadiusUniform = FloatUniform(shader.getUniformLocation("u_Radius"))
        private val shaderInnerRectUniform = Vec4Uniform(shader.getUniformLocation("u_InnerRect"))

        fun drawRoundedRectangle(left: Float, top: Float, right: Float, bottom: Float, radius: Float, color: Color) {
            UniversalGraphicsHandler.pushMatrix()

            shader.bindIfUsable()
            shaderRadiusUniform.setValue(radius)
            shaderInnerRectUniform.setValue(Vector4f(left + radius, top + radius, right - radius, bottom - radius))

            UIBlock.drawBlock(color, left.toDouble(), top.toDouble(), right.toDouble(), bottom.toDouble())

            shader.unbindIfUsable()

            UniversalGraphicsHandler.popMatrix()
        }
    }
}