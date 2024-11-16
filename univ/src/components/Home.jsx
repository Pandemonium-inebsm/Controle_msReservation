import React from "react";
import { Navbar, Nav, Container, Button, Row, Col, Card } from "react-bootstrap";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <>
      {/* Navbar */}
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand as={Link} to="/">Réservation de salles de réunion</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          {/* <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/salle">Salle</Nav.Link> 
              <Nav.Link as={Link} to="/utilisateurs">Utilisateurs</Nav.Link>
              <Nav.Link as={Link} to="/reservation">Réservation</Nav.Link>
            </Nav>
          </Navbar.Collapse> */}
        </Container>
      </Navbar>

      {/* Hero Section */}
      <div className="hero-section bg-primary text-white text-center py-5">
        <Container>
          <h1>Bienvenue dans le système de réservation de salles</h1>
          <p>Réservez facilement des salles de réunion pour vos événements professionnels</p>
          <Link to="/reservation">
            <Button variant="light" size="lg">
              Réserver une Salle
            </Button>
          </Link>
        </Container>
      </div>

      {/* Feature Section */}
      <Container className="my-5">
        <Row>
          <Col md={4}>
            <Card>
              <Card.Body>
                <Card.Title>Salles</Card.Title>
                <Card.Text>
                  Gérez et réservez vos salles de réunion avec une interface simple et intuitive.
                </Card.Text>
                <Link to="/salle">
                  <Button variant="primary">Voir les Salles</Button>
                </Link>
              </Card.Body>
            </Card>
          </Col>

          <Col md={4}>
            <Card>
              <Card.Body>
                <Card.Title>Utilisateurs</Card.Title>
                <Card.Text>
                  Gérez les utilisateurs de votre organisation et assignez des réservations.
                </Card.Text>
                <Link to="/utilisateurs">
                  <Button variant="primary">Voir les Utilisateurs</Button>
                </Link>
              </Card.Body>
            </Card>
          </Col>

          <Col md={4}>
            <Card>
              <Card.Body>
                <Card.Title>Réservations</Card.Title>
                <Card.Text>
                  Consultez et modifiez les réservations des salles pour une gestion efficace.
                </Card.Text>
                <Link to="/reservation">
                  <Button variant="primary">Voir les Réservations</Button>
                </Link>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default Home;
