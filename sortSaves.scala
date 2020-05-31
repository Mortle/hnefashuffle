import java.io.File

import scala.collection.mutable
import scala.io.Source

object sortSaves {

  def main(args: Array[String]): Unit = {

    val savesDir = args(0)
    val saveExtension = args(1)
    val processLineIndex = args(2).toInt

    val files = getPathsList(savesDir, saveExtension)
    val lines = files.map(x => saveParser(x, processLineIndex))

    val sortedMap = (files zip lines).toSeq.sortBy(_._2.length)
    for((k, v) <- sortedMap) println(k)
  }

  def saveParser(fileName: String, lineIndex: Integer): String = {
    val file = Source.fromFile(fileName)
    val line = file.getLines().toList(lineIndex)
    file.close()

    line.toString
  }

  def getPathsList(dir: String, extension: String): List[String] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile)
      .filter(_.getName.endsWith(extension))
      .map(_.getPath).toList
  }
}
