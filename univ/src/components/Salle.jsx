import React, { useEffect, useState } from "react";
import axios_client from "../config/host-app";
import { Table, Button, Modal, Form } from "react-bootstrap";
import { FaEdit, FaTrashAlt, FaPlus } from "react-icons/fa";
import Swal from "sweetalert2"; // Bibliothèque pour les alertes personnalisées
import Layout from "./Layout";

function Salle() {
  const [listSalle, setListSalle] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedSalle, setSelectedSalle] = useState(null);
  const [newSalle, setNewSalle] = useState({ nom: "", capacite: "", equipement: "" });
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
  
  const getAllSalles = async () => {
    axios_client
      .get("/api/salle/salles",config)
      .then((rep) => {
        setListSalle(rep.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const saveSalle = async () => {
    if (selectedSalle) {
      axios_client
        .put(`/api/salle/${selectedSalle.id}`, newSalle,config)
        .then(() => {
          getAllSalles();
          handleCloseModal();
        })
        .catch((err) => console.log(err));
    } else {
      axios_client
        .post("/api/salle/add", newSalle,config)
        .then(() => {
          getAllSalles();
          handleCloseModal();
        })
        .catch((err) => console.log(err));
    }
  };

  const confirmDeleteSalle = (id) => {
    Swal.fire({
      title: "Êtes-vous sûr(e) ?",
      text: "Cette action supprimera définitivement la salle.",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Oui, supprimer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.isConfirmed) {
        deleteSalle(id);
      }
    });
  };

  const deleteSalle = async (id) => {
    axios_client
      .delete(`/api/salle/${id}`,config)
      .then(() => {
        getAllSalles();
      })
      .catch((err) => console.log(err));
  };

  const handleShowModal = (salle = null) => {
    setSelectedSalle(salle);
    setNewSalle(salle || { nom: "", capacite: "", equipement: "" });
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedSalle(null);
  };

  useEffect(() => {
    getAllSalles();
  }, []);

  return (
    <Layout>
      <div className="container mt-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h2>Liste des Salles</h2>
          <Button variant="primary" onClick={() => handleShowModal()}>
            <FaPlus /> Ajouter Salle
          </Button>
        </div>

        <Table striped bordered hover responsive className="text-center">
          <thead className="table-primary">
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
                    onClick={() => confirmDeleteSalle(salle.id)}
                  >
                    <FaTrashAlt /> Supprimer
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>

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
    </Layout>
  );
}

export default Salle;
