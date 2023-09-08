import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faVideoSlash } from "@fortawesome/free-solid-svg-icons";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { NavLink } from "react-router-dom";
import {useGlobalContext} from '../../context';
import {useNavigate} from "react-router-dom";
import { getAuthToken, removeAuthToken } from '../../api/axiosConfig';

const Header = () => {

    const {authorized, setAuthorized, openModal, setIsLogin} = useGlobalContext();

    
    const logout = () => {
        setAuthorized(false);
        removeAuthToken();
    }

    const navigate = useNavigate();
    const toHome = () => {
        navigate("/");
    }

    
    if (getAuthToken()) {
        setAuthorized(true);
    }
  

    return (
        <Navbar bg="dark" variant="dark" expand="lg">

            <Container fluid>
                <Navbar.Brand  style={{"color":'gold', 'cursor':'pointer'}} onClick={toHome}>
                    <FontAwesomeIcon icon = { faVideoSlash} />Gold
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarScroll" />
                <Navbar.Collapse id="navbarScroll">

                <Nav
                    className="me-auto mt-2 mt-lg-0"
                    style={{maxHeight: '100px'}}
                    navbarScroll
                >
                    <NavLink className="nav-link" to="/">Home</NavLink>
                    
                     <NavLink className="nav-link" to="/watchlist">Watchlist</NavLink>
                    
                </Nav>
               
    
                { !authorized ? <>    
                    <Button variant="outline-info" className="me-2" 
                        onClick={() => {
                            openModal();
                            setIsLogin(true);
                          }}>
                            Login
                        </Button>
                    <Button variant="outline-info" className="me-2" onClick={() => {
                            openModal();
                            setIsLogin(false);
                          }} >Register</Button>
                    </>
                    :
                    <Button variant="outline-info" className="me-2" onClick={logout}>Logout</Button>
                    
                }

                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;