package com.aliyun.odps

import java.io.PrintWriter

import com.hankcs.hanlp.HanLP
import com.hankcs.hanlp.corpus.tag.Nature
import com.hankcs.hanlp.seg.Segment
import com.hankcs.hanlp.seg.common.Term
import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
 * Created by admin on 10/14/15.
 */
object SegmentDemo {
  def convertHanNumber(source:String): String ={
    var temp = source.replaceAll("[一二三四五六七八九零]", "1")
//    temp = temp.replaceAll("\\s", "")
    temp
  }
  def main(args:Array[String]): Unit ={
    val out = new PrintWriter("../../iwencai/corpus/seg.data.pinyin")
    val segment: Segment = HanLP.newSegment().enableNameRecognize(true)

    segment.enablePartOfSpeechTagging(true)
//    val splitsToParse = new ArrayBuffer[String]()
//    val sourceToParse = new ArrayBuffer[String]()
    for (line <- Source.fromFile("../../iwencai/corpus/train.data", "UTF-8").getLines()) {
      val splits = line.split("\t")
      var segString = ""
      if(splits.size == 3) {
        //to simplofied chinese
        val simplifiedChinese = HanLP.convertToSimplifiedChinese(splits(2))
        val processedString = convertHanNumber(simplifiedChinese)
        val segList = segment.seg(processedString).asScala.toList
        segString = (for (i <- 0 until segList.size) yield {
          if (segList(i).nature == Nature.m) {
            val sb = new StringBuffer("NUM" + segList(i).word.length)
            if(i > 0)
              sb.append(" " + segList(i - 1).word + "NUM" + segList(i).word.length)
            if(i < segList.size - 1)
              sb.append(" " + "NUM" + segList(i).word.length + segList(i + 1).word)
            if(i < segList.size - 2)
              sb.append(" " + "NUM" + segList(i).word.length + segList(i + 2).word)
            if(i > 1)
              sb.append(" " + segList(i - 2).word + "NUM" + segList(i).word.length)
            sb.toString
          }
          else if (segList(i).nature == Nature.nr) {
            "NR" + segList(i).word.length + " " + segList(i).word
          }
          else
            segList(i).word
        }).map(
            wd => wd + " " + HanLP.convertToPinyinList(wd).asScala.map(py => py.toString).mkString("")
          )
          .mkString(" ")
      }
      //
//      sourceToParse.append(line)
//      splitsToParse.append(segString)
      out.println(line + "\t" + segString)
    }
//    val repBuf = new ArrayBuffer[Int]
//    for(split1 <- splitsToParse){
//      var maxRep = 0
//      if(split1.length > 20)
//        for(split2 <- splitsToParse){
//          if( split2.length > 20){
////            if(LCS.get(split1, split2) / split1.length + 0.0 > 0.8)
//              if(split1 == split2)
//              maxRep += 1
//          }
//        }
//      repBuf.append(maxRep)
//    }
//    for(i <- 0 until splitsToParse.size){
//      out.println(sourceToParse(i) + "\t" + splitsToParse(i) + " " + "REP"+ (repBuf(i)/3))
//    }
    out.close()
  }
}
