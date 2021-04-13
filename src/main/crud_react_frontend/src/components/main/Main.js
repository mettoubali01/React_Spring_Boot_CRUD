import { useContext, useEffect, useState } from 'react';
import {Table, Button, Col, Row} from 'react-bootstrap';
import RemoveModal from '../modals/RemoveModal';
import CreateModal from '../modals/CreateModal';
import EditModal from '../modals/EditModal';
import { UserContext } from '../../conf/UserContext';
import { useHistory } from 'react-router';
import Api from '../../api/Api';
import './main.css';

//in this arrow function we showL
//All movies with the following acctions:
    //add new Movie
    //edit a movie
    //remove movie 
    //copy movie
const Main = (props) => {
    const [movies, setMovies] = useState([]);
    const {logged, credential} = useContext(UserContext);
    const [token, setToken] = credential;
    const api =  new Api(token);
    const [isAuthenticated, setIsAuthenticated] = logged;
    const history = useHistory();
    const [show, setShow] = useState({
        isShown: false,
        modalNumber: 0,
    });
    const [clickedMovie, setClickedMovie] = useState({});

    const retrieveAllMovies = _ => {
        api.getAll()
            .then(response => {
                if(response.status === 200){
                    setMovies(eval(response.data));
                }
            });
    };

    useEffect(_ =>{
        if(!isAuthenticated){
            history.push("/");
        }

        //show all the movies we have benn created
        retrieveAllMovies();
        
    },[]);

    //reset the isShown to false to hide/close the modal
    const closeModal= () =>{
        setShow({
            isShown: false,
            modalNumber: 0 
        });
    }

    //retrieve the remove movie form modal
    const retreiveRemoveModal= (prop)=>{

        return <RemoveModal 
                {...prop}
                currentMovie={clickedMovie}/>
    }

    //retrieve the create movie form modal
    const retreiveCreateModal= (prop)=>{
        return <CreateModal 
                {...prop}/>
    }

    //retrieve the edit movie form modal
    const retreiveEditModal= (prop)=>{
        console.log("Clicked movie ", clickedMovie)
        return <EditModal 
                {...prop}
                currentMovie={clickedMovie}/>
    }

    //this method shows the corresponding modal depende on 
    //the selected button by the admin
    const showOneOfModals = _ =>{
        const {isShown, modalNumber} = show;
        
        const prop = {
            show: isShown,
            handleClose: closeModal,
            action: props.action,
            setMessage: props.setMessage,
            setMessageStyle: props.setMessageStyle,
            fetchMovies: retrieveAllMovies
        };

        switch (modalNumber) {
            case 1:
                return retreiveCreateModal(prop);
            case 2:
                return retreiveEditModal(prop);
            case 3:
                return retreiveRemoveModal(prop);
            default:
                break;
        }
    }

    return(
        <>
        <div className='crud_p_container'>
            <div className="main__1_section">
                <Button className="mr-1" 
                    onClick={_ => {
                        setShow({
                            isShown: true,
                            modalNumber: 1
                        });
                    }} 
                size="sm" variant="success">Create</Button>
            </div>
            <Table striped bordered hover size="sm">
                <thead>
                    <tr>
                    <th>#</th>
                    <th>Movie Title</th>
                    <th>Type</th>
                    <th>Actors</th>
                    <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {movies.map((movie , index) =>{
                        let actorsName = '';
                        return (
                                <tr key={index}>
                                    <td>{movie.id}</td>
                                    <td>{movie.title}</td>
                                    <td>{movie.type}</td>
                                    {movie.actors.map(actor => {
                                        if(movie.actors.length > 1){
                                            actorsName +=  ',' + actor.name
                                        }if(movie.actors.length === 1){
                                            actorsName = actor.name
                                        }
                                    })}
                                    <td>{actorsName.indexOf(',') ? actorsName : actorsName.replace(/^\,/, "")}</td>

                                    <td>
                                        <Row>
                                            <Col xs="auto">
                                                <Button onClick={_ => {
                                                    setClickedMovie({ 
                                                        id: movie.id,
                                                        name: movie.title,
                                                        type: movie.type,
                                                        actors: movie.actors    
                                                    });
                                                    setShow({
                                                        isShown: true,
                                                        modalNumber: 2 
                                                    });
                                                }}
                                                className="mr-1" size="sm" variant="warning">Edit</Button>
                                            </Col>
                                            <Col xs="auto">
                                                <Button onClick={_ => {
                                                    setClickedMovie({ id: movie.id, name: movie.title});
                                                    setShow({
                                                        isShown: true,
                                                        modalNumber: 3 
                                                    });
                                                }}
                                                className="mr-1"
                                                size="sm" 
                                                variant="danger">Remove</Button>
                                            </Col>
                                        </Row>
                                    </td>
                                </tr>
                        )
                    })}
                    
                </tbody>
            </Table>
        </div>
        
        {showOneOfModals()}
        </>
    )
}

export default Main;