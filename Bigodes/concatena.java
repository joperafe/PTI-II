        File file = new File("C:/Users/Asus/Desktop/jn/jnMundo.xml");
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();

        Files.write(Paths.get("C:/Users/Asus/Desktop/jnmundo1.xml"), bytesArray);
        String now = new String(bytesArray, StandardCharsets.UTF_8);
        System.out.println("\n**********************" + now);
