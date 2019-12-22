package com.gildedrose

class GildedRose(var items: Array<Item>) {
    private val sulfurasStrategy = SulfurasStrategy()
    private val agedBrieStrategy = AgedBrieStrategy()

    private val strategies = listOf(sulfurasStrategy)

    fun updateQuality() {
        for (item in items) {
            val strategy = strategies.firstOrNull { it.appliesOn(item) }
            if (strategy != null) {
                strategy.decrementSellInDays(item)
                strategy.updateQuality(item)
            } else {
                if (!item.name.equals("Aged Brie") && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    decreaseQuality(item)
                } else {
                    if (item.quality < 50) {
                        increaseQuality(item)
                        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                            if (item.sellIn < 11) {
                                increaseQuality(item)
                            }
                            if (item.sellIn < 6) {
                                increaseQuality(item)
                            }
                        }
                    }
                }

                decrementSellInDays(item)

                if (item.sellIn < 0) {
                    if (!item.name.equals("Aged Brie")) {
                        if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                            decreaseQuality(item)
                        } else {
                            dropQuality(item)
                        }
                    } else {
                        increaseQuality(item)
                    }
                }
            }
        }
    }

    private fun dropQuality(item: Item) {
        item.quality = 0
    }

    private fun decrementSellInDays(item: Item) {
        item.sellIn--
    }

    private fun decreaseQuality(item: Item) {
        if (item.quality > 0)
            item.quality--
    }

    private fun increaseQuality(item: Item) {
        if (item.quality < 50)
            item.quality++
    }

}


class SulfurasStrategy {
    fun appliesOn(item: Item): Boolean {
        return item.name == "Sulfuras, Hand of Ragnaros"
    }

    fun decrementSellInDays(item: Item) {
        //do nothing
    }

    fun updateQuality(item: Item) {
        //do nothing
    }
}

class AgedBrieStrategy {
    fun appliesOn(item: Item): Boolean {
        return item.name == "Aged Brie"
    }

    fun decrementSellInDays(item: Item) {
        item.sellIn--
    }

    fun updateQuality(item: Item) {
        if (item.quality < 50)
            item.quality++
    }
}

