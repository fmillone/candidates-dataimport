package io.virtualmind.candidates.dataimport.job

import groovy.transform.CompileStatic
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.springframework.batch.item.ItemReader

@CompileStatic
class RowReader implements ItemReader<Row> {

    Iterator<Row> iterator

    RowReader(Sheet sheet) {
        iterator = sheet.iterator()
        skipHeadersRow()
    }

    private void skipHeadersRow() {
        if (iterator.hasNext()) {
            iterator.next()
        }
    }

    @Override
    Row read() {
        iterator.hasNext() ? iterator.next() : null
    }
}