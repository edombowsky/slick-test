import freeslick.OracleProfile.api._

// Represents a database row
case class OrdOrderCode(orderCodeId: Option[Long],
                        name: String,
                        coreDescription: String,
                        isActive: Boolean)

class OrdOrderCodeTable(tag: Tag) extends Table[OrdOrderCode](tag, "ORD_ORDER_CODE") {
  def orderCodeId     = column[Long]("ORDER_CODE_ID", O.PrimaryKey, O.AutoInc)
  def name            = column[String]("NAME")
  def coreDescription = column[String]("CORE_DESCRIPTION")
  def isActive        = column[Boolean]("IS_ACTIVE")

  def * = (orderCodeId.?, name, coreDescription, isActive) <> ((OrdOrderCode.apply _).tupled, OrdOrderCode.unapply)
}
