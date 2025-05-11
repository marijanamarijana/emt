import {useEffect, useState} from "react";
import authorRepository from "../repository/authorRepository.js";
import bookRepository from "../repository/bookRepository.js";

const useBooksDetails = (id) => {
    const [state, setState] = useState({
        "book": null,
        "author": null,
    });

    useEffect(() => {
        bookRepository
            .findById(id)
            .then((response) => {
                setState(prevState => ({...prevState, "book": response.data}));
                authorRepository
                    .findById(response.data.authorId)
                    .then((response) => {
                        setState(prevState => ({...prevState, "author": response.data}));
                    })
                    .catch((error) => console.log(error));

            })
            .catch((error) => console.log(error));
    }, [id]);

    return state;
};

export default useBooksDetails;
