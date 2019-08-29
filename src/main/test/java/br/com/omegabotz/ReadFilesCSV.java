package br.com.omegabotz;

import  br.com.omegabotz.model.Flight;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFilesCSV {

    public static void main(String[] args) throws IOException {

        Reader reader = Files.newBufferedReader(Paths.get("data/uberair.csv"));

        CsvToBean<Flight> csvToBean = new CsvToBeanBuilder(reader)
                .withType(Flight.class)
                .build();

        List<Flight> flights = csvToBean.parse();

        for (Flight flight : flights)
            System.out.println(flight);

    }


}
