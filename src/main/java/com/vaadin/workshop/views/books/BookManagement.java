package com.vaadin.workshop.views.books;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;


@PageTitle("Book Management")
@AnonymousAllowed
@Route(value = "books")
@Menu(title = "Book Management", order= 6, icon = LineAwesomeIconUrl.BOOK_SOLID)
public class BookManagement extends VerticalLayout {

    public BookManagement() {
        // Title
        Span title = new Span("My Books");
        title.getStyle().set("font-size", "24px");
        add(title);
        HorizontalLayout horizontallayout = new HorizontalLayout();
        // ComboBoxes
        ComboBox<String> authorComboBox = new ComboBox<>();
        authorComboBox.setLabel("Author");
        authorComboBox.setItems("Author 1", "Author 2", "Author 3");
        ComboBox<String> titleComboBox = new ComboBox<>();
        titleComboBox.setLabel("Title");
        titleComboBox.setItems("Title 1", "Title 2", "Title 3");
        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.setLabel("Genre");
        genreComboBox.setItems("Genre 1", "Genre 2", "Genre 3");
        horizontallayout.add(titleComboBox, genreComboBox, authorComboBox);
        add(horizontallayout);
        // Grid
        Grid<Book> grid = new Grid<>(Book.class);
        grid.setItems(
                new Book("J.R.R. Tolkien", "The Hobbit", "Fantasy"),
                new Book("George Orwell", "1984", "Dystopian"),
                new Book("Harper Lee", "To Kill a Mockingbird", "Fiction"),
                new Book("Aldous Huxley", "Brave New World", "Science Fiction"),
                new Book("Herman Melville", "Moby Dick", "Adventure")
        );
        add(grid);
    }

    public record Book(String author, String title, String genre) {
    }
}
