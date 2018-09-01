package guiceex

import java.util.UUID

import javax.inject.{Provider, Singleton}

import scala.collection.concurrent.TrieMap

case class ItemId(id: String)
case class Order(content: String)

trait OrderService {
  def order(order: Order): ItemId
  def searchOrdered(items: ItemId*): List[(ItemId, Option[Order])]
  def listOrdered(): List[(ItemId, Order)]
}

@Singleton
class OrderServiceImpl extends OrderService {

  // Should be thread safe in Singleton class.

  private[this] val inMemoryStore = TrieMap[ItemId, Order]()

  override def order(order: Order): ItemId = {
    val itemId = ItemId(UUID.randomUUID().toString)
    inMemoryStore += (itemId -> order)
    itemId
  }

  override def listOrdered(): List[(ItemId, Order)] = inMemoryStore.toList

  override def searchOrdered(items: ItemId*): List[(ItemId, Option[Order])] =
    items.map(item => (item, inMemoryStore.get(item))).toList
}

class OrderServiceProvider extends Provider[OrderService] {
  override def get: OrderService = new OrderServiceImpl
}
