package com.project.bongyang_club_backend.domain.clubJournal.service;

import com.project.bongyang_club_backend.domain.clubJournal.domain.ClubJournal;
import com.project.bongyang_club_backend.domain.clubJournal.repository.ClubJournalRepository;
import com.project.bongyang_club_backend.domain.schoolClub.domain.SchoolClub;
import com.project.bongyang_club_backend.domain.schoolClub.dto.WriteClubJournalRequest;
import com.project.bongyang_club_backend.domain.schoolClub.repository.SchoolClubRepository;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubJournalServiceImpl implements ClubJournalService {

    private static ControlTable table;

    private final String path = "journal" + File.separator;

    private final SchoolClubRepository schoolClubRepository;

    private final ClubJournalRepository clubJournalRepository;

    @Override
    public ResponseEntity<BasicResponse> writeClubJournal(WriteClubJournalRequest request, SchoolClub schoolClub) throws Exception {
        String filePath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator;
        HWPFile hwpFile = HWPReader.fromFile(filePath + "clubJournalExample.hwp");

        File file = new File(path);

        if (!file.exists()) {
            if (!file.mkdir()) {
            }
        }

        String uuid = String.valueOf(UUID.randomUUID());

        if (hwpFile != null) {
            ControlTable title = findTable1(hwpFile);
            table = findTable2(hwpFile);

            settingTitle(title, schoolClub);
            settingTable(table, request, schoolClub);

            HWPWriter.toFile(hwpFile, path + schoolClub.getName() + "_" + uuid + ".hwp");
        }

        ClubJournal clubJournal = ClubJournal.builder()
                .path(path + schoolClub.getName() + "_" + uuid + ".hwp")
                .name(schoolClub.getName() + LocalDate.now())
                .createdAt(LocalDateTime.now())
                .build();

        clubJournalRepository.save(clubJournal);

        schoolClub.getClubJournals().add(clubJournal);

        schoolClubRepository.save(schoolClub);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("일지 작성이 정상적으로 완료되었습니다.")
                .count(1)
                .result(clubJournal)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    private static ControlTable findTable1(HWPFile hwpFile) {
        Section s = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph firstParagraph = s.getParagraph(0);
        assert (firstParagraph.getControlList().get(0).getType() == ControlType.Table);
        return (ControlTable) (firstParagraph.getControlList().get(2));
    }

    private static ControlTable findTable2(HWPFile hwpFile) {
        Section s = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph firstParagraph = s.getParagraph(1);
        assert (firstParagraph.getControlList().get(0).getType() == ControlType.Table);
        return (ControlTable) (firstParagraph.getControlList().get(0));
    }

    private static void settingTitle(ControlTable title, SchoolClub schoolClub) throws UnsupportedEncodingException {
        title.getRowList().get(0).getCellList().get(0).getParagraphList().getParagraph(1).getText().addString(schoolClub.getName() + " 동아리 운영일지");
    }

    private static void settingTable(ControlTable table, WriteClubJournalRequest request, SchoolClub schoolClub) throws UnsupportedEncodingException {
        table.getRowList().get(0).getCellList().get(3).getParagraphList().getParagraph(0).getText().addString(schoolClub.getTeacher().getName());

        table.getRowList().get(1).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(schoolClub.getName());
        table.getRowList().get(1).getCellList().get(3).getParagraphList().getParagraph(0).getText().addString(request.getPlace());

        table.getRowList().get(2).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(request.getActivityDate());
        table.getRowList().get(2).getCellList().get(3).getParagraphList().getParagraph(0).getText().addString(request.getActivityTime());

        table.getRowList().get(3).getCellList().get(2).getParagraphList().getParagraph(0).getText().addString(request.getParticipantCount() + "명");

        table.getRowList().get(4).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(request.getAbsents());

        table.getRowList().get(5).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(request.getTotal() + "명");

        table.getRowList().get(6).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(request.getContent());

        table.getRowList().get(7).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(request.getLeaderRatting());

        table.getRowList().get(8).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(request.getStudentRatting());

        table.getRowList().get(9).getCellList().get(2).getParagraphList().getParagraph(0).getText().addString(request.getDueDate());

        table.getRowList().get(10).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(request.getDuePlan());

        table.getRowList().get(11).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(request.getDueGoal());

        table.getRowList().get(12).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString(request.getEtc());
    }

}
