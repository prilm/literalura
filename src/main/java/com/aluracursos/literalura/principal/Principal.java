package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.model.MapeoJsonLibro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com";
    private ConvierteDatos conversor = new ConvierteDatos();
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ________________________________________________
                    1 - Buscar libro por titulo (en la API Gutendex)
                    2 - Busqueda de libro por título
                    3 - Lista de todos los libros
                    4 - Lista de libros por idioma
                    5 - Lista de autores
                    6 - Lista de autores vivos en determinado año

                    0 - Salir
                    ________________________________________________
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    getDatosLibro();
                    break;
                case 2:
                    buscarLibrosPorTitulo();
                    break;
                case 3:
                    listarTodosLosLibros();
                    break;
                case 4:
                    listarLibrosPorIdioma();
                    break;
                case 5:
                    listarTodosLosAutores();
                    break;
                case 6:
                    listarAutoresVivosEnDeterminadoAnio();
                    break;
                case 0:
                    System.out.println("🚪 Cerrando la aplicación...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }

    }

    private void getDatosLibro() {
        System.out.println("Escribe el titulo del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + "/books?search=" + nombreLibro.replace(" ", "%20"));
        // descomentar este json para prueba rapida. ya que la API de Gutendex a veces es muy lenta e impide utilizar esta app
//        var json = "{\"count\":5,\"next\":null,\"previous\":null,\"results\":[{\"id\":2701,\"title\":\"Moby Dick; Or, The Whale\",\"authors\":[{\"name\":\"Melville, Herman\",\"birth_year\":1819,\"death_year\":1891}],\"summaries\":[\"\\\"Moby Dick; Or, The Whale\\\" by Herman Melville is a novel written in the mid-19th century. The story follows Ishmael, a sailor on a whaling voyage, who seeks adventure and escape from his gloomy life on land. As he embarks on this journey, he becomes drawn into the complex world of whaling and is introduced to the ominous figure of Captain Ahab, whose obsession with a legendary white whale ultimately drives the narrative.  At the start of the novel, Ishmael introduces himself and shares his philosophy about the sea as a remedy for his melancholic disposition. He muses on the magnetic pull of the ocean, describing not only his own urge to set sail but also the collective longing of city dwellers for the water. Ishmael's journey takes him to New Bedford, where he experiences a series of humorous and strange encounters while seeking lodging before joining a whaling ship. As he navigates his way through the town, he is introduced to Queequeg, a tattooed harpooner with a mysterious past, setting the stage for a unique friendship that unfolds amidst the backdrop of whaling adventures. (This is an automatically generated summary.)\"],\"translators\":[],\"subjects\":[\"Adventure stories\",\"Ahab, Captain (Fictitious character) -- Fiction\",\"Mentally ill -- Fiction\",\"Psychological fiction\",\"Sea stories\",\"Ship captains -- Fiction\",\"Whales -- Fiction\",\"Whaling -- Fiction\",\"Whaling ships -- Fiction\"],\"bookshelves\":[\"Best Books Ever Listings\",\"Category: Adventure\",\"Category: American Literature\",\"Category: Classics of Literature\",\"Category: Novels\"],\"languages\":[\"en\"],\"copyright\":false,\"media_type\":\"Text\",\"formats\":{\"text/html\":\"https://www.gutenberg.org/ebooks/2701.html.images\",\"application/epub+zip\":\"https://www.gutenberg.org/ebooks/2701.epub3.images\",\"application/x-mobipocket-ebook\":\"https://www.gutenberg.org/ebooks/2701.kf8.images\",\"text/plain; charset=us-ascii\":\"https://www.gutenberg.org/ebooks/2701.txt.utf-8\",\"application/rdf+xml\":\"https://www.gutenberg.org/ebooks/2701.rdf\",\"image/jpeg\":\"https://www.gutenberg.org/cache/epub/2701/pg2701.cover.medium.jpg\",\"application/octet-stream\":\"https://www.gutenberg.org/cache/epub/2701/pg2701-h.zip\"},\"download_count\":115502},{\"id\":15,\"title\":\"Moby-Dick; or, The Whale\",\"authors\":[{\"name\":\"Melville, Herman\",\"birth_year\":1819,\"death_year\":1891}],\"summaries\":[\"\\\"Moby-Dick; or, The Whale\\\" by Herman Melville is a novel written in the mid-19th century. The book explores themes of obsession, vengeance, and humanity's relationship with nature through the experiences of its central character, Ishmael, who embarks on a whaling voyage aboard the Pequod, captained by the enigmatic and vengeful Ahab.  The opening of \\\"Moby-Dick\\\" introduces Ishmael, who shares his existential musings and the reasons for his desire to go to sea. He portrays the bustling port city of New Bedford, highlighting the magnetic pull of the ocean on the hearts of men. As he prepares for his journey, Ishmael reflects on his own internal struggles and motivations, ultimately leading him to desire adventure in the whaling industry. He arrives in New Bedford, confronts the challenges of finding a place to stay, and has a rather amusing encounter with the landlord and an unexpected harpooneer, setting the stage for his subsequent adventures at sea. (This is an automatically generated summary.)\"],\"translators\":[],\"subjects\":[\"Adventure stories\",\"Ahab, Captain (Fictitious character) -- Fiction\",\"Mentally ill -- Fiction\",\"Psychological fiction\",\"Sea stories\",\"Ship captains -- Fiction\",\"Whales -- Fiction\",\"Whaling -- Fiction\",\"Whaling ships -- Fiction\"],\"bookshelves\":[\"Adventure\",\"Best Books Ever Listings\",\"Category: Adventure\",\"Category: American Literature\",\"Category: Classics of Literature\",\"Category: Novels\"],\"languages\":[\"en\"],\"copyright\":false,\"media_type\":\"Text\",\"formats\":{\"text/html\":\"https://www.gutenberg.org/ebooks/15.html.images\",\"application/epub+zip\":\"https://www.gutenberg.org/ebooks/15.epub3.images\",\"application/x-mobipocket-ebook\":\"https://www.gutenberg.org/ebooks/15.kf8.images\",\"application/rdf+xml\":\"https://www.gutenberg.org/ebooks/15.rdf\",\"image/jpeg\":\"https://www.gutenberg.org/cache/epub/15/pg15.cover.medium.jpg\",\"application/octet-stream\":\"https://www.gutenberg.org/cache/epub/15/pg15-h.zip\",\"text/plain; charset=us-ascii\":\"https://www.gutenberg.org/ebooks/15.txt.utf-8\"},\"download_count\":15841},{\"id\":28794,\"title\":\"Moby Dick; Or, The Whale\",\"authors\":[{\"name\":\"Melville, Herman\",\"birth_year\":1819,\"death_year\":1891}],\"summaries\":[],\"translators\":[],\"subjects\":[\"Adventure stories\",\"Ahab, Captain (Fictitious character) -- Fiction\",\"Mentally ill -- Fiction\",\"Psychological fiction\",\"Sea stories\",\"Ship captains -- Fiction\",\"Whales -- Fiction\",\"Whaling -- Fiction\",\"Whaling ships -- Fiction\"],\"bookshelves\":[\"Best Books Ever Listings\"],\"languages\":[\"en\"],\"copyright\":false,\"media_type\":\"Sound\",\"formats\":{\"image/jpeg\":\"https://www.gutenberg.org/cache/epub/28794/pg28794.cover.medium.jpg\",\"text/plain; charset=us-ascii\":\"https://www.gutenberg.org/files/28794/28794_readme.txt\",\"text/html\":\"https://www.gutenberg.org/files/28794/28794_index.html\",\"audio/ogg\":\"https://www.gutenberg.org/files/28794/ogg/28794-01.ogg\",\"audio/mp4\":\"https://www.gutenberg.org/files/28794/m4b/28794-01.m4b\",\"audio/mpeg\":\"https://www.gutenberg.org/files/28794/mp3/28794-01.mp3\",\"application/rdf+xml\":\"https://www.gutenberg.org/ebooks/28794.rdf\"},\"download_count\":1487},{\"id\":2489,\"title\":\"Moby Dick; Or, The Whale\",\"authors\":[{\"name\":\"Melville, Herman\",\"birth_year\":1819,\"death_year\":1891}],\"summaries\":[\"\\\"Moby Dick; Or, The Whale\\\" by Herman Melville is a novel written in the mid-19th century. The story follows Ishmael, the narrator, who embarks on a whaling voyage aboard the Pequod, captained by the enigmatic Ahab, who is obsessed with pursuing the infamous whale, Moby Dick. The narrative explores themes of obsession, humanity's relationship with nature, and existential questions, set against the backdrop of the whaling industry.  The opening of \\\"Moby Dick\\\" introduces us to Ishmael, who seeks solace at sea after feeling a sense of melancholy and disconnection from life on land. He describes a vibrant yet insular New York City, with its wharves and the allure of the ocean that captivates many a land-bound soul. This leads him to his decision to go whaling, driven by both an itch for adventure and intrigue about the great whale itself. As Ishmael travels to New Bedford, the excitement builds around his impending voyage, and the first few chapters set a rich scene, populated with colorful characters and an atmosphere thick with the mystique of the sea and whaling life. Ultimately, Ishmael's encounter with Queequeg, a tattooed harpooner from the South Seas, leads to a deeper exploration of friendship and cultural differences as they share a bed at the Spouter Inn, which hints at the adventures and challenges that await them at sea. (This is an automatically generated summary.)\"],\"translators\":[],\"subjects\":[\"Adventure stories\",\"Ahab, Captain (Fictitious character) -- Fiction\",\"Mentally ill -- Fiction\",\"Psychological fiction\",\"Sea stories\",\"Ship captains -- Fiction\",\"Whales -- Fiction\",\"Whaling -- Fiction\",\"Whaling ships -- Fiction\"],\"bookshelves\":[\"Best Books Ever Listings\",\"Category: Adventure\",\"Category: American Literature\",\"Category: Classics of Literature\",\"Category: Novels\"],\"languages\":[\"en\"],\"copyright\":false,\"media_type\":\"Text\",\"formats\":{\"text/html\":\"https://www.gutenberg.org/ebooks/2489.html.images\",\"application/epub+zip\":\"https://www.gutenberg.org/ebooks/2489.epub3.images\",\"application/x-mobipocket-ebook\":\"https://www.gutenberg.org/ebooks/2489.kf8.images\",\"text/plain; charset=us-ascii\":\"https://www.gutenberg.org/ebooks/2489.txt.utf-8\",\"application/rdf+xml\":\"https://www.gutenberg.org/ebooks/2489.rdf\",\"image/jpeg\":\"https://www.gutenberg.org/cache/epub/2489/pg2489.cover.medium.jpg\",\"application/octet-stream\":\"https://www.gutenberg.org/cache/epub/2489/pg2489-h.zip\"},\"download_count\":390},{\"id\":9147,\"title\":\"Moby Dick\",\"authors\":[{\"name\":\"Melville, Herman\",\"birth_year\":1819,\"death_year\":1891}],\"summaries\":[],\"translators\":[],\"subjects\":[\"Adventure stories\",\"Ahab, Captain (Fictitious character) -- Fiction\",\"Mentally ill -- Fiction\",\"Psychological fiction\",\"Sea stories\",\"Ship captains -- Fiction\",\"Whales -- Fiction\",\"Whaling -- Fiction\",\"Whaling ships -- Fiction\"],\"bookshelves\":[\"Best Books Ever Listings\"],\"languages\":[\"en\"],\"copyright\":true,\"media_type\":\"Sound\",\"formats\":{\"image/jpeg\":\"https://www.gutenberg.org/cache/epub/9147/pg9147.cover.medium.jpg\",\"text/plain; charset=us-ascii\":\"https://www.gutenberg.org/files/9147/9147-readme.txt\",\"audio/mpeg\":\"https://www.gutenberg.org/files/9147/mp3/9147-000.mp3\",\"application/rdf+xml\":\"https://www.gutenberg.org/ebooks/9147.rdf\",\"application/octet-stream\":\"https://www.gutenberg.org/files/9147/9147-mp3.zip\",\"text/html\":\"https://www.gutenberg.org/files/9147/9147-index.htm\"},\"download_count\":386}]}\n";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // Navegar en el atributo "results" y retornar solo el primer elemento
        JsonNode primerResultado = root.get("results").get(0);

        if (primerResultado == null) {
            System.out.println("Libro no encontrado en Gutendex");
            return;
        }

        // Transformar el string JSON a un objeto
        MapeoJsonLibro datos = conversor.obtenerDatos(primerResultado.toString(), MapeoJsonLibro.class);

        Autor autor = new Autor(datos.autores().get(0));
        Libro libro = new Libro(datos);

        // Verificar si el autor ya existe (busqueda por nombre)
        var autorEncontrado = autorRepository.findByNombre(autor.getNombre());
        if (autorEncontrado.isEmpty()) {
            // Si no exsite, crear el autor en la base de datos
            System.out.println("El autor " + autor.getNombre() + " no existe en la base de datos, creando...");
            Autor autorCreado = autorRepository.save(autor);
            // Asignar este autor creado al libro
            libro.setAutor(autorCreado);
        } else {
            // Si el autor ya existe, asignarselo al libro
            System.out.println("El autor " + autor.getNombre() + " ya existe en la base de datos, asociando al libro descargado...");
            libro.setAutor(autorEncontrado.get());
        }

        if (libroRepository.findById(libro.getId()).isEmpty()) {
            libroRepository.save(libro);
        }

        System.out.println(libro);
    }

    private void buscarLibrosPorTitulo(){
        System.out.println("Escribe el nombre del libro que deseas buscar en la base de datos");
        var nombreLibro = teclado.nextLine();
        Optional<Libro> libroBuscado = libroRepository.findByTituloContainsIgnoreCase(nombreLibro);

        if (libroBuscado.isPresent()){
            System.out.println("El libro buscado es: " + libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado");
        }
    }
    private void listarTodosLosLibros(){
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros aún");
        } else {
            libros.stream().forEach(System.out::println);
        }
    }
    private void listarLibrosPorIdioma(){
        System.out.println("""
        ________________________________________________
        Ingresa el idioma que deseas buscar:
        - es
        - en
        - pt
        ________________________________________________
        """);
        var idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findByIdioma(idioma.toLowerCase());

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con el idioma "+idioma.toLowerCase());
        } else {
            libros.stream().forEach(System.out::println);
        }
    }
    private void listarTodosLosAutores(){
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores aún");
        } else {
            autores.stream().forEach(System.out::println);
        }
    }
    private void listarAutoresVivosEnDeterminadoAnio(){
        System.out.println("Ingresa el año a buscar (ejemplo: '1984'): ");
        var anio = teclado.nextLine();
        List<Autor> autores = autorRepository.autoresVivosEnDeterminadoAnio(Integer.parseInt(anio));

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos durante el año " + anio);
        } else {
            autores.stream().forEach(System.out::println);
        }
    }
}

