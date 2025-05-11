import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import BooksGrid from "../../components/books/BooksGrid/BooksGrid.jsx";
import AddBookDialog from "../../components/books/AddBookDialog/AddBookDialog.jsx";
import "./BooksPage.css";
import useBooks from "../../../hooks/useBooks.js";
const BooksPage = () => {
    const {books, loading, onAdd, onEdit, onDelete} = useBooks();
    const [addBookDialogOpen, setAddBokDialogOpen] = useState(false);
    return (
        <>
            <Box className="book-box">
                {loading && (
                    <Box className="progress-box">
                        <CircularProgress/>
                    </Box>
                )}
                {!loading &&
                    <>
                        <Box sx={{display: "flex", justifyContent: "flex-end", mb: 2}}>
                            <Button variant="contained" color="primary" onClick={() => setAddBokDialogOpen(true)}>
                                Add Book
                            </Button>
                        </Box>
                        <BooksGrid books={books} onEdit={onEdit} onDelete={onDelete}/>
                    </>}
            </Box>
            <AddBookDialog
                open={addBookDialogOpen}
                onClose={() => setAddBokDialogOpen(false)}
                onAdd={onAdd}
            />
        </>
    );
};
export default BooksPage;