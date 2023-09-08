import React from 'react';
import {useGlobalContext} from '../../context';
import {Container, Row, Col} from'react-bootstrap';
import api from '../../api/axiosConfig';
import "./watchlist.css";
import Searchbar from '../searchComponents/Searchbar';
import SearchResults from '../searchComponents/SearchResults';
import e from 'cors';
import { Link } from "react-router-dom";



const Watchlist = () => {
    const {authorized,  watchListId} = useGlobalContext();
    const [searchResults, setSearchResults] = React.useState([]);
    const [input, setInput] = React.useState('');
    const [searchResultsOpen, setSearchResultsOpen] = React.useState(false);
    const [watchListmovies, setWatchListmovies] = React.useState([]);

    const getMovies = async () => {

        try{
          const response = await api.get("/api/v1/user/watchlist/all",
          {
            params: {
                watchListId: watchListId,
            }
        });
        console.log("response : " + response.data);
          setWatchListmovies(response.data);
        }
        catch(err){
          console.log("ERRROR " + err);
        }
    }

    const addMovie = async (movie) => {
        console.log(movie.imdbId);

        try{
          const response = await api.post("/api/v1/user/watchlist/add",
          {
            watchListId: watchListId,
            imdbId: movie.imdbId,
        });
        console.log("response : " + response.data);
        getMovies();
        }
        catch(err){
          console.log("ERRROR " + err);
        }
    }

    const removeMovie = async (movie) => {
        try{
            const response = await api.post("/api/v1/user/watchlist/delete",
          {
            watchListId: watchListId,
            imdbId: movie.imdbId,
        });
        console.log("response : " + response.data);
        getMovies();
        }
        catch(err){
          console.log("ERRROR " + err);
        }
    }
    

      
    React.useEffect(() => {
        if (authorized) {
        getMovies();
    }
    }, [watchListId])

            
    return (
        <div className='c' onClick={() => setInput('')}>
        {!authorized ? 
            <h1 className='text-info d-flex justify-content-center mt-5'>You are not authorized to view this page</h1>
        :
        <>
        <div className='search-bar-container' onClick={e => e.stopPropagation()}>
            <Searchbar setSearchResults={setSearchResults} input={input} setInput={setInput} />
            {input && 
                <SearchResults searchResults={searchResults} addMovie={addMovie} />
            }
        </div>    
            <div >
                    <Row >
                        <h1 className="text-lg text-center mt-5"> Watchlist</h1>
                        
                    </Row>
                    <div className='d-flex watchlist-container mx-3'>
                        {watchListmovies?.length === 0  && 
                        <h2 className='m-auto mt-5'>Your watchlist is empty</h2> }
                        {watchListmovies?.map((movie, index) => {
                            return (
                                <div key={index} className='movie-cardd'>
                                        <Link to={`/Reviews/${movie.imdbId}`}>
                                            <div className='poster-container'>
                                                <img className='movie-poster' src={movie.poster} alt={movie.title} />
                                            </div>
                                        </Link>
                                        <div className='movie-tittle'>
                                            <h4>{movie.title}</h4>
                                        </div>
                                        <div className='remove_b'>
                                            <button className='btn btn-danger'
                                            onClick={() => removeMovie(movie)}
                                            >Remove</button>
                                        </div>
                                </div>
                            );
                        })}
                    </div>
            </div>
        </>
        }

    </div>
    );
};

export default Watchlist;