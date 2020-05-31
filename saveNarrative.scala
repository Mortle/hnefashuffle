import scala.io.Source

object saveNarrative {
  def main(args: Array[String]): Unit = {
    val savePath = args(0)

    printSaveNarrative(savePath)
  }

  def printSaveNarrative(savePath: String): Unit = {
    val file = Source.fromFile(savePath)

    val lines = file.getLines()

    lines.next()
    val movesSeq = lines.next()
    val tokens = movesSeq.split(", ")

    var moveMaker = "Attackers"
    for(i <- 0 until tokens.length) {
      val moveCoords = tokens(i).split(" → ")

      moveMaker = if (moveMaker.equals("Attackers")) "Defenders" else "Attackers"

      printf(
        "Turn %d: %s player moved piece from %s to %s\n",
        i + 1,
        moveMaker,
        moveCoords(0),
        moveCoords.last
      )
    }

    file.close()
  }
}