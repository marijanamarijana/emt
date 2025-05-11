import {useEffect, useState} from "react";
import countryRepository from "../repository/countryRepository.js";
import authorRepository from "../repository/authorRepository.js";

const useAuthorDetails = (id) => {
    const [state, setState] = useState({
        "author": null,
        "country": null,
    });

    useEffect(() => {
        authorRepository
            .findById(id)
            .then((response) => {
                setState(prevState => ({...prevState, "author": response.data}));
                countryRepository
                    .findById(response.data.countryId)
                    .then((response) => {
                        setState(prevState => ({...prevState, "country": response.data}));
                    })
                    .catch((error) => console.log(error));

            })
            .catch((error) => console.log(error));
    }, [id]);

    return state;
};

export default useAuthorDetails;
