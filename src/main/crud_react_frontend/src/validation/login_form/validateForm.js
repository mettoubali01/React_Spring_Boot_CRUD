function validateForm(name, value){
    let errors = {};

    if (name === "username"){
        if(value.trim().length < 4)
            errors.username = "Your username must be at least 4 characters long."
    }
    if(name === "password") {
        if(value.trim().length === 0)
            errors.password = "Your password must be at least more than one characters long."
    }
    console.log("Erros " , errors);

    return errors;

}

export default validateForm;