package com.gildedrose

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        for (item in items) {
            if (!item.name.equals("Aged Brie") && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                    decreaseQuality(item)
                }
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

            if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                decrementSellInDays(item)
            }

            if (item.sellIn < 0) {
                if (!item.name.equals("Aged Brie")) {
                    if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                            decreaseQuality(item)
                        }
                    } else {
                        dropQuality(item)
                    }
                } else {
                    increaseQuality(item)
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

