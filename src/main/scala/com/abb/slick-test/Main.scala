import com.typesafe.scalalogging._
import freeslick.OracleProfile.api._

import scala.util.{Success, Failure}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends LazyLogging {

  // Original method of getting connection information
  /*
  val dbUrl = "jdbc:oracle:thin:@dellr815c.ventyx.us.abb.com:1521:r12102"
  // val dbUrl = "jdbc:oracle:thin:@vsshp01.ventyx.us.abb.com:1521:r12102"
  val username = "ssvm011_odb1"
  val password = "ssvm011_odb1"
  lazy val db = Database.forURL(dbUrl, username, password, driver = "oracle.jdbc.OracleDriver")
  // Workaround for bug #1440: https://github.com/slick/slick/issues/1400
  // db.run(sql"""SELECT 1""".as[Int
  // Force initialization here to work around #1400:
  val session = db.createSession()
  try session.force() finally session.close()
  */
 
  val db = Database.forConfig("ssvm011_odb")
  // Workaround for bug #1440: https://github.com/slick/slick/issues/1400
  // db.run(sql"""SELECT 1""".as[Int
  // Force initialization here to work around #1400:
  val session = db.createSession()
  try {
    session.force() 
  } catch {
    case e: Throwable => 
      println(e.getMessage)
      sys.exit(1)
  } finally session.close()
  
  // Helper method for running query - blocking
  def exec[T](action: DBIO[T]): T = Await.result(db.run(action), 150.seconds)

  def main(argv: Array[String]) {
    logger.info("starting Main...")
    println("Let's run some queries")

    val ordOrder     = new OrdOrderDAO(db)
    val ordOrderCode = new OrdOrderCodeDAO(db)

    val monadicCrossJoin = for {
      oo <- ordOrder
      ooc <- ordOrderCode if oo.orderCode === ooc.orderCodeId
    } yield (oo.orderNum, ooc.coreDescription)

    println("Join:: " + exec(monadicCrossJoin.result))

    try {
      val numOrders = ordOrder.count

      numOrders.onComplete {
        case Success(n) => println(s"Number of orders: ${n}")
        case Failure(e) => println("e.printStackTrace")
      }

      ordOrder.allOrderCodes.onSuccess {case s => println(s"\nAll Order Codes:: $s")}
      ordOrder.allOrderNums.onSuccess {case s => println(s"\nAll Order Nums:: $s")}
      ordOrder.all.onSuccess {case s => println(s"\nAll Orders[Seq]:: $s")}
      ordOrder.getAll.onSuccess {case s => println(s"\nAll Orders[List]:: $s")}

      ordOrder.getByOrderNum("DSP1000000000000020").onSuccess {case s => println(s"\nResult: $s")}

      // Plain Selects
      // Count the number of rows in the message table.
      println("Number of orders: " + exec(sql"SELECT count(*) FROM ord_order".as[Int]))

    } finally db.close
  }
}
