import java.sql.{Date, Time, Timestamp}
import java.time.{LocalDate, LocalTime, LocalDateTime, ZoneOffset, ZonedDateTime}

import freeslick.OracleProfile.api._

// Represents a database row
case class OrdOrder(orderId: Option[Long], 
                    orderNum: String, 
                    orderCode: Long, 
                    expires: Date,
                    wqLock: Boolean)

// The ordorder table
// object OrdOrder {
  class OrdOrderTable(tag: Tag) extends Table[OrdOrder](tag, "ORD_ORDER") {
    def orderId   = column[Long]("ORDER_ID", O.PrimaryKey, O.AutoInc)
    def orderNum  = column[String]("ORDER_NUM")
    def orderCode = column[Long]("ORDER_CODE")
    def expires   = column[Date]("EXPIRES")
    def wqLock    = column[Boolean]("WQ_LOCK")

    def * = (orderId.?, orderNum, orderCode, expires, wqLock) <> ((OrdOrder.apply _).tupled, OrdOrder.unapply)
  }

  //val table = TableQuery[OrdOrderTable]
//}

/*
// Query Extensions
// suppliers.page(5)
// coffies.sortBy(_.name).page(5)
implicit class QueryExtensions[T,E] (val q: Query[T,E]) {
  def page(no: Int, pagerSize: Int = 10) : Query[T,E] = q.drop((no-1)*pageSize).take(pageSize)
}

// Query extensions by Table
// coffees.byName("ColumbiaDecaf").page(5)
implicit class CoffeeExtensions(val 1: Query[Coffees, C]) {
  def byName(name: Column[Sring]) : Query[Coffees, C] = q.filter(_.name === name).sortBy(_.name)
}
*/
