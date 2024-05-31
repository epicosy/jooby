package kt

import org.jooby.Kooby
import org.jooby.param

class App1075 : Kooby({
    path("/v2"){
        path("/orders") {
            get {
                listOf("order-1", "order-2")
            }

            get ("/:id") {
                val id = param<Int>("id")
                id
            }
        }
        path("/products") {
            get {
                listOf("product-1", "product-2")
            }
        }
    }
})