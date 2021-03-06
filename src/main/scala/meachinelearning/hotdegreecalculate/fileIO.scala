package meachinelearning.hotdegreecalculate

import java.io.{File, PrintWriter}

import _root_.util.TimeUtil

import scala.collection.mutable
import scala.io.Source

/**
  * Created by li on 16/7/11.
  * 计算社区热度的过程中,分别将计算的结果保存到本地的文件系统中, 以及从本地文件中读取前一小时的数据
  */
object fileIO {

  /** 将结果保存到本地,将每小时数据保存为一个txt文件,一天的数据保存在一个文件夹里.
    *
    * @param dir 文件保存的目录
    * @param result
    */
  def saveAsTextFile(dir: String, result: Array[(String, Double)]): Unit ={

    val day = TimeUtil.getDay
    val hour = TimeUtil.getCurrentHour

    val writer = new PrintWriter(new File(dir +"%s".format(day) + "-" + "%s".format(hour) + ".txt"))

    for (line <- result) {

      writer.write(line._1 + "\t" + line._2 + "\n")

      }

    writer.close()
  }


  /**
    * 读取当前时间前一个小时的数据,读取本地文件中的结果.
    *
    * @param dir 数据保存的目录
    * @return
    */
  def readFromFile(dir: String): Array[(String, Double)] ={

    val date = TimeUtil.getPreHourStr
    val res = new mutable.ArrayBuffer[(String, Double)]

    if (Source.fromFile(dir + "%s".format(date) + ".txt" ) != null) {
      val temp = Source.fromFile(dir + "%s".format(date) + ".txt" )

      temp.getLines().foreach{
        line =>{
          val temp = line.split("\t")
          res.+=((temp(0), temp(1).toDouble))
        }
      }
    } else {

      res.+=(("init", 0.0))
    }

    res.toArray
  }

}
