package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class GildedRoseTest {
    private val backstagePasses = "Backstage passes to a TAFKAL80ETC concert"
    private val standardItem = "Standard item"
    val aQualityIncreseableItem = "Aged Brie"
    private val defaultDellIn = 10
    private val initialQuality = 10

    @Test
    fun `an standard item degrades quality and reduces sell in days when app updates quality`() {
        val app = givenAGildedRoseApp()
        app.updateQuality()
        val item = app.items[0]
        assertEquals(standardItem, item.name)
        assertEquals(9, item.quality)
        assertEquals(9, item.sellIn)
    }

    //- Once the sell by date has passed, Quality degrades twice as fast
    @Test
    fun `quality degrades twice as fast after sell by date has passed`() {
        val app = givenAGildedRoseApp(sellIn = 0)

        app.updateQuality()

        val item = app.items[0]
        assertEquals(standardItem, item.name)
        assertEquals(8, item.quality)
        assertEquals(-1, item.sellIn)
    }

    @Test
    internal fun `The Quality of an item is never negative`() {
        val app = givenAGildedRoseApp(quality = 0, sellIn = 0)

        app.updateQuality()

        val item = app.items[0]
        assertEquals(standardItem, item.name)
        assertEquals(0, item.quality)
        assertEquals(-1, item.sellIn)
    }

    @Test
    internal fun `"Aged Brie" actually increases in Quality the older it gets`() {
        val app = givenAGildedRoseApp(aQualityIncreseableItem)

        app.updateQuality()

        val item = app.items[0]
        assertEquals(aQualityIncreseableItem, item.name)
        assertEquals(9, item.sellIn)
        assertEquals(11, item.quality)
    }

    @Test
    internal fun `"Aged Brie" increases in Quality twice the older it gets, after sellin date`() {
        val app = givenAGildedRoseApp(aQualityIncreseableItem, sellIn = -1)

        app.updateQuality()

        with(app.items[0]) {
            assertEquals(-2, sellIn)
            assertEquals(12, quality)
        }

    }

    @Test
    internal fun `The Quality of an item is never more than 50`() {
        val app = givenAGildedRoseApp(aQualityIncreseableItem, quality = 50)
        app.updateQuality()
        val item = app.items[0]
        assertEquals(aQualityIncreseableItem, item.name)
        assertEquals(9, item.sellIn)
        assertEquals(50, item.quality)
    }

    @Test
    internal fun `"Sulfuras", being a legendary item, never has to be sold or decreases in Quality`() {
        val sulfuras = "Sulfuras, Hand of Ragnaros"
        val app = givenAGildedRoseApp(sulfuras)

        app.updateQuality()

        val item = app.items[0]
        assertEquals(sulfuras, item.name)
        assertEquals(10, item.sellIn)
        assertEquals(10, item.quality)
    }

    @Test
    internal fun `Backstage passes quality drops to 0 after the concert`() {

        val app = givenAGildedRoseApp(backstagePasses, sellIn = 0)

        app.updateQuality()

        with(app.items[0]) {
            assertEquals(-1, sellIn)
            assertEquals(0, quality)
        }
    }


    @Test
    internal fun `Backstage passes Quality increases by 2 when there are 10 days or less`() {
        val app = givenAGildedRoseApp(backstagePasses)

        app.updateQuality()

        with(app.items[0]) {
            assertEquals(9, sellIn)
            assertEquals(12, quality)
        }
    }

    @Test
    internal fun `Backstage passes Quality increases by 3 when there are 5 days or less`() {
        val app = givenAGildedRoseApp(backstagePasses, sellIn = 5)

        app.updateQuality()

        with(app.items[0]) {
            assertEquals(4, sellIn)
            assertEquals(13, quality)
        }
    }

    @Test
    internal fun `Backstage passes Quality increases by 1 when there are 11 days or more`() {
        val app = givenAGildedRoseApp(backstagePasses, sellIn = 11)

        app.updateQuality()

        with(app.items[0]) {
            assertEquals(10, sellIn)
            assertEquals(11, quality)
        }
    }


    private fun givenAGildedRoseApp(itemName: String = standardItem, sellIn: Int = defaultDellIn, quality: Int = initialQuality): GildedRose {
        val items = arrayOf(Item(itemName, sellIn, quality))
        return GildedRose(items)
    }

/*

	-
	- "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
	Quality drops to 0 after the concert
     */

}


