import React, {useState} from 'react';
import {useNavigate, Link} from 'react-router-dom';
import{FiArrowLeft} from 'react-icons/fi'
import './styles.css';


import api from '../../services/api'

export default function NewRecipe(){

    const [id, setId] = useState(null);
    const [title, setTitle] = useState('');
    const [img, setImg] = useState('');
    const [price, setPrice] = useState('');

    const navigate = useNavigate();
    async function createNewRecipe(e){
        e.preventDefault();

        const data = {
            title, 
            img,
            price,
        }

        try {
            await api.post('food', data);
            navigate('/');
        } catch (error) {
            alert('Error while recording Recipe! Try again!')
        }
    }
    return(
        <div className="new-recipe-container">
            <div className="content">
                <section className="form">
                    <h1>Add new Recipe</h1>
                    <p>Enter the Recipe information and click on 'Add'!</p>
                </section>
                <form onSubmit={createNewRecipe}>
                    <input placeholder="Title"
                        value={title}
                        onChange={e => setTitle(e.target.value)}
                        />
                    <input placeholder="Image"
                        value={img}
                        onChange={e => setImg(e.target.value)}
                        />
                    <input placeholder="Price"
                        value={price}
                        onChange={e => setPrice(e.target.value)}
                        />

                    <button className="button" type="submit">Add</button>
                    <Link className="back-link" to="/">
                        <FiArrowLeft size = {16} color="#251fc5"/>
                         Home
                    </Link>
                </form>
            </div>
        </div>
    )
}