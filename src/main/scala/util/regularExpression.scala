package util

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

/**
  * Created by li on 16/6/20.
  * 正则表达式,读取文本中所有双引号里面的内容.
  */
object regularExpression extends App{
  val conf = new SparkConf().setMaster("local").setAppName("regularexpression")
  val sc = new SparkContext(conf)

  val data = sc.textFile("file:/Users/li/kunyan/111.txt")

  def quotationMatch(sentence:String): Array[String] = {

//    val regex = new Regex("\"([^\"]*)\"") //匹配双引号
//    val regex = new Regex("(?<=\").{1,}(?=\")") //匹配双引号
    val regex = new Regex("([-+]?\\d+(\\.\\d+)?%)|[-+]?\\d+(\\.\\d+)?")//匹配正(负)整数\浮点数\含有百分号的数

    // val regex = "\"([^\"]*)\"".r
    val num = regex.findAllIn(sentence)
    val res = new ListBuffer[String]
    while(num.hasNext){
      val item = num.next()
      res += item.replaceAll("\"", "")
    }
      res.toArray
  }

  // val res = quotationMatch(data)
  data.foreach {

    x =>{
      val res =  quotationMatch(x)
      res.foreach(println)
    }
  }




}
