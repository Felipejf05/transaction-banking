import React from 'react';
import {Link} from 'react-router-dom';
import './card.css';
import sanduiche from '../../assets/sanduiche.jpg'
import sushi from '../../assets/receita-de-sushi.jpg'
import pizza from '../../assets/uma-fatia-de-pizza-crocante-com-carne-e-queijo.jpg'
import hamburguer from '../../assets/hamburguer-de-vista-frontal-em-um-carrinho.jpg'

import api from '../../services/api'

export default function Card(){
    return (
     <div className="card-container">
        <ul>
            <li>
            <h1>Cardápio</h1>
            <Link className="button" to="new/recipe">Add nova receita</Link>
            
            <img src={sanduiche} alt="Felipe img"/>
            <strong>Sanduiche</strong>
            <p>Valor: 20.0</p>
            <img src={sushi} alt="Felipe img"/>
            <strong>Sushi</strong>
            <p>Valor: 30.0</p>
            <img src={pizza} alt="Felipe img"/> 
            <strong>Pizza</strong>
            <p>Valor: 50.0</p>
            <img src={hamburguer} alt="Felipe img"/>
            <strong>Hamburguer</strong>
            <p>Valor: 25.0</p>
            </li>
        </ul>
     </div>
    )
}