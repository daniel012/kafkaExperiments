package com.github.daniel012.avro.specific;

import com.example.Customer;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

public class specifRecordExample {

    public static void main(String[] args) {
        //create
        // you have an error is you missing a file
        Customer.Builder customerBuilder =  Customer.newBuilder();
        customerBuilder.setAge(25);
        customerBuilder.setFirstName("Joe");
        customerBuilder.setLastName("Doe");
        customerBuilder.setWeight(28f);
        customerBuilder.setHeight(1.67f);
        Customer customer = customerBuilder.build();
        System.out.println(customer);
        //write
        final DatumWriter<GenericRecord> datumWriter = new SpecificDatumWriter(Customer.class);
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {

            //another way
            // dataFileWriter.create(schema, new File("customer-generic.avro"));

            dataFileWriter.create(customer.getSchema(), new File("customer-generic_sp.avro"));
            dataFileWriter.append(customer);
            System.out.println("Written customer-generic.avro");
        } catch (IOException e) {
            System.out.println("Couldn't write file");
            e.printStackTrace();
        }
        //read
        final File file = new File("customer-generic_sp.avro");
        final DatumReader<GenericRecord> datumReader = new SpecificDatumReader(Customer.class);
        GenericRecord customerRead;
        DataFileReader <Customer> dataFileReader;
        try {
            System.out.println("reading an spefic record");
            dataFileReader = new DataFileReader(file,datumReader);
            while(dataFileReader.hasNext()){
                Customer readCustomer = dataFileReader.next();
                System.out.println(readCustomer.toString());
               }

        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //interpreted
    }

}
