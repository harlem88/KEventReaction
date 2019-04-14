package org.dronix.knative.data.retriever

import kotlinx.cinterop.*
import kotlinx.serialization.json.Json
import org.dronix.knative.data.model.EventModel
import org.dronix.knative.domain.CommandOut
import org.dronix.knative.domain.CommandRetriever
import org.dronix.knative.entities.Command
import org.dronix.knative.entities.EVENT
import platform.posix.*

private const val TAG = "SerialCommandRetriever"

class SerialCommandRetriever(private val port: String) : CommandRetriever, CommandOut{
    private var fd: Int = -1
    private var commandCallback: ((Command)-> Unit) ?= null

    private fun initSerial(){
         fd = open(port, O_RDWR or O_NOCTTY or O_NDELAY)

        println("$TAG try to open $port")

        if (fd == -1) {
            println("$TAG open_port: Unable to open $port")
        } else {
            println("$TAG opened $port")

            memScoped {
                val options: termios = alloc()
                val optionsPtr = options.ptr

                tcgetattr(fd, optionsPtr)
                cfsetispeed(optionsPtr, B115200)
                cfsetospeed(optionsPtr, B115200)

                options.c_cflag = options.c_cflag and PARENB.toUInt().inv()
                options.c_cflag = options.c_cflag and CSTOPB.toUInt().inv()
                options.c_cflag = options.c_cflag and CSIZE.toUInt().inv()
                options.c_cflag = options.c_cflag or CS8.toUInt()
                options.c_cflag = options.c_cflag and  CRTSCTS.toUInt().inv()
                options.c_cflag = options.c_cflag or (CREAD.toUInt() or CLOCAL.toUInt())  // turn on READ & ignore ctrl lines

                options.c_lflag = options.c_lflag and (ICANON.toUInt() or ECHO.toUInt() or ECHOE.toUInt() or ISIG.toUInt()).inv()

                options.c_iflag = options.c_iflag and (IXON.toUInt() or IXOFF.toUInt() or IXANY.toUInt()).inv()

                tcsetattr(fd, TCSANOW, optionsPtr)

                tcflush(fd, TCIFLUSH)
            }

            sleep(2)
        }
    }

    override fun setCommandListener(listener: (Command) -> Unit) {
        commandCallback = listener
    }


    override fun start(eventCallback: (EVENT)->Unit){
        initSerial()

        while (true){
            val reads = read()
            val eventModel = parse(reads.stringFromUtf8())

            if(eventModel != null){
                when {
                    eventModel.switch_1 == 1 -> eventCallback.invoke(EVENT.EV1)
                    eventModel.switch_2 == 1 -> eventCallback.invoke(EVENT.EV2)
                    eventModel.switch_3 == 1 -> eventCallback.invoke(EVENT.EV3)
                }
            }
            usleep(200*1000)
        }
    }



    fun read() : ByteArray{
        if (fd == -1) return ByteArray(0)

        val bufferByteArray = ArrayList<Byte>()

        memScoped {
            var foundEnd = false
            val size = 1
            val buffer = allocArray<ByteVar>(size)
            var reads = read(fd, buffer, size.convert()).toInt()

            while(!foundEnd){
                val i = 0

                if( reads >= 0 && !foundEnd ){
                    if (buffer[i].toInt() == 10) {
                        foundEnd = true
                    } else {
                        bufferByteArray.add(buffer[i])
                    }
                }

                if(!foundEnd) reads = read(fd, buffer, size.convert()).toInt()
            }
        }
        return bufferByteArray.toByteArray()
    }


    fun write(values: String){
        write(fd, values.cstr, values.length.convert())
    }

    override fun stop() {
        if(fd != -1) close(fd)
    }

    override fun reset() {
        write("led_off")
    }

    override fun commandOK() {
        write("led_ok")
    }

    override fun commandKO() {
        write("led_ko")
    }

private fun parse(json: String): EventModel?{

    return if(json.length > 3){
        println("parse $json")
        try {
            Json.parse(EventModel.serializer(), json)
        }catch (e: Exception){
            println("parse errror ${e.message}")
            null
        }
    }else null
}}