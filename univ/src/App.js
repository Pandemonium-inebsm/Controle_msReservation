import React from "react";
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Home from './components/Home';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Reservation from './components/Reservation';
import Utilisateurs from './components/Utilisateur';
import Login from './components/Login';
import Salle from './components/Salle';
import PrivateRoute from './components/PrivateRoute';

const App = () => {
  return (
    <Router>
      <Routes>
      
        <Route path="/" element={<Login />} />
        <Route path="/home" element={<PrivateRoute element= {<Home />}/> }/>
        <Route path="/salle" element={<PrivateRoute element={<Salle />} />} />
        <Route path="/utilisateurs" element={<PrivateRoute element={<Utilisateurs />} />} />
        <Route path="/reservation" element={<PrivateRoute element={<Reservation />} />} />
      </Routes>
    </Router>
  );
};

export default App;
