import {useCallback, useEffect, useState} from "react";
import bookRepository from "../repository/bookRepository.js"
const initialState = {
    "books": [],
    "loading": true,
};
const useBooks = () => {
    const [state, setState] = useState(initialState);

    const fetchAuthors = useCallback(() => {
        bookRepository
            .findAll()
            .then((response) => {
                console.log(response.data)
                setState({
                    "books": response.data,
                    "loading": false,
                });
            })
            .catch((error) => console.log(error));
    }, []);

    const onAdd = useCallback((data) => {
        bookRepository
            .add(data)
            .then(() => {
                console.log("Successfully added a new book.");
                fetchAuthors();
            })
            .catch((error) => console.log(error));
    }, [fetchAuthors]);

    const onEdit = useCallback((id, data) => {
        bookRepository
            .edit(id, data)
            .then(() => {
                console.log(`Successfully edited the book with ID ${id}.`);
                fetchAuthors();
            })
            .catch((error) => console.log(error));
    }, [fetchAuthors]);

    const onDelete = useCallback((id) => {
        bookRepository
            .delete(id)
            .then(() => {
                console.log(`Successfully deleted the book with ID ${id}.`);
                fetchAuthors();
            })
            .catch((error) => console.log(error));
    }, [fetchAuthors]);

    useEffect(() => {
        fetchAuthors();
    }, [fetchAuthors]);

    return {...state, onAdd: onAdd, onEdit: onEdit, onDelete: onDelete};
};
export default useBooks;
