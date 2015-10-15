package com.aliyun.odps

import java.io.PrintWriter

import com.hankcs.hanlp.HanLP
import com.hankcs.hanlp.corpus.tag.Nature
import com.hankcs.hanlp.seg.Segment
import com.hankcs.hanlp.seg.common.Term
import scala.collection.JavaConverters._
import scala.io.Source

/**
 * Created by admin on 10/14/15.
 */
object SegmentDemo {
  def main(args:Array[String]): Unit ={
    val testCase: Array[String] = Array[String](
      "商品和服务", "结婚的和尚未结婚的确实在干扰分词啊",
      "买水果然后来世博园最后去世博会", "中国的首都是北京",
      "欢迎新老师生前来就餐",
      "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作",
      "随着页游兴起到现在的页游繁盛，依赖于存档进行逻辑判断的设计减少了，但这块也不能完全忽略掉。",
      "这只票，千万不要慌，跟随市场做出判断和决定才好的方案600778这q上得组，。新老股民聚集，不少朋友都在里面商研这股，有的朋友速来了")
    val out = new PrintWriter("test.seg.data")
    val segment: Segment = HanLP.newSegment()
    segment.enablePartOfSpeechTagging(true)
    for (line <- Source.fromFile("test.data", "UTF-8").getLines()) {
      val splits = line.split("\t")
      var segString = ""
      if(splits.size == 3)
        segString = segment.seg(splits(2)).asScala.toList.map(x => {
          if(x.nature == Nature.m)
            x.word(0) + "NUM" + x.word.length
          else
            x.word
        }).mkString(" ")
      out.println(segString)
    }
    out.close()
  }
}
