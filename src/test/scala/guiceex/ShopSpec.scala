package guiceex

import org.scalatest.{FlatSpec, Matchers}

class ShopSpec extends FlatSpec with Matchers {

  behavior of "Shop"

  it should "use injector to inject the order service" in {
    val shopService = ShopService()
    val id = shopService.order("hello")
    shopService.search(id) should be(List(id -> Some(Order("hello"))))
  }
}
