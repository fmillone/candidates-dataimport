package io.virtualmind.candidates.dataimport

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.springframework.batch.item.ItemReader


class RowReader implements ItemReader<Row> {

    Iterator<Row> iterator

    RowReader(Sheet sheet) {
        iterator = sheet.iterator()
        skipHeadersRow()
    }

    void skipHeadersRow() {
        if (iterator.hasNext()) {
            iterator.next()
        }
    }

    @Override
    Row read() {
        iterator.hasNext() ? iterator.next() : null
    }
}