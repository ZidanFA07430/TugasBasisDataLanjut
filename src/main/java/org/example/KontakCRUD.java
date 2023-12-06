package org.example;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Scanner;


public class KontakCRUD {

    private static final String DATABASE_NAME = "AplikasiKontak";
    private static final String COLLECTION_NAME = "col";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MongoClient mongoClient = MongoClients.create("mongodb+srv://zidan:eyeng123@kontak.xlbyuzt.mongodb.net/?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Tambahkan Kontak");
            System.out.println("2. Tampilkan Kontak");
            System.out.println("3. Update Kontak");
            System.out.println("4. Delete Kontak");
            System.out.println("5. Quit");

            System.out.print("Pilih menu (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    tambahKontak(scanner, collection);
                    break;
                case 2:
                    tampilkanKontak(collection);
                    break;
                case 3:
                    updateKontak(scanner, collection);
                    break;
                case 4:
                    hapusKontak(scanner, collection);
                    break;
                case 5:
                    System.out.println("Program selesai.");
                    mongoClient.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tambahKontak(Scanner scanner, MongoCollection<Document> collection) {
        System.out.print("Masukkan nama: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan nomor telepon: ");
        String nomorTelepon = scanner.nextLine();

        Document document = new Document("nama", nama)
                .append("nomorTelepon", nomorTelepon);

        collection.insertOne(document);

        System.out.println("Kontak berhasil ditambahkan.");
    }

    private static void tampilkanKontak(MongoCollection<Document> collection) {
        FindIterable<Document> documents = collection.find();

        System.out.println("Daftar Kontak:");
        for (Document document : documents) {
            System.out.println("Nama: " + document.getString("nama"));
            System.out.println("Nomor Telepon: " + document.getString("nomorTelepon"));
            System.out.println("----------------------");
        }
    }

    private static void updateKontak(Scanner scanner, MongoCollection<Document> collection) {
        System.out.print("Masukkan nama kontak yang ingin diupdate: ");
        String targetNama = scanner.nextLine();

        System.out.print("Masukkan nama baru: ");
        String namaBaru = scanner.nextLine();
        System.out.print("Masukkan nomor telepon baru: ");
        String nomorTeleponBaru = scanner.nextLine();

        Document filter = new Document("nama", targetNama);
        Document update = new Document("$set", new Document("nama", namaBaru)
                .append("nomorTelepon", nomorTeleponBaru));

        collection.updateOne(filter, update);

        System.out.println("Kontak berhasil diupdate.");
    }

    private static void hapusKontak(Scanner scanner, MongoCollection<Document> collection) {
        System.out.print("Masukkan nama kontak yang ingin dihapus: ");
        String targetNama = scanner.nextLine();

        Document filter = new Document("nama", targetNama);
        collection.deleteOne(filter);

        System.out.println("Kontak berhasil dihapus.");
    }
}
