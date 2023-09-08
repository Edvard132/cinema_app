import './App.css';
import api from './api/axiosConfig';
import { useState, useEffect, useMemo } from 'react';
import Layout from './components/Layout';
import {Routes, Route} from 'react-router-dom';
import Home from './components/home/Home';
import Header from './components/header/Header';
import Trailer from './components/trailer/Trailer';
import Reviews from './components/reviews/Reviews';
import Login from './components/authorization/Login';
import AuthorizationModal from './components/authorization/AuthorizationModal';
import Watchlist from './components/watchlist/Watchlist';

function App() {

  const [movies, setMovies] = useState();
  const [movie, setMovie] = useState();
  const [reviews, setReviews] = useState([]);

  const getMovies = async () => {

    try{
      const response = await api.get(`/api/v1/movies/all`);

      setMovies(response.data);
    }
    catch(err){
      console.log(err);
    }
  }

  const getMovieData = async (movieId) => {

    try
    {
      const response = await api.get(`/api/v1/movies/${movieId}`);
      const singleMovie = response.data;
      console.log(singleMovie);

      setMovie(singleMovie);

      setReviews(singleMovie.reviewIds);
    }

    catch(err){
      console.log(err);
    }
  }

  useEffect(() => {
    getMovies();
  }, [])


  return (
    <div className="App">
      <AuthorizationModal/>
      <Header/>
      <Routes>
        <Route path="*" element={<Layout/>}></Route>
        <Route exact path="/" element={<Home movies={movies} />}></Route>
        <Route path="/api/v1/user/login" element={<Login />}></Route>
        <Route path="/watchlist" element={<Watchlist />}></Route>
        <Route path="/trailer/:yTrailerId" element={<Trailer/>}></Route>
        <Route path = "/Reviews/:movieId" element = {<Reviews getMovieData={getMovieData} movie={movie} reviews={reviews} setReviews={setReviews} />}></Route>
      </Routes>


    </div>
  );
}

export default App;
