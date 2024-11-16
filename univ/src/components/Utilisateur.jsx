import React, { useState, useEffect } from "react";
import { Table, Button, Modal, Form } from "react-bootstrap";
import { FaEdit, FaTrashAlt, FaPlus } from "react-icons/fa"; 

import axios_client from "../config/host-app";

const Utilisateur = () => {
  const [utilisateurs, setUtilisateurs] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [modalData, setModalData] = useState({
    nom: "",
    email: "",
    id: "",
  });
  const [editMode, setEditMode] = useState(false);

  // Fonction pour récupérer la liste des utilisateurs
  const fetchUtilisateurs = async () => {
    try {
      const response = await axios_client.get("/api/utilisateur/utilisateurs");
      setUtilisateurs(response.data);
    } catch (err) {
      console.error("Error fetching utilisateurs:", err);
    }
  };

  // Fonction pour ajouter ou modifier un utilisateur
  const saveUtilisateur = async () => {
    try {
      if (editMode) {
        await axios_client.put(`/api/utilisateur/${modalData.id}`, modalData);
      } else {
        await axios_client.post("/api/utilisateur/add", modalData);
      }
      setShowModal(false);
      fetchUtilisateurs();  // Rafraîchir la liste des utilisateurs
    } catch (err) {
      console.error("Error saving utilisateur:", err);
    }
  };

  // Fonction pour supprimer un utilisateur
  const deleteUtilisateur = async (id) => {
    try {
      await axios_client.delete(`/api/utilisateur/${id}`);
      fetchUtilisateurs();  // Rafraîchir la liste après la suppression
    } catch (err) {
      console.error("Error deleting utilisateur:", err);
    }
  };

  // Fonction pour ouvrir le modal en mode ajout ou modification
  const handleShowModal = (utilisateur = null) => {
    setEditMode(!!utilisateur);
    setModalData(
      utilisateur || {
        nom: "",
        email: "",
        id: "",
      }
    );
    setShowModal(true);
  };

  const handleCloseModal = () => setShowModal(false);

  // Appeler la fonction fetchUtilisateurs à chaque chargement de la page
  useEffect(() => {
    fetchUtilisateurs();
  }, []);

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Liste des Utilisateurs</h2>
        <Button variant="primary" onClick={() => handleShowModal()}>
          <FaPlus /> Ajouter Utilisateur
        </Button>
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Nom</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {utilisateurs.map((utilisateur) => (
            <tr key={utilisateur.id}>
              <td>{utilisateur.nom}</td>
              <td>{utilisateur.email}</td>
              <td>
                <Button
                  variant="success"
                  size="sm"
                  onClick={() => handleShowModal(utilisateur)}
                >
                  <FaEdit /> Modifier
                </Button>{" "}
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => deleteUtilisateur(utilisateur.id)}
                >
                  <FaTrashAlt /> Supprimer
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      {/* Modal pour Ajouter/Modifier Utilisateur */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>
            {editMode ? "Modifier l'Utilisateur" : "Ajouter un Utilisateur"}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Nom</Form.Label>
              <Form.Control
                type="text"
                value={modalData.nom}
                onChange={(e) => setModalData({ ...modalData, nom: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                value={modalData.email}
                onChange={(e) => setModalData({ ...modalData, email: e.target.value })}
              />
            </Form.Group>
            {/* Vous pouvez ajouter d'autres champs ici */}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Annuler
          </Button>
          <Button variant="primary" onClick={saveUtilisateur}>
            {editMode ? "Modifier" : "Ajouter"}
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default Utilisateur;
