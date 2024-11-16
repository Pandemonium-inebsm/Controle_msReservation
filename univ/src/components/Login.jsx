import React, { useState } from "react";
import { Button, Form, Container, Alert, Card, Row, Col } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axios_client from "../config/host-app";

const Login = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    nom: "",
    prenom: "",
    role: "USER", 
  });
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState("");
  const [isLogin, setIsLogin] = useState(true); 
  const navigate = useNavigate();

  const toggleAuthMode = () => {
    setIsLogin(!isLogin);
    setError(null);
    setSuccessMessage("");
    setFormData({
      email: "",
      password: "",
      nom: "",
      prenom: "",
      role: "USER",
    });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.email || !formData.password || (!isLogin && (!formData.nom || !formData.prenom || !formData.role))) {
      setError("Veuillez remplir tous les champs obligatoires.");
      return;
    }
    try {
        const endpoint = isLogin ? "/api/auth/authenticate" : "/api/auth/register";
        const response = await axios_client.post(endpoint, {
          email: formData.email,
          password: formData.password,
          ...(isLogin ? {} : { nom: formData.nom, prenom: formData.prenom, role: formData.role }),
        });
      
        if (isLogin) {
        console.log(response.data);
          localStorage.setItem("token", response.data.token);
          setSuccessMessage("Connexion réussie !");
          navigate("/home");
        } else {
          setSuccessMessage("Inscription réussie ! Connectez-vous.");
          toggleAuthMode();
        }
      
        setError(null);
      } catch (err) {
        console.error("Erreur lors de la requête:", err); // Afficher les détails de l'erreur
        setError(err.response ? err.response.data.message : "Une erreur s'est produite. Vérifiez vos informations.");
        setSuccessMessage("");
      }
  };      

  return (
    <Container className="d-flex justify-content-center align-items-center min-vh-100">
      <Card className="shadow-lg" style={{ width: "800px", borderRadius: "20px" }}>
        <Row>
          <Col md={6} className="p-5 bg-primary text-white d-flex flex-column align-items-center justify-content-center">
            <h2>{isLogin ? "Bienvenue !" : "Rejoignez-nous !"}</h2>
            <p>{isLogin ? "Connectez-vous pour accéder à vos réservations." : "Créez un compte pour commencer."}</p>
            <Button variant="outline-light" onClick={toggleAuthMode}>
              {isLogin ? "Pas encore inscrit ? Créez un compte" : "Déjà inscrit ? Connectez-vous"}
            </Button>
          </Col>

          <Col md={6} className="p-5">
            <h3 className="mb-4">{isLogin ? "Connexion" : "Inscription"}</h3>
            {error && <Alert variant="danger">{error}</Alert>}
            {successMessage && <Alert variant="success">{successMessage}</Alert>}

            <Form onSubmit={handleSubmit}>
              {!isLogin && (
                <Form.Group controlId="formNom" className="mb-3">
                  <Form.Label>Nom</Form.Label>
                  <Form.Control
                    type="text"
                    name="nom"
                    placeholder="Entrez votre nom"
                    value={formData.nom}
                    onChange={handleChange}
                  />
                </Form.Group>
              )}
              {!isLogin && (
                <Form.Group controlId="formPrenom" className="mb-3">
                  <Form.Label>Prénom</Form.Label>
                  <Form.Control
                    type="text"
                    name="prenom"
                    placeholder="Entrez votre prénom"
                    value={formData.prenom}
                    onChange={handleChange}
                  />
                </Form.Group>
              )}
              <Form.Group controlId="formEmail" className="mb-3">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  name="email"
                  placeholder="Entrez votre email"
                  value={formData.email}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group controlId="formPassword" className="mb-3">
                <Form.Label>Mot de passe</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  placeholder="Entrez votre mot de passe"
                  value={formData.password}
                  onChange={handleChange}
                />
              </Form.Group>

              {!isLogin && (
                <Form.Group controlId="formRole" className="mb-3">
                  <Form.Label>Rôle</Form.Label>
                  <Form.Select name="role" value={formData.role} onChange={handleChange}>
                    <option value="USER">Utilisateur</option>
                    <option value="ADMIN">Administrateur</option>
                  </Form.Select>
                </Form.Group>
              )}

              <Button variant="primary" type="submit" className="w-100">
                {isLogin ? "Se connecter" : "S'inscrire"}
              </Button>
            </Form>
          </Col>
        </Row>
      </Card>
    </Container>
  );
};

export default Login;
