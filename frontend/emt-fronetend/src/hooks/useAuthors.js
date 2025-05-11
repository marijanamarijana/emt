import {useCallback, useEffect, useState} from "react";
import authorsRepository from "../repository/authorRepository.js";

const initialState = {
    "authors": [],
    "loading": true,
};

const useAuthors = () => {
    const [state, setState] = useState(initialState);

    const fetchAuthors = useCallback(() => {
        authorsRepository
            .findAll()
            .then((response) => {
                console.log(response.data)
                setState({
                    "authors": response.data,
                    "loading": false,
                });
            })
            .catch((error) => console.log(error));
    }, []);

    const onAdd = useCallback((data) => {
        authorsRepository
            .add(data)
            .then(() => {
                console.log("Successfully added a new author.");
                fetchAuthors();
            })
            .catch((error) => console.log(error));
    }, [fetchAuthors]);

    const onEdit = useCallback((id, data) => {
        authorsRepository
            .edit(id, data)
            .then(() => {
                console.log(`Successfully edited the author with ID ${id}.`);
                fetchAuthors();
            })
            .catch((error) => console.log(error));
    }, [fetchAuthors]);

    const onDelete = useCallback((id) => {
        authorsRepository
            .delete(id)
            .then(() => {
                console.log(`Successfully deleted the author with ID ${id}.`);
                fetchAuthors();
            })
            .catch((error) => console.log(error));
    }, [fetchAuthors]);

    useEffect(() => {
        fetchAuthors();
    }, [fetchAuthors]);

    return {...state, onAdd: onAdd, onEdit: onEdit, onDelete: onDelete};
};
export default useAuthors;
