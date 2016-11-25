/*
 * Copyright (c) 2016 Jon Humble
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.me.humble.jon.shop
import scala.collection.immutable.Seq

object Shop {
  def main(args: Array[String]): Unit = {
    val products = args.collect(toProduct)
    val total    = Shop.scan(products.toList)
    println("Total Cost is " + total + " pence")
  }

  // Public API for the shop, enables the scanning and PricingSpec
  // of a sequence of products.
  def scan(products: Seq[Product]): Price = {
    val groups = products.groupBy(p => p)
    val counts = groups.mapValues(_.length)
    val prices = counts.toList.map(x => multiPrice(x._1, x._2))
    prices.fold(0.0)(_ + _)
  }

  def price(product: Product): Price = product match {
    case Apple  => 60.0
    case Orange => 25.0
  }

  def multiPrice(product: Product, count: Integer): Price = product match {
    case Apple  => priceApples(count)
    case Orange => priceOranges(count)
  }

  def priceApples(count: Integer): Price = {
    // Apples have buy one get one free.
    val applesToChargeFor = (count / 2) + (count % 2)
    price(Apple) * applesToChargeFor
  }

  def priceOranges(count: Integer): Price = {
    // Oranges are on 3 for 2
    val orangesToChargeFor = ((count / 3) * 2) + (count % 3)
    price(Orange) * orangesToChargeFor
  }

  val toProduct: PartialFunction[String, Product] = {
    case "Apple"  => Apple
    case "Orange" => Orange
  }
}
