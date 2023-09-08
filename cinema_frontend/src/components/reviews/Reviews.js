import { useEffect, useRef } from "react";
import api from '../../api/axiosConfig';
import { useParams } from "react-router-dom";
import { Container, Row, Col } from 'react-bootstrap';
import ReviewForm from "../reviewForm/ReviewForm";

import React from 'react';

const Reviews = ({getMovieData, movie, reviews, setReviews}) => {

    const revText = useRef();
    let params = useParams();
    const movieId = params.movieId;

    useEffect(() => {
        getMovieData(movieId);
    }, [])

    const addReview = async (e) => {
        e.preventDefault();
        const rev = revText.current;
        if (rev.value.length < 1) return;
        try 
        {
            const response = await api.post("/api/v1/reviews", {reviewBody: rev.value, imdbId:movieId});

            const updatedReviews = Array.isArray(reviews) ? [...reviews, { body: rev.value }] : [{ body: rev.value }];
            rev.value = "";
            console.log(reviews);
            setReviews(updatedReviews);
        }
        catch (err){
            console.log(err);
        }
    }

    return (
        <Container>
            <Row>
                <Col><h3>Reviews</h3></Col>
            </Row>
                <Row className="mt-2">
                    <Col>
                        <img src={movie?.poster} alt="" />
                    </Col>
                    <Col>
                        {
                            <>
                                <Row>
                                    <Col>
                                        <ReviewForm handleSubmit={addReview} revText={revText} labelText="Write a review..."/>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col>
                                        <hr />
                                    </Col>
                                </Row>
                            </>
                        }
                        <div style={{"overflowY": "auto", "maxHeight" : "500px"}}>
                        {
                            reviews?.map((r, index) => {
                                return (
                                    <React.Fragment key={index} >
                                        <Row>
                                            <Col>{r.body}</Col>
                                        </Row>
                                        <Row>
                                            <Col>
                                            <hr />
                                            </Col>
                                        </Row>
                                    </React.Fragment>
                                )
                            })
                        }
                        </div>
                    </Col>
                </Row>
            
        </Container>
    );
};

export default Reviews;