import React from 'react';
import "./searchResults.css";


const SearchResults = ({searchResults, addMovie}) => {

    return (
        <div className='results-list mt-2'>
            {
                searchResults.map((movie,id) => {
                    return (
                        <div key={id} className='result-item px-2 py-1'>
                            <div className='result-title' onClick={() => addMovie(movie)}>{movie.title}</div>
                        </div>
                )}
            )}
        </div>
    );
};

export default SearchResults;