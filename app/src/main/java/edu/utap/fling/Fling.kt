package edu.utap.fling

import android.annotation.SuppressLint
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import android.widget.ImageView
import androidx.core.view.doOnLayout


class Fling(private val puck: View,
            private val border: Border,
            private val testing: Boolean
)  {
    private val puckMinX = border.minX().toFloat()
    private val puckMaxX = (border.maxX() - puck.width).toFloat()
    private val puckMinY= border.minY().toFloat()
    private val puckMaxY = (border.maxY() - puck.height).toFloat()
    private val friction = 3.0f
    private var goalBorder = Border.Type.T
    private lateinit var flingAnimationX: FlingAnimation
    private lateinit var flingAnimationY: FlingAnimation
    private var flingEnd = false // TODO: Added code

    private fun placePuck() {
        if (testing) {
            puck.x = ((border.maxX() - border.minX()) / 2).toFloat()
            puck.y = ((border.maxY() - border.minY()) / 2).toFloat()
        } else {
            // XXX Write me
            puck.x = border.randomX(puck.width)
            puck.y = border.randomY(puck.height)
        }
        // If puck had been made invisible, make it visible now
        puck.visibility = View.VISIBLE
    }

    private fun success(goalAchieved: () -> Unit) {
        // XXX Write me
        //cancel the animations
        // flingAnimationX?.let { if (it.isRunning) it.cancel() }
        //  flingAnimationY?.let { if (it.isRunning) it.cancel() }
        puck.clearAnimation()
        puck.isActivated=false
        //make the puck disappear
        puck.visibility=View.INVISIBLE
        //goalAchieved
        goalAchieved()
    }

    fun makeXFlingAnimation(initVelocity: Float,
                            goalAchieved: () -> Unit): FlingAnimation {
        return FlingAnimation(puck, DynamicAnimation.X)
            .setFriction(friction)
            // XXX Write me
            .addEndListener { animation, canceled, value, velocity ->
                if(goalBorder==Border.Type.S){
                    if(puck.x==puckMinX ){
                        success(goalAchieved)
                    }else if (!flingEnd){ // TODO: Added code
                        makeXFlingAnimation(0f,goalAchieved).apply {
                            setMinValue(puckMinX)
                                .setMaxValue(puckMaxX)
                        }?.setStartVelocity(-velocity)?.start()
                    }
                }
                if(goalBorder==Border.Type.E){
                    if(puck.x==puckMaxX ){
                        success(goalAchieved)
                    }else if (!flingEnd){ // TODO: Added code
                        makeXFlingAnimation(0f,goalAchieved).apply {
                            setMinValue(puckMinX)
                                .setMaxValue(puckMaxX)
                        }?.setStartVelocity(-velocity)?.start()
                    }
                }
            }
    }

    fun makeYFlingAnimation(initVelocity: Float,
                            goalAchieved: () -> Unit): FlingAnimation {
        //Log.d("XXX", "Fling Y vel $initVelocity")
        return FlingAnimation(puck, DynamicAnimation.Y)
            .setFriction(friction)
            // XXX Write me
            .addEndListener { animation, canceled, value, velocity ->
                if(goalBorder==Border.Type.T){
                    if(puck.y==puckMinY ){
                        success(goalAchieved)
                    }else if (!flingEnd){ // TODO: Added code
                        makeYFlingAnimation(0f,goalAchieved).apply {
                            setMinValue(puckMinY)
                                .setMaxValue(puckMaxY)
                        }?.setStartVelocity(-velocity)?.start()
                    }
                }
                if(goalBorder==Border.Type.B){
                    if(puck.y==puckMaxY ){
                        success(goalAchieved)
                    }else if (!flingEnd){ // TODO: Added code
                        makeYFlingAnimation(0f,goalAchieved).apply {
                            setMinValue(puckMinY)
                                .setMaxValue(puckMaxY)
                        }?.setStartVelocity(-velocity)?.start()
                    }
                }
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun listenPuck(goalAchieved: ()->Unit) {
        // A SimpleOnGestureListener notifies us when the user puts their
        // finger down, and when they edu.utap.edu.utap.fling.
        // Note that here we construct the listener object "on the fly"
        flingEnd = false // TODO: Added code
        flingAnimationX = makeXFlingAnimation(0f,goalAchieved).apply{
            setMinValue(puckMinX)
                .setMaxValue(puckMaxX)
        }
        flingAnimationY = makeYFlingAnimation(0f,goalAchieved).apply{
            setMinValue(puckMinY)
                .setMaxValue(puckMaxY)
        }
        val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                println("onDown")
                if(puck.isActivated) {
                    println("puck.isActivated "+puck.isActivated)
                    return true
                }
                else {
                    println("puck.isActivated "+puck.isActivated)
                    return false
                }
            }
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                // XXX Write me
                flingAnimationX?.setStartVelocity(velocityX)?.start()
                flingAnimationY?.setStartVelocity(velocityY)?.start()
                return true
            }
        }

        val gestureDetector = GestureDetector(puck.context, gestureListener)
        // When Android senses that the puck is being touched, it will call this code
        // with a motionEvent object that describes the motion.  Our detector
        // will take sequences of motion events and send them to the gesture listener to
        // let us know what the user is doing.
        puck.setOnTouchListener { _, motionEvent ->
            gestureDetector.onTouchEvent(motionEvent)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun deactivatePuck() {
        // XXX Write me
        println("deactivatePuck")
        flingEnd = true // TODO: Added code
        puck.clearAnimation()
        puck.isActivated=false
        border.resetBorderColors()
        //  puck.isClickable = false
    }

    fun playRound(goalAchieved: () -> Unit) {
        // XXX Write me
        placePuck()
        puck.isActivated=true
        border.resetBorderColors()
        goalBorder = border.nextGoal()
        listenPuck( goalAchieved)
        //puck.visibility=View.VISIBLE
    }
}