import React, { useEffect, useState } from "react";
import axios_client from "../config/host-app";
import { Table, Button, Modal, Form } from "react-bootstrap";
import { FaEdit, FaTrashAlt, FaPlus } from "react-icons/fa"; 

function Salle() {
  const [listSalle, setListSalle] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedSalle, setSelectedSalle] = useState(null);
  const [newSalle, setNewSalle] = useState({ nom: "", capacite: "", equipement: "" });

  // Fetch all salles
  const getAllSalles = async () => {
    axios_client
      .get("/api/salle/salles")
      .then((rep) => {
        setListSalle(rep.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // Add or Update Salle
  const saveSalle = async () => {
    if (selectedSalle) {
      // Update existing salle
      axios_client
        .put(`/api/salle/${selectedSalle.id}`, newSalle)
        .then(() => {
          getAllSalles();
          handleCloseModal();
        })
        .catch((err) => console.log(err));
    } else {
      // Add new salle
      axios_client
        .post("/api/salle/add", newSalle)
        .then(() => {
          getAllSalles();
          handleCloseModal();
        })
        .catch((err) => console.log(err));
    }
  };

  // Delete Salle
  const deleteSalle = async (id) => {
    axios_client
      .delete(`/api/salle/${id}`)
      .then(() => {
        getAllSalles();
      })
      .catch((err) => console.log(err));
  };

  // Handle modal visibility
  const handleShowModal = (salle = null) => {
    setSelectedSalle(salle);
    setNewSalle(salle || { nom: "", capacite: "", equipement: "" });
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedSalle(null);
  };

  // Fetch salles on component mount
  useEffect(() => {
    getAllSalles();
  }, []);

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Liste des Salles</h2>
        <Button variant="primary" onClick={() => handleShowModal()}>
        <FaPlus /> Ajouter Salle
        </Button>
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Nom</th>
            <th>Capacité</th>
            <th>Équipement</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {listSalle.map((salle) => (
            <tr key={salle.id}>
              <td>{salle.nom}</td>
              <td>{salle.capacite}</td>
              <td>{salle.equipement}</td>
              <td>
                <Button
                  variant="success"
                  className="me-2"
                  onClick={() => handleShowModal(salle)}
                >
                  <FaEdit /> Modifier
                  
                </Button>
                <Button
                  variant="danger"
                  onClick={() => deleteSalle(salle.id)}
                >
                   <FaTrashAlt /> Supprimer
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      {/* Modal for Add/Edit Salle */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>{selectedSalle ? "Modifier Salle" : "Ajouter Salle"}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Nom</Form.Label>
              <Form.Control
                type="text"
                value={newSalle.nom}
                onChange={(e) => setNewSalle({ ...newSalle, nom: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Capacité</Form.Label>
              <Form.Control
                type="number"
                value={newSalle.capacite}
                onChange={(e) => setNewSalle({ ...newSalle, capacite: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Équipement</Form.Label>
              <Form.Control
                type="text"
                value={newSalle.equipement}
                onChange={(e) => setNewSalle({ ...newSalle, equipement: e.target.value })}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Annuler
          </Button>
          <Button variant="primary" onClick={saveSalle}>
            {selectedSalle ? "Modifier" : "Ajouter"}
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default Salle;
