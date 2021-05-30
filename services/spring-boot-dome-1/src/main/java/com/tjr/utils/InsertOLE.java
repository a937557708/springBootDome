package com.tjr.utils;


import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.OleObjectType;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.DocOleObject;
import com.spire.doc.fields.DocPicture;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class InsertOLE {

    public static void main(String[] args) {
        //创建Document对象，并加载Word文档
        Document doc = new Document();
        doc.loadFromFile("F:\\日志临时存放位置\\新建 DOC 文档.doc");

        //获取最后一节
        Section section = doc.getLastSection();

        //添加段落
        Paragraph par = section.addParagraph();
        //加载一个图片，它将作为外部文件的符号显示在Word文档中
        DocPicture pdfIcon = new DocPicture(doc);
        pdfIcon.loadImage("F:\\日志临时存放位置\\0944_1.png");

        //将一个PDF文件作为OLE对象插入Word文档
        par.appendOleObject("F:\\日志临时存放位置\\安装步骤.txt", pdfIcon, OleObjectType.Adobe_Acrobat_Document);

        //另存为一个文档
        doc.saveToFile("F:\\日志临时存放位置\\EmbedDocument11.doc", FileFormat.Docx_2013);
    }
}