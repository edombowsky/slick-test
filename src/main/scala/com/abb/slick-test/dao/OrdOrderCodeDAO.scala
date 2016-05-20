import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import freeslick.OracleProfile.api._

class OrdOrderCodeDAO(db: Database) extends TableQuery(new OrdOrderCodeTable(_)) {
  // lazy val orders = TableQuery[OrdOrderTable]

  def count: Future[Int] = {
    db.run(this.length.result)
  }

  def allOrdOrderCodes: Future[Seq[Long]] = {
    val q = this.map(_.orderCodeId)
    // same as above:: val q = for (o <- orders) yield o.orderCode
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
  def all: Future[Seq[OrdOrderCode]] = {
    db.run(this.result)
  }

  def getAll: Future[List[OrdOrderCode]] = {
    db.run(this.to[List].result)
  }

  def getByOrderCodeName(name: String): Future[Option[OrdOrderCode]] = {
    val query = this.filter(_.name === name)
    val action = query.result.headOption
    db.run(action)
  }
}
