function validateForm(values){
    let errors = {};

    if(!values.title){
        errors.title = "The movie title required."

    } else if(values.title.length <= 4){
        errors.title = "The movie must be at least 4 characters long."
    }

   if(values.actors.length <= 4){
        errors.actors = "Your have to fill up the field with actors."
    }

    return errors;
}

export default validateForm;