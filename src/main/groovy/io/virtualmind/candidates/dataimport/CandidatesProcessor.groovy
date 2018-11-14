package io.virtualmind.candidates.dataimport

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.virtualmind.candidates.dataimport.model.Candidate
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.springframework.batch.item.ItemProcessor

import java.time.LocalDate
import java.time.ZoneId

@Slf4j
@CompileStatic
class CandidatesProcessor implements ItemProcessor<Row, Candidate> {

    private static final int PROFILE = 0
    private static final int NAME = 1
    private static final int LAST_CONTACT = 2
    private static final int INTERVIEW = 3
    private static final int STATUS = 4
    private static final int NOTES = 5
    private static final int ENGLISH = 6
    private static final int RESIDENCE = 7
    private static final int SALARY = 8
    private static final int PHONE = 9
    private static final int EMAIL = 10
    private static final int SENDER_TYPE = 11
    private static final int SENDER = 12
    private static final int FOLLOWER = 13

    @Override
    Candidate process(Row row) throws Exception {
        log.info("processing row number: $row.rowNum")

        return new Candidate(
                profile: stringOf(row, PROFILE),
                name: stringOf(row, NAME),
                lastContact: dateOf(row, LAST_CONTACT),
                interview: dateOf(row, INTERVIEW),
                status: stringOf(row, STATUS),
                notes: stringOf(row, NOTES),
                english: numberOf(row, ENGLISH)?.toInteger(),
                residence: stringOf(row, RESIDENCE),
                salary: numberOf(row, SALARY)?.toLong(),
                phone: phoneOf(row, PHONE),
                email: stringOf(row, EMAIL),
                senderType: stringOf(row, SENDER_TYPE),
                sender: stringOf(row, SENDER),
                follower: stringOf(row, FOLLOWER)
        )
    }

    private String stringOf(Row row, int index) {
        Cell cell = row.getCell(index)
        cell.cellType = CellType.STRING
        return cell.stringCellValue ?: null
    }

    private String phoneOf(Row row, int index) {
        Double phone = numberOf(row, index)
        return phone ? phone.toLong() : null
    }

    private LocalDate dateOf(Row row, int index) {
        Date date = row.getCell(index).dateCellValue
        return date ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null
    }

    private Double numberOf(Row row, int index) {
        row.getCell(index).numericCellValue ?: null
    }
}
