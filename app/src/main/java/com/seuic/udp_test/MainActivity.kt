package com.seuic.udp_test

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var mSocket: DatagramSocket
    lateinit var mPacket: DatagramPacket

    lateinit var msgTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //监听局域网10086端口
        mSocket = DatagramSocket(null)
        mSocket.bind(InetSocketAddress(10086))

        //数据包
        val buffer = ByteArray(32)
        mPacket = DatagramPacket(buffer, buffer.size)

        msgTv = findViewById(R.id.msg_tv)
        msgTv.movementMethod = ScrollingMovementMethod.getInstance()

        // TODO: 2020/10/14 记得运行 python_udp.py 发送数据
    }

    fun getdata(view: View) {
        thread {
            while (true) {
                try {
                    //接收数据
                    mSocket.receive(mPacket)

                    //获取数据
                    val ipAddr = mPacket.address?.toString()
                    val serverPort = mPacket.port
                    val data = String(mPacket.data, 0, mPacket.length)

                    runOnUiThread {
                        msgTv.append("\n").apply {
                            msgTv.append("$ipAddr:$serverPort\n$data")
                            val offset: Int = msgTv.lineCount * msgTv.lineHeight
                            if (offset > msgTv.height) {
                                msgTv.scrollTo(0, offset - msgTv.height)
                            }
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }
    }
}