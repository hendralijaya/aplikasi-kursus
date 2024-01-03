package com.example.aplikasikursus.utils;

import com.example.aplikasikursus.domain.*;
import com.example.aplikasikursus.repository.*;
import com.example.aplikasikursus.service.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Label;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PdfGenerator {
    private TutorialService tutorialService;
    private TutorialDetailService tutorialDetailService;
    private SiswaService siswaService;
    private TutorService tutorService;
    private CoursesService coursesService;

    public PdfGenerator(TutorialService tutorialService, TutorialDetailService tutorialDetailService, SiswaService siswaService, TutorService tutorService, CoursesService coursesService) {
        this.tutorialService = tutorialService;
        this.tutorialDetailService = tutorialDetailService;
        this.siswaService = siswaService;
        this.tutorService = tutorService;
        this.coursesService = coursesService;
    }

    public void generatePdfReport(Tutorial selectedTutorial) {
        // Create a new document
        Document document = new Document();
        try {
            // Create a PdfWriter instance to write the document to a file
            Siswa currentSiswa = siswaService.findById(selectedTutorial.getSiswaId()).getObject();
            String namaSiswa = currentSiswa.getNama();
            Tutor currentTutor = tutorService.findById(selectedTutorial.getTutorId()).getObject();
            String namaTutor = currentTutor.getNama();
            Courses currentCourses = coursesService.findById(selectedTutorial.getCoursesId()).getObject();
            String namaCourses = currentCourses.getNamaKursus();
            String filename = namaSiswa + "-" + namaTutor + "-" + namaCourses + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filename));

            // Open the document
            document.open();

            // Add tutorial information
            Paragraph title = new Paragraph("Report Tutorial");
            document.add(title);

            // Add labels and values to left column pane
            Label[] labels = {
                    new Label("Nama Siswa:"),
                    new Label("Nama Tutor:"),
                    new Label("Nama Kursus:"),
                    new Label("Term:"),
                    new Label("Tanggal Mulai Term:"),
                    new Label("Nilai Akhir:")
            };

            String[] values = {
                    namaSiswa,
                    namaTutor,
                    namaCourses,
                    String.valueOf(selectedTutorial.getTerm()),
                    String.valueOf(selectedTutorial.getTanggalMulaiTerm()),
                    String.valueOf(selectedTutorial.getNilaiAkhir())
            };

            for (int i = 0; i < labels.length; i++) {
                document.add(new Paragraph(labels[i].getText() + " " + values[i]));
            }

            // Add additional labels and values
            Label[] additionalLabels = {
                    new Label("Jam Kehadiran:"),
                    new Label("Catatan Tutor:"),
                    new Label("Status:"),
                    new Label("Created At:"),
                    new Label("Updated At:"),
                    new Label("Deactivate At:")
            };

            String[] additionalValues = {
                    String.valueOf(selectedTutorial.getJamKehadiran()),
                    selectedTutorial.getCatatanTutor(),
                    selectedTutorial.getStatus(),
                    selectedTutorial.getCreatedAt() != null ? selectedTutorial.getCreatedAt().toString() : "N/A",
                    selectedTutorial.getUpdatedAt() != null ? selectedTutorial.getUpdatedAt().toString() : "N/A",
                    selectedTutorial.getDeactivateAt() != null ? selectedTutorial.getDeactivateAt().toString() : "N/A"
            };

            for (int i = 0; i < additionalLabels.length; i++) {
                document.add(new Paragraph(additionalLabels[i].getText() + " " + additionalValues[i]));
            }

            // Get tutorial details
            List<TutorialDetail> details = tutorialDetailService.findByTutorialId(selectedTutorial.getId());

            // Iterate over tutorial details and add content to the document
            // Iterate over tutorial details and add content to the document
            for (TutorialDetail detail : details) {
                // Create a new page for each tutorial detail
                document.newPage();

                // Add tutorial detail content
                Paragraph content = new Paragraph("Pertemuan: " + detail.getPertemuan());
                document.add(content);

                // Add labels and values to the document
                Label[] pertemuanLabels = {
                        new Label("Sesi:"),
                        new Label("Pertemuan:"),
                        new Label("Durasi:"),
                        new Label("Tanggal Tutorial:")
                };

                Label[] pertemuanValues = {
                        new Label(String.valueOf(detail.getSesi())),
                        new Label(String.valueOf(detail.getPertemuan())),
                        new Label(String.valueOf(detail.getDurasi())),
                        new Label(detail.getTanggalTutorial().toString())
                };

                // Add labels and values to the document
                for (int i = 0; i < pertemuanLabels.length; i++) {
                    document.add(new Paragraph(pertemuanLabels[i].getText() + " " + pertemuanValues[i].getText()));
                }

                // Add additional labels and values
                Label[] additionalPertemuanLabels = {
                        new Label("Jam Mulai:"),
                        new Label("Jam Selesai:"),
                        new Label("Realisasi Materi:"),
                        new Label("Realisasi PR:")
                };

                Label[] additionalPertemuanValues = {
                        new Label(detail.getJamMulai().toString()),
                        new Label(detail.getJamSelesai().toString()),
                        new Label(detail.getRealisasiMateri()),
                        new Label(detail.getRealisasiPr())
                };

                // Add additional labels and values to the document
                for (int i = 0; i < additionalPertemuanLabels.length; i++) {
                    document.add(new Paragraph(additionalPertemuanLabels[i].getText() + " " + additionalPertemuanValues[i].getText()));
                }

                // Create remaining labels and values
                Label[] remainingPertemuanLabels = {
                        new Label("Rating Tutor:"),
                        new Label("Rating Siswa:"),
                        new Label("Nilai:"),
                        new Label("Diinput Oleh:")
                };

                Label[] remainingPertemuanValues = {
                        new Label(String.valueOf(detail.getRatingTutor())),
                        new Label(String.valueOf(detail.getRatingSiswa())),
                        new Label(String.valueOf(detail.getNilai())),
                        new Label(detail.getDiinputOleh())
                };

                // Add remaining labels and values to the document
                for (int i = 0; i < remainingPertemuanLabels.length; i++) {
                    document.add(new Paragraph(remainingPertemuanLabels[i].getText() + " " + remainingPertemuanValues[i].getText()));
                }

                // Create additional remaining labels and values
                Label[] additionalRemainingPertemuanLabels = {
                        new Label("Status:"),
                        new Label("Created At:"),
                        new Label("Updated At:"),
                        new Label("Deactivate At:")
                };

                Label[] additionalRemainingPertemuanValues = {
                        new Label(detail.getStatus()),
                        new Label(detail.getCreatedAt() != null ? detail.getCreatedAt().toString() : "N/A"),
                        new Label(detail.getUpdatedAt() != null ? detail.getUpdatedAt().toString() : "N/A"),
                        new Label(detail.getDeactivateAt() != null ? detail.getDeactivateAt().toString() : "N/A")
                };

                // Add additional remaining labels and values to the document
                for (int i = 0; i < additionalRemainingPertemuanLabels.length; i++) {
                    document.add(new Paragraph(additionalRemainingPertemuanLabels[i].getText() + " " + additionalRemainingPertemuanValues[i].getText()));
                }
            }

// Close the document
            document.close();

            System.out.println("PDF generated successfully.");
        } catch (DocumentException | FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
