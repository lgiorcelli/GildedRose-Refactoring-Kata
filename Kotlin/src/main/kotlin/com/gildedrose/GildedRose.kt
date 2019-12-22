package com.gildedrose

class GildedRose(var items: Array<Item>) {
    private val sulfurasStrategy = SulfurasStrategy()
    private val agedBrieStrategy = AgedBrieStrategy()

    private val strategies = listOf(sulfurasStrategy, agedBrieStrategy, BackstageTicketsStrategy())

    fun updateQuality() {
        for (item in items) {
            val strategy = strategies.firstOrNull { it.appliesOn(item) }
            if (strategy != null) {
                strategy.decrementSellInDays(item)
                strategy.updateQuality(item)
            } else {
                decreaseQuality(item)
                decrementSellInDays(item)
                if (item.sellIn < 0) {
                    decreaseQuality(item)
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


interface ItemAgingStrategy {
    fun appliesOn(item: Item): Boolean
    fun decrementSellInDays(item: Item)
    fun updateQuality(item: Item)
}

class SulfurasStrategy : ItemAgingStrategy {
    override fun appliesOn(item: Item): Boolean {
        return item.name == "Sulfuras, Hand of Ragnaros"
    }

    override fun decrementSellInDays(item: Item) {
        //do nothing
    }

    override fun updateQuality(item: Item) {
        //do nothing
    }
}

class AgedBrieStrategy : ItemAgingStrategy {
    override fun appliesOn(item: Item): Boolean {
        return item.name == "Aged Brie"
    }

    override fun decrementSellInDays(item: Item) {
        item.sellIn--
    }

    override fun updateQuality(item: Item) {
        increaseQuality(item)
        if (item.sellIn < 0) {
            increaseQuality(item)
        }
    }

    private fun increaseQuality(item: Item) {
        if (item.quality < 50)
            item.quality++
    }
}

class BackstageTicketsStrategy : ItemAgingStrategy {
    override fun appliesOn(item: Item): Boolean {
        return item.name == "Backstage passes to a TAFKAL80ETC concert"
    }

    override fun decrementSellInDays(item: Item) {
        item.sellIn--
    }

    override fun updateQuality(item: Item) {
        if (item.sellIn >= 10) {
            item.quality++
        }

        if (item.sellIn in 9 downTo 6) {
            item.quality += 2
        }

        if (item.sellIn in 5 downTo 0) {
            item.quality += 3
        }

        if (item.sellIn < 0) {
            item.quality = 0
        }

    }

}

