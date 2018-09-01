package guiceex

import com.google.inject.Guice
import guiceex.inject.Module
import javax.inject.{Inject, Singleton}

class ShopService @Inject()(private[this] val orderService: OrderService) {
  def order(content: String): ItemId = orderService.order(Order(content))
  def search(ids: ItemId*) = orderService.searchOrdered(ids: _*)
}

//# Trivial scala-based abstraction and DSL via implicit extension from Guice.

class ShopModule extends Module {
  override def configure(): Unit =
    //# Default provider: bind[OrderService].to[OrderServiceImpl].in[Singleton]
    bind[OrderService].toProvider[OrderServiceProvider].in[Singleton]
}

object ShopService {
  def apply() = Guice.createInjector(new ShopModule).getInstance(classOf[ShopService])
}
