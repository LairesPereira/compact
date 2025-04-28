package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileCompressor {

    public void comprimirArquivo(String caminhoArquivo) {
        Path caminhoFonte = Paths.get(caminhoArquivo);

        if (!Files.exists(caminhoFonte)) {
            System.out.println("Arquivo não encontrado ou caminho inválido: " + caminhoArquivo);
            return;
        }

        String nomeArquivoZip = gerarNomeArquivoZip(caminhoFonte);

        //ler o arquivo
        try {
            FileOutputStream fos = new FileOutputStream(nomeArquivoZip);
            ZipOutputStream zos = new ZipOutputStream(fos);

            ZipEntry zipEntry = new ZipEntry(caminhoFonte.getFileName().toString());
            zos.putNextEntry(zipEntry);

            byte[] dados = Files.readAllBytes(caminhoFonte);
            zos.write(dados, 0, dados.length);
            zos.closeEntry();

            System.out.println("Arquivo compactado com sucesso: " + nomeArquivoZip);

        } catch (IOException e) {
            System.out.println("Erro ao compactar o arquivo: " + e.getMessage());
        }
    }

    private String gerarNomeArquivoZip(Path caminhoFonte) {
        String nomeOriginal = caminhoFonte.getFileName().toString();
        int indicePonto = nomeOriginal.lastIndexOf('.');
        String nomeBase = (indicePonto == -1) ? nomeOriginal : nomeOriginal.substring(0, indicePonto);
        return caminhoFonte.getParent() + "/"+ nomeBase + "compact.zip";
    }
}