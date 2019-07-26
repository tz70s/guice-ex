package guiceex

import org.scalatest.{Matchers, WordSpec}

class ShopSpec extends WordSpec with Matchers {

  "Shop Service" should {
    "use injector to inject the order service" in {
      val shopService = ShopService()
      val id = shopService.order("hello")
      shopService.search(id) should be(List(id -> Some(Order("hello"))))
    }
  }
}
