import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import freeslick.OracleProfile.api._

class OrdOrderDAO(db: Database) extends TableQuery(new OrdOrderTable(_)) {
  // lazy val orders = TableQuery[OrdOrderTable]

  def count: Future[Int] = {
    db.run(this.length.result)
  }

  def allOrderCodes: Future[Seq[Long]] = {
    val q = this.map(_.orderCode)
    // same as above:: val q = for (o <- orders) yield o.orderCode
    val a = q.result
    db.run(a)
  }

  def allOrderNums: Future[Seq[String]] = {
    val q = this.map(_.orderNum)
    // same as above:: val q = for (o <- orders) yield o.orderNum
    val a = q.result
    db.run(a)
  }

/*
  def all: Future[Seq[OrdOrder]] = {
    val f = db.run(this.result)
    f.map(seq => seq.map(ordOrderRowtoOrdOrder(_)))
  }

  private def ordOrderRowtoOrdOrder(ordOrderRow: OrdOrder): OrdOrder = {
    OrdOrder(ordOrderRow.orderId, ordOrderRow.orderNum, ordOrderRow.orderCode)
  }
*/
  def all: Future[Seq[OrdOrder]] = {
    db.run(this.result)
  }

  def getAll: Future[List[OrdOrder]] = {
    db.run(this.to[List].result)
  }

  def getByOrderNum(orderNum: String): Future[Option[OrdOrder]] = {
    val query = this.filter(_.orderNum === orderNum)
    val action = query.result.headOption
    db.run(action)
  }
}
