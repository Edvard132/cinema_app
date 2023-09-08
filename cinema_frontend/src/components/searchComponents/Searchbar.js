import React from 'react';
import { FaSearch } from 'react-icons/fa';
import "./searchbar.css"
import api from '../../api/axiosConfig';


const Searchbar = ({setSearchResults, input, setInput}) => {

    const fetchMovies = async () => {
        try{
            const response = await api.get(`/api/v1/movies/all?filterValue=${input}`);
            
            console.log(response.data);
            setSearchResults(response.data);
          }
          catch(err){
            console.log(err);
          }
        
    }
    const handleInputChange = (filter) => {
        setInput(filter);
        fetchMovies(input); //
    }

    return (
        <div className='input-wrapper'>
            <FaSearch className='search-icon mx-1' />
            <input type='text' className='text' placeholder='Search for a movie...'
             value={input}
             onChange={(e) => handleInputChange(e.target.value)} />
        </div>
    );
};

export default Searchbar;