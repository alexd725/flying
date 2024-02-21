package edu.utap.fling

import android.view.View
import android.os.Bundle

class Jump(private val puck: View,
           private val border: Border
) {
    // XXX remember some X and Y values and any other state
    private var curX = border.minX().toFloat()
    private var curY = border.minY().toFloat()

    private fun placePuck() {
        // XXX Write me
        when{
            puck.x==border.minX().toFloat() && puck.y==border.minY().toFloat() ->{
                puck.x=(border.maxX() - puck.width).toFloat()
                puck.y=border.minY().toFloat()
                curX=(border.maxX() - puck.width).toFloat()
                curY=border.minY().toFloat()
            }
            puck.x==(border.maxX() - puck.width).toFloat() && puck.y==border.minY().toFloat() ->{
                puck.x=(border.maxX() - puck.width).toFloat()
                puck.y= (border.maxY() - puck.height).toFloat()
                curX=(border.maxX() - puck.width).toFloat()
                curY=(border.maxY() - puck.height).toFloat()
            }
            puck.x==border.maxX().toFloat()-puck.width && puck.y==border.maxY().toFloat()-puck.height ->{
                puck.x=border.minX().toFloat()
                puck.y=(border.maxY() - puck.height).toFloat()
                curX=border.minX().toFloat()
                curY=(border.maxY() - puck.height).toFloat()
            }
            puck.x==border.minX().toFloat() && puck.y==border.maxY().toFloat()-puck.height ->{
                puck.x=border.minX().toFloat()
                puck.y=border.minY().toFloat()
                curX=border.minX().toFloat()
                curY=border.minY().toFloat()
            }
        }
    }
    fun start() {
        puck.visibility = View.VISIBLE
        puck.isClickable = true
        // XXX Write me
        println("inside jump start")
        puck.x=curX.toFloat()
        puck.y=curY.toFloat()
        puck.setOnClickListener{
            println("inside jump setOnClickListener")
            placePuck()
        }

    }
    fun finish() {
        // XXX Write me
        puck.isClickable= false
        //puck.visibility=View.INVISIBLE
    }
}