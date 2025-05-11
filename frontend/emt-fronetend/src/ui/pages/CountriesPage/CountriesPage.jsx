import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import "./CountriesPage.css";
import useCountries from "../../../hooks/useCountries.js";
import CountryGrid from "../../components/countries/CountryGrid/CountryGrid.jsx";
import AddCountryDialog from "../../components/countries/AddCountryDialog/AddCountryDialog.jsx";

const CountriesPage = () =>  {
    const {countries, loading, onAdd, onEdit, onDelete} = useCountries();
    const [addCountryDialogOpen, setAddCountriesDialogOpen] = useState(false);

    return (
        <>
            <Box className="countries-box">
                {loading && (
                    <Box className="progress-box">
                        <CircularProgress/>
                    </Box>
                )}
                {!loading &&
                    <>
                        <Box sx={{display: "flex", justifyContent: "flex-end", mb: 2}}>
                            <Button variant="contained" color="primary" onClick={() => setAddCountriesDialogOpen(true)}>
                                Add Country
                            </Button>
                        </Box>
                        <CountryGrid countries={countries} onEdit={onEdit} onDelete={onDelete}/>
                    </>}
            </Box>
            <AddCountryDialog
                open={addCountryDialogOpen}
                onClose={() => setAddCountriesDialogOpen(false)}
                onAdd={onAdd}
            />
        </>
    );
};

export default CountriesPage;
