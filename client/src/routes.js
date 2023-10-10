import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Card from './pages/Card';
import NewRecipe from './pages/NewRecipe';

export default function AppRoutes(){
    return(
        <BrowserRouter>
        <Routes>
             <Route path="/"  element={<Card/>}/>
             <Route path="/new/recipe" element={<NewRecipe/>}/> 
            </Routes>
        </BrowserRouter>
    );
}
