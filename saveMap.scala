import scala.io.Source

object saveMap {
  def main(args: Array[String]): Unit = {
    val xDim: Integer = 11
    val yDim: Integer = 11

    val savePath = args(0)

    printSaveMap(savePath, xDim, yDim)
  }

  def printSaveMap(filePath: String, xDim: Integer, yDim: Integer): Unit = {
    val file = Source.fromFile(filePath)
    val lines = file.getLines()

    val currentPlayer = lines.next()
    printf("Current turn: %s\n", currentPlayer)

    lines.next()

    var defendersNum: Int = 0
    var attackersNum: Int = 0

    val gameMap = Array.tabulate(xDim, yDim)(calculateCellValue)

    while(lines.hasNext) {
      val tokens = lines.next().split(" ")
      val xCoordinate = tokens(0).toInt
      val yCoordinate = tokens(1).toInt
      val pieceType = tokens(2)
      val union = tokens(3)

      if (union.equals("Defender")) {
        defendersNum += 1

        gameMap(xCoordinate)(yCoordinate) = pieceType(0)
      } else if (union.equals("Attacker")) {
        attackersNum += 1

        gameMap(xCoordinate)(yCoordinate) = 'v'
      }
    }
    printf("Defenders: %d, Attackers: %d\n", defendersNum, attackersNum)

    gameMap foreach { row => row foreach print; println }

    file.close()
  }

  def calculateCellValue(i: Int, j: Int) =
    if ((i == 0 && j == 10) ||
      (i == 0 && j == 0) ||
      (i == 10 && j == 0) ||
      (i == 10 && j == 10)
    ) '#' else '.'
}