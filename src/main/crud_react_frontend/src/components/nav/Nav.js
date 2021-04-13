import { Navbar } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import './nav.css';
import { useContext } from "react";
import { UserContext } from "../../conf/UserContext";
import { useHistory } from "react-router";

const Nav = _ => {
    const {logged} = useContext(UserContext);
    const [authenticated, setIsAuthenticated] = logged;
    const history = useHistory();

    const handleLogout = _ => {
        setIsAuthenticated(false);

        history.push("/");
    }

    return (
        <>
            <Navbar bg="primary" variant="dark">
                <Navbar.Brand href="#home">Home</Navbar.Brand>
                {authenticated && <Navbar.Collapse className="justify-content-end">
                    <Navbar.Text>
                    <a href="#login" onClick={handleLogout}>Logout</a>
                    </Navbar.Text>
              </Navbar.Collapse>}
            </Navbar>    
        </>
    )
}

export default Nav;