import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Home from './components/Home';
import { Container } from 'react-bootstrap';
import Salle from './components/Salle';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Reservation from './components/Reservation';
import Utilisateurs from './components/Utilisateur';

function App() {
  return (
    <Router>
    <Home />
    <Routes>
      <Route path="/salle" element={<Salle />} /> 
      <Route path="/utilisateurs" element={<Utilisateurs />} />  
     <Route path="/reservation" element={<Reservation />} />
  
    </Routes>
  </Router>
  );
}

export default App;
