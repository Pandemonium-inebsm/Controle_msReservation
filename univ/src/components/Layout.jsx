import React from "react";
import { Navbar, Nav, Container, Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import { logout } from "./auth"; 

const Layout = ({ children }) => {
  return (
    <>
      <Navbar bg="light" expand="lg" style={{ boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)" }}>
        <Container>
          <Navbar.Brand as={Link} to="/home" style={{ fontWeight: "bold", color: "#007bff" }}>
            Réservation de salles de réunion
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/salle" >
                Salle
              </Nav.Link>
              <Nav.Link as={Link} to="/utilisateurs" >
                Utilisateurs
              </Nav.Link>
              <Nav.Link as={Link} to="/reservation" >
                Réservation
              </Nav.Link>
            </Nav>
            <Button
              variant="outline-primary"
              onClick={logout}
              style={{
                fontWeight: "bold",
                borderRadius: "20px",
                padding: "5px 15px",
              }}
            >
              Déconnexion
            </Button>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <main style={{ padding: "20px", backgroundColor: "#f8f9fa" }}>{children}</main>
    </>
  );
};

export default Layout;
