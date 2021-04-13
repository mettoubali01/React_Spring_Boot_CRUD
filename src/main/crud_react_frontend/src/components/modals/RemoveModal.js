import {Modal, Button} from 'react-bootstrap';
import {useContext} from 'react';
import {UserContext} from '../../conf/UserContext';
import Api from '../../api/Api';

const RemoveModal = props => {
    const {currentMovie, show, handleClose, action, setMessage, setMessageStyle, fetchMovies} = props;
    const { credential} = useContext(UserContext);
    const [token] = credential;
    const api = new Api(token);
    const successMessage = "The movie is deleted succesufully";
    const successMessageStyle = "success";
    const failedMessageStyle = "danger";
    const failedMessage = "The movie couldn't be deleted";

    //delete the movie by his id
    const deleteMovie = (id) =>{

        api.delete(id)
            .then(response => {
                if (response.status === 200) {
                    setMessage(successMessage);
                    setMessageStyle(successMessageStyle);

                    //show movies to see refressh the main movies list
                    fetchMovies();
                }
            })
            .catch(err => {
                console.log("Error in removing the movie " ,err)
                setMessage(failedMessage);
                setMessageStyle(failedMessageStyle);
    
            });

        action(true);
    }

    return(
      <> 
        <Modal
            show={show}
            onHide={_ => {
                handleClose()
            }}
        >
            <Modal.Header closeButton>
                <Modal.Title>Remove movie</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                Are you sure you want to remove the movie with the name <b>{currentMovie.name}</b>?.
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={_ => {handleClose()}}>
                    Close
                </Button>
                <Button variant="danger" onClick={_ => {
                    if(show){
                        deleteMovie(currentMovie.id);  
                        handleClose();

                    }
                }}>
                    Delete
                </Button>
            </Modal.Footer>
        </Modal>
    </>
    )
}

export default RemoveModal;