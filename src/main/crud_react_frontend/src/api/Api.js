import axios from 'axios';

class Api {

  
  headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  };

  BASE_URL = '/api/movie';
  
  constructor(authToken) {
    this.authToken = authToken;
  }
    
  
  createHeaders() {
    return this.authToken ? {
      ...this.headers,
      'Authorization': 'Bearer ' + this.authToken
    } : this.headers;
  }

  async getAll() {
    return axios({
      method: 'GET',
      url:`${this.BASE_URL}/all`,
      headers: this.createHeaders()            
    }); 
  }
  
  async getById(id) {
    return await fetch(`${this.BASE_URL}/${id}`, {
      method: 'GET',
      headers: this.createHeaders()
    });
  }
  
  async delete(id) {
    return axios({
      method: 'DELETE',
      url: `${this.BASE_URL}/remove/${id}`,
      headers: this.createHeaders()
    });
  }
  
  async update(item, id) {
    return axios({
      method: 'PUT',
      url: `${this.BASE_URL}/update/${id}`,
      headers: this.createHeaders(),
      data: JSON.stringify(item)
    });
  }
  
  async create(item, username) {
    return axios({
      method: 'POST',
      url: `/api/movie/add/${username}`,
      headers: this.createHeaders(),
      data: JSON.stringify(item)
    });
  }

  async login(credentials){
    return axios({
      method: 'post',
      url: "/login",
      headers: this.headers,
      data : JSON.stringify(credentials)
    });
  }
}
  
export default Api;