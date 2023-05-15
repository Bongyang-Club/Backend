package com.project.bongyang_club_backend.domain.test;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderGso;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.*;
import kr.dogfoot.hwplib.object.bodytext.control.gso.ControlRectangle;
import kr.dogfoot.hwplib.object.bodytext.control.gso.GsoControlType;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.ShapeComponentNormal;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.lineinfo.*;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.shadowinfo.ShadowInfo;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponent.shadowinfo.ShadowType;
import kr.dogfoot.hwplib.object.bodytext.control.gso.shapecomponenteach.ShapeComponentRectangle;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.docinfo.BinData;
import kr.dogfoot.hwplib.object.docinfo.bindata.BinDataCompress;
import kr.dogfoot.hwplib.object.docinfo.bindata.BinDataState;
import kr.dogfoot.hwplib.object.docinfo.bindata.BinDataType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.FillInfo;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.ImageFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.ImageFillType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PictureEffect;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;

import java.awt.*;
import java.io.*;

public class Main {

    private static ControlTable table;

    public static void main(String[] args) throws Exception {

        String filePath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator;
        String imgFilePath = filePath + "sample.jpg";
        HWPFile hwpFile = HWPReader.fromFile(filePath + "clubJournalExample.hwp");

        if (hwpFile != null) {
            ControlTable title = findTable1(hwpFile);
            table = findTable2(hwpFile);

            IMPL_InsertPicture(hwpFile, "필드3", imgFilePath);
            IMPL_InsertPicture(hwpFile, "필드2", imgFilePath);
            IMPL_InsertPicture(hwpFile, "필드1", imgFilePath);

            settingTitle(title);
            settingTable(table);

            HWPWriter.toFile(hwpFile, filePath + "clubJournalResult.hwp");
        }
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

    private static void settingTitle(ControlTable title) throws UnsupportedEncodingException {
        title.getRowList().get(0).getCellList().get(0).getParagraphList().getParagraph(1).getText().addString("런닝머신 동아리 운영일지");
    }

    private static void settingTable(ControlTable table) throws UnsupportedEncodingException {
        table.getRowList().get(0).getCellList().get(3).getParagraphList().getParagraph(0).getText().addString("지도 교사");

        table.getRowList().get(1).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("동아리명");
        table.getRowList().get(1).getCellList().get(3).getParagraphList().getParagraph(0).getText().addString("장 소");

        table.getRowList().get(2).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("2023년 3월 7일 2교시");
        table.getRowList().get(2).getCellList().get(3).getParagraphList().getParagraph(0).getText().addString("2시간");

        table.getRowList().get(3).getCellList().get(2).getParagraphList().getParagraph(0).getText().addString("5명");

        table.getRowList().get(4).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("2명(조수빈: 나쁨, 조수빈: 학교에서 잠만 잠)");

        table.getRowList().get(5).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("5명");

        table.getRowList().get(6).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("없음");

        table.getRowList().get(7).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("대형이는 착하지만 조수빈은 나쁨");

        table.getRowList().get(8).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("조수빈 나쁨");

        table.getRowList().get(9).getCellList().get(2).getParagraphList().getParagraph(0).getText().addString("2023년 3월 7일 2교시");

        table.getRowList().get(10).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("활동 계획 없음");

        table.getRowList().get(11).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("활동 목표 없음");

        table.getRowList().get(12).getCellList().get(1).getParagraphList().getParagraph(0).getText().addString("기타사항 없음");
    }

    public static void IMPL_InsertPicture(HWPFile hwpFile, String fieldName, String imgFilePath) throws IOException {
        System.out.println("IMPL_InsertPicture, fieldName=" + fieldName + ", imgFilePath=" + imgFilePath);
        Main tii = new Main();
        tii.insertShapeWithImage(hwpFile, fieldName, imgFilePath);
    }

    private final String imageFileExt = "jpg";
    private final BinDataCompress compressMethod = BinDataCompress.ByStorageDefault;

    private int instanceID = 0x5bb840e1;

    private HWPFile hwpFile;

    private ControlRectangle rectangle;

    private String imageFilePath;
    private String fieldName;
    private int streamIndex;
    private int binDataID;

    private Rectangle shapePosition = new Rectangle(0, 0, 20, 20);

    private void insertShapeWithImage(HWPFile hwpFile, String fieldName, String imgFilePath) throws IOException {
        this.hwpFile = hwpFile;
        this.fieldName = fieldName;
        this.imageFilePath = imgFilePath;

        addBinData();
        binDataID = addBinDataInDocInfo(streamIndex);
        addGsoControl();
    }

    private void addBinData() throws IOException {
        streamIndex = hwpFile.getBinData().getEmbeddedBinaryDataList().size() + 1;
        String streamName = getStreamName();
        byte[] fileBinary = loadFile();

        hwpFile.getBinData().addNewEmbeddedBinaryData(streamName, fileBinary, compressMethod);
    }

    private String getStreamName() {
        return "Bin" + String.format("%04X", streamIndex) + "." + imageFileExt;
    }

    private byte[] loadFile() throws IOException {
        File file = new File(imageFilePath);
        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            ios.read(buffer);
        } finally {
            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return buffer;
    }

    private int addBinDataInDocInfo(int streamIndex) {
        BinData bd = new BinData();
        bd.getProperty().setType(BinDataType.Embedding);
        bd.getProperty().setCompress(compressMethod);
        bd.getProperty().setState(BinDataState.NotAccess);
        bd.setBinDataID(streamIndex);
        bd.setExtensionForEmbedding(imageFileExt);
        hwpFile.getDocInfo().getBinDataList().add(bd);
        return hwpFile.getDocInfo().getBinDataList().size();
    }

    private void addGsoControl() {
        createRectangleControlAtCell();

        setCtrlHeaderGso();
        setShapeComponent();
        setShapeComponentRectangle();
    }

    private void createRectangleControlAtCell() {
        Paragraph firstPara = table.getRowList().get(6).getCellList().get(1).getParagraphList().getParagraph(0);
        ParaText paraText = firstPara.getText();
        if (paraText == null) {
            firstPara.createText();
            paraText = firstPara.getText();
        }

        // 문단에서 사각형 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
        paraText.addExtendCharForGSO();

        // 문단에 사각형 컨트롤 추가한다.
        rectangle = (ControlRectangle) firstPara.addNewGsoControl(GsoControlType.Rectangle);
    }

    private void setCtrlHeaderGso() {
        CtrlHeaderGso hdr = rectangle.getHeader();
        GsoHeaderProperty prop = hdr.getProperty();
        prop.setLikeWord(false);
        prop.setApplyLineSpace(false);
        prop.setVertRelTo(VertRelTo.Para);
        prop.setVertRelativeArrange(RelativeArrange.TopOrLeft);
        prop.setHorzRelTo(HorzRelTo.Para);
        prop.setHorzRelativeArrange(RelativeArrange.TopOrLeft);
        prop.setVertRelToParaLimit(true);
        prop.setAllowOverlap(true);
        prop.setWidthCriterion(WidthCriterion.Absolute);
        prop.setHeightCriterion(HeightCriterion.Absolute);
        prop.setProtectSize(false);
        prop.setTextFlowMethod(TextFlowMethod.FitWithText);
        prop.setTextHorzArrange(TextHorzArrange.BothSides);
        prop.setObjectNumberSort(ObjectNumberSort.Figure);

        hdr.setyOffset(fromMM(shapePosition.y));
        hdr.setxOffset(fromMM(shapePosition.x));
        hdr.setWidth(fromMM(shapePosition.width));
        hdr.setHeight(fromMM(shapePosition.height));
        hdr.setzOrder(0);
        hdr.setOutterMarginLeft(0);
        hdr.setOutterMarginRight(0);
        hdr.setOutterMarginTop(0);
        hdr.setOutterMarginBottom(0);
        hdr.setInstanceId(instanceID);
        hdr.setPreventPageDivide(false);
        hdr.getExplanation().setBytes(null);
    }

    private int fromMM(int mm) {
        if (mm == 0) {
            return 1;
        }

        return (int) ((double) mm * 72000.0f / 254.0f + 0.5f);
    }

    private void setShapeComponent() {
        ShapeComponentNormal sc = (ShapeComponentNormal) rectangle.getShapeComponent();
        sc.setOffsetX(0);
        sc.setOffsetY(0);
        sc.setGroupingCount(0);
        sc.setLocalFileVersion(1);
        sc.setWidthAtCreate(fromMM(shapePosition.width));
        sc.setHeightAtCreate(fromMM(shapePosition.height));
        sc.setWidthAtCurrent(fromMM(shapePosition.width));
        sc.setHeightAtCurrent(fromMM(shapePosition.height));
        sc.setRotateAngle(0);
        sc.setRotateXCenter(fromMM(shapePosition.width / 2));
        sc.setRotateYCenter(fromMM(shapePosition.height / 2));

        sc.createLineInfo();
        LineInfo li = sc.getLineInfo();
        li.getProperty().setLineEndShape(LineEndShape.Flat);
        li.getProperty().setStartArrowShape(LineArrowShape.None);
        li.getProperty().setStartArrowSize(LineArrowSize.MiddleMiddle);
        li.getProperty().setEndArrowShape(LineArrowShape.None);
        li.getProperty().setEndArrowSize(LineArrowSize.MiddleMiddle);
        li.getProperty().setFillStartArrow(true);
        li.getProperty().setFillEndArrow(true);
        li.getProperty().setLineType(LineType.None);
        li.setOutlineStyle(OutlineStyle.Normal);
        li.setThickness(0);
        li.getColor().setValue(0);

        sc.createFillInfo();
        FillInfo fi = sc.getFillInfo();
        fi.getType().setPatternFill(false);
        fi.getType().setImageFill(true);
        fi.getType().setGradientFill(false);
        fi.createImageFill();
        ImageFill imgF = fi.getImageFill();
        imgF.setImageFillType(ImageFillType.FitSize);
        imgF.getPictureInfo().setBrightness((byte) 0);
        imgF.getPictureInfo().setContrast((byte) 0);
        imgF.getPictureInfo().setEffect(PictureEffect.RealPicture);
        imgF.getPictureInfo().setBinItemID(binDataID);

        sc.createShadowInfo();
        ShadowInfo si = sc.getShadowInfo();
        si.setType(ShadowType.None);
        si.getColor().setValue(0xc4c4c4);
        si.setOffsetX(283);
        si.setOffsetY(283);
        si.setTransparent((short) 0);

        sc.setMatrixsNormal();
    }

    private void setShapeComponentRectangle() {
        ShapeComponentRectangle scr = rectangle.getShapeComponentRectangle();
        scr.setRoundRate((byte) 0);
        scr.setX1(0);
        scr.setY1(0);
        scr.setX2(fromMM(shapePosition.width));
        scr.setY2(0);
        scr.setX3(fromMM(shapePosition.width));
        scr.setY3(fromMM(shapePosition.height));
        scr.setX4(0);
        scr.setY4(fromMM(shapePosition.height));
    }

}
